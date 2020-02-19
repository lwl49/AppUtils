package com.allen.apputils.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Android Studio.
 * User: Allen
 * Date: 2019/4/9
 * Time: 15:43
 */
public class ToastUtils {
    private static Toast mToast = null;

    public static void init(Context context) {
        if(mToast==null){
            mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
    }
    public static void showToast(String text) {
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }
}
