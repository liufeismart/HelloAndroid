package com.liufeismart.helloandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.liufeismart.helloandroid.R;
import com.liufeismart.helloandroid.utils.LanguageUtil;

import java.util.Locale;

/**
 * Created by humax on 18/7/5
 */

public class LanguageSettingsActiivty extends Activity {

    private TextView tv_chinese;
    private TextView tv_english;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);
        tv_chinese = this.findViewById(R.id.tv_chinese);
        tv_english = this.findViewById(R.id.tv_english);
        tv_chinese.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LanguageUtil.changeAppLanguage(LanguageSettingsActiivty.this, Locale.SIMPLIFIED_CHINESE, true);
//                LanguageUtil.getSystemProp("persist.sys.language", "zh");
                Locale locale = Locale.CHINESE;
                LanguageUtil.setLanguage(locale);
            }
        });
        tv_english.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LanguageUtil.changeAppLanguage(LanguageSettingsActiivty.this, Locale.ENGLISH, true);
//                LanguageUtil.getSystemProp("persist.sys.language", "en");
//                Locale locale = Locale.ENGLISH;
//                LanguageUtil.setLanguage(locale);
            }
        });
    }



}
