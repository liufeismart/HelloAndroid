package com.liufeismart.helloandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.liufeismart.helloandroid.R;

import java.util.Locale;

/**
 * Created by humax on 18/7/5
 */

public class LanguageSettingsActiivty extends Activity {

    private TextView tv_chinese;
    private TextView tv_english;

    private final String LANGUAGE = "language";
    private final String COUNTRY = "country";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);
        tv_chinese = this.findViewById(R.id.tv_chinese);
        tv_english = this.findViewById(R.id.tv_english);
        tv_chinese.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeAppLanguage(LanguageSettingsActiivty.this, Locale.SIMPLIFIED_CHINESE, true);
            }
        });
        tv_english.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeAppLanguage(LanguageSettingsActiivty.this, Locale.ENGLISH, true);
            }
        });
    }


    public void changeAppLanguage(Context context, Locale locale,

                                         boolean persistence) {

        Resources resources = context.getResources();

        DisplayMetrics metrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            configuration.setLocale(locale);

        } else {

            configuration.locale = locale;

        }

        resources.updateConfiguration(configuration, metrics);

        if (persistence) {

            saveLanguageSetting(context, locale);

        }

    }



    private void saveLanguageSetting(Context context, Locale locale) {

        String name = context.getPackageName() + "_" + LANGUAGE;

        SharedPreferences preferences =

                context.getSharedPreferences(name, Context.MODE_PRIVATE);

        preferences.edit().putString(LANGUAGE, locale.getLanguage()).apply();

        preferences.edit().putString(COUNTRY, locale.getCountry()).apply();

    }
}
