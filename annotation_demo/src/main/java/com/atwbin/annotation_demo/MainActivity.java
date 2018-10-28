package com.atwbin.annotation_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.atwbin.annotation_demo.utils.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv1)
    private TextView tv1;
    @ViewById(R.id.tv2)
    private TextView tv2;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);


        tv1.setText("注解演示1");
        tv2.setText("注解演示2");
    }
}
