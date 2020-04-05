package com.allen.aplib.http.nohttp

import android.content.Context
import com.allen.aplib.http.ResponseCallBack
import com.yanzhenjie.nohttp.InitializationConfig
import com.yanzhenjie.nohttp.Logger
import com.yanzhenjie.nohttp.NoHttp
import com.yanzhenjie.nohttp.download.DownloadQueue
import com.yanzhenjie.nohttp.rest.Request
import com.yanzhenjie.nohttp.rest.RequestQueue


/**
 * @user Allen
 * @date 2020/4/5 0005
 * @purpose
 */
class NohttpUtils {
    var queue: RequestQueue? = null
    var DLQueue: DownloadQueue? = null
    init {
        queue = NoHttp.newRequestQueue()
        DLQueue = NoHttp.newDownloadQueue()
    }
    companion object {
        var inst: NohttpUtils? = null

        /**
         *  配置文件默认
         * */
        fun init(ctx: Context): NohttpUtils? {
            if (inst == null) {
                inst = NohttpUtils()
                NoHttp.initialize(ctx)
                return inst
            }
            return inst
        }

        /**
         *  配置文件自定义
         * */
        fun init(config: InitializationConfig): NohttpUtils? {
            if (inst == null) {
                inst = NohttpUtils()
                NoHttp.initialize(config)
                return inst
            }
            return inst
        }
    }

    /**
     * 是否打开调试
     * */
    fun setLogger(flag: Boolean) {
        Logger.setDebug(flag)// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttpLog")// 打印Log的tag。
    }
    //
    fun <T> request(
        what: Int,
        request: Request<T>,
        listener: ResponseCallBack<T>
    ) {
        var simple = OnResponseTemp(listener)
        queue!!.add(what, request,simple)
    }
    fun destory(){
        queue?.stop()
        DLQueue?.stop()
    }

}