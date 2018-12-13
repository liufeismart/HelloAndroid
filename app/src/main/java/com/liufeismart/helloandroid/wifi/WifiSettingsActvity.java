package com.liufeismart.helloandroid.wifi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.liufeismart.helloandroid.R;

/**
 * Created by humax on 18/12/7
 * 打开系统Wifi界面的四种方式
 */
public class WifiSettingsActvity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_settings);
        View tv_settings = this.findViewById(R.id.tv_settings);
        tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一种
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                startActivity(intent);
                //第二种
//                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                //第三种
//                Intent i = new Intent();
//                if(android.os.Build.VERSION.SDK_INT >= 11){
//                    //Honeycomb
//                    i.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
//                }else{
//                    //other versions
//                    i.setClassName("com.android.settings"
//                            , "com.android.settings.wifi.WifiSettings");
//                }
//                startActivity(i);
                //第四种
//                Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
//                startActivity(wifiSettingsIntent);

            }
        });
    }
}
