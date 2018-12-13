package com.liufeismart.helloandroid.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liufeismart.helloandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by humax on 18/12/6
 *
 * 1.scan
 * 2.broadcastreceiver
 * 3.addNetwork,enableNetwork
 */
public class WifiActivity extends Activity {
    private RecyclerView rv;
    private final String TAG = "WifiActivity";

    private WifiManager mWifiManager;
    private WifiActivity.MyAdapter myAdapter;

    private List<ScanResult> data = new ArrayList<>();
    public final String[] capabilities = {"PSK", "WEP", "Open"};

    private ConnectionReceiver connectionReceiver;
    private ScanReceiver mScanReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        rv = (RecyclerView) this.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //
        myAdapter = new WifiActivity.MyAdapter(data);
        rv.setAdapter(myAdapter);
        //
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiManager.startScan();

        registerWifi();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }



    private void registerWifi() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        connectionReceiver = new ConnectionReceiver();
        registerReceiver(connectionReceiver, intentFilter);
        IntentFilter scanFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mScanReceiver = new ScanReceiver();
        registerReceiver(mScanReceiver, scanFilter);
    }


    private void unregisterReceiver() {
        unregisterReceiver(connectionReceiver);
        unregisterReceiver(mScanReceiver);
    }

    class MyAdapter extends RecyclerView.Adapter<WifiActivity.MyViewHolder> {

        private List<ScanResult> data;
        public MyAdapter(List<ScanResult> data) {
            this.data = data;
        }

        @Override
        public WifiActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(WifiActivity.this).inflate(R.layout.item_wifi_info, null);
            WifiActivity.MyViewHolder viewHolder = new WifiActivity.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(WifiActivity.MyViewHolder holder, final int position) {
            final ScanResult result = data.get(position);
            if(result.SSID!=null && !"".equals(result.SSID)) {
                holder.tv_wifi_name.setText(result.SSID);
            } else {
                holder.tv_wifi_name.setText(result.BSSID);
            }



            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    WifiConfiguration conf = new WifiConfiguration();
                    String ssidToConnect = result.SSID.replace("\"", "");
                    conf.SSID = "\"" + ssidToConnect + "\"";
                    Log.v(TAG, ssidToConnect+"  result.capabilities = " + result.capabilities);
                    for(int i=0; i<capabilities.length; i++) {
                        if(result.capabilities.contains(capabilities[i])) {
                            String password = "12345678";
                            switch(i) {
                                case 0: //PSK
                                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

                                    if (password.matches("[0-9A-Fa-f]{64}")) {
                                        conf.preSharedKey = password;
                                    } else {
                                        conf.preSharedKey = '"' + password + '"';
                                    }

                                    break;
                                case 1: //WEP
                                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                                    conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                                    conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                                    if (password.length() != 0) {
                                        int length = password.length();
                                     // WEP-40, WEP-104, and 256-bit WEP (WEP-232?)
                                        if ((length == 10 || length == 26 || length == 58) &&
                                               password.matches("[0-9A-Fa-f]*")) {
                                            conf.wepKeys[0] = password;
                                        } else {
                                            conf.wepKeys[0] = '"' + password + '"';
                                        }
                                    }
                                    break;
                                case 2: //Open
                                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                                    break;
                            }
                            mWifiManager.disconnect();
                            int netIdToConnect = mWifiManager.addNetwork(conf);

                            boolean result = mWifiManager.enableNetwork(netIdToConnect, true);
                            Log.v(TAG, "Connect: "+ssidToConnect+": result = " + result);
                            mWifiManager.reconnect();
                            mWifiManager.saveConfiguration();
                        }
                    }

                }
            });
        }



        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_wifi_name;
        TextView tv_wifi_state;
        View itemView;

        MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_wifi_name = itemView.findViewById(R.id.tv_wifi_name);
            tv_wifi_state = itemView.findViewById(R.id.tv_wifi_state);
        }
    }


    class ConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo mNetworkInfo = (NetworkInfo)(intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO));
                NetworkInfo.State mState = mNetworkInfo.getState();
                if(mState == NetworkInfo.State.CONNECTED) {
                    Log.v(TAG, mNetworkInfo.getExtraInfo()+": "+ "CONNECTED");
                } else if(mState == NetworkInfo.State.CONNECTING) {
                    Log.v(TAG, mNetworkInfo.getExtraInfo()+": "+ "CONNECTING");
                } else if(mState == NetworkInfo.State.DISCONNECTED) {
                    Log.v(TAG, mNetworkInfo.getExtraInfo()+": "+ "DISCONNECTED");
                } else if(mState == NetworkInfo.State.DISCONNECTING) {
                    Log.v(TAG, mNetworkInfo.getExtraInfo()+": "+ "DISCONNECTING");
                } else if(mState == NetworkInfo.State.SUSPENDED) {
                    Log.v(TAG, mNetworkInfo.getExtraInfo()+": "+ "SUSPENDED");
                } else if(mState == NetworkInfo.State.UNKNOWN) {
                    Log.v(TAG, mNetworkInfo.getExtraInfo()+": "+ "UNKNOWN");
                }
//                switch(mState) {
//                    case NetworkInfo.State.CONNECTED:
//                        break;
//                }
            }
        }
    }

    class ScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> list =  mWifiManager.getScanResults();
            data.clear();
            data.addAll(list);
            for(int i=0; i<data.size(); i++) {
                ScanResult result = data.get(i);
//                Log.v(TAG, "result.SSID ="+ result.SSID);
            }
            myAdapter.notifyDataSetChanged();
        }
    }


}



























































              