package com.zj.zhijue.model;

import android.text.TextUtils;
import android.util.SparseArray;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.log.BleDataLog;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ThreadPoolUtilsLocal;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.huige.library.utils.SharedPreferencesUtils;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.common.http.retrofit.netutils.NetUtil;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.bean.ReqTrainingDataBean2;
import com.zj.zhijue.bean.ReqTrainingDetailBean2;
import com.zj.zhijue.bean.UseInfoBean;
import com.zj.zhijue.bean.bledata.send.SendCalibrationModeCmdBean2;
import com.zj.zhijue.bean.bledata.send.SendGlassesQueryUserIdBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendMachineBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendUpdateEyeInfoBean2;
import com.zj.zhijue.bean.bledata.send.SendUpdateTimestampBean2;
import com.zj.zhijue.bean.bledata.send.SendUpdateUserInfoBean2;
import com.zj.zhijue.bean.bledata.send.SendUserInfoBleCmdBean;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;
import com.zj.zhijue.ble.BleOptHelper;
import com.zj.zhijue.config.Constants;
import com.zj.zhijue.event.CallbackDataEvent;
import com.zj.zhijue.event.CmdBleDataEvent;
import com.zj.zhijue.event.ConnectEvent;
import com.zj.zhijue.event.DeviceElectricityEvent;
import com.zj.zhijue.event.DfuModeEvent2;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.HeartbeatEvent;
import com.zj.zhijue.event.NotifyDataEvent;
import com.zj.zhijue.event.SynBleDataEvent;
import com.zj.zhijue.event.UpRealLocalTimeEvent;
import com.zj.zhijue.event.UpRealTimeEvent;
import com.zj.zhijue.fragment.TrainFragment;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IHttpRequestRunParasmBean;
import com.zj.zhijue.http.response.HttpResponseGlassInitDataBackBean;
import com.zj.zhijue.http.response.HttpResponseInterveneFeedbackParamsBean;
import com.zj.zhijue.http.response.HttpResponsePostRunParamsBean;
import com.zj.zhijue.util.AppUtils;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.DateUtil;
import com.zj.zhijue.util.GsonUtil;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class GlassesBleDataModel {
    private StringBuffer totalCallBackReceiveDataBuffer = new StringBuffer();
    private StringBuffer totalNotifyReceiverDataBuffer = new StringBuffer();
    private StringBuffer currentNotfiyReceiverDataButter = new StringBuffer();
    private SparseArray<String> glassesRunningArray = new SparseArray<>();
    private volatile AtomicLong currentUserId = new AtomicLong(-1);
    private volatile AtomicInteger glassesCurrentStatus = new AtomicInteger(-1);
    private volatile AtomicBoolean queryCurrentUserIdSuccess = new AtomicBoolean(false);
    private volatile AtomicInteger synThreadID = new AtomicInteger(-1);//同步蓝牙数据的线程ID
    private ExecutorService singleExecutorService;

    private UseInfoBean useInfoBean;

    private final int intervalTime = 100;

    //当前电量
    private int curElectricity = -1;

    private static class Innner {
        public final static GlassesBleDataModel single = new GlassesBleDataModel();
    }

    public static GlassesBleDataModel getInstance() {
        return GlassesBleDataModel.Innner.single;
    }


    private GlassesBleDataModel() {
        registerEventBus();

        initUserInfo();
    }

    /**
     * 初始化用户信息，先这样
     */
    public void initUserInfo() {
        String gs = (String) SharedPreferencesUtils.get(Constants.USER_INFO, "");
        if (!TextUtils.isEmpty(gs)) {
            useInfoBean = GsonUtil.GsonToBean(gs, UseInfoBean.class);

            LogUtils.e("============初始化用户信息 " + useInfoBean.getData().getName() + "---" +
                    useInfoBean.getData().getNickname());
        }

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReceiveBleDeviceConnnectStatus(ConnectEvent connectEvent) {

        LogUtils.e(connectEvent.getDeviceType() + "======== 111 " + connectEvent.isSuccess());

        if (connectEvent.isSuccess()) {

            BleOptHelper.bindWriteChannel(connectEvent.getDeviceType());
            BleOptHelper.bindNotifyChannel(connectEvent.getDeviceType());
            /**
             * 发送用户的视力信息和训练数据到蓝牙眼镜
             */
            resetQueryCurrentUserIdStatus();
            checkGlassesCurrentStatus(false);

        }
    }

    public void resetQueryCurrentUserIdStatus() {
        setQueryCurrentUserIdSuccess(false);
        setCurrentUserId(-1);
    }

    /**
     * 检查当前眼镜的运行状态和用户ID等信息
     */
    public void checkGlassesCurrentStatus(final boolean needWaitStop) {
        final String memeberId = Config.getConfig().getDecodeUserName();

        LogUtils.e("========== checkGlassesCurrentStatus 000 " + memeberId + "---" + needWaitStop);

        if (CommonUtils.isEmpty(memeberId)) {
            return;
        }

        createSingleExectorService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new SynBleDataEvent(true));
                LogUtils.d("singleThread1 = " + Thread.currentThread().getId());
                deleteLogFile();

                LogUtils.e("========== checkGlassesCurrentStatus 111 " + memeberId + "---" + needWaitStop);

                if (needWaitStop) {
                    threadSleep(1000);
                } else {
                    /**
                     * 处理在刚连上蓝牙眼镜的时候，等待较长的时间，减少查询蓝牙眼镜状态失败的概率
                     */
                    threadSleep(intervalTime * 2);
                }

                threadSleep(intervalTime);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 同步时间戳
                 */
                sendUpdateTimestamp();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 同步用户信息
                 */
                sendUpdateUserInfo();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 简单初始化上传未上传的训练数据
                 */
                GlassesBleDataModel.initTrainingList();

                /**
                 * 同步用左眼信息
                 */
                sendUpdateEyeInfoBean2(0);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 同步用右眼信息
                 */
                sendUpdateEyeInfoBean2(1);

                LogUtils.e("========== checkGlassesCurrentStatus 222 " + getCurrentUserId() + "---" + getQueryCurrentUserIdSuccess());

            }
        };
        synThreadID.set(runnable.hashCode());
        singleExecutorService.submit(runnable);
    }

    /**
     * 操作数据事件的反馈监听(用去读取 READ_PROPERTY 的值)
     *
     * @param callbackDataEvent
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReceiveBleDevicSendCallBackStatus(CallbackDataEvent callbackDataEvent) {

        if (null != callbackDataEvent) {
            int deviceType = callbackDataEvent.getDeviceType();
            if (EventConstant.GLASS_BLUETOOTH_EVENT_TYPE == deviceType) {
                String tmpData = BaseParseCmdBean.bytesToString(callbackDataEvent.getData());
                LogUtils.d("CallbackData tmpData = " + tmpData);
            } else if (EventConstant.LIGHT_BLUETOOTH_EVENT_TYPE == deviceType) {

            }
        }
    }

    /**
     * 接收数据的事件监听
     *
     * @param notifyDataEvent
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReceiveBleDevicResponseStatus(NotifyDataEvent notifyDataEvent) {
        if (null != notifyDataEvent) {
            handleReceiveBleData(notifyDataEvent.getData());
        }
    }

    /**
     * 删除日志文件
     */
    private void deleteLogFile() {
        String bleLogPath = BleDataLog.getLogDirPath() + BleDataLog.getCommonLogFileName();
        File bleDataFile = new File(bleLogPath);
        try {
            boolean deletBleDataLogFile = bleDataFile.delete();
            LogUtils.d("deletBleDataLogFile = " + deletBleDataLogFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!bleDataFile.exists()) {
                    bleDataFile.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void clearCallBackDataBuffer() {
        totalCallBackReceiveDataBuffer.setLength(0);
    }

    public void clearNotifyDataBuffer() {
        totalNotifyReceiverDataBuffer.setLength(0);
    }

    public void clearCurrentNotifyDataButter() {
        currentNotfiyReceiverDataButter.setLength(0);
    }

    public StringBuffer getTotalCallBackReceiveDataBuffer() {
        return totalCallBackReceiveDataBuffer;
    }

    public StringBuffer getTotalNotifyReceiverDataBuffer() {
        return totalNotifyReceiverDataBuffer;
    }

    public StringBuffer getCurrentNotfiyReceiverDataButter() {
        return currentNotfiyReceiverDataButter;
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

    private boolean isSynSuccess = true;

    /**
     * 处理接受到蓝牙眼镜发送的蓝牙数据
     *
     * @param receiveBleDataArray
     */
    private void handleReceiveBleData(byte[] receiveBleDataArray) {
        String tmpData = BaseParseCmdBean.bytesToString(receiveBleDataArray);
        LogUtils.d("NotifyData tmpData = " + tmpData);
        totalNotifyReceiverDataBuffer.append(tmpData);//这行代码不能删掉，GlassesBleDataCmdModel 中会获取该值来判断发送的指令是否成功接收
        currentNotfiyReceiverDataButter.setLength(0);
        currentNotfiyReceiverDataButter.append(tmpData);

        BleDataLog.writeBleData("ReceiveMsg：[" + tmpData + "]");

        if (tmpData.length() < 8) {
            return;
        }
        String cmdType = tmpData.substring(4, 6);
        if (tmpData.startsWith("AAC5")) {
            cmdType = "C5";
        }

        switch (cmdType) {
            case "60":
                //同步时间戳
            case "61":
                //同步用户信息
            case "62":
                //同步眼睛信息
                SynBleDataEvent synBleDataEvent = new SynBleDataEvent(false);
                synBleDataEvent.setSynSuccess(true);
                EventBus.getDefault().post(synBleDataEvent);
                break;
            case "65":
                //控制命令
                break;
            case "66":
                //APP发送获取训练实时记录数据
                getBleBackDataCmd66(tmpData);
                //运行中的operationType状态
                if (TrainFragment.mCurControlCmd == 0) {
                    TrainFragment.mCurControlCmd = 4;
                }
                break;
            case "67":
                //APP发送获取眼镜训练累积记录数据
                getBleBackDataCmd67(tmpData);
                break;
            case "68":
                //APP发送获取眼镜当前电池电量数据

                //剩余电量
                int eleQuantity = Integer.parseInt(tmpData.substring(10, 12), 16);
//                if (curElectricity != eleQuantity) {
                curElectricity = eleQuantity;
                DeviceElectricityEvent deviceElectricityEvent = new DeviceElectricityEvent(eleQuantity);
                EventBus.getDefault().post(deviceElectricityEvent);
//                }

                break;
            case "46":
                //APP发送眼镜校准模式
                SendCalibrationModeCmdBean2 modeCmdBean2 = new SendCalibrationModeCmdBean2();
                EventBus.getDefault().post(modeCmdBean2);
                break;
            case "50":
                //固件升级
                String dfuData = tmpData.substring(6, 8);
                boolean isSuccess = TextUtils.equals(dfuData, "00");
                DfuModeEvent2 dfuModeEvent2 = new DfuModeEvent2(isSuccess);
                EventBus.getDefault().post(dfuModeEvent2);
                break;
            case "C5":
            case "c5":
                //眼镜发送当前训练记录数据到APP
                getBleBackDataCmdC5(tmpData);
                break;
            case "69":
                //心跳包数据
                EventBus.getDefault().post(new HeartbeatEvent(true));
                break;
            default:
                break;
        }

        MLog.d("APPGLASS接收：[" + BaseParseCmdBean.bytesToStringWithSpace(receiveBleDataArray) + "]  -- " + cmdType);
    }

    public static int totalTraingSeconds = 0;//累计训练秒数

    /**
     * 获取命令66蓝牙返回的数据
     */
    private void getBleBackDataCmd66(String tmpData) {
        if (tmpData.length() < 40) {
            return;
        }

        ReqTrainingDataBean2 dataBean2 = new ReqTrainingDataBean2();

        //操作命令
        String cmd = tmpData.substring(4, 6);
        dataBean2.setOperationCmd(cmd);

        //操作类型：0-启动,1-暂停,2-停止,3-干预，4-运行 对应APP4个操作按键
        dataBean2.setOperationType(TrainFragment.mCurControlCmd);

        //训练模式 0x00：预留 0x01：训练模式 0x02：矫正模式；0x03：安全模式；其他：预留
        String trainingMode = tmpData.substring(6, 8);
        dataBean2.setTrainModel(Integer.parseInt(trainingMode, 16));

        //本次训练起始时间——年
        String year = tmpData.substring(8, 12);
        String[] year2Array = BaseCmdBean.reverseStringByte(year);
        year = "";
        for (String str : year2Array) {
            year += str;
        }
        year = Integer.parseInt(year, 16) + "";

        //本次训练起始时间——月
        String month = tmpData.substring(12, 14);
        month = Integer.parseInt(month, 16) + "";

        //本次训练起始时间——日
        String day = tmpData.substring(14, 16);
        day = Integer.parseInt(day, 16) + "";

        //本次训练起始时间——时
        String hour = tmpData.substring(16, 18);
        hour = Integer.parseInt(hour, 16) + "";

        //本次训练起始时间——分
        String minute = tmpData.substring(18, 20);
        minute = Integer.parseInt(minute, 16) + "";

        //本次训练起始时间——秒
        String second = tmpData.substring(20, 22);
        second = Integer.parseInt(second, 16) + "";

        dataBean2.setTrainingTime(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
        dataBean2.setMobileBluetoothTime(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);

        //本次训练起始时间——时区
        String timeZone = tmpData.substring(22, 24);
        dataBean2.setTimeZone(Integer.parseInt(timeZone, 16) + "");

        //本次训练累积时间（单位：s）
        String totalTime = tmpData.substring(24, 32);
        String[] totalTime2Array = BaseCmdBean.reverseStringByte(totalTime);
        totalTime = "";
        for (String str : totalTime2Array) {
            totalTime += str;
        }
        dataBean2.setTrainingSeconds(Integer.parseInt(totalTime, 16));

        //当前眼镜距离（单位：cm）
        String cm = tmpData.substring(32, 34);
        dataBean2.setMearsureDistance(Integer.parseInt(cm, 16));

        //当前电池电压（单位：0.01V）
        String vbatt = tmpData.substring(34, 38);
        String[] vbatt2Array = BaseCmdBean.reverseStringByte(vbatt);
        vbatt = "";
        for (String str : vbatt2Array) {
            vbatt += str;
        }
        dataBean2.setBatteryVoltage(Integer.parseInt(vbatt, 16));

        //当前电池电量百分比
        String batteryLevel = tmpData.substring(38);
        dataBean2.setBatteryPercent(Integer.parseInt(batteryLevel, 16));

        //APP本地生成的ID，防止断网本地存储，后提交的时候，避免重复提交数据
        dataBean2.setMobileRealTimeId("android_" + new Date().getTime());

        //用户标识：手机号
        dataBean2.setUserCode(useInfoBean.getData().getPhone());

        //累计训练秒数
        dataBean2.setTotalTraingSeconds(totalTraingSeconds);

        EventBus.getDefault().post(new UpRealLocalTimeEvent(dataBean2.getTrainingSeconds()));

        postTrainingData(dataBean2);

    }

    /**
     * 获取命令67蓝牙返回的数据
     */
    private void getBleBackDataCmd67(String tmpData) {

        if (tmpData.length() < 18) {
            return;
        }

        //训练总的累计时间（单位：s）
        StringBuilder totalTraingSec = new StringBuilder(tmpData.substring(6, 14));
        String[] totalTime2Array = BaseCmdBean.reverseStringByte(totalTraingSec.toString());
        totalTraingSec = new StringBuilder();
        for (String str : totalTime2Array) {
            totalTraingSec.append(str);
        }
        totalTraingSeconds = Integer.parseInt(totalTraingSec.toString(), 16);


        LogUtils.e("========== 训练总的累计时间：" + totalTraingSeconds);


        //周干预次数
        String weekCount = tmpData.substring(14, 18);
    }

    // 接收C5的状态  0：未收到  1：收到一只眼睛  2：收到两只眼睛
    public int receiveC5Status = 0;
    //需要提交的数据
    private ReqTrainingDetailBean2 uploadDetailBean;


    /**
     * 获取命令C5蓝牙返回的数据
     */
    private void getBleBackDataCmdC5(String tmpData) {


        if (tmpData.length() < 40) {
            return;
        }

        if (uploadDetailBean == null) {
            uploadDetailBean = new ReqTrainingDetailBean2();
        }

        uploadDetailBean.setUtdid(AppUtils.getUtdId());

        //操作命令
        String cmd = tmpData.substring(2, 4);

        //0x00:左眼 0x01:右眼
        String eye = tmpData.substring(4, 6);

        //本次训练起始时间——年
        String year = tmpData.substring(6, 10);
        String[] year2Array = BaseCmdBean.reverseStringByte(year);
        year = "";
        for (String str : year2Array) {
            year += str;
        }
        //本次训练起始时间——月
        String month = tmpData.substring(10, 12);
        //本次训练起始时间——日
        String day = tmpData.substring(12, 14);
        //本次训练起始时间——时
        String hour = tmpData.substring(14, 16);
        //本次训练起始时间——分
        String minute = tmpData.substring(16, 18);
        //本次训练起始时间——秒
        String second = tmpData.substring(18, 20);

        //本次训练起始时间——时区
        String timeZone = tmpData.substring(20, 22);

        //当前训练负区间
        String TrainingMinusRegion = tmpData.substring(22, 26);
        String[] TrainingMinusRegion2Array = BaseCmdBean.reverseStringByte(TrainingMinusRegion);
        TrainingMinusRegion = "";
        for (String str : TrainingMinusRegion2Array) {
            TrainingMinusRegion += str;
        }

        //当前训练正区间
        String TrainingPlusRegion = tmpData.substring(26, 30);
        String[] TrainingPlusRegion2Array = BaseCmdBean.reverseStringByte(TrainingPlusRegion);
        TrainingPlusRegion = "";
        for (String str : TrainingPlusRegion2Array) {
            TrainingPlusRegion += str;
        }

        //当前训练负区间步长
        String TrainingMinusRegionStep = tmpData.substring(30, 32);
        //当前训练正区间步长
        String TrainingPlusRegionStep = tmpData.substring(32, 34);
        //当前训练速度
        String TrainingSpeed = tmpData.substring(34, 36);
        //眼镜距离（单位：cm）
        String cm = tmpData.substring(36, 38);
        //训练步长系数
        String TrainingStepParam = tmpData.substring(38);

        //0x00:左眼 0x01:右眼
        if (TextUtils.equals("00", eye)) {
            //左眼
            uploadDetailBean.setLeftTrainingPlusRegion(Integer.parseInt(TrainingPlusRegion, 16) + "");
            uploadDetailBean.setLeftTrainingMinusRegion(Integer.parseInt(TrainingMinusRegion, 16) + "");
            uploadDetailBean.setLeftTrainingPlusRegionStep(Integer.parseInt(TrainingPlusRegionStep, 16) + "");
            uploadDetailBean.setLeftTrainingMinusRegionStep(Integer.parseInt(TrainingMinusRegionStep, 16) + "");
            uploadDetailBean.setLeftTrainingStartSpeed(Integer.parseInt(TrainingSpeed, 16) + "");//左眼训练开始速度
            uploadDetailBean.setLeftTrainingStepParam(Integer.parseInt(TrainingStepParam, 16) + "");//左眼训练步长系数

            uploadDetailBean.setLeftTrainingPlusRegionMaxStep("255");
            uploadDetailBean.setLeftTrainingMinusRegionMaxStep("255");
            uploadDetailBean.setLeftTrainingMaxTimes("255");
            uploadDetailBean.setLeftTrainingMaxSpeed("255");
            uploadDetailBean.setLeftTrainingSpeedInc("255");//左眼训练速度增量
        } else {
            //右眼
            uploadDetailBean.setRightTrainingPlusRegion(Integer.parseInt(TrainingPlusRegion, 16) + "");
            uploadDetailBean.setRightTrainingMinusRegion(Integer.parseInt(TrainingMinusRegion, 16) + "");
            uploadDetailBean.setRightTrainingPlusRegionStep(Integer.parseInt(TrainingPlusRegionStep, 16) + "");
            uploadDetailBean.setRightTrainingMinusRegionStep(Integer.parseInt(TrainingMinusRegionStep, 16) + "");
            uploadDetailBean.setRightTrainingStartSpeed(Integer.parseInt(TrainingSpeed, 16) + "");
            uploadDetailBean.setRightTrainingStepParam(Integer.parseInt(TrainingStepParam, 16) + "");

            uploadDetailBean.setRightTrainingPlusRegionMaxStep("255");
            uploadDetailBean.setRightTrainingMinusRegionMaxStep("255");
            uploadDetailBean.setRightTrainingMaxTimes("255");
            uploadDetailBean.setRightTrainingMaxSpeed("255");
            uploadDetailBean.setRightTrainingSpeedInc("255");
        }
        receiveC5Status++;
        if (receiveC5Status >= 2) {
            receiveC5Status = 0;
            postUploadTrainingDetail(uploadDetailBean);

            uploadDetailBean = null;
        }
    }

    /**
     * APP上报用户训练参数接口
     */
    private void postUploadTrainingDetail(ReqTrainingDetailBean2 detailBean2) {

        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> datalistItemMap = new HashMap<>();
        datalistItemMap.put("utdid", detailBean2.getUtdid());
        datalistItemMap.put("leftTrainingPlusRegion", detailBean2.getLeftTrainingPlusRegion());
        datalistItemMap.put("leftTrainingMinusRegion", detailBean2.getLeftTrainingMinusRegion());
        datalistItemMap.put("leftTrainingPlusRegionStep", detailBean2.getLeftTrainingPlusRegionStep());
        datalistItemMap.put("leftTrainingMinusRegionStep", detailBean2.getLeftTrainingMinusRegionStep());
        datalistItemMap.put("leftTrainingPlusRegionMaxStep", detailBean2.getLeftTrainingPlusRegionMaxStep());
        datalistItemMap.put("leftTrainingMinusRegionMaxStep", detailBean2.getLeftTrainingMinusRegionMaxStep());
        datalistItemMap.put("leftTrainingMaxTimes", detailBean2.getLeftTrainingMaxTimes());
        datalistItemMap.put("leftTrainingMaxSpeed", detailBean2.getLeftTrainingMaxSpeed());
        datalistItemMap.put("leftTrainingStartSpeed", detailBean2.getLeftTrainingStartSpeed());
        datalistItemMap.put("leftTrainingSpeedInc", detailBean2.getLeftTrainingSpeedInc());
        datalistItemMap.put("leftTrainingStepParam", detailBean2.getLeftTrainingStepParam());
        datalistItemMap.put("rightTrainingPlusRegion", detailBean2.getRightTrainingPlusRegion());
        datalistItemMap.put("rightTrainingMinusRegion", detailBean2.getLeftTrainingMinusRegion());
        datalistItemMap.put("rightTrainingPlusRegionStep", detailBean2.getRightTrainingPlusRegionStep());
        datalistItemMap.put("rightTrainingMinusRegionStep", detailBean2.getRightTrainingMinusRegionStep());
        datalistItemMap.put("rightTrainingPlusRegionMaxStep", detailBean2.getRightTrainingPlusRegionMaxStep());
        datalistItemMap.put("rightTrainingMinusRegionMaxStep", detailBean2.getRightTrainingMinusRegionMaxStep());
        datalistItemMap.put("rightTrainingMaxTimes", detailBean2.getRightTrainingMaxTimes());
        datalistItemMap.put("rightTrainingMaxSpeed", detailBean2.getRightTrainingMaxSpeed());
        datalistItemMap.put("rightTrainingStartSpeed", detailBean2.getRightTrainingStartSpeed());
        datalistItemMap.put("rightTrainingSpeedInc", detailBean2.getRightTrainingSpeedInc());
        datalistItemMap.put("rightTrainingStepParam", detailBean2.getRightTrainingStepParam());
        LogUtils.v("datalistItemMap:" + datalistItemMap);
        list.add(datalistItemMap);

        final HashMap<String, Object> headerMap = new HashMap<>();
        final HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IHttpRequestRunParasmBean.dataList, list);

        bodyMap.put("memberId", Config.getConfig().getUserServerId());
        bodyMap.put("areaCode", useInfoBean.getData().getAreaCode());
        bodyMap.put("operationType", TrainFragment.mCurControlCmd);
        bodyMap.put("loginName", useInfoBean.getData().getLoginName());
        bodyMap.put("collectTime", com.android.common.baselibrary.util.DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        bodyMap.put("deviceId", Config.getConfig().getLastConnectBleGlassesUUID());
        bodyMap.put("platform", "1");

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String jsonStr = null;

                try {
                    jsonStr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean postSuccess = false;

                if (!com.android.common.baselibrary.util.comutil.CommonUtils.isEmpty(jsonStr)) {
                    HttpResponsePostRunParamsBean httpResponsePostRunParamsBean = (HttpResponsePostRunParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponsePostRunParamsBean>() {
                    });
                    if (null != httpResponsePostRunParamsBean && httpResponsePostRunParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        postSuccess = true;
                    }
                }

                if (postSuccess) {

                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
        TrainSuscribe.postRunParamData(headerMap, bodyMap, disposableObserver);

    }

    //缓存未上传的训练数据
    public static List<ReqTrainingDataBean2> mTrainingList = new ArrayList<>();
    //缓存未上传的训练详情数据 - training/uploadDetail
    public static List<ReqTrainingDetailBean2> mTrainingDetailList = new ArrayList<>();


    /**
     * 简单保存未上传的训练数据
     */
    public static void saveTrainingList() {
        SPUtils.getInstance().put(SPUtils.KEY_SAVE_TRAINING_DATA, GsonUtil.GsonString(mTrainingList));
    }

    /**
     * 简单初始化上传未上传的训练数据
     */
    public static void initTrainingList() {
        String json = SPUtils.getInstance().getString(SPUtils.KEY_SAVE_TRAINING_DATA, "");
        if (!json.isEmpty()) {
            mTrainingList = GsonUtil.GsonToList(json, ReqTrainingDataBean2.class);
        }
    }

    /**
     * APP发送获取训练实时记录数据
     */
    public void postTrainingData(final ReqTrainingDataBean2 reqTrainingDataBean2) {

        if (NetUtil.isNetworkAvalible(MyApplication.getInstance())) {

            final String memberId = Config.getConfig().getUserServerId();
            if (com.android.common.baselibrary.util.comutil.CommonUtils.isEmpty(memberId)) {
                return;
            }

            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
            if (null == userInfoDBBeanList || userInfoDBBeanList.size() <= 0) {
                return;
            }
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);

            List<HashMap<String, Object>> list = new ArrayList<>();
            String glassMacStr = Config.getConfig().getLastConnectBleGlassesUUID();


            if (null != reqTrainingDataBean2) {
                HashMap<String, Object> datalistItemMap = new HashMap<>();
                datalistItemMap.put("mearsureDistance", reqTrainingDataBean2.getMearsureDistance());//训练测距
                datalistItemMap.put("trainModel", reqTrainingDataBean2.getTrainModel());//训练模式 1：训练模式；2：矫正模式；3：安全模式
                datalistItemMap.put("operationType", reqTrainingDataBean2.getOperationType());//操作类型：0-启动,1-暂停,2-停止,3-干预，4-运行 对应APP4个操作按键
                datalistItemMap.put("batteryVoltage", reqTrainingDataBean2.getBatteryVoltage());//电量
                datalistItemMap.put("batteryPercent", reqTrainingDataBean2.getBatteryPercent());//电量百分比
                datalistItemMap.put("trainingTime", reqTrainingDataBean2.getTrainingTime());//训练时间
                datalistItemMap.put("timeZone", reqTrainingDataBean2.getTimeZone());//训练时区
                datalistItemMap.put("totalTraingSeconds", reqTrainingDataBean2.getTotalTraingSeconds());//累计训练秒数
                datalistItemMap.put("trainingSeconds", reqTrainingDataBean2.getTrainingSeconds());//本次训练秒数
                datalistItemMap.put("operationCmd", reqTrainingDataBean2.getOperationCmd());//操作命令
                datalistItemMap.put("mobileBluetoothTime", reqTrainingDataBean2.getMobileBluetoothTime());//蓝牙时间
                datalistItemMap.put("mobileRealTimeId", reqTrainingDataBean2.getMobileRealTimeId());//APP本地生成的ID，防止断网本地存储，后提交的时候，避免重复提交数据
                datalistItemMap.put("userCode", reqTrainingDataBean2.getUserCode());//用户标识：手机号


                LogUtils.v("上传时间：Time:totalTraingSeconds:" + reqTrainingDataBean2.getTotalTraingSeconds() +
                        "\ntrainingSeconds:" + reqTrainingDataBean2.getTrainingSeconds() +
                        "\ntrainingTime:" + reqTrainingDataBean2.getTrainingTime());

                //添加未上传的数据
                if (mTrainingList != null) {
                    for (ReqTrainingDataBean2 dataBean2 : mTrainingList) {
                        HashMap<String, Object> itemMap = new HashMap<>();
                        itemMap.put("mearsureDistance", dataBean2.getMearsureDistance());//训练测距
                        itemMap.put("trainModel", dataBean2.getTrainModel());//训练模式 1：训练模式；2：矫正模式；3：安全模式
                        itemMap.put("operationType", dataBean2.getOperationType());//操作类型：0-启动,1-暂停,2-停止,3-干预，4-运行 对应APP4个操作按键
                        itemMap.put("batteryVoltage", dataBean2.getBatteryVoltage());//电量
                        itemMap.put("batteryPercent", dataBean2.getBatteryPercent());//电量百分比
                        itemMap.put("trainingTime", dataBean2.getTrainingTime());//训练时间
                        itemMap.put("timeZone", dataBean2.getTimeZone());//训练时区
                        itemMap.put("totalTraingSeconds", dataBean2.getTotalTraingSeconds());//累计训练秒数
                        itemMap.put("trainingSeconds", dataBean2.getTrainingSeconds());//本次训练秒数
                        itemMap.put("operationCmd", dataBean2.getOperationCmd());//操作命令
                        itemMap.put("mobileBluetoothTime", dataBean2.getMobileBluetoothTime());//蓝牙时间
                        itemMap.put("mobileRealTimeId", dataBean2.getMobileRealTimeId());//APP本地生成的ID，防止断网本地存储，后提交的时候，避免重复提交数据
                        itemMap.put("userCode", dataBean2.getUserCode());//用户标识：手机号
                        list.add(itemMap);
                    }
                }

                list.add(datalistItemMap);
            }

            HashMap<String, Object> headerMap = new HashMap<>();
            final HashMap<String, Object> bodyMap = new HashMap<>();


            bodyMap.put("memberId", memberId);
            bodyMap.put("loginName", userInfoDBBean.getLogin_name());
            bodyMap.put("deviceId", glassMacStr);
            bodyMap.put("platform", IBaseRequest.PLATFORM_VALUE_ANDROID);
            bodyMap.put("areaCode", userInfoDBBean.getAreaCode());
            bodyMap.put("utdid", AppUtils.getUtdId());
            bodyMap.put("dataList", list);

            DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    String jsonStr = null;

                    try {
                        jsonStr = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    boolean postSuccess = false;

                    if (!com.android.common.baselibrary.util.comutil.CommonUtils.isEmpty(jsonStr)) {
                        HttpResponseInterveneFeedbackParamsBean httpResponseInterveneFeedbackParamsBean = (HttpResponseInterveneFeedbackParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponseInterveneFeedbackParamsBean>() {
                        });
                        if (null != httpResponseInterveneFeedbackParamsBean && httpResponseInterveneFeedbackParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                            postSuccess = true;
                        }
                    }
                    EventBus.getDefault().post(new UpRealTimeEvent(true));

                    //如果成功则移除保存的数据
                    if (postSuccess) {
                        mTrainingList.clear();
                    } else {
                        mTrainingList.add(reqTrainingDataBean2);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
//                    EventBus.getDefault().post(new UpRealTimeEvent(false));
                    mTrainingList.add(reqTrainingDataBean2);
                }

                @Override
                public void onComplete() {

                }
            };
            LogUtils.v("bodyMap:" + bodyMap);
            TrainSuscribe.postCommonFeedBackData(headerMap, bodyMap, disposableObserver);

        } else {
            mTrainingList.add(reqTrainingDataBean2);
        }
    }

    /**
     * 同步时间戳
     */
    public void sendUpdateTimestamp() {
        LogUtils.d("sendUpdateTimestamp start");

        SendUpdateTimestampBean2 sendUpdateTimestampBean = new SendUpdateTimestampBean2();
        sendUpdateTimestampBean.setYear(DateUtil.getAssignTimeType(1));
        sendUpdateTimestampBean.setMonth(DateUtil.getAssignTimeType(2));
        sendUpdateTimestampBean.setDay(DateUtil.getAssignTimeType(3));
        sendUpdateTimestampBean.setHour(DateUtil.getAssignTimeType(4));
        sendUpdateTimestampBean.setMinute(DateUtil.getAssignTimeType(5));
        sendUpdateTimestampBean.setSeconds(DateUtil.getAssignTimeType(6));
        sendUpdateTimestampBean.setUtc(DateUtil.getUTC());
        sendUpdateTimestampBean.setUtcTime(new Date().getTime() / 1000);
        sendUpdateTimestampBean.setWeek(DateUtil.getWeekInt(DateUtil.getAssignDate(new Date().getTime(), 1)));

        byte[] data = sendUpdateTimestampBean.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== 同步时间戳  " +
                BaseParseCmdBean.bytesToStringWithSpace(data) + "----\n" + sendUpdateTimestampBean.toString());

    }

    /**
     * 同步用户信息
     */
    public void sendUpdateUserInfo() {
        LogUtils.d("sendUpdateUserInfo start " + useInfoBean.getData().getName() + "---" + useInfoBean.getData().getPhone());
        if (useInfoBean == null) {
            return;
        }

        SendUpdateUserInfoBean2 sendUpdateUserInfoBean2 = new SendUpdateUserInfoBean2();
        sendUpdateUserInfoBean2.setAge(useInfoBean.getData().getAge());
        sendUpdateUserInfoBean2.setUserID(com.zj.zhijue.util.CommonUtils.getLong(useInfoBean.getData().getPhone()));
        sendUpdateUserInfoBean2.setKg(60);
        sendUpdateUserInfoBean2.setStature(170);
        sendUpdateUserInfoBean2.setSex(useInfoBean.getData().getSex());

        byte[] data = sendUpdateUserInfoBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== 同步用户信息  " +
                BaseParseCmdBean.bytesToStringWithSpace(data));

    }

    //设备参数信息（发送命令用）
    private HttpResponseGlassInitDataBackBean.DataBean mDeviceInfoBean;

    /**
     * APP同步用户眼睛信息到眼镜
     *
     * @param eye 0左眼，1右眼
     */
    public void sendUpdateEyeInfoBean2(int eye) {
        LogUtils.d("sendUpdateUserInfo start " + useInfoBean.getData().getName() + "---" + useInfoBean.getData().getPhone());
        if (mDeviceInfoBean == null) {
            String newStringDataJson = SPUtils.getInstance().getString(SPUtils.KEY_DEVICE_PARAM, "");
            if (mDeviceInfoBean == null && !StringUtils.isEmpty(newStringDataJson)) {
                HttpResponseGlassInitDataBackBean dataBackBean =
                        (HttpResponseGlassInitDataBackBean) JsonUtil.json2objectWithDataCheck(
                                newStringDataJson, new TypeReference<HttpResponseGlassInitDataBackBean>() {
                                });
                mDeviceInfoBean = dataBackBean == null ? null : dataBackBean.getData();
            }
            if (mDeviceInfoBean == null) {
                return;
            }
        }

        int visionType;
        int vision;
        int trainingPlusRegion;
        int trainingMinusRegion;
        int trainingPlusRegionStep;
        int trainingMinusRegionStep;
        int trainingPlusRegionMaxStep;
        int trainingMinusRegionMaxStep;
        int trainingMaxTimes;
        int trainingMaxSpeed;
        int trainingStartSpeed;
        int trainingSpeedInc;
        int trainingStepParam;
        if (eye == 0) {
            //左眼
            visionType = mDeviceInfoBean.getDiopterState();
            vision = (int) mDeviceInfoBean.getLeftHandleDegree();
            trainingPlusRegion = (int) mDeviceInfoBean.getLeftTrainingPlusRegion();
            trainingMinusRegion = (int) mDeviceInfoBean.getLeftTrainingMinusRegion();
            trainingPlusRegionStep = (int) mDeviceInfoBean.getLeftTrainingPlusRegionStep();
            trainingMinusRegionStep = (int) mDeviceInfoBean.getLeftTrainingMinusRegionStep();
            trainingPlusRegionMaxStep = (int) mDeviceInfoBean.getLeftTrainingPlusRegionMaxStep();
            trainingMinusRegionMaxStep = (int) mDeviceInfoBean.getLeftTrainingMinusRegionMaxStep();
            trainingMaxTimes = mDeviceInfoBean.getLeftTrainingMaxTimes();
            trainingMaxSpeed = mDeviceInfoBean.getLeftTrainingMaxSpeed();
            trainingStartSpeed = mDeviceInfoBean.getLeftTrainingStartSpeed();
            trainingSpeedInc = mDeviceInfoBean.getLeftTrainingSpeedInc();
            trainingStepParam = mDeviceInfoBean.getLeftTrainingStepParam();
        } else {
            //右眼
            visionType = mDeviceInfoBean.getDiopterState();
            vision = (int) mDeviceInfoBean.getRightHandleDegree();
            trainingPlusRegion = (int) mDeviceInfoBean.getRightTrainingPlusRegion();
            trainingMinusRegion = (int) mDeviceInfoBean.getRightTrainingMinusRegion();
            trainingPlusRegionStep = (int) mDeviceInfoBean.getRightTrainingPlusRegionStep();
            trainingMinusRegionStep = (int) mDeviceInfoBean.getRightTrainingMinusRegionStep();
            trainingPlusRegionMaxStep = (int) mDeviceInfoBean.getRightTrainingPlusRegionMaxStep();
            trainingMinusRegionMaxStep = (int) mDeviceInfoBean.getRightTrainingMinusRegionMaxStep();
            trainingMaxTimes = mDeviceInfoBean.getRightTrainingMaxTimes();
            trainingMaxSpeed = mDeviceInfoBean.getRightTrainingMaxSpeed();
            trainingStartSpeed = mDeviceInfoBean.getRightTrainingStartSpeed();
            trainingSpeedInc = mDeviceInfoBean.getRightTrainingSpeedInc();
            trainingStepParam = mDeviceInfoBean.getRightTrainingStepParam();
        }


        SendUpdateEyeInfoBean2 sendUpdateEyeInfoBean2 = new SendUpdateEyeInfoBean2();
        sendUpdateEyeInfoBean2.setEye(eye);
        sendUpdateEyeInfoBean2.setVisionType(visionType);
        sendUpdateEyeInfoBean2.setVision(vision);
        sendUpdateEyeInfoBean2.setTrainingPlusRegion(trainingPlusRegion);
        sendUpdateEyeInfoBean2.setTrainingMinusRegion(trainingMinusRegion);
        sendUpdateEyeInfoBean2.setTrainingPlusRegionStep(trainingPlusRegionStep);
        sendUpdateEyeInfoBean2.setTrainingMinusRegionStep(trainingMinusRegionStep);
        sendUpdateEyeInfoBean2.setTrainingPlusRegionMaxStep(trainingPlusRegionMaxStep);
        sendUpdateEyeInfoBean2.setTrainingMinusRegionMaxStep(trainingMinusRegionMaxStep);
        sendUpdateEyeInfoBean2.setTrainingMaxTimes(trainingMaxTimes);
        sendUpdateEyeInfoBean2.setTrainingMaxSpeed(trainingMaxSpeed);
        sendUpdateEyeInfoBean2.setTrainingStartSpeed(trainingStartSpeed);
        sendUpdateEyeInfoBean2.setTrainingSpeedInc(trainingSpeedInc);
        sendUpdateEyeInfoBean2.setTrainingStepParam(trainingStepParam);

        byte[] data = sendUpdateEyeInfoBean2.buildCmdByteArray();
        EventBus.getDefault().post(new CmdBleDataEvent(data));

        LogUtils.e(Config.getConfig().getDecodeUserName() + " ======== APP同步用户眼睛信息到眼镜  " +
                BaseParseCmdBean.bytesToStringWithSpace(data));

    }

    public byte[] getQueryCurrentUserIdByteArray() {
        SendGlassesQueryUserIdBleCmdBeaan sendGlassesQueryUserIdBleCmdBeaan = new SendGlassesQueryUserIdBleCmdBeaan();
        return sendGlassesQueryUserIdBleCmdBeaan.buildCmdByteArray();
    }

    /**
     * 从本地数据库中查询用户的电话
     *
     * @return
     */
    private Long getUserPhoneFromDB() {
        String memeberId = Config.getConfig().getDecodeUserName();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memeberId);
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);
            String phone = userInfoDBBean.getPhone();
            Long phoneLong = convertPhone2Long(phone);
            return phoneLong;
        }
        return null;
    }

    /**
     * 发送机器数据，用户数据，，运行参数到眼镜中
     */
    private void sendUserInfo2Glass() {
        SendMachineBleCmdBeaan sendMachineBleCmdBeaan = TrainModel.getInstance().createSendMachineBleHexDataForTrain();
        EventBus.getDefault().post(new CmdBleDataEvent(sendMachineBleCmdBeaan.buildCmdByteArray()));

        threadSleep(intervalTime);
        HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBean = TrainModel.getInstance().getHttpResponseGlassInitDataBackBean();
        HttpResponseGlassInitDataBackBean.DataBean dataBean = httpResponseGlassInitDataBackBean.getData();

        SendUserInfoBleCmdBean sendUserInfoBleCmdBean = TrainModel.getInstance().createUserInfoBleHexDataForTrain(Config.getConfig().getLastSelectedTrainMode() + 1, dataBean.getIsNewUser(), 1);
        EventBus.getDefault().post(new CmdBleDataEvent(sendUserInfoBleCmdBean.buildCmdByteArray()));

        HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean = TrainModel.getInstance().getHttpResponseGlassesRunParamBean();
        if (null != httpResponseGlassesRunParamBean && !CommonUtils.isEmpty(httpResponseGlassesRunParamBean.getMemberId())) {
            /**
             * 云端给的运行参数是有效的数据
             */
            threadSleep(intervalTime);

            SparseArray<BaseCmdBean> baseCmdBeanArray = TrainModel.getInstance().createSendRunParamsBleHexData();

            //byte[] retByteArray = new byte[baseCmdBeanArray.size() * 20];

            for (int m = 0; m < baseCmdBeanArray.size(); m++) {
                BaseCmdBean baseCmdBean = baseCmdBeanArray.get(m);
                byte[] sendByte = baseCmdBean.buildCmdByteArray();
                EventBus.getDefault().post(new CmdBleDataEvent(sendByte));
                threadSleep(intervalTime);
                //System.arraycopy(sendByte, 0, retByteArray, m * 20, 20);
            }
            //EventBus.getDefault().post(new CmdBleDataEvent(retByteArray));
        }
    }

    public void queryGlassCurrentStatusByTimer() {
        createSingleExectorService();
    }

    public static Long convertPhone2Long(String phone) {
        if (!CommonUtils.isEmpty(phone)) {
            if (phone.startsWith("+")) {
                phone = phone.substring(1);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
            return Long.parseLong(phone);
        }
        return 0L;
    }

    private void createSingleExectorService() {
        if (null == singleExecutorService || singleExecutorService.isShutdown() || singleExecutorService.isTerminated()) {
            singleExecutorService = ThreadPoolUtilsLocal.newSingleThreadPool();
        }
    }

    public long getCurrentUserId() {
        return currentUserId.get();
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId.set(currentUserId);
    }

    public int getGlassesCurrentStatus() {
        return glassesCurrentStatus.get();
    }

    public void setGlassesCurrentStatus(int glassesCurrentStatusInt) {
        this.glassesCurrentStatus.set(glassesCurrentStatusInt);
    }

    public boolean getQueryCurrentUserIdSuccess() {
        return queryCurrentUserIdSuccess.get();
    }

    public void setQueryCurrentUserIdSuccess(boolean queryCurrentUserIdSuccess) {
        this.queryCurrentUserIdSuccess.set(queryCurrentUserIdSuccess);
    }

    private void threadSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearModelData() {
        totalCallBackReceiveDataBuffer.setLength(0);
        totalNotifyReceiverDataBuffer.setLength(0);
        currentNotfiyReceiverDataButter.setLength(0);
        setCurrentUserId(-1);
        setGlassesCurrentStatus(-1);
        setQueryCurrentUserIdSuccess(false);
    }
}
