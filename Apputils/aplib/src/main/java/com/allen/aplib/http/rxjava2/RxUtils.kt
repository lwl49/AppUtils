package com.allen.aplib.http.rxjava2

import com.google.gson.JsonParseException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


/**
 * @user Allen
 * @date 2020/4/12 0012 21:40
 * @purpose
 */
class RxUtils {
    init {
        init()
        initRetrofit()
    }

    companion object {
        private var ins: RxUtils? = null
        fun getIns(): RxUtils {
            if (ins == null) {
                ins = RxUtils()
            }
            return ins as RxUtils
        }
    }


    var mRetrofit: Retrofit? = null
    var mOkHttpClient: OkHttpClient? = null

    fun init() {
        mOkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response? {
                        val request: Request = chain.request()

                        //在这里获取到request后就可以做任何事情了
                        val response: Response = chain.proceed(request)
                        return response
                    }
                }
            ).build()
        initRetrofit()
    }


    fun initRetrofit(): Retrofit {
        mRetrofit = Retrofit.Builder()
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(RxUrl.baseUrl)
            .build()
        return mRetrofit as Retrofit;
    }

    /**
     *  获取不同的接口
     * */
    fun <T> getService(classz: Class<T>?): T {
        return mRetrofit!!.create(classz)
    }

    //订阅事件
    fun <T> setSubscriber(
        observable: Observable<T>,
        subscriber: Observer<T>?
    ) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)
    }

    fun <T> doPost(observable: Observable<T>) {
        var dd: Disposable? = null
        setSubscriber(observable, object : Observer<T> {
            override fun onSubscribe(d: Disposable?) {
                dd = d;
            }

            override fun onNext(t: T) {

            }

            override fun onError(error: Throwable) {

            }

            override fun onComplete() {
                //    Logger.d("request", "读取完成");
                if(!dd!!.isDisposed){
                    dd?.dispose()
                }

            }
        })
    }

}