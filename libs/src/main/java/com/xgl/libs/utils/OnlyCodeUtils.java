package com.xgl.libs.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class OnlyCodeUtils {
    private String MacAddress = "";
    private int ipAddress = 0;

    public OnlyCodeUtils(Context context) {
        try {
            // 获取wifi服务
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            // 判断wifi是否开启
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ipAddress = wifiInfo.getIpAddress();
            MacAddress = wifiInfo.getMacAddress();

        } catch (Exception e1) {

            ipAddress = 0;
            MacAddress = "";
        }
    }

    public String getMacAddress() {
        return MacAddress;
    }

}
