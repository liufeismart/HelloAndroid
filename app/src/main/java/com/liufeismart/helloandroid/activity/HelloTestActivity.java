package com.liufeismart.helloandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.liufeismart.helloandroid.R;

public class HelloTestActivity extends Activity {

    private TextView tv_test1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelloTestActivity.this, DialogActivity.HelloTestActivity2.class);
                startActivity(intent);
            }
        });
    }
}
