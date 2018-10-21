package com.atwbin.dev.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.wangcaigu.encryption.jni.AESCryptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author：Created by Wbin on 2018/10/21
 *
 * Description：参数签名
 */
public class ParamsSignUtil {

    /** 秘钥 */
    private static final String SECRET = "W3c79GH";

    /** hmacsha1算法 */
    private static final String HMAC_SHA1 = "HmacSHA1";


    /**
     * 生成参数签名
     */
    public static String createSignture(String params) {
        if (TextUtils.isEmpty(params)) {
            return "";
        }

        TreeMap<String, String> treeMap = getParamsMap(params);
        String jointParams = jointParams(treeMap);
        try {
            String result = URLDecoder.decode(jointParams, "utf-8");
            return signture(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 签名
     *
     * @param resource 需要签名的字符串
     * @return
     */
    private static String signture(String resource) {
        try {
            SecretKeySpec sha1Key = new SecretKeySpec(createSecret().getBytes(), HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(sha1Key);
            byte[] sigBytes = mac.doFinal(resource.getBytes());
            // base 64 encode the binary signature
            // Base64 is JDK 1.8 only - older versions may need to use Apache Commons or similar.
//            byte[] base4sign = Base64.encode(sigBytes, Base64.NO_WRAP);
            String str = new String(sigBytes, "UTF-8");
            LoggerUtils.d("origin str = " + str);
            String hexStr = EncodeUtils.bytesToHex(sigBytes);
            byte[] base4sign = Base64.encode(hexStr.getBytes(), Base64.NO_WRAP);
            LoggerUtils.d("params encode base64 = " + new String(base4sign));
            return new String(base4sign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建签名
     *
     * @return
     */
    private static String createSecret() {
        byte[] bytes = AESCryptor.crypt(SECRET.getBytes(), System.currentTimeMillis(), 0);
        String secret = Base64.encodeToString(bytes, Base64.NO_WRAP);
        LoggerUtils.d("secret=" + secret);
        return secret;
    }

    /**
     * 拼接参数名和参数值
     *
     * @param map TreeMap集合
     * @return
     */
    private static String jointParams(TreeMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append(map.get(key));
            LoggerUtils.d("joint params" + sb.toString().trim());
        }
        return sb.toString();
    }

    /**
     * 获取参数map
     *
     * @param params {@code a=apple&a=apricot}
     * @return true  false
     */
    private static TreeMap<String, String> getParamsMap(String params) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String[] split = params.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            if (split1.length == 2) {
                map.put(split1[0], split1[2]);
            }
        }
        return new TreeMap<>(map);
    }
}
