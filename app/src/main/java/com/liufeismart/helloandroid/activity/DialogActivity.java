package com.liufeismart.helloandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liufeismart.helloandroid.R;

/**
 * Created by humax on 18/7/27
 */
public class DialogActivity extends Activity {


    public static class HelloTestActivity2 extends Activity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activty_test2);
        }
    }
}
