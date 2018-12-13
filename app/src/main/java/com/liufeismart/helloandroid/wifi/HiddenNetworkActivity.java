package com.liufeismart.helloandroid.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liufeismart.helloandroid.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by humax on 18/12/13
 */
public class HiddenNetworkActivity extends Activity {

    static final int SECURITY_NONE = 0;
    static final int SECURITY_WEP = 1;
    static final int SECURITY_PSK = 2;
    static final int SECURITY_EAP = 3;
    public static final int WIFI_PEAP_PHASE2_NONE = 0;
    public static final int WIFI_PEAP_PHASE2_MSCHAPV2 = 1;
    public static final int WIFI_PEAP_PHASE2_GTC = 2;

    private WifiManager wifiManager;
    private WifiConfiguration config;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_network);

        View tv_connect = this.findViewById(R.id.tv_connect);
        tv_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ssid = "rrrr";
                String password = "12345678";
                int security = SECURITY_PSK;
                config = getConfig(ssid, password, security);

                wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                Class cls = null;
                try {
                    cls = Class.forName("android.net.wifi.WifiManager");
                    Class<?> ActionListener = Class.forName("android.net.wifi.WifiManager$ActionListener");//
                    Method setMethod = cls.getDeclaredMethod("connect",WifiConfiguration.class, ActionListener);
                    Object listener = Proxy.newProxyInstance(ActionListener.getClassLoader(),
                            new Class[] { ActionListener }, new ActionListenerImpl(new HiddenNetworkActivity.OnActionListenerCallback() {
                                @Override
                                public void onSuccess() {

                                    wifiManager.disconnect();
                                    int netIdToConnect = wifiManager.addNetwork(config);

                                    boolean result = wifiManager.enableNetwork(netIdToConnect, true);
//                                    Log.v(TAG, "Connect: "+ssidToConnect+": result = " + result);
                                    wifiManager.reconnect();
                                    wifiManager.saveConfiguration();
                                }
                                @Override
                                public void onFailure(int reason) {
                                    Toast.makeText(HiddenNetworkActivity.this, "连接网络失败", Toast.LENGTH_SHORT).show();
                                }
                            }));
                    setMethod.invoke(wifiManager, config, listener);
                    Log.v("wifiDebug", "setMethod.invoke");
                } catch (Exception e) {
                    Log.v("wifiDebug", "Exception: " +e.getMessage());
                    e.printStackTrace();
                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    WifiConfiguration getConfig(String ssid, String password, int security) {

        WifiConfiguration config = new WifiConfiguration();

        config.SSID = "\"" + ssid + "\"";
        config.hiddenSSID = true;
        int length = 0;
        switch (security) {
            case SECURITY_NONE:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                break;
            case SECURITY_WEP:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                length = password.length();
                // WEP-40, WEP-104, and 256-bit WEP (WEP-232?)
                if ((length == 10 || length == 26 || length == 58) &&
                        password.matches("[0-9A-Fa-f]*")) {
                    config.wepKeys[0] = password;
                } else {
                    config.wepKeys[0] = '"' + password + '"';
                }
                break;
            case SECURITY_PSK:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                length = password.length();
                if (password.matches("[0-9A-Fa-f]{64}")) {
                    config.preSharedKey = password;
                } else {
                    config.preSharedKey = '"' + password + '"';
                }
                break;

            case SECURITY_EAP:
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
                config.enterpriseConfig = new WifiEnterpriseConfig();
                int eapMethod = 0;//mEapMethodSpinner.getSelectedItemPosition();
                int phase2Method = 0;//mPhase2Spinner.getSelectedItemPosition();
                config.enterpriseConfig.setEapMethod(eapMethod);
                switch (eapMethod) {
                    case WifiEnterpriseConfig.Eap.PEAP:
                        // PEAP supports limited phase2 values
                        // Map the index from the PHASE2_PEAP_ADAPTER to the one used
                        // by the API which has the full list of PEAP methods.
                        switch (phase2Method) {
                            case WIFI_PEAP_PHASE2_NONE:
                                config.enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.NONE);
                                break;
                            case WIFI_PEAP_PHASE2_MSCHAPV2:
                                config.enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.MSCHAPV2);
                                break;
                            case WIFI_PEAP_PHASE2_GTC:
                                config.enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.GTC);
                                break;
                            default:
                                Log.v("wifiConfig", "Unknown phase2 method" + phase2Method);
                                break;
                        }
                        break;
                    default:
                        // The default index from PHASE2_FULL_ADAPTER maps to the API
                        config.enterpriseConfig.setPhase2Method(phase2Method);
                        break;
                }
                String unspecifiedCert = "(unspecified)";
                String caCert = unspecifiedCert;
                try {
                    Class WifiEnterpriseConfig = Class.forName("android.net.wifi.WifiEnterpriseConfig");
                    Method setCaCertificateAlias = WifiEnterpriseConfig.getDeclaredMethod("setCaCertificateAlias", String.class);
                    setCaCertificateAlias.invoke(config.enterpriseConfig, caCert);
                    String clientCert = unspecifiedCert;
                    if (clientCert.equals(unspecifiedCert)) {
                        clientCert = unspecifiedCert;
                    }

                    Method setClientCertificateAlias = WifiEnterpriseConfig.getDeclaredMethod("setClientCertificateAlias", String.class);
                    setClientCertificateAlias.invoke(config.enterpriseConfig, caCert);
//                      //
                    config.enterpriseConfig.setIdentity("");
                    config.enterpriseConfig.setAnonymousIdentity(
                            "");
                } catch (Exception e) {
//                    LogUtil.logV("wifiDebug", "Exception: " + e.getMessage());
                    e.printStackTrace();
                }

                config.enterpriseConfig.setPassword(password);

                break;
            default:
                return null;
        }
//
        Field[] fs = config.getClass().getDeclaredFields();
        for(int i = 0 ; i < fs.length; i++){
            Field field = fs[i];
            String name = field.getName();
            switch (name) {
                case "proxySettings":
                    try {
                        Class ProxySettings = Class.forName("android.net.wifi.WifiConfiguration.ProxySettings");
                        field.set(config, ProxySettings.getField("NONE").get(null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "ipAssignment":
                    try {
                        Class IpAssignment = Class.forName("android.net.wifi.WifiConfiguration.IpAssignment");
                        field.set(config, IpAssignment.getField("DHCP").get(null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "linkProperties":
                    try {
//                    Class IpAssignment = Class.forName("android.net.wifi.WifiConfiguration.IpAssignment");
//                    field.set(config, new LinkProperties());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
        return config;
    }


    public interface OnActionListenerCallback {
        void onSuccess();
        void onFailure(int reason);
    }


    public class ActionListenerImpl implements InvocationHandler {

        private OnActionListenerCallback mCallback;

        public ActionListenerImpl(OnActionListenerCallback callback) {
            mCallback = callback;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            try {
                Log.v("wifiDebug", method.getName());

                if("onFailure".equals(method.getName())) {

                    int reason = (Integer) args[0];
                    Log.v("wifiDebug", "连接失败 reason = " + reason);
                    mCallback.onFailure(reason);
                }
                else if ("onSuccess".equals(method.getName())) {
                    Log.v("wifiDebug", "连接成功");
                    mCallback.onSuccess();
                }

            } catch (Exception e) {
                Log.v("wifiDebug", e.toString());
            }

            return proxy;
        }

    }

}
