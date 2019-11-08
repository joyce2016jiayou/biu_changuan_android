package com.noplugins.keepfit.android.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.widget.Toast

/**
 * 监听网络状态变化
 * Created by Travis on 2017/10/11.
 */

class NetWorkChangReceiver : BroadcastReceiver() {

    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private fun getConnectionType(type: Int): String {
        var connType = ""
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据"
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络"
        }
        return connType
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION == intent.action) {// 监听wifi的打开与关闭，与wifi的连接无关
            val wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0)
            Log.e("TAG", "wifiState:$wifiState")
            when (wifiState) {
                WifiManager.WIFI_STATE_DISABLED -> {
                }
                WifiManager.WIFI_STATE_DISABLING -> {
                }
            }
        }
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val info = intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)

            if (isNetAvailable(context)){
                Log.i("TAG", getConnectionType(info.type) + "连上")
            } else {
                Toast.makeText(context, "网络开小差了，请检查网络是否连接成功", Toast.LENGTH_SHORT)
                        .show()
            }
            //获取联网状态的NetworkInfo对象
//            if (info != null) {
//                //如果当前的网络连接成功并且网络连接可用
//                if (NetworkInfo.State.CONNECTED == info.state && info.isAvailable) {
//                    if (info.type == ConnectivityManager.TYPE_WIFI || info.type == ConnectivityManager.TYPE_MOBILE) {
//
//                    }
//                } else {
//                    //
//
//                }
//            }
        }
    }

    private fun isNetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= 29) {
            val ca = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            ca != null &&
                    ca.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    ca.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            val netInfo = connectivityManager.activeNetworkInfo
            netInfo?.isConnected == true
        }
    }
}