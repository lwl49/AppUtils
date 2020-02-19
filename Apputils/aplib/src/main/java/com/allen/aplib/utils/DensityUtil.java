package com.allen.aplib.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil
{
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    /** 
     * 将sp值转换为px值，保证文字大小不变 
     *  
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    }
    public static int width;
    public static int height;
    public static float density;
    public static int densityDpi;
    /**
     * 获取屏幕尺寸
     */
    public static void getScreenSize(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
        density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
    }
    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * (double)density + 0.5D);
    }
}
