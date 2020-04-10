package com.allen.aplib.http.nohttp

import android.content.Context
import com.allen.aplib.http.ResponseCallBack
import com.yanzhenjie.nohttp.InitializationConfig
import com.yanzhenjie.nohttp.Logger
import com.yanzhenjie.nohttp.NoHttp
import com.yanzhenjie.nohttp.RequestMethod
import com.yanzhenjie.nohttp.download.DownloadListener
import com.yanzhenjie.nohttp.download.DownloadQueue
import com.yanzhenjie.nohttp.download.DownloadRequest
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
    /**
     *  @param T 需要的javabean
     *  @param what 区分下载
     *  @param request 请求
     *  @param listener 回调
     * */
    fun <T> request(
        what: Int,
        request: Request<T>,
        listener: ResponseCallBack<T>
    ) {
        var simple = OnResponseTemp(listener)
        queue?.add(what, request,simple)
    }

    /**
     *
     *  文件下载
     *  @param url 下载地址
     *  @param what 如果有同时又多个网络请求时区分
     *  @param method GET POST  RequestMethod.GET
     *  @param path 文件保存地址，绝对路径
     *  @param filename 文件名
     *  @param range 是否断点续传  服务器支持
     *  @param delOld 是否删除旧同名文件
     *  @param callback 下载回调
     * */
    fun downFile(what:Int,url:String ,method: RequestMethod, path:String,filename:String,range:Boolean,delOld:Boolean,callback:DownloadListener){
       var downloadRequest =  DownloadRequest(url,method,path,filename,range,delOld)
        DLQueue?.add(what,downloadRequest,callback)
    }
    /**
     *  退出时候销毁
     * */
    fun destory(){
        queue?.stop()
        DLQueue?.stop()
    }

}