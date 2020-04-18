package com.allen.aplib.http.rxjava2

import com.allen.aplib.bean.BaseBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @user Allen
 * @date 2020/4/10 0010 16:15
 * @purpose rx url
 */
interface AppService {
    @FormUrlEncoded
    @POST(UrlTest.userInfo)
    fun getMainData(): Observable<BaseBean>
}