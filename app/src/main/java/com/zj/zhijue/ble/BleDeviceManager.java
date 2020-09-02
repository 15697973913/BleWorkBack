package com.zj.zhijue.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.util.BleHexUtil;
import com.android.common.baselibrary.util.ThreadPoolUtilsLocal;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.vise.baseble.BuildConfig;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IBleCallback;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.callback.scan.IScanCallback;
import com.vise.baseble.callback.scan.ScanCallback;
import com.vise.baseble.common.BleConstant;
import com.vise.baseble.common.ConnectState;
import com.vise.baseble.common.PropertyType;
import com.vise.baseble.core.BluetoothGattChannel;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.core.DeviceMirrorPool;
import com.vise.baseble.exception.BleException;
import com.vise.baseble.model.BluetoothLeDevice;
import com.vise.baseble.model.BluetoothLeDeviceStore;
import com.zj.zhijue.bean.bledata.send.SendBatteryLevelBean2;
import com.zj.zhijue.callback.CheckBleMacByServerCallBack;
import com.zj.zhijue.constant.ConstantString;
import com.zj.zhijue.event.CallbackDataEvent;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.event.ConnectEvent;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.HeartbeatEvent;
import com.zj.zhijue.event.NotifyDataEvent;
import com.zj.zhijue.event.ScanEvent;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.Config;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;

public class BleDeviceManager {
    private static BleDeviceManager instance;
    private final static String TAG = BleDeviceManager.class.getSimpleName();
    private DeviceMirrorPool mDeviceMirrorPool;
    private ScheduledExecutorService executorService = null;
    private AtomicInteger threadHashCode = new AtomicInteger(0);
    private Handler threadHandler = new Handler(Looper.getMainLooper());
    private boolean haveExitApp;
    private final static int WHAT_HEARTBEAT = 0x2000;
    private final static int WHAT_RECONNECTDEVICE = 0x2001;

    private Handler handler;
    private final static int heartbeatTime = 10000;
    private final static int reConnectDeviceTime = 12000;


    /**
     * 操作数据回调
     */
    private IBleCallback mGlassesBleCallback = new IBleCallback() {
        @Override
        public void onSuccess(byte[] bArr, BluetoothGattChannel bluetoothGattChannel, BluetoothLeDevice bluetoothLeDevice) {
            LogUtils.e("======= 000 onSuccess " + BaseParseCmdBean.bytesToStringWithSpace(bArr));
            LogUtils.e("======= characteristic.getUuid() " + Arrays.toString(bluetoothLeDevice.getDevice().getUuids()));

            if (bArr != null) {
                EventBus.getDefault().post(mGlasseCallbackDataEvent.setData(bArr).setSuccess(true).setBluetoothLeDevice(bluetoothLeDevice).setBluetoothGattChannel(bluetoothGattChannel));
                if (bluetoothGattChannel != null && (bluetoothGattChannel.getPropertyType() == PropertyType.PROPERTY_INDICATE
                        || bluetoothGattChannel.getPropertyType() == PropertyType.PROPERTY_NOTIFY)) {
                    DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(bluetoothLeDevice);
                    deviceMirror.setNotifyListener(bluetoothGattChannel.getGattInfoKey(), mGlassesReceiveCallback);
                }
            }
        }

        @Override
        public void onFailure(BleException bleException) {
            LogUtils.e("======= 000 onFailure");
            if (bleException != null) {
                EventBus.getDefault().post(mGlasseCallbackDataEvent.setSuccess(false));
            }
        }
    };

    private IBleCallback mLightBleCallback = new IBleCallback() {
        @Override
        public void onSuccess(byte[] bArr, BluetoothGattChannel bluetoothGattChannel, BluetoothLeDevice bluetoothLeDevice) {
            if (bArr != null) {
                EventBus.getDefault().post(mLightCallbackDataEvent.setData(bArr).setSuccess(true).setBluetoothLeDevice(bluetoothLeDevice).setBluetoothGattChannel(bluetoothGattChannel));
                if (bluetoothGattChannel != null && (bluetoothGattChannel.getPropertyType() == PropertyType.PROPERTY_INDICATE
                        || bluetoothGattChannel.getPropertyType() == PropertyType.PROPERTY_NOTIFY)) {
                    DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(bluetoothLeDevice);
                    deviceMirror.setNotifyListener(bluetoothGattChannel.getGattInfoKey(), mLightReceiveCallback);
                }
            }
        }

        @Override
        public void onFailure(BleException bleException) {
            if (bleException != null) {
                EventBus.getDefault().post(mLightCallbackDataEvent.setSuccess(false));
            }
        }
    };


    /**
     * 连接回调
     */
    private IConnectCallback mGlassesConnectCallback = new IConnectCallback() {
        @Override
        public void onConnectSuccess(DeviceMirror deviceMirror) {

            BluetoothLeDevice bluetoothLeDevice = deviceMirror.getBluetoothLeDevice();
            Config.getConfig().saveLastConnectBleGlassesName(bluetoothLeDevice.getName());
            Config.getConfig().saveLastConnectBleGlassesMac(bluetoothLeDevice.getAddress());

            EventBus.getDefault().post(mGlassesConnectEvent.setDeviceMirror(deviceMirror).setSuccess(true));
            shutDownScheduledExecutorService();

            startSendHeartbeat();


        }

        @Override
        public void onConnectFailure(BleException bleException) {
            EventBus.getDefault().post(mGlassesConnectEvent.setSuccess(false).setDisconnected(false));
            createScheduleSerivce();

        }

        @Override
        public void onDisconnect(boolean z) {
            EventBus.getDefault().post(mGlassesConnectEvent.setSuccess(false).setDisconnected(true));
            createScheduleSerivce();
        }

        @Override
        public void foundDeviceByMac(BluetoothLeDevice bluetoothLeDevice) {
            setmGlassesBluetoothLeDevice(bluetoothLeDevice);
            String mac = Config.getConfig().getLastConnectBleGlassesMac();
            if (!CommonUtils.isEmpty(mac)) {
                if (null != bluetoothLeDevice && bluetoothLeDevice.getAddress().equals(mac)) {
                    Config.getConfig().saveLastConnectBleGlassesName(bluetoothLeDevice.getName());
                    connect(bluetoothLeDevice, this);
                }
            }
        }


        @Override
        public void onConnectTimeoutOrFailByMac() {
            LogUtils.e("onConnectTimeoutOrFailByMac");
            /**
             * 通过 mac 地址连接超时或失败的回调
             */
            String mac = Config.getConfig().getLastConnectBleGlassesMac();
            if (!CommonUtils.isEmpty(mac)) {
                createScheduleSerivce();
            } else {
                /**
                 * 用户取消了连接操作
                 */
            }
        }
    };

    private IConnectCallback mLightConnectCallback = new IConnectCallback() {
        @Override
        public void onConnectSuccess(DeviceMirror deviceMirror) {
            EventBus.getDefault().post(mLightConnectEvent.setDeviceMirror(deviceMirror).setSuccess(true));
        }

        @Override
        public void onConnectFailure(BleException bleException) {
            EventBus.getDefault().post(mLightConnectEvent.setSuccess(false).setDisconnected(false));
        }

        @Override
        public void onDisconnect(boolean z) {
            EventBus.getDefault().post(mLightConnectEvent.setSuccess(false).setDisconnected(true));
        }

        @Override
        public void foundDeviceByMac(BluetoothLeDevice bluetoothLeDevice) {
            setmLightBluetoothLeDevice(bluetoothLeDevice);
        }

        @Override
        public void onConnectTimeoutOrFailByMac() {
            /**
             * 通过 mac 地址连接超时或失败的回调
             */
        }
    };

    private BluetoothLeDevice mGlassesBluetoothLeDevice;
    private BluetoothLeDevice mLightBluetoothLeDevice;

    private ConnectEvent mGlassesConnectEvent = new ConnectEvent(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
    private ConnectEvent mLightConnectEvent = new ConnectEvent(EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE);

    private NotifyDataEvent mGlassesNotifyDataEvent = new NotifyDataEvent(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
    private NotifyDataEvent mLightNotifyDataEvent = new NotifyDataEvent(EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE);

    private CallbackDataEvent mGlasseCallbackDataEvent = new CallbackDataEvent(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);
    private CallbackDataEvent mLightCallbackDataEvent = new CallbackDataEvent(EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE);

    /**
     * 是否允许连接
     * 1. 首页没有开启设备按钮则不允许连接
     * 2. 没有填写视力信息也不允许连接
     */
    public boolean isAllowConn = false;

    /**
     * 接收数据回调
     */
    private IBleCallback mGlassesReceiveCallback = new IBleCallback() {
        @Override
        public void onSuccess(byte[] bArr, BluetoothGattChannel bluetoothGattChannel, BluetoothLeDevice bluetoothLeDevice) {
            LogUtils.e("======= 接收数据回调 onSuccess" + BaseParseCmdBean.bytesToStringWithSpace(bArr) + " --- " + bluetoothLeDevice.getName());
            if (bArr != null) {
                EventBus.getDefault().post(mGlassesNotifyDataEvent.setData(bArr)
                        .setBluetoothLeDevice(bluetoothLeDevice)
                        .setBluetoothGattChannel(bluetoothGattChannel));
            }
        }

        @Override
        public void onFailure(BleException bleException) {
            LogUtils.e("======= 接收数据回调 onFailure" + bleException.toString());
            if (bleException != null) {
            }
        }
    };

    private IBleCallback mLightReceiveCallback = new IBleCallback() {
        @Override
        public void onSuccess(byte[] bArr, BluetoothGattChannel bluetoothGattChannel, BluetoothLeDevice bluetoothLeDevice) {
            if (bArr != null) {
                EventBus.getDefault().post(mLightNotifyDataEvent.setData(bArr)
                        .setBluetoothLeDevice(bluetoothLeDevice)
                        .setBluetoothGattChannel(bluetoothGattChannel));
            }
        }

        @Override
        public void onFailure(BleException bleException) {
            if (bleException != null) {
            }
        }
    };

    private ScanCallback mGlassesScanCallBack = new ScanCallback(new IScanCallback() {
        @Override
        public void onScanFinish(BluetoothLeDeviceStore bluetoothLeDeviceStore) {

        }

        @Override
        public void onDeviceFound(BluetoothLeDevice bluetoothLeDevice) {
            LogUtils.e(isGlassesBleDevice(bluetoothLeDevice) + "---" + isLightBleDevice(bluetoothLeDevice) + "onDeviceFound ==========" + bluetoothLeDevice.getName() + "  " + bluetoothLeDevice.getAddress());
            if (isGlassesBleDevice(bluetoothLeDevice) || isLightBleDevice(bluetoothLeDevice)) {
                if (isGlassesBleDevice(bluetoothLeDevice)) {
                    EventBus.getDefault().post(mGlassesScanEvent.setSuccess(true).setBluetoothLeDevice(bluetoothLeDevice));
                } else if (isLightBleDevice(bluetoothLeDevice)) {
                    EventBus.getDefault().post(mLightScanEvent.setSuccess(true).setBluetoothLeDevice(bluetoothLeDevice));
                }
            }
        }

        @Override
        public void onScanTimeout() {
            EventBus.getDefault().post(new ScanEvent(EventConstant.ALL_BLUETOOTH_EVENT_TYPE).setSuccess(false).setBluetoothLeDevice(null));
        }
    });

    private ScanEvent mGlassesScanEvent = new ScanEvent(EventConstant.GLASS_BLUETOOTH_EVENT_TYPE);

    private ScanEvent mLightScanEvent = new ScanEvent(EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE);

    private BleDeviceManager() {
    }

    public static BleDeviceManager getInstance() {
        instance = SingleTon.single;
        return instance;
    }

    private static class SingleTon {
        private static final BleDeviceManager single = new BleDeviceManager();
    }

    public void init(Context context) {
        if (context != null) {
            //蓝牙相关配置修改
            ViseBle.config()
                    .setScanTimeout(BleConstant.DEFAULT_SCAN_TIME)//扫描超时时间，这里设置为永久扫描
                    .setConnectTimeout(BleConstant.DEFAULT_CONN_TIME)//连接超时时间
                    .setOperateTimeout(BleConstant.DEFAULT_OPERATE_TIME)//设置数据操作超时时间
                    .setConnectRetryCount(BleConstant.DEFAULT_MAX_CONNECT_COUNT)//设置连接失败重试次数
                    .setConnectRetryInterval(BleConstant.DEFAULT_RETRY_INTERVAL)//设置连接失败重试间隔时间
                    .setOperateRetryCount(BleConstant.DEFAULT_RETRY_COUNT)//设置数据操作失败重试次数
                    .setOperateRetryInterval(BleConstant.DEFAULT_RETRY_INTERVAL)//设置数据操作失败重试间隔时间
                    .setMaxConnectCount(2);//设置最大连接设备数量
            //蓝牙信息初始化，全局唯一，必须在应用初始化时调用
            ViseBle.getInstance().init(context);
            mDeviceMirrorPool = ViseBle.getInstance().getDeviceMirrorPool();
            //createScheduleSerivce();
            EventBus.getDefault().register(this);
        }

        if (handler == null) {
            handler = new MyHandler(this);
        }
    }

    public void startScan(String str) {
        if (BuildConfig.DEBUG) {
            ToastUtil.showLongToast("开始扫描");
        }
        startScanAllBleDevice();
        LogUtils.e("startScan ============" + str);

    }

    private void startScanAllBleDevice() {
        ViseBle.getInstance().startScan(mGlassesScanCallBack);
    }

    public void stopScan() {
        ViseBle.getInstance().stopScan(mGlassesScanCallBack);
    }

    public void stopScanByMac() {
        ViseBle.getInstance().stopScanByMac();
    }

    private void connect(BluetoothLeDevice bluetoothLeDeviceParam, IConnectCallback connectCallback) {

        //判断是否允许连接
        if (!isAllowConn) {
            disconnectLightBleDevice();
            return;
        }

        if (bluetoothLeDeviceParam != null) {
            ViseBle.getInstance().connect(bluetoothLeDeviceParam, connectCallback);
        }
    }

    public void connectGlassesByMac(String mac) {
        Config.getConfig().saveLastConnectBleGlassesMac(mac);
        if (!CommonUtils.isEmpty(mac)) {
            ViseBle.getInstance().connectByMac(mac, mGlassesConnectCallback);
        }
    }

    public void connectLightByMac(String mac) {
        if (!CommonUtils.isEmpty(mac)) {
            ViseBle.getInstance().connectByMac(mac, mLightConnectCallback);
        }
    }

    public void connectGlassesBleDevice() {
        Config.getConfig().saveLastConnectBleGlassesMac(mGlassesBluetoothLeDevice.getAddress());
        Config.getConfig().saveLastConnectBleGlassesName(mGlassesBluetoothLeDevice.getName());
        connect(mGlassesBluetoothLeDevice, mGlassesConnectCallback);
    }

    public void connectLightBleDevice() {
        connect(mLightBluetoothLeDevice, mLightConnectCallback);
    }

    private void disconnect(BluetoothLeDevice bluetoothLeDeviceParam) {
        /**
         * 断开所有设备
         */
        ViseBle.getInstance().disconnect();
        stopSendHeartbeat();
    }

    public void disconnectGlassesBleDevice(boolean clearMacValue) {
        if (clearMacValue) {
            Config.getConfig().saveLastConnectBleGlassesMac("");
            Config.getConfig().saveLastConnectBleGlassesName("");
        }
        disconnect(mGlassesBluetoothLeDevice);
    }

    public void disconnectLightBleDevice() {
        disconnect(mLightBluetoothLeDevice);
    }

    private boolean isConnected(BluetoothLeDevice bluetoothLeDeviceParam) {
        return bluetoothLeDeviceParam != null ? ViseBle.getInstance().isConnect(bluetoothLeDeviceParam) : false;
    }

    public boolean isGlassesBleDeviceConnected() {
        return isConnected(mGlassesBluetoothLeDevice);
    }

    public boolean isGlassesBleDeviceConnecting() {
        return ViseBle.getInstance().getConnectState(mGlassesBluetoothLeDevice) == ConnectState.CONNECT_PROCESS;
    }

    public boolean isLightBleDeviceConnected() {
        return isConnected(mLightBluetoothLeDevice);
    }

    public boolean isLightBleDeviceConnecting() {
        return ViseBle.getInstance().getConnectState(mLightBluetoothLeDevice) == ConnectState.CONNECT_PROCESS;
    }

    private void bindChannel(BluetoothLeDevice bluetoothLeDevice, IBleCallback iBleCallbackParam, PropertyType propertyType, UUID uuid, UUID uuid2, UUID uuid3) {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(bluetoothLeDevice);
        if (deviceMirror != null) {
            BluetoothGattChannel bluetoothGattChannel = new BluetoothGattChannel.Builder()
                    .setBluetoothGatt(deviceMirror.getBluetoothGatt())
                    .setPropertyType(propertyType)
                    .setServiceUUID(uuid)
                    .setCharacteristicUUID(uuid2)
                    .setDescriptorUUID(uuid3).builder();

            deviceMirror.bindChannel(iBleCallbackParam, bluetoothGattChannel);
        }
    }

    private void unBindChannel(BluetoothLeDevice bluetoothLeDevice, PropertyType propertyType, UUID uuid, UUID uuid2, UUID uuid3) {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(bluetoothLeDevice);
        if (deviceMirror != null) {
            BluetoothGattChannel bluetoothGattChannel = new BluetoothGattChannel.Builder()
                    .setBluetoothGatt(deviceMirror.getBluetoothGatt())
                    .setPropertyType(propertyType)
                    .setServiceUUID(uuid)
                    .setCharacteristicUUID(uuid2)
                    .setDescriptorUUID(uuid3).builder();

            deviceMirror.unbindChannel(bluetoothGattChannel);
        }
    }

    public void bindGlassesChannel(PropertyType propertyType, UUID uuid, UUID uuid2, UUID uuid3) {
        bindChannel(mGlassesBluetoothLeDevice, mGlassesBleCallback, propertyType, uuid, uuid2, uuid3);
    }

    public void unbindGlassesChannel(PropertyType propertyType, UUID uuid, UUID uuid2, UUID uuid3) {
        unBindChannel(mGlassesBluetoothLeDevice, propertyType, uuid, uuid2, uuid3);
    }

    public void bindLightChannel(PropertyType propertyType, UUID uuid, UUID uuid2, UUID uuid3) {
        bindChannel(mLightBluetoothLeDevice, mLightBleCallback, propertyType, uuid, uuid2, uuid3);
    }

    public void registerGlassesNotify(boolean isIndicationParam) {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(mGlassesBluetoothLeDevice);
        if (null != deviceMirror) {
            deviceMirror.registerNotify(isIndicationParam);
        }
    }

    public void registerLightNotify(boolean isIndicationParam) {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(mLightBluetoothLeDevice);
        if (null != deviceMirror) {
            deviceMirror.registerNotify(isIndicationParam);
        }
    }

    public void writeData2GlassesBleDevice(byte[] glassessDataArray) {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(mGlassesBluetoothLeDevice);
        if (null != deviceMirror) {
            write(deviceMirror, glassessDataArray);
        }
    }

    public void readGlassData() {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(mGlassesBluetoothLeDevice);
        if (null != deviceMirror) {
            readData(deviceMirror);
        }
    }

    private void write(final DeviceMirror deviceMirror, byte[] data) {
        if (dataInfoQueue != null) {
            dataInfoQueue.clear();
            dataInfoQueue = splitPacketFor20Byte(data);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    send(deviceMirror);
                }
            });
        }
    }

    private void send(final DeviceMirror deviceMirror) {
        if (dataInfoQueue != null && !dataInfoQueue.isEmpty()) {
            if (dataInfoQueue.peek() != null && deviceMirror != null) {
                deviceMirror.writeData(dataInfoQueue.poll());
            }
            if (dataInfoQueue.peek() != null) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        send(deviceMirror);
                    }
                }, 100);
            }
        }
    }

    public void readData(final DeviceMirror deviceMirror) {
        deviceMirror.readData();
    }

    private Queue<byte[]> dataInfoQueue = new LinkedList<>();

    private Queue<byte[]> splitPacketFor20Byte(byte[] data) {
        Queue<byte[]> dataInfoQueue = new LinkedList<>();
        if (data != null) {
            int index = 0;
            do {
                byte[] surplusData = new byte[data.length - index];
                byte[] currentData;
                System.arraycopy(data, index, surplusData, 0, data.length - index);
                if (surplusData.length <= 20) {
                    currentData = new byte[surplusData.length];
                    System.arraycopy(surplusData, 0, currentData, 0, surplusData.length);
                    index += surplusData.length;
                } else {
                    currentData = new byte[20];
                    System.arraycopy(data, index, currentData, 0, 20);
                    index += 20;
                }
                dataInfoQueue.offer(currentData);
            } while (index < data.length);
        }
        return dataInfoQueue;
    }

    public void writeData2LightBleDevice(byte[] lightDataArray) {
        DeviceMirror deviceMirror = mDeviceMirrorPool.getDeviceMirror(mLightBluetoothLeDevice);
        if (null != deviceMirror) {
            deviceMirror.writeData(lightDataArray);
        }
    }


    public BluetoothLeDevice getGlassesBluetoothLeDevice() {
        return this.mGlassesBluetoothLeDevice;
    }

    public BluetoothLeDevice getLightBluetoothLeDevice() {
        return this.mLightBluetoothLeDevice;
    }

    public DeviceMirror getGlassesDeviceMirror() {
        return mDeviceMirrorPool.getDeviceMirror(mGlassesBluetoothLeDevice);
    }

    public DeviceMirror getLightDeviceMirror() {
        return mDeviceMirrorPool.getDeviceMirror(mLightBluetoothLeDevice);
    }

    private boolean isGlassesBleDevice(BluetoothLeDevice bluetoothLeDeviceParam) {
        if (null == bluetoothLeDeviceParam) {
            return false;
        }
        BluetoothDevice bluetoothDevice = bluetoothLeDeviceParam.getDevice();
        if (null != bluetoothDevice) {
            String name = bluetoothDevice.getName();
            String mac = bluetoothDevice.getAddress();
            if (!CommonUtils.isEmpty(name) && name.startsWith(ConstantString.GLAASESS_NAME_COMMONT_PREFIX)) {
                return true;
            }
        }

        return false;
    }

    private boolean isLightBleDevice(BluetoothLeDevice bluetoothLeDeviceParam) {

        return false;
    }

    public void setmGlassesBluetoothLeDevice(BluetoothLeDevice mGlassesBluetoothLeDevice) {
        //当前蓝牙设备
        this.mGlassesBluetoothLeDevice = mGlassesBluetoothLeDevice;
    }

    public void setmLightBluetoothLeDevice(BluetoothLeDevice mLightBluetoothLeDevice) {
        this.mLightBluetoothLeDevice = mLightBluetoothLeDevice;
    }

    private void createScheduleSerivce() {
        if (isHaveExitApp()) {
            shutDownScheduledExecutorService();
            return;
        }

        if (null == executorService || executorService.isTerminated() || executorService.isShutdown()) {
            executorService = ThreadPoolUtilsLocal.newScheduledThreadPool();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (threadHashCode.get() != hashCode()) {
                        LogUtils.e("threadTAG HashCode.get() != hashCode()");
                        return;
                    }

                    final String macAddress = Config.getConfig().getLastConnectBleGlassesMac();
                    LogUtils.e("macAddress = " + macAddress);
                    if (!CommonUtils.isEmpty(macAddress)) {
                        boolean glassesConnnected = isGlassesBleDeviceConnected();
                        boolean isConnecting = isGlassesBleDeviceConnecting();
                        LogUtils.e("macAddress = " + macAddress + " glassesConnnected = " + glassesConnnected + " isConnecting = " + isConnecting);
                        if (glassesConnnected || isConnecting) {
                            shutDownScheduledExecutorService();
                        } else {
                            threadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    boolean isScanning = ViseBle.getInstance().isScanningByMac();
                                    if (isScanning) {
                                        onlyStopScan();
                                    } else {
                                        TrainModel.getInstance().checkDeviceBindStatus(macAddress, null, false, new CheckBleMacByServerCallBack() {
                                            @Override
                                            public void checkStatus(boolean avaliable, String mac) {
                                                if (avaliable) {
                                                    connectGlassesByMac(macAddress);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    } else {
                        shutDownScheduledExecutorService();
                    }
                }
            };
            threadHashCode.set(runnable.hashCode());
            executorService.scheduleWithFixedDelay(runnable, 10, 10, TimeUnit.SECONDS);
        }
    }

    public void shutDownScheduledExecutorService() {
        onlyShutDownExecutorService();
        onlyStopScan();
    }

    private void onlyStopScan() {
        stopScan();
        stopScanByMac();
    }

    private void onlyShutDownExecutorService() {
        if (null != executorService) {
            executorService.shutdownNow();
        }
    }

    public boolean isHaveExitApp() {
        return haveExitApp;
    }

    public void setHaveExitApp(boolean haveExitApp) {
        this.haveExitApp = haveExitApp;
    }


    /**
     * 心跳包回调
     *
     * @param heartbeatEvent isSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveSynStatusEvent(HeartbeatEvent heartbeatEvent) {
        handler.removeMessages(WHAT_RECONNECTDEVICE);
        handler.sendEmptyMessageDelayed(WHAT_RECONNECTDEVICE, reConnectDeviceTime);
    }

    /**
     * 停止心跳包
     */
    public void stopSendHeartbeat() {
        handler.removeMessages(WHAT_HEARTBEAT);
        handler.removeMessages(WHAT_RECONNECTDEVICE);
    }

    /**
     * 开始心跳包
     */
    private void startSendHeartbeat() {
        handler.sendEmptyMessageDelayed(WHAT_HEARTBEAT, heartbeatTime);
        handler.sendEmptyMessageDelayed(WHAT_RECONNECTDEVICE, reConnectDeviceTime);
    }


    public static class MyHandler extends Handler {
        private WeakReference<BleDeviceManager> weakReference;

        MyHandler(BleDeviceManager bleDeviceManager) {
            weakReference = new WeakReference<>(bleDeviceManager);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_HEARTBEAT:
                    //发送心跳包
                    weakReference.get().sendHeartBeat();

                    //发送电量
                    weakReference.get().sendBatteryLevelBean2();

                    sendEmptyMessageDelayed(WHAT_HEARTBEAT, heartbeatTime);
                    break;
                case WHAT_RECONNECTDEVICE:
                    //当心跳包收不到的时候执行的方法
                    LogUtils.v("心跳包收不到了，赶快重连！！！！！");

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * APP发送心跳包
     * 0xAA：包头
     * 0x69：命令
     */
    public void sendHeartBeat() {
        String heartBeatMsg =
                "AA 69 00 00 00 "
                        + "00 00 00 00 00 "
                        + "00 00 00 00 00 "
                        + "00 00 00 00 00 ";
        EventBus.getDefault().post(new CmdBleDataEvent(BleHexUtil.getRealSendData(heartBeatMsg.replace(" ", ""))));

        LogUtils.e("发送心跳包：" + heartBeatMsg);

    }

    /**
     * APP发送获取眼镜当前电池电量数据
     */
    public void sendBatteryLevelBean2() {

        SendBatteryLevelBean2 sendBatteryLevelBean2 = new SendBatteryLevelBean2();

        byte[] data = sendBatteryLevelBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== APP发送获取眼镜当前电池电量数据  " +
                BaseParseCmdBean.bytesToStringWithSpace(data));

    }
}
