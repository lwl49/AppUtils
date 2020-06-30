@file:Suppress("DEPRECATION")

package com.allen.aplib.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


/***
 * create by Allen on 2018/12/29
 */
@SuppressLint("MissingPermission")
class PhoneUtils(act: Activity) {
    private var instance: PhoneUtils? = null

     var tm: TelephonyManager? = null
     var act: Activity? = null
    init {
        tm = act!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        this.act = act
    }


    fun getInstance(act: Activity): PhoneUtils {
        if (instance == null) {
            instance = PhoneUtils(act)
        } else if (instance!!.act !== act) {
            instance = PhoneUtils(act)
        }
        return instance!!
    }


    /**
     * 检测某程序是否安装
     */
    fun isInstalledApp(context: Context, packageName: String): Boolean {
        var flag: Boolean? = false

        try {
            val pm = context.packageManager
            val pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
            for (pkg in pkgs) {
                // 当找到了名字和该包名相同的时候，返回
                if (pkg.packageName == packageName) {
                    flag = true
                }
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return false
        }

        return flag!!
    }

    /**
     * 安装.apk文件
     *
     * @param context
     */
    fun install(context: Context?, fileName: String) {
        if (TextUtils.isEmpty(fileName) || context == null) {
            return
        }
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = android.content.Intent.ACTION_VIEW
            intent.setDataAndType(Uri.fromFile(File(fileName)), "application/vnd.android.package-archive")
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 安装.apk文件
     *
     * @param context
     */
    fun install(context: Context, file: File) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     *   判断手机是否有该权限，没有就申请
     *  获取手机权限， 申请单个权限
     * */
    fun checkPermission(context: Context,permission:Array<String>,Code : Int):Boolean {
        if (ContextCompat.checkSelfPermission(context,
                permission[0])
            == PackageManager.PERMISSION_GRANTED) {
            return true
        }else{
            ActivityCompat.requestPermissions(context as Activity,permission,Code )
            return false
        }

    }



    /**
     * 获取手机及SIM卡相关信息
     * @param context
     * @return
     */

    fun getPhoneInfo(context: Context): Map<String, String> {
        val map = HashMap<String, String>()
        val tm = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var imei = tm.deviceId
        var imsi = tm.subscriberId
        var phoneMode = android.os.Build.MODEL
        var phoneSDk = android.os.Build.VERSION.RELEASE
        if(imei==null){
            imei = ""
        }
        if(imsi==null){
            imsi = ""
        }
        map["imei"] = imei
        map["imsi"] = imsi
        map["phoneMode"] = "$phoneMode##$phoneSDk"
        map["model"] = phoneMode
        map["sdk"] = phoneSDk
        return map
    }

    /** 是否处于飞行模式  */
    fun isAirModeOpen(): Boolean {
        return Settings.System.getInt(act!!.contentResolver, Settings.System.AIRPLANE_MODE_ON, 0) === 1
    }

    /** 获取手机号码  */
    fun getPhoneNumber(): String? {
        return if (tm == null) null else tm!!.line1Number
    }

    /** 获取手机sim卡的序列号（IMSI）  */
    fun getIMSI(): String? {
        return if (tm == null) null else tm!!.subscriberId
    }

    /** 获取手机IMEI  */
    fun getIMEI(): String? {
        return if (tm == null) null else tm!!.deviceId
    }

    /** 获取手机型号  */
    fun getModel(): String {
        return android.os.Build.MODEL
    }

    /** 获取手机品牌  */
    fun getBrand(): String {
        return android.os.Build.BRAND
    }

    /** 获取手机系统版本  */
    fun getVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /** 获得手机系统总内存  */
    fun getTotalMemory(): String {
        val str1 = "/proc/meminfo"// 系统内存信息文件
        val str2: String
        val arrayOfString: Array<String>
        var initial_memory: Long = 0

        try {
            val localFileReader = FileReader(str1)
            val localBufferedReader = BufferedReader(localFileReader, 8192)
            str2 = localBufferedReader.readLine()// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            initial_memory = (Integer.valueOf(arrayOfString[1]).toInt() * 1024).toLong()// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close()

        } catch (e: IOException) {
        }

        return Formatter.formatFileSize(act, initial_memory)// Byte转换为KB或者MB，内存大小规格化
    }



    /** 获取应用包名  */
    fun getPackageName(): String {
        return act!!.packageName
    }

    /**
     * 获取手机MAC地址 只有手机开启wifi才能获取到mac地址
     */
    fun getMacAddress(): String {
        var result = ""
        val wifiManager = act!!.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        result = wifiInfo.macAddress
        return result
    }

    /**
     * 获取手机CPU信息 //1-cpu型号 //2-cpu频率
     */
    fun getCpuInfo(): Array<String> {
        val str1 = "/proc/cpuinfo"
        var str2 = ""
        val cpuInfo = arrayOf("", "") // 1-cpu型号 //2-cpu频率
        var arrayOfString: Array<String>
        try {
            val fr = FileReader(str1)
            val localBufferedReader = BufferedReader(fr, 8192)
            str2 = localBufferedReader.readLine()
            arrayOfString = str2.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in 2 until arrayOfString.size) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " "
            }
            str2 = localBufferedReader.readLine()
            arrayOfString = str2.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            cpuInfo[1] += arrayOfString[2]
            localBufferedReader.close()
        } catch (e: IOException) {
        }

        return cpuInfo
    }

    /** 获取Application中的meta-data内容  */
    fun getMetaData(name: String): String {
        var result: String? = ""
        try {
            val appInfo = act!!.packageManager.getApplicationInfo(
                getPackageName(),
                PackageManager.GET_META_DATA
            )
            result = appInfo.metaData.getString(name)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result!!
    }
    /**
     * 检测是否安装支付宝
     * @param context
     * @return
     */
    open fun isAliPayInstalled(context: Context): Boolean {
        val uri: Uri = Uri.parse("alipays://platformapi/startApp")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val componentName = intent.resolveActivity(context.packageManager)
        return componentName != null
    }

    /**
     * 检测是否安装微信
     * @param context
     * @return
     */
    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager // 获取packagemanager
        val pinfo =
            packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }
}