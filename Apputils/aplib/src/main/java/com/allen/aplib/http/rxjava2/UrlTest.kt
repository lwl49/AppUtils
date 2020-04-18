package com.allen.aplib.http.rxjava2

/**
 * @user Allen
 * @date 2020/4/17 0017 11:17
 * @purpose
 */
class UrlTest : RxUrl() {


    fun setUUU() {
        baseUrl = "https://www.baidu.com/"
    }

    companion object {
        const val userInfo: String = "userInfo"
    }

}