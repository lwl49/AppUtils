package com.allen.apputils.utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.*;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Android Studio.
 * User: Allen
 * Date: 2019/4/9
 * Time: 11:07
 */
public class UsbUtils {
    static UsbUtils usbUtils;
    UsbManager usbManager;
    UsbInterface usbInterface;  // 被连接的USB设备
    UsbEndpoint usbEndpoint;    // 连接通道
    UsbEndpoint usbEndpointIn;    // 连接通道 进
    UsbEndpoint usbEndpointOut;    // 连接通道 出
    UsbDeviceConnection usbDeviceConnection;    //数据交互

    UsbConfiguration usbConfiguration;
//    UsbConstants   UsbConstants  usb 常量 定义了跟Linux内核中linux/usb/ch9.h文件定义对应的USB常量


    static String ACTION_USB_PERMISSION = "com.allen.USB_PERMISSION";
    PendingIntent pendingIntent;
    private WeakReference<Context> mWeakContext;

    public static UsbUtils getInstance() {
        if (usbUtils == null) {
            usbUtils = new UsbUtils();
        }
        return usbUtils;
    }

    public UsbUtils init(Context context) {
        mWeakContext = new WeakReference<>(context);
        usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        register();
        return usbUtils;
    }

    public void setUsbCallback(USBCallback usbCallback) {
        this.usbCallback = usbCallback;
    }

    boolean isDstroy = false;

    public void destroy() {
        isDstroy = true;
        unregister();
        usbGetThread = null;
    }

    /**
     * 注册USB广播
     */
    public synchronized void register() {
        Context context = mWeakContext.get();
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        context.registerReceiver(mUsbReceiver, filter);
    }

    /**
     * 注销USB广播
     */
    public synchronized void unregister() {
        if (pendingIntent != null) {
            final Context context = mWeakContext.get();
            try {
                if (context != null) {
                    context.unregisterReceiver(mUsbReceiver);
                }
            } catch (final Exception e) {
                LoggerUtils.i(e.getMessage());
            }
            pendingIntent = null;
        }
    }


    /**
     * 找到自定设备
     *
     * @param vendorId  厂商ID
     * @param productId 产品ID
     * @return device
     */
    public UsbDevice findUSB(int vendorId, int productId) {
        //2)获取到所有设备 选择出满足的设备
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            LoggerUtils.i("vendorID--" + device.getVendorId() + "ProductId--" + device.getProductId());
            if (device.getVendorId() == vendorId && device.getProductId() == productId) {
                return device; // 获取USBDevice
            }
        }
        return null;
    }

    boolean openPort = false;

    /**
     * 打开通信端口
     */
    public boolean openPort(UsbDevice device) {
        usbDevice = device;
        //获取设备接口，一般只有一个，多个的自己研究去
        usbInterface = device.getInterface(0);

        // 判断是否有权限
        if (hasPermission(device)) {
            // 打开设备，获取 UsbDeviceConnection 对象，连接设备，用于后面的通讯
            usbDeviceConnection = usbManager.openDevice(device);

            if (usbDeviceConnection == null) {
                return false;
            }
            if (usbDeviceConnection.claimInterface(usbInterface, true)) {
                //获取接口上的两个端点，分别对应 OUT 和 IN
                for (int i = 0; i < usbInterface.getEndpointCount(); ++i) {
                    UsbEndpoint end = usbInterface.getEndpoint(i);
                    if (end.getDirection() == UsbConstants.USB_DIR_IN) {
                        usbEndpointIn = end;
                    } else {
                        usbEndpointOut = end;
                    }
                }
                openPort = true;
                reciveData();
                ToastUtils.showToast("找到 USB 设备接口");
            } else {
                openPort = false;
                usbDeviceConnection.close();
                ToastUtils.showToast("没有找到 USB 设备接口");
                return false;
            }
        } else {
            ToastUtils.showToast("没有 USB 权限");
            requestPermission(device);
            return false;
        }


        return true;
    }

    boolean putData = false;

    /**
     * 发送数据
     */
    public void sendData(byte[] bytes) {
        if (usbDeviceConnection == null) {
            return;
        }
        int ret = usbDeviceConnection.bulkTransfer(usbEndpointOut, bytes, bytes.length, 500);
        ToastUtils.showToast(ret + "");
    }

    USBGetThread usbGetThread;

    public class USBGetThread extends Thread {

        @Override
        public void run() {
            if (usbDeviceConnection == null) {
                return;
            }
            while (!isDstroy) {
                int inMax = usbEndpointIn.getMaxPacketSize();
                ByteBuffer byteBuffer = ByteBuffer.allocate(inMax);
                UsbRequest usbRequest = new UsbRequest();
                usbRequest.initialize(usbDeviceConnection, usbEndpointIn);
                usbRequest.queue(byteBuffer, inMax);
                if (usbDeviceConnection.requestWait() == usbRequest) {
                    byte[] retData = byteBuffer.array();
                    for (Byte byte1 : retData) {
                        LoggerUtils.e(byte1 + "");
                    }
                    usbCallback.readDataFromUsb(retData);
                }
            }
            usbDeviceConnection.close();
        }
    }


    public void reciveData() {
        isDstroy = false;
        usbGetThread = new USBGetThread();
        usbGetThread.start();
    }

    /**
     * get UsbDeviceConnection
     *
     * @return
     */
    public synchronized UsbDeviceConnection getConnection() {
        return usbDeviceConnection;
    }

    /**
     * 判断对应 USB 设备是否有权限
     */
    public boolean hasPermission(UsbDevice device) {
        return usbManager.hasPermission(device);
    }

    /**
     * 请求获取指定 USB 设备的权限
     */
    public void requestPermission(UsbDevice device) {
        if (device != null) {
            if (usbManager.hasPermission(device)) {
                LoggerUtils.e("已经获取到权限");
                openPort(device);
            } else {
                if (pendingIntent != null) {
                    usbManager.requestPermission(device, pendingIntent);
                    LoggerUtils.e("请求USB权限");
                } else {
                    LoggerUtils.e("请注册USB广播");
                    register();
                }
            }
        }
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LoggerUtils.e("action", action);
            if (ACTION_USB_PERMISSION.equals(action)) {
                // 获取权限结果的广播
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device != null) {
                        //call method to set up device communication
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            LoggerUtils.e("USBReceiver", "获取权限成功：" + device.getDeviceName());
                            // 查询相关设备
                            findUSB(2385, 5698);
                        } else {
                            LoggerUtils.e("USBReceiver", "获取权限失败：" + device.getDeviceName());
                        }
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                requestPermission(device);
                //新设备插入
//                askforpermission();
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                // 设备拔出
            }
        }
    };
    UsbDevice usbDevice;
//
//    public void getUInfo(UsbDevice device){
//        // 设备分区
//        Partition partition = device.getPartitions().get(0);
//        // 文件系统
//        FileSystem currentFs = partition.getFileSystem();
//        // 获取 U 盘的根目录
//        UsbFile mRootFolder = currentFs.getRootDirectory();
//        // 获取 U 盘的容量
//        long capacity = currentFs.getCapacity();
//        // 获取 U 盘的剩余容量
//        long freeSpace = currentFs.getFreeSpace();
//        // 获取 U 盘的标识
//        String volumeLabel = currentFs.getVolumeLabel();
//    }

    public static class UsbDeviceInfo {
        public String usb_version;
        public String manufacturer;
        public String product;
        public String version;
        public String serial;

        private void clear() {
            usb_version = manufacturer = product = version = serial = null;
        }

        @Override
        public String toString() {
            return String.format("UsbDevice:usb_version=%s,manufacturer=%s,product=%s,version=%s,serial=%s",
                    usb_version != null ? usb_version : "",
                    manufacturer != null ? manufacturer : "",
                    product != null ? product : "",
                    version != null ? version : "",
                    serial != null ? serial : "");
        }
    }

    USBCallback usbCallback;

    public interface USBCallback {
        void readDataFromUsb(byte[] bytes);
    }
}
