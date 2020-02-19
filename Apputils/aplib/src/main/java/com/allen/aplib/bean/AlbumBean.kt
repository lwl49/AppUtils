package com.allen.aplib.bean

import java.io.Serializable

/**
 * @author Allen
 * @date 2019/9/25 16:37
 * @describe
 */
class AlbumBean : Serializable {
    var filename: String? = null
    var path: String? = null
    var size: Long = 0
    var type: Int = 0 //0  视频  1  图片
    var isSelect: Boolean = false //是否选中，用于删除
}
