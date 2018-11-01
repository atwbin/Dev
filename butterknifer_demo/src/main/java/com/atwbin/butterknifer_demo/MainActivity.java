package com.atwbin.butterknifer_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.atwbin.butterknife.ButterKnife;
import com.atwbin.butterknife.Unbinder;
import com.butterknife_anntations.BindView;


public class MainActivity extends AppCompatActivity {


    Unbinder unbinder;

    @BindView(R.id.tv1)
    TextView tv1;

    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        //防止内存泄漏
        unbinder.unbind();

        super.onDestroy();
    }
}
