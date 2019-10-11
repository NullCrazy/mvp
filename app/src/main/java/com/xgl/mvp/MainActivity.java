package com.xgl.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.reactivex.schedulers.Schedulers;

/**
 * @author xingguolei
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Schedulers.io();
        findViewById(R.id.title_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
