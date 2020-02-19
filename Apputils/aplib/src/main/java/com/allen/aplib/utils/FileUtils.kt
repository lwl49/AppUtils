package com.allen.aplib.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.BaseColumns
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.allen.aplib.bean.AlbumBean
import com.allen.aplib.bean.VideoInfo
import com.allen.apputils.utils.FileComparator
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DecimalFormat
import java.util.*



object FileUtils {
    /**
     * 应用根目录
     */
    val rootDir: String
        get() {
            val path = Environment.getExternalStorageDirectory().toString() + "/DCIM/FeiyuCam"
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            return path
        }

    /**
     * 图片文件夹
     */
    val imageDir: String
        get() {
            val path = "$rootDir/image"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 日志文件夹
     */
    val logDir: String
        get() {
            val path = "$rootDir/log/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 视频文件夹
     */
    val videoDir: String
        get() {
            val path = "$rootDir/video/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * pdf 说明书文件夹
     */
    val pdfDir: String
        get() {
            val path = "$rootDir/pdf"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * crash 文件夹
     */
    val crashDir: String
        get() {
            val path = "$rootDir/crash/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }


    var upgradeFileName = "SPHOST.BRN"

    /**
     * 下载文件夹
     */
    val downloadDir: String
        get() {
            val path = "$rootDir/download/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 视频编辑文件夹
     */
    val videoEditDir: String
        get() {
            val path = "$rootDir/videoedit"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 头像保存
     */
    val headerDir: String
        get() {
            val path = "$rootDir/header"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 全景文件夹
     */
    val panoDir: String
        get() {
            val path = "$rootDir/pano/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 全景临时文件夹
     */
    val panoTempDir: String
        get() {
            val path = "$rootDir/panoTemp/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 普通全景临时文件夹
     */
    val panoOrdinaryTempDir: String
        get() {
            val path = panoTempDir + "ordinaryTemp/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 4全景临时文件夹
     */
    val panoForeTempDir: String
        get() {
            val path = panoTempDir + "foreTemp/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 9全景临时文件夹
     */
    val panoNineTempDir: String
        get() {
            val path = panoTempDir + "nineTemp/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    /**
     * 全景临时文件夹Test
     */
    val panoTempTestDir: String
        get() {
            val path = "$rootDir/panoTempTest/"
            val file = File(path)
            if (!file.exists()) {
                file.mkdir()
            }
            return path
        }

    private val VIDEO_PROJECT = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DATE_MODIFIED,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.DATE_TAKEN
    )

    var headerName = System.currentTimeMillis().toString() + ".png"

    /**
     * 应用data/packname/files
     */
    fun getAppFileDir(context: Context): String {
        //        String path = Environment.getExternalStorageDirectory().toString() + "/DCIM/FeiyuCam";
        return context.getExternalFilesDir(null)!!.absolutePath + "/"
    }

    /**
     * log日志
     */
    fun getAppFileLogDir(context: Context): String {
        //        String path = Environment.getExternalStorageDirectory().toString() + "/DCIM/FeiyuCam";
        val path = getAppFileDir(context) + "/log/"
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        return path
    }

    /**
     * 固件下载地址
     */
    fun getAppFileDownDir(context: Context): String {
        //        String path = Environment.getExternalStorageDirectory().toString() + "/DCIM/FeiyuCam";
        val path = getAppFileDir(context) + "/download/"
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        return path
    }

    // 获取当前目录下所有的mp4,mov文件
    fun getAlbumList(fileAbsolutePath: String): ArrayList<AlbumBean> {
        val vecFile = ArrayList<AlbumBean>()
        val file = File(fileAbsolutePath)
        val subFile = file.listFiles()

        for (i in subFile.indices) {
            // 判断是否为文件夹
            if (!subFile[i].isDirectory) {
                val filename = subFile[i].name
                // 判断是否为MP4结尾
                if (filename.trim { it <= ' ' }.toLowerCase().endsWith(".mp4") || filename.trim { it <= ' ' }.toLowerCase().endsWith(
                        ".mov"
                    )
                ) {
                    val bean = AlbumBean()
                    bean.filename = filename
                    bean.path = subFile[i].absolutePath
                    bean.size = subFile[i].length()
                    bean.type = 0
                    vecFile.add(bean)
                }
            }
        }
        return vecFile
    }

    // 获取当前目录下所有的文件
    fun getDirList(fileAbsolutePath: String): List<File>? {
        val fileList = ArrayList<File>()
        val file = File(fileAbsolutePath)
        if (file.exists() && file.isDirectory) {
            val files = file.listFiles()
            for (i in files.indices) {
                fileList.add(files[i])
            }
            Collections.sort(fileList, FileComparator())
            for (f in fileList) {
                LoggerUtils.e("aaa", "fff=" + f.absolutePath)
            }
            return fileList
        }
        return null
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private fun FormetFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        var fileSizeString = ""
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        if (fileS < 1024) {
            fileSizeString = df.format(fileS.toDouble()) + "B"
        } else if (fileS < 1048576) {
            fileSizeString = df.format(fileS.toDouble() / 1024) + "KB"
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(fileS.toDouble() / 1048576) + "MB"
        } else {
            fileSizeString = df.format(fileS.toDouble() / 1073741824) + "GB"
        }
        return fileSizeString
    }

    /**
     *  获取视频信息
     * */
    fun getVideoInfo(context: Context, path: String): VideoInfo {
        var info: VideoInfo? = null
        val selection = MediaStore.Video.Media.DATA + " = ?"
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            selection,
            arrayOf(path),
            null
        )
        //        LoggerUtils.e("xxx", "cursor.getCount() = " + cursor.getCount());
        if (cursor != null) {
            val idindex = cursor.getColumnIndex(BaseColumns._ID)
            val modifiedindex = cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED)
            val durationindex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)
            val dataindex = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
            val takenindex = cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN)
            val widthindex = cursor.getColumnIndex(MediaStore.Video.Media.WIDTH)
            val heightindex = cursor.getColumnIndex(MediaStore.Video.Media.HEIGHT)
            val titleindex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
            val sizeindex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                info = VideoInfo()
                info!!.id = cursor.getInt(idindex)
                info!!.name = cursor.getString(titleindex)
                info!!.path = cursor.getString(dataindex)
                val tt = cursor.getLong(modifiedindex)
                info!!.dateModified = TimeFormate.getCurrentDate(tt)
                val cc = cursor.getLong(takenindex)
                info!!.dateTaken = TimeFormate.getCurrentDate(cc)
                val dd = cursor.getInt(durationindex) / 1000
                info!!.duration = TimeFormate.secondsToMinuteOrHours(dd)
                info!!.width = cursor.getInt(widthindex)
                info!!.height = cursor.getInt(heightindex)
                info!!.size = TimeFormate.ByteConversionGBMBKB(cursor.getInt(sizeindex))
            }
        }
        return info!!
    }

    /**
     * mimeTypes 为null，根据后罪名判断类型
     */
    fun putFileIntoSysAlbum(context: Context, path: String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(path), null, null
        )
    }

    fun putVideoIntoSysAlbum(context: Context, path: String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(path),
            arrayOf("video/*"), null
        )
    }

    /**
     * 刷新图库
     */
    fun putImagIntoSysAlbum(context: Context, path: String) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(path),
            arrayOf("image/*"), null
        )
    }

    fun fileExists(path: String): Boolean {
        return File(path).exists()
    }

    /**
     * 通过URI 启动裁剪ACT
     */
    fun cropAndThumbnail(context: Fragment, mImageUri: Uri, code: Int) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(mImageUri, "image/*")//设置要缩放的图片Uri和类型
        intent.putExtra("aspectX", 3)//宽度比
        intent.putExtra("aspectY", 4)//高度比
        intent.putExtra("outputX", 240)//输出图片的宽度
        intent.putExtra("outputY", 320)//输出图片的高度
        intent.putExtra("scale", true)//缩放
        intent.putExtra("return-data", true)//当为true的时候就返回缩略图，false就不返回，需要通过Uri
        intent.putExtra("noFaceDetection", false)//前置摄像头
        //        cropUri= Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory() + "/DCIM/FeiyuCam" + "/" + "myHeader_crop.jpg")
        val str = "file:///$headerDir/$headerName"
        val cropUri = Uri.parse(str)
        LoggerUtils.e("xxx", "cropUri====cropUri = $cropUri")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivityForResult(intent, code)//打开剪裁Activity
    }


    /**
     * 根据图片路径转bitmap
     *
     * @param path 图片路径
     * @param w    宽
     * @param h    高
     */
    fun toBitmap(path: String, w: Int, h: Int): Bitmap? {
        try {
            val opts = BitmapFactory.Options()
            opts.inJustDecodeBounds = true
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888
            BitmapFactory.decodeFile(path, opts)
            val width = opts.outWidth
            val height = opts.outHeight
            var scaleWidth = 0f
            var scaleHeight = 0f
            if (width > w || height > h) {
                // 缩放
                scaleWidth = width * 1f / w
                scaleHeight = height * 1f / h
            }
            opts.inJustDecodeBounds = false
            val scale = Math.max(scaleWidth, scaleHeight)
            opts.inSampleSize = scale.toInt()
            return BitmapFactory.decodeFile(path, opts)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *
     * */
    fun bitmap2Bytes(bm: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }


    fun RGB888ToRGB565(rgb8888: Int): Int {
        return rgb8888 shr 19 and 31 shl 11 or (rgb8888 shr 10 and 63 shl 5) or (rgb8888 shr 3 and 31)
    }

    fun ARGBTOGRB(img_in: Bitmap): ByteArray {
        val im_w = img_in.width
        val im_h = img_in.height
        val img = ByteArray(im_w * im_h * 3)
        LoggerUtils.e("1111", "get img")
        //图片转数组
        val argb1 = IntArray(im_w * im_h)
        img_in.getPixels(argb1, 0, im_w, 0, 0, im_w, im_h)
        for (i in argb1.indices) {
            val `val` = argb1[i]
            //            byte testt = (byte)((val >> 24) & 0xFF); //A通道
            img[i * 3] = (`val` shr 16 and 0xFF).toByte()
            img[i * 3 + 1] = (`val` shr 8 and 0xFF).toByte()
            img[i * 3 + 2] = (`val` and 0xFF).toByte()
        }
        return img
    }

    /**
     * 通过反射获取私有的成员变量
     *
     * @param obj  需要反射的实体，
     * @param fieldName  需要
     * @return
     */
    private fun getPrivateValue(obj: Object, fieldName: String): Any? {

        try {
            val field = obj.`class`.getDeclaredField(fieldName)
            // 参数值为true，打开禁用访问控制检查
            //setAccessible(true) 并不是将方法的访问权限改成了public，而是取消java的权限控制检查。
            //所以即使是public方法，其accessible 属相默认也是false
            field.isAccessible = true
            return field.get(obj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     *  通过ID 获取文件
     * */
    fun getRealPath(id: String): String {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(id).build()
            .toString()
    }

    /**
     *  数据库对应字段
     * */
    private var mediaColumns = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.TITLE,
        MediaStore.Video.Media.MIME_TYPE,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.DATE_ADDED,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.WIDTH,
        MediaStore.Video.Media.HEIGHT
    )

    /**
     *  获取视频列表
     * */
    fun getVideoInfoList(context: Context): List<VideoInfo> {
        var list: List<VideoInfo>? = null
        var mContentResolver = context.contentResolver
        val mCursor = mContentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            mediaColumns,
            null,
            null,
            MediaStore.Video.Media.DATE_ADDED
        )
            ?: return emptyList()
        var ixData = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
        var ixMime = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)
        // ID 是在 Android Q 上读取文件的关键字段
        var ixId = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        var ixSize = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
        var ixTitle = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
        var ixWidth = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH)
        var ixHeight = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT)
        while (mCursor.moveToNext()) {
            var info = VideoInfo()
            var path = getRealPath(ixId as String)
            info.path = path
        }
        return list!!
    }
}
