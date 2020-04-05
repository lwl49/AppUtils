package com.allen.apputils

import android.app.Application
import com.allen.aplib.http.nohttp.NohttpUtils

/**
 * @user Allen
 * @date 2020/4/5 0005
 * @purpose
 */
class MyApp :Application(){
    override fun onCreate() {
        super.onCreate()
        NohttpUtils.init(applicationContext)
    }
}