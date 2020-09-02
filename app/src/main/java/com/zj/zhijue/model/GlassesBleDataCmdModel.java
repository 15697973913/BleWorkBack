package com.zj.zhijue.model;

import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.log.BleDataLog;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.BleHexUtil;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.zj.zhijue.BuildConfig;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesFeedbackBleDataBean;
import com.zj.zhijue.bean.bledata.send.SendForGetCommonFeedBackDataBean;
import com.zj.zhijue.bean.bledata.send.SendGlassesQueryUserIdBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendGlassesRunParam1BleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendMachineBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendUserInfoBleCmdBean;
import com.zj.zhijue.bean.bledata.send.SendUserInfoControlBleCmdBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.fragment.mine.SystemSettings2Fragment;
import com.zj.zhijue.fragment.mine.SystemSettingsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GlassesBleDataCmdModel {
    private Lock lock = new ReentrantLock();

    private static class Innner {
        public final static GlassesBleDataCmdModel single = new GlassesBleDataCmdModel();
    }

    public static GlassesBleDataCmdModel getInstance() {
        return GlassesBleDataCmdModel.Innner.single;
    }

    private GlassesBleDataCmdModel() {
        registerEventBus();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onWriteBleData2GlassesDevice(CmdBleDataEvent cmdBleDataEvent) {
        lock.lock();
        MLog.d(" cmdBleDataEvent Thread.ID = " + Thread.currentThread().getId());
        try {
            if (null != cmdBleDataEvent) {
                byte[] bleData = cmdBleDataEvent.getBleCmdData();
                if (null != bleData && bleData.length > 0) {
                    MLog.d("APPGLASS发送：[" + BaseParseCmdBean.bytesToStringWithSpace(bleData) + "]");
                    //if (handleUserIdWhile()) {
                    BleDeviceManager.getInstance().writeData2GlassesBleDevice(bleData);
                    if (BaseApplication.isDebugMode) {
                        BleDataLog.writeBleData("SendMsg：[" + BaseParseCmdBean.bytesToStringWithSpace(bleData) + "]");
                    }
                    handleSendDataCallBack(bleData);
                    //}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 检测当前的蓝牙是否已经获取到当前用户的ID
     *
     * @return
     */
    private boolean handleUserIdWhile() {
        while (true) {
            if (!GlassesBleDataModel.getInstance().getQueryCurrentUserIdSuccess()) {
                if (BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
                    byte[] userIDCmdArray = GlassesBleDataModel.getInstance().getQueryCurrentUserIdByteArray();
                    GlassesBleDataModel.getInstance().clearNotifyDataBuffer();
                    BleDeviceManager.getInstance().writeData2GlassesBleDevice(userIDCmdArray);
                    handleSendDataCallBack(userIDCmdArray);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    private void handleSendDataCallBack(byte[] writeDataArray) {
        String sourData = BaseParseCmdBean.bytesToString(writeDataArray);
        MLog.d("handleSendDataCallBack sourData = " + sourData);
        String lowerSourceData = sourData.toLowerCase();
        boolean receiveSuccess = false;
        String receiveBleData = "";
        for (int i = 0; i < 20; i++) {
            receiveBleData = GlassesBleDataModel.getInstance().getCurrentNotfiyReceiverDataButter().toString().toLowerCase();
            if (!CommonUtils.isEmpty(receiveBleData)
                    && (receiveBleData.startsWith(BaseParseCmdBean.USER_DATA_PREFIX)
                    || receiveBleData.endsWith(BaseParseCmdBean.USER_DATA_SUFIX))) {
                MLog.d(" receiveBleData = " + receiveBleData);
                receiveSuccess = true;
                break;
            }
            waitReceiveDataTime(50);
        }

        if (!receiveSuccess) {
            handleTimeout(lowerSourceData);
            if (!BuildConfig.DEBUG) {
                /**
                 * 正式版本，则不需要往下进行
                 */
                return;
            }
        }

    }

    private void handleTimeout(String lowerSendSourceData) {
        MLog.d(" handleTimeout ");
        /**
         * 根据发送的指令，以及在超时反馈中，做重发处理
         */
        if (lowerSendSourceData.startsWith(SendUserInfoBleCmdBean.USER_DATA_PREFIX)) {
            /**
             * 发送用户数据超时,确认是否重发
             */
            ToastUtil.showLong("发送用户数据失败");
        } else if (lowerSendSourceData.startsWith(SendMachineBleCmdBeaan.GLASSES_MACHINE_DATA_PREFIX)) {
            /**
             * 发送机器数据超时,确认是否重发
             */
            ToastUtil.showLong("发送机器数据失败");
        } else if (lowerSendSourceData.startsWith(SendGlassesRunParam1BleCmdBeaan.GLASSES_MACHINE_DATA_PREFIX)) {
            /**
             * 发送运行参数数据超时,确认是否重发
             */
            ToastUtil.showLong("发送运行参数数据失败");
        } else if (lowerSendSourceData.startsWith(SendUserInfoControlBleCmdBean.CONTROL_DATA_PREFIX)) {
            /**
             * 发送控制指令超时,确认是否重发
             */
            ToastUtil.showLong("发送控制指令数据失败");
        }
    }

    private void calcOperationCode(int operationCode, long operationTime) {
        switch (operationCode) {
            case SendUserInfoControlBleCmdBean.START_OPERATION_CODE:
                ToastUtil.showLong("开始成功");
                break;

            case SendUserInfoControlBleCmdBean.PASUSE_OPERATION_CODE:
                ToastUtil.showLong("暂停成功");
                break;

            case SendUserInfoControlBleCmdBean.CONTINUE_OPERATION_CODE:
                ToastUtil.showLong("继续成功");
                break;

            case SendUserInfoControlBleCmdBean.STOP_OPERATION_CODE:
                ToastUtil.showLong("停止成功");
                break;

            case SendUserInfoControlBleCmdBean.INTEVENE_OPERATION_CODE:
                ToastUtil.showLong("干预成功");
                break;

            case SendUserInfoControlBleCmdBean.POWER_OFF_OPERATION_CODE:
                ToastUtil.showLong("关闭电源");
                break;

            default:
                break;

        }
    }

    /**
     * 单次等待蓝牙数据的时间
     *
     * @param millis
     */
    private void waitReceiveDataTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean operationSuccess(String operationCode) {
        if (!CommonUtils.isEmpty(operationCode)) {
            int operationCodeInt = Integer.parseInt(operationCode, 16);
            if (operationCodeInt == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
