package com.allen.apputils.utils;

import android.util.Log;

/**
 * Created by Android Studio.
 * User: Allen
 * Date: 2019/4/9
 * Time: 11:37
 */
public class LoggerUtils {
    static boolean DEBUG = true;
    static String TAG = LoggerUtils.class.getClass().getName();

    public static void setDEBUG(boolean DEBUG) {
        LoggerUtils.DEBUG = DEBUG;
    }

    public static void v(String msg){
        if(DEBUG){
            Log.v(TAG,msg);
        }
    }
    public static void d(String msg){
        if(DEBUG){
            Log.d(TAG,msg);
        }
    }
    public static void i(String msg){
        if(DEBUG){
            Log.i(TAG,msg);
        }
    }
    public static void e(String msg){
        if(DEBUG){
            Log.e(TAG,msg);
        }
    }



    public static void v(String tag,String msg){
        if(DEBUG){
            Log.v(tag,msg);
        }
    }
    public static void d(String tag,String msg){
        if(DEBUG){
            Log.d(tag,msg);
        }
    }
    public static void i(String tag,String msg){
        if(DEBUG){
            Log.i(tag,msg);
        }
    }
    public static void e(String tag, String msg){
        if(DEBUG){
            Log.e(tag,msg);
        }
    }
}
