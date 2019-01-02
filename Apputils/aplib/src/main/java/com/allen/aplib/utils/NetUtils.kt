package com.allen.apputils.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException


/***
 * create by Allen on 2018/12/29
 */
class NetUtils {
    companion object {
        /**
         * 判断是否有网络连接
         * */
        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context!!
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    mNetworkInfo.type   // 可以得到网络的类型
                    return mNetworkInfo.isAvailable
                }
            }
            return false
        }

        //根据Wifi信息获取本地Mac  需要连接wifi 否则为null
        fun getLocalMacAddressFromWifiInfo(context: Context): String {
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifi.connectionInfo
            return info.macAddress
        }

        //获取本地IP
        fun getLocalIpAddressIPV6(): String? {
            try {
                val en = NetworkInterface
                    .getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf
                        .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
                            return inetAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                Log.e("IpAddress", ex.toString())
            }


            return null
        }

        @Throws(SocketException::class)
        private fun getLocalIPAddressIPV4(): String {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress().toString()
                    }
                }
            }
            return "null"
        }

        /**
         *  无线，有线IP都能获取
         * */
        fun getIpAddress(): String? {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
                            val ipAddress = inetAddress.hostAddress.toString()
                            if (!ipAddress.contains("::")) {  // 出掉IPV6 ， 有":;"的是不是IPV6的地址
                                return ipAddress
                            }

                        }
                    }
                }
            } catch (ex: SocketException) {
                Log.e("IpAddress", ex.toString())
            }

            return null

        }
    }

}