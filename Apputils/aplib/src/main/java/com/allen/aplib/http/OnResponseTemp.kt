package com.allen.aplib.http

import com.yanzhenjie.nohttp.rest.Response
import com.yanzhenjie.nohttp.rest.SimpleResponseListener

/**
 * @user Allen
 * @date 2020/4/6 0006 上午 12:33
 * @purpose
 */
class OnResponseTemp<T>(listener: ResponseCallBack<T>) : SimpleResponseListener<T>() {
    private var listener1: ResponseCallBack<T> = listener

    override fun onStart(what: Int) {
        super.onStart(what)
        listener1.onStart(what)
    }

    override fun onSucceed(what: Int, response: Response<T>?) {
        super.onSucceed(what, response)
        listener1.onSucceed(what, response)
    }

    override fun onFailed(what: Int, response: Response<T>?) {
        super.onFailed(what, response)
        listener1.onFailed(what, response)

    }

    override fun onFinish(what: Int) {
        super.onFinish(what)
        listener1.onFinish(what)
    }
}