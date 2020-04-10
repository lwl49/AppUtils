package com.allen.aplib.http

import com.yanzhenjie.nohttp.rest.Response

/**
 * @user Allen
 * @date 2020/4/6 0006 上午 12:18
 * @purpose  网络请求回调
 */
interface ResponseCallBack<T> {
    fun onStart(what: Int)

    fun onSucceed(
        what: Int,
        response: Response<T>
    )

    fun onFailed(what: Int, response: Response<T>)

    fun onFinish(what: Int)
}