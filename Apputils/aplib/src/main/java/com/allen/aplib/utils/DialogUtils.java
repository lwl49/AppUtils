package com.allen.aplib.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;


/**
 * 对话框工具类
 */
public class DialogUtils {


    private static ProgressDialog defaultProgressDialog;
    private static Dialog customDialog;
    private static final int CANCELABLE_FLAG = 1;
    private static final int CANCELABLE_ONTOUCHOUTSIDE_FLAG = 2;

    private DialogUtils() {
    }

    /**
     * 显示默认的对话框
     *
     * @param message
     */
    public static void showDefaultProgressDialog(Context context, String message) {
        createDefaultProgressDialog(context, message, -1, false, false, -1, -1);
        showDefaultProgressDialog();
    }


    public static void showDefaultProgressDialogBeCancelable(Context context, String message, boolean isCancelable, int style) {

        createDefaultProgressDialog(context, message, -1, false, isCancelable, style, CANCELABLE_FLAG);
        showDefaultProgressDialog();
    }

    /**
     * 隐藏默认的对话框
     */
    public static void dissmissDefaultProgressDialog() {
        if (defaultProgressDialog != null && defaultProgressDialog.isShowing()) {
            defaultProgressDialog.dismiss();
            defaultProgressDialog = null;
        }
    }

    /**
     * 显示自定义布局的dialog
     *
     * @param dialogLayout 显示的布局文件
     * @param style        dialog样式 <!-- 自定义loading dialog --> <style
     *                     name="loading_dialog" parent="android:style/Theme.Dialog">
     *                     <item name="android:windowFrame">@null</item> <item
     *                     name="android:windowNoTitle">true</item> <item
     *                     name="android:windowBackground"
     *                     >@android:color/transparent</item> <item
     *                     name="android:windowIsFloating">true</item> <item
     *                     name="android:windowContentOverlay">@null</item> </style>
     */
    public static void showCustomDialogBeCancelable(Context context, int dialogLayout, int style) {

        createCustomDialog(context, dialogLayout, false, true, style, CANCELABLE_FLAG);
        showCustomDialog();
    }


    /**
     * 显示自定义布局的dialog
     *
     * @param dialogLayout               显示的布局文件
     * @param style                      dialog样式 <!-- 自定义loading dialog --> <style
     *                                   name="loading_dialog" parent="android:style/Theme.Dialog">
     *                                   <item name="android:windowFrame">@null</item> <item
     *                                   name="android:windowNoTitle">true</item> <item
     *                                   name="android:windowBackground"
     *                                   >@android:color/transparent</item> <item
     *                                   name="android:windowIsFloating">true</item> <item
     *                                   name="android:windowContentOverlay">@null</item> </style>
     * @param isCancelableOnTouchOutside 按对话框以外的地方不起作用。按返回键还起作用   true 为可取消， false为不可取消
     */
    public static void showCustomDialogBeTouchOutside(Context context, int dialogLayout, boolean isCancelableOnTouchOutside, int style) {


        createCustomDialog(context, dialogLayout, isCancelableOnTouchOutside, false, style, CANCELABLE_ONTOUCHOUTSIDE_FLAG);
        showCustomDialog();

    }


    /**
     * 显示自定义布局的dialog
     *
     * @param dialogLayout 显示的布局文件
     * @param style        dialog样式 <!-- 自定义loading dialog --> <style
     *                     name="loading_dialog" parent="android:style/Theme.Dialog">
     *                     <item name="android:windowFrame">@null</item> <item
     *                     name="android:windowNoTitle">true</item> <item
     *                     name="android:windowBackground"
     *                     >@android:color/transparent</item> <item
     *                     name="android:windowIsFloating">true</item> <item
     *                     name="android:windowContentOverlay">@null</item> </style>
     * @param isCancelable 触摸弹框外的UI ，是否可取消弹框, true 为可以取消  false 为不可以
     */
    public static void showCustomDialogBeCancelable(Context context, int dialogLayout, boolean isCancelable, int style) {

        createCustomDialog(context, dialogLayout, false, isCancelable, style, CANCELABLE_FLAG);
        showCustomDialog();
    }


    public static View createCustomDialogBeCancelable(Context context, int dialogLayout, boolean isCancelable, int style) {
        return createCustomDialog(context, dialogLayout, false, isCancelable, style, CANCELABLE_FLAG);
    }

    public static View createCustomDialogBeTouchOutside(Context context, int dialogLayout, boolean isCancelableOnTouchOutside, int style) {
        return createCustomDialog(context, dialogLayout, isCancelableOnTouchOutside, false, style, CANCELABLE_ONTOUCHOUTSIDE_FLAG);
    }


    /**
     * 关闭自定义的dialog
     */
    public static void dismissCustomProgressDialog() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
            customDialog = null;
        }
    }


    /**
     * show ProgressDialog
     */
    public static void showDefaultProgressDialog() {

        if (defaultProgressDialog == null) {
            throw new NullPointerException("defaultProgressDialog  ==  null");
        }

        if (!defaultProgressDialog.isShowing()) {
            defaultProgressDialog.show();
        }
    }

    /**
     * 创建ProgressDialog
     *
     * @param context
     * @param message
     */
    private static void createDefaultProgressDialog(Context context, String message, int dialogLayout, boolean isCancelableOnTouchOutside, boolean isCancelable, int style, int flag) {

        if (defaultProgressDialog == null) {
            defaultProgressDialog = new ProgressDialog(context);

            if (flag == CANCELABLE_FLAG) {
                defaultProgressDialog.setCancelable(isCancelable);
            } else if (flag == CANCELABLE_ONTOUCHOUTSIDE_FLAG) {
                defaultProgressDialog.setCanceledOnTouchOutside(isCancelableOnTouchOutside);
            }
            defaultProgressDialog.setMessage(message);
        }

    }

    /**
     * @param context
     * @param dialogLayout
     * @param isCancelableOnTouchOutside
     * @param isCancelable
     * @param style
     * @param flag                       1 设置setCancelable（）方法   2 设置setCanceledOnTouchOutside（）
     * @return
     */
    private static View createCustomDialog(Context context, int dialogLayout, boolean isCancelableOnTouchOutside, boolean isCancelable, int style, int flag) {
        View view = LayoutInflater.from(context).inflate(dialogLayout, null);
        if (customDialog == null) {
            customDialog = new Dialog(context, style);
            if (flag == CANCELABLE_FLAG) {
                customDialog.setCancelable(isCancelable);
            } else if (flag == CANCELABLE_ONTOUCHOUTSIDE_FLAG) {
                customDialog.setCanceledOnTouchOutside(isCancelableOnTouchOutside);
            }


            customDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

//                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
//                        if (customDialog.isShowing()) {
//                            customDialog.dismiss();
//                        }
//                        return true;
//                    }
//                    return false;
                    return true;
                }
            });

        }

        customDialog.setContentView(view);
        return view;
    }

    public static void showCustomDialog() {

        if (customDialog == null) {
            throw new NullPointerException("customDialog  ==  null");
        }
        if (!customDialog.isShowing()) {
            customDialog.show();
        }
    }
    public static void showQuitDialog( Context context ,String title ,String okTxt,String cancelTxt,final DialogButtonCallback call) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(context);

        builder.setMessage(title);
        builder.setPositiveButton(cancelTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(okTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                call.btnOk();
            }
        });

        builder.show();
    }
}
