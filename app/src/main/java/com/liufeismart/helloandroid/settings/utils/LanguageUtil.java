package com.liufeismart.helloandroid.settings.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by humax on 18/7/6
 */

public class LanguageUtil {

    private static final String LANGUAGE = "language";
    private static final String COUNTRY = "country";

    /**
     * 应用内的语言切换
     * @param context
     * @param locale
     * @param persistence
     */
    public static void changeAppLanguage(Context context, Locale locale,
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



    private static void saveLanguageSetting(Context context, Locale locale) {
        String name = context.getPackageName() + "_" + LANGUAGE;
        SharedPreferences preferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putString(LANGUAGE, locale.getLanguage()).apply();
        preferences.edit().putString(COUNTRY, locale.getCountry()).apply();
    }


    /**
     *
     * @param key
     * @param defaultValue
     * @return
     * key 利用adb shell getprop
     * 不起作用
     */
    public static String getSystemProp(final String key, final String defaultValue) {
        try {
            Method getStringPropMethod = Class.forName("android.os.SystemProperties")
                    .getMethod("set", String.class, String.class);
            return (String) getStringPropMethod.invoke(null, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }


    public static void setLanguage(Locale locale) {
        try {
            Object objIActMag;
            Class clzIActMag = Class.forName("android.app.IActivityManager");
            Class clzActMagNative = Class
                    .forName("android.app.ActivityManagerNative");
            Method mtdActMagNative$getDefault = clzActMagNative
                    .getDeclaredMethod("getDefault");
            objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
            Method mtdIActMag$getConfiguration = clzIActMag
                    .getDeclaredMethod("getConfiguration");
            Configuration config = (Configuration) mtdIActMag$getConfiguration
                    .invoke(objIActMag);
            config.locale = locale;
            Class clzConfig = Class
                    .forName("android.content.res.Configuration");
            java.lang.reflect.Field userSetLocale = clzConfig
                    .getField("userSetLocale");
            userSetLocale.set(config, true);
//            Class activityRecordClass = Class
//                    .forName("com.android.server.am.ActivityRecord");
            //

            //
//            Class[] clzParams = { Configuration.class, activityRecordClass, boolean.class, boolean.class };
//            Method mtdIActMag$updateConfiguration = clzIActMag
//                    .getDeclaredMethod("updateConfigurationLocked", clzParams);
//            mtdIActMag$updateConfiguration.invoke(objIActMag, config, null, false, false );
//            Method mtdIActMag$updateConfiguration = clzIActMag
//                    .getDeclaredMethod("updateConfigurationLocked");
//            mtdIActMag$updateConfiguration.invoke(objIActMag);
//            BackupManager.dataChanged("com.android.providers.settings");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void isToday() throws Exception{
        int i = 1%0;
    }
}
