package com.liufeismart.helloandroid;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.liufeismart.helloandroid.R;
import com.liufeismart.helloandroid.activity.DialogActivity;
import com.liufeismart.helloandroid.activity.HelloTestActivity;

public class HelloTestActivityTest2 extends ActivityInstrumentationTestCase2<HelloTestActivity> {
    private final String TAG = "HelloTestActivityTest2";
    public HelloTestActivityTest2() {
        super(HelloTestActivity.class);
    }

    private HelloTestActivity activity;
    private DialogActivity.HelloTestActivity2 activity2;
    private TextView tv_test1;
    private String test_text = "hello test";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.v(TAG, Thread.currentThread().getName());
        Intent intent = new Intent();
        intent.setClassName("com.liufeismart.helloandroid", HelloTestActivity.class.getName());
        activity = (HelloTestActivity) getInstrumentation().startActivitySync(intent);
        //
        tv_test1 = (TextView)activity.findViewById(R.id.tv_test1);
//        getInstrumentation().addM
                Button btn;
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @MediumTest
    public void testJumpToActivity() throws Throwable {
        Instrumentation.ActivityMonitor monitor = getInstrumentation()
                .addMonitor(DialogActivity.HelloTestActivity2.class.getName(),null, false);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                String content = tv_test1.getText().toString();
                if(content.equals("HelloTestActivity")) {
                    tv_test1.performClick();
                }
            }
        });
        activity2 = (DialogActivity.HelloTestActivity2)getInstrumentation().waitForMonitor(monitor);
        assertTrue(activity2!=null);

    }

}

