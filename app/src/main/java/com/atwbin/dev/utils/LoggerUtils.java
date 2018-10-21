package com.atwbin.dev.utils;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Author：Created by Wbin on 2018/10/19
 *
 * Description：Logger
 */
public class LoggerUtils {
    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void init() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .logStrategy(new LogCatStrategy())
                .methodCount(2)
                .tag("WCG")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    public static void v(String s) {
        if (DEBUG) {
            Logger.v(s);
        }
    }


    public static void v(String tag, String s) {
        if (DEBUG) {
            Logger.t(tag).v(s);
        }
    }


    public static void w(String s) {
        if (DEBUG) {
            LoggerUtils.w(s);
        }
    }

    public static void w(String tag, String s) {
        if (DEBUG) {
            Logger.t(tag).w(s);
        }
    }

    public static void e(String s) {
        if (DEBUG) {
            Logger.e(s);
        }
    }

    public static void e(String tag, String s) {
        if (DEBUG) {
            Logger.t(tag).e(s);
        }
    }

    public static void e(String tag, Throwable t) {
        if (DEBUG) {
            Logger.t(tag).e(t.getMessage());
        }
    }

    public static void e(Throwable t) {
        if (DEBUG) {
            if (t != null) {
                Logger.e(t.getMessage());
            } else {
                Logger.e("");
            }

        }
    }

    public static void d(String s) {
        if (DEBUG) {
            Logger.d(s);
        }
    }


    public static void d(String tag, String s) {
        if (DEBUG) {
            if (s == null) {
                s = "";
            }
            Logger.t(tag).d(s);
        }
    }

    public static void i(String s) {
        if (DEBUG) {
            Logger.i(s);
        }
    }

    public static void i(String tag, String s) {
        if (DEBUG) {
            Logger.t(tag).i(s);
        }
    }

}
