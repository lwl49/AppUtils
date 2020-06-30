package com.allen.aplib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Android Studio.
 * User: Allen
 * Date: 2019/4/9
 * Time: 15:43
 */
public class ToastUtils {
    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShort(Context context, String msg) {

        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
