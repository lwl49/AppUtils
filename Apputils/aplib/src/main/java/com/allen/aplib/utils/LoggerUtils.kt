package com.allen.aplib.utils

import android.util.Log

/***
 * create by Allen on 2019/1/2
 */
class LoggerUtils {

    companion object {
        var TAG = "LoggerUtils"
        var DEBBUG = true
        fun e(title:String){
            if(DEBBUG){
                Log.e(TAG,title)
            }
        }
        fun d(title:String){
            if(DEBBUG){
                Log.d(TAG,title)
            }
        }
        fun i(title:String){
            if(DEBBUG){
                Log.i(TAG,title)
            }
        }
        fun v(title:String){
            if(DEBBUG){
                Log.v(TAG,title)
            }
        }
    }
}