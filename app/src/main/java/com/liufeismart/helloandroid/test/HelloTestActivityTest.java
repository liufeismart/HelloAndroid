package com.liufeismart.helloandroid.test;

import android.test.suitebuilder.TestSuiteBuilder;
import android.widget.TextView;

import com.liufeismart.helloandroid.R;
import com.liufeismart.helloandroid.activity.HelloTestActivity;

import junit.framework.Test;
import junit.framework.TestCase;

public class HelloTestActivityTest extends TestCase {
    public static Test suite() {
        return new TestSuiteBuilder(HelloTestActivityTest.class)
                .includeAllPackagesUnderHere().build();
    }

    private HelloTestActivity activity;
    private TextView tv_test1;
    private String test_text = "hello test";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = new HelloTestActivity();
        tv_test1 = activity.findViewById(R.id.tv_test1);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void getText1() {
        String text = tv_test1.getText().toString();
        assertTrue(text.compareToIgnoreCase(test_text) == 0);
    }
}
