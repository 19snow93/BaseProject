package com.cpgc.baseproject.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cpgc.baseproject.R;
import com.cpgc.baseproject.base.activity.SimpleActivity;
import com.cpgc.baseproject.ui.Test.Test1Activity;
import com.cpgc.baseproject.ui.Test.TestActivity;
import com.cpgc.baseproject.utils.SPUtils;
import com.cpgc.baseproject.widget.ToastUtil;

import butterknife.BindView;

public class MainActivity extends SimpleActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TestActivity.getIntent(MainActivity.this));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Test1Activity.getIntent(MainActivity.this));
            }
        });
    }
}
