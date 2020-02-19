package com.allen.aplib.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * @author Allen
 * @date 2020/2/19 11:44
 * @describe log 打印日志保存, 文件的保存以分钟为单位
 * permission:<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_LOGS" />
 */
public class LogCatHelper {
    private static LogCatHelper instance = null;
    private String dirPath;//保存路径
    private int appid;//应用pid
    private ExecutorService executorService;

    /**
     * @param mContext
     * @param path     log日志保存根目录
     * @return
     */
    public static LogCatHelper getInstance(Context mContext, String path) {
        if (instance == null) {
            instance = new LogCatHelper(mContext, path);
        }
        return instance;
    }

    private LogCatHelper(Context mContext, String path) {
        appid = android.os.Process.myPid();
        if (TextUtils.isEmpty(path)) {
            dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "seeker" + File.separator + mContext.getPackageName();
        } else {
            dirPath = path;
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
    }

    /**
     * 启动log日志保存
     *   cmds 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
     *   cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
     *   cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
     *   cmds = "logcat -s way";//打印标签过滤信息
     */
    public void start() {
        executorService.execute(new LogRunnable(appid,dirPath));
    }

    private static class LogRunnable implements Runnable {

        private Process mProcess;
        private FileOutputStream fos;
        private BufferedReader mReader;
        private String cmds;
        private String mPid;

        public LogRunnable(int pid, String dirPath) {
            this.mPid = "" + pid;
            try {
                File file = new File(dirPath, FormatDate.getFormatDate() + ".log");
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cmds = "logcat *:v | grep \"(" + mPid + ")\"";
        }

        @Override
        public void run() {
            try {
                mProcess = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()), 1024);
                String line;
                while ((line = mReader.readLine()) != null) {
                    if (line.length() == 0) {
                        continue;
                    }
                    if (fos != null && line.contains(mPid)) {
                        fos.write((FormatDate.getFormatTime() + "	" + line + "\r\n").getBytes());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mProcess != null) {
                    mProcess.destroy();
                    mProcess = null;
                }
                try {
                    if (mReader != null) {
                        mReader.close();
                        mReader = null;
                    }
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static class FormatDate {

        public static String getFormatDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
            return sdf.format(System.currentTimeMillis());
        }

        public static String getFormatTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(System.currentTimeMillis());
        }
    }
}
