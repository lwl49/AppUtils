package com.allen.aplib.http.nohttp

import com.google.gson.Gson
import com.yanzhenjie.nohttp.Headers
import com.yanzhenjie.nohttp.RequestMethod
import com.yanzhenjie.nohttp.rest.Request
import com.yanzhenjie.nohttp.rest.StringRequest

/**
 * @user Allen
 * @date 2020/4/8 0008 22:34
 * @purpose  通用javabean request
 */
class ModeJsonRequest<T>(
    url: String?,
    requestMethod: RequestMethod?,
    var clss: Class<T>?
) : Request<T>(url, requestMethod) {

    constructor(url: String?, cls: Class<T>?) : this(
        url,
        RequestMethod.GET,
        cls
    ) {
    }

    @Throws(Exception::class)
    override fun parseResponse(
        responseHeaders: Headers,
        responseBody: ByteArray
    ): T {
        val result = StringRequest.parseResponseString(responseHeaders, responseBody)
        return Gson().fromJson(result, clss)
    }

}