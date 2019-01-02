package com.allen.apputils.utils

import android.app.Activity
import android.content.Context

/***
 * create by Allen on 2018/12/29
 */
class ScreenUtils {
    companion object {
        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         *
         * @return 返回像素值
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         *
         * @return 返回dp值
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /** 获取手机屏幕宽  */
        fun getScreenWidth(context: Activity): Int {
            return context!!.windowManager.defaultDisplay.width
        }

        /** 获取手机屏高宽  */
        fun getScreenHeight(context: Activity): Int {
            return context!!.windowManager.defaultDisplay.height
        }
    }
}