package com.zj.zhijue.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.app.BaseApplication;
import com.android.common.baselibrary.blebean.BaseCmdBean;
import com.android.common.baselibrary.blebean.BaseParseCmdBean;
import com.android.common.baselibrary.constant.BaseConstantString;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.DateUtil;
import com.android.common.baselibrary.util.FileUtils;
import com.android.common.baselibrary.util.ThreadPoolUtils;
import com.android.common.baselibrary.util.ThreadPoolUtilsLocal;
import com.android.common.baselibrary.util.ToastUtil;
import com.zj.zhijue.util.AppUtils;
import com.android.common.baselibrary.util.comutil.CommonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zj.common.http.retrofit.netsubscribe.TrainSuscribe;
import com.zj.common.http.retrofit.netutils.NetUtil;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.bindglasses.BindPersonalInfoActivity;
import com.zj.zhijue.bean.bledata.BleDataBeanConvertUtil;
import com.zj.zhijue.bean.bledata.receive.ReceiveGlassesFeedbackBleDataBean;
import com.zj.zhijue.bean.bledata.receive.ReceiveInteveneFeedbackBleDataBean;
import com.zj.zhijue.bean.bledata.send.SendMachineBleCmdBeaan;
import com.zj.zhijue.bean.bledata.send.SendUserInfoBleCmdBean;
import com.zj.zhijue.bean.request.HttpRequestCommonFeedbackBean;
import com.zj.zhijue.bean.request.HttpRequestGlassesRunParamBean;
import com.zj.zhijue.bean.request.HttpRequestInterveneFeedbackBean;
import com.zj.zhijue.bean.request.HttpRequestUserTrainTimeInfoBean;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.callback.CheckBleMacByServerCallBack;
import com.zj.zhijue.event.TrainTimeUpdateStatusEvent;
import com.zj.zhijue.fragment.mine.SystemSettings2Fragment;
import com.zj.zhijue.greendao.greendaobean.CommonFeedBackBleDataDBBean;
import com.zj.zhijue.greendao.greendaobean.InterveneFeedBackBleDataDBBean;
import com.zj.zhijue.greendao.greendaobean.ReviewDataEyeSightDBBean;
import com.zj.zhijue.greendao.greendaobean.RunParamsBleDataDBBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoTrainTimeDBBean;
import com.zj.zhijue.greendao.greenddaodb.CommonFeedBackBleDataBeanDaoOpe;
import com.zj.zhijue.greendao.greenddaodb.InterveneFeedBackBleDataBeanDaoOpe;
import com.zj.zhijue.greendao.greenddaodb.ReviewDataEyeSightBeanDaoOpe;
import com.zj.zhijue.greendao.greenddaodb.RunParamsBleDataBeanDaoOpe;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.greendao.greenddaodb.UserInfoTrainTimeBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IBindDevice;
import com.zj.zhijue.http.request.IHttpInterveneFeedBackFeedBackBean;
import com.zj.zhijue.http.request.IHttpRequestCommonFeedBackBean;
import com.zj.zhijue.http.request.IHttpRequestRunParasmBean;
import com.zj.zhijue.http.request.IReviewEyeSightData;
import com.zj.zhijue.http.request.ITrainTimeInfo;
import com.zj.zhijue.http.request.IgetDeviceParam;
import com.zj.zhijue.http.request.IrecentlyTrainingDetail;
import com.zj.zhijue.http.response.HttpResponseBindDeviceBean;
import com.zj.zhijue.http.response.HttpResponseCommonFeedbackParamsBean;
import com.zj.zhijue.http.response.HttpResponseGetRunParamsBean;
import com.zj.zhijue.http.response.HttpResponseGlassInitDataBackBean;
import com.zj.zhijue.http.response.HttpResponseInterveneFeedbackParamsBean;
import com.zj.zhijue.http.response.HttpResponseMemberTrainTimeBean;
import com.zj.zhijue.http.response.HttpResponsePostRunParamsBean;
import com.zj.zhijue.http.response.HttpResponseReviewEyeSightDataBean;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.GsonTools;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.pnikosis.materialishprogress.BuildConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class TrainModel {
    private static AtomicBoolean isUploadingData = new AtomicBoolean(false);
    private static AtomicBoolean isGetTrainTimeData = new AtomicBoolean(false);
    private static AtomicBoolean isGetInitMachingData = new AtomicBoolean(false);
    private static AtomicBoolean isGetRunParamsData = new AtomicBoolean(false);
    private static AtomicBoolean isCheckGlassDevice = new AtomicBoolean(false);

    private ReadWriteLock machineDataLock = new ReentrantReadWriteLock();
    private ReadWriteLock runParamDataLock = new ReentrantReadWriteLock();
    private ReadWriteLock userInfoDBDataLock = new ReentrantReadWriteLock();

    private boolean needReport = true;
    private static volatile HttpResponseGlassInitDataBackBean mHttpResponseGlassInitDataBackBean;
    private static volatile HttpResponseGlassesRunParamBean mHttpResponseGlassesRunParamBean;
    private static volatile UserInfoDBBean mUserInfoDBBean;
    private ExecutorService singleExecutorService;
    public static final String GLASSES_RUNNING_PARAM_FILE_NAME = "bleglasses_runingparams";
    public static final String HTTP_GLASSES_INIT_MACHINE_FILE_NAME = "http_initmachine_bledata";
    public static final String HTTP_GLASSES_RUNNING_PARAM_FILE_NAME = "http_bleglasses_runingparams";
    public static final String HTTP_LOGIN_USER_INFO = "http_login_user_info_file";
    private List<DisposableObserver> httpDispList = new ArrayList<>();
    private BroadcastReceiver netBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MLog.i("networkchange....");
            handleBleDataInDB2Server();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkBindDeviceStatusInBackGround();
                    uploadAllBleOperationDataInThread();
                }
            }, 5000);

        }
    };

    private volatile List<UserInfoTrainTimeDBBean> trainTimeList = new ArrayList<>();

    private Timer timer = null;
    private TimerTask timerTask = null;

    private static class Innner {
        public final static TrainModel single = new TrainModel();
    }

    public static TrainModel getInstance() {
        return Innner.single;
    }

    private TrainModel() {
        registerInnerNetBroadCastReceiver();
    }

    public void uploadAllBleOperationDataInThread() {
        MLog.i("isUploadingData start = " + isUploadingData.get());
        MLog.d("uploadAllBleOperationDataInThread");
        if (!isUploadingData.get()) {
            isUploadingData.set(true);
        } else {
            /**
             * 如果正在上传数据中，则不重复往下走
             */
            return;
        }

        if (CommonUtils.isEmpty(Config.getConfig().getUserServerId())) {
            isUploadingData.set(false);
            return;
        }

        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                MLog.i("upload " + Thread.currentThread().getId());

                getReviewData();

                getUserTrainTimeInfoFromServer();
                isUploadingData.set(false);
                MLog.i("isUploadingData end = " + isUploadingData);
                getAllTrainTimeWithLimit();
            }
        });
    }

    private void getUserTrainTimeInfoFromServer() {
        List<HttpRequestUserTrainTimeInfoBean> httpRequestUserTrainTimeInfoBeanList = getUserTrainTimeHttpRequestBean();
        int listSize = 0;
        if (null != httpRequestUserTrainTimeInfoBeanList && (listSize = httpRequestUserTrainTimeInfoBeanList.size()) > 0) {
            CountDownLatch countDownLatch = new CountDownLatch(listSize);
            for (HttpRequestUserTrainTimeInfoBean bean : httpRequestUserTrainTimeInfoBeanList) {
                getUserTrainTimeinf(bean, countDownLatch, true);
            }
            try {
                MLog.d(" getUserTrainTimeInfoFromServer await");
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<HttpRequestUserTrainTimeInfoBean> getUserTrainTimeHttpRequestBean() {
        String serverID = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverID)) {
            return null;
        }

        List<HttpRequestUserTrainTimeInfoBean> httpRequestUserTrainTimeInfoBeanList = new ArrayList<>();
        long dayLong = 24L * 60 * 60 * 1000;
        UserInfoTrainTimeDBBean userInfoTrainTimeDBBean = UserInfoTrainTimeBeanDaoOpe.queryRawLastUserInfoTrainTimeByServerID(MyApplication.getInstance(), serverID);
        if (null == userInfoTrainTimeDBBean) {
            /**
             * 处理第一次使用的情况
             */
            userInfoTrainTimeDBBean = new UserInfoTrainTimeDBBean();
            try {
                userInfoTrainTimeDBBean.setTrainDate(DateUtil.tlocalformatter.parse(DateUtil.startTime).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (null != userInfoTrainTimeDBBean) {
            long lastTrainDate = userInfoTrainTimeDBBean.getTrainDate();
            lastTrainDate = lastTrainDate - dayLong;
            /**
             * 如果距离上次插入的训练时间超过一年，则分成多次查询（每次查询的最长间隔为一年）
             */
            do {
                HttpRequestUserTrainTimeInfoBean httpRequestUserTrainTimeInfoBean = new HttpRequestUserTrainTimeInfoBean();
                httpRequestUserTrainTimeInfoBean.setBt(DateUtil.tlocalformatter.format(new Date(lastTrainDate)));
                httpRequestUserTrainTimeInfoBean.setMemberId(serverID);
                lastTrainDate = lastTrainDate + 365L * dayLong;

                httpRequestUserTrainTimeInfoBean.setEt(DateUtil.tlocalformatter.format(new Date(lastTrainDate)));
                httpRequestUserTrainTimeInfoBeanList.add(httpRequestUserTrainTimeInfoBean);
            } while (lastTrainDate < System.currentTimeMillis());

            return httpRequestUserTrainTimeInfoBeanList;

        }
        return null;
    }

    /**
     * 用于每次提交实时反馈数据之后，重新获取最新的训练时长数据，及时更新数据报表界面
     */
    public void updateReportFormData() {
        /**
         * 查询当天的训练时间
         */
        HttpRequestUserTrainTimeInfoBean httpRequestUserTrainTimeInfoBean = new HttpRequestUserTrainTimeInfoBean();

        String todayYearMonthDayStr = DateUtil.localformatterDay.format(new Date(System.currentTimeMillis()));
        String tomorrowYearMonthDayStr = DateUtil.localformatterDay.format(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000));
        long todayStartLong = System.currentTimeMillis() - 24 * 60 * 60 * 1000L;
        long tommorStartLong = System.currentTimeMillis() + 24 * 60 * 60 * 1000L;
        try {
            todayStartLong = DateUtil.localformatterDay.parse(todayYearMonthDayStr).getTime();
            tommorStartLong = DateUtil.localformatterDay.parse(tomorrowYearMonthDayStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        httpRequestUserTrainTimeInfoBean.setMemberId(Config.getConfig().getUserServerId());
        httpRequestUserTrainTimeInfoBean.setBt(DateUtil.localformatter.format(new Date(todayStartLong)));
        httpRequestUserTrainTimeInfoBean.setEt(DateUtil.localformatter.format(new Date(tommorStartLong)));
        getUserTrainTimeinf(httpRequestUserTrainTimeInfoBean, null, true);

    }

    /**
     * 获取用户的训练时间数据
     */
    public void getUserTrainTimeinf(HttpRequestUserTrainTimeInfoBean userTrainTimeInfoBean, final CountDownLatch countDownLatch, final boolean needUpdateReportForm) {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put(ITrainTimeInfo.MEMBERID, userTrainTimeInfoBean.getMemberId());
        bodyMap.put(ITrainTimeInfo.BT, userTrainTimeInfoBean.getBt());
        bodyMap.put(ITrainTimeInfo.ET, userTrainTimeInfoBean.getEt());
        bodyMap.put(ITrainTimeInfo.DEVICEID, Config.getConfig().getLastConnectBleGlassesUUID());
        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MLog.e("===========getUserTrainTimeinf ");

                HttpResponseMemberTrainTimeBean httpResponseMemberTrainTimeBean = (HttpResponseMemberTrainTimeBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseMemberTrainTimeBean>() {
                });
                //MLog.d("httpResponseMemberTrainTimeBean = " + httpResponseMemberTrainTimeBean);
                //SdLogUtil.writeCommonLog("httpResponseMemberTrainTimeBean = " + httpResponseMemberTrainTimeBean);
                saveTrainTime2DB(httpResponseMemberTrainTimeBean, countDownLatch, needUpdateReportForm);
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();
                countDown(countDownLatch);
            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.getUserTrainTimeInfo(headerMap, bodyMap, disposableObserver);

    }

    private void countDown(CountDownLatch countDownLatch) {
        if (null != countDownLatch) {
            countDownLatch.countDown();
        }
    }

    public void saveTrainTime2DB(final HttpResponseMemberTrainTimeBean httpResponseMemberTrainTimeBean, final CountDownLatch countDownLatch, final boolean needUpdateReportForm) {
        if (null != httpResponseMemberTrainTimeBean && httpResponseMemberTrainTimeBean.getStatus().equals(IBaseRequest.SUCCESS)) {
            ThreadPoolUtils.execute(new Runnable() {
                @Override
                public void run() {
                    List<HttpResponseMemberTrainTimeBean.DataBean> dataBeanList = httpResponseMemberTrainTimeBean.getData();
                    int dataCount = 0;
                    if (null != dataBeanList && (dataCount = dataBeanList.size()) > 0) {
                        List<UserInfoTrainTimeDBBean> trainTimeDBBeanList = new ArrayList<>();
                        for (HttpResponseMemberTrainTimeBean.DataBean httpBean : dataBeanList) {
                            UserInfoTrainTimeDBBean dbBean = new UserInfoTrainTimeDBBean();
                            dbBean.setLocalid(UUID.randomUUID().toString());
                            dbBean.setServerId(httpBean.getMemberId());
                            dbBean.setTrainCount(httpBean.getTrainTotalCount());
                            dbBean.setTrainDateStr(httpBean.getTrainingDate());
                            dbBean.setTrainTimeYear(httpBean.getTrainTimeYear());
                            dbBean.setTrainTimeMonth(httpBean.getTrainTimeMonth());
                            dbBean.setTrainTimeDay(httpBean.getTrainTimeDay());
                            dbBean.setTrainTimeHour(httpBean.getTrainTimeHour());
                            dbBean.setTrainTimeMinute(httpBean.getTrainTimeMinute());
                            dbBean.setTrainTimeSecond(httpBean.getTrainTimeSecond());
                            dbBean.setTrainTime(httpBean.getTrainingSeconds());
                            //dbBean.setTrainDate(0L);
                            try {
                                if (!CommonUtils.isEmpty(httpBean.getTrainingDate())) {
                                    dbBean.setTrainDate(DateUtil.localformatter.parse(httpBean.getTrainingDate()).getTime());
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            long createTimeLong = System.currentTimeMillis();
                            if (dbBean.getTrainDate() > 0 && createTimeLong < dbBean.getTrainDate()) {
                                createTimeLong = dbBean.getTrainDate();//处理用户设置的手机当前时间不正确的情况
                            }

                            dbBean.setCreateTime(DateUtil.localformatter.format(new Date(createTimeLong)));
                            dbBean.setUpdateTime(DateUtil.localformatter.format(new Date(createTimeLong)));
                            dbBean.setUpdateTimeLong(createTimeLong);

                            trainTimeDBBeanList.add(dbBean);
                        }

                        createSingleExectorService();
                        singleExecutorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                UserInfoTrainTimeBeanDaoOpe.saveData(MyApplication.getInstance(), trainTimeDBBeanList);
                            }
                        });
                    }

                    countDown(countDownLatch);

                    if (needUpdateReportForm) {
                        if (null == countDownLatch) {
                            //EventBus.getDefault().post(new TrainTimeUpdateStatusEvent(true, dataCount));
                            updateTrainDateMap();
                        } else if (countDownLatch.getCount() == 0) {
                            //EventBus.getDefault().post(new TrainTimeUpdateStatusEvent(true, dataCount));
                            updateTrainDateMap();
                        }
                    }
                }
            });
        } else {
            countDown(countDownLatch);
        }

    }

    private void updateTrainDateMap() {
        List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = UserInfoTrainTimeBeanDaoOpe.queryUserInfoTrainTimeServerIDAndLimit(MyApplication.getInstance(), Config.getConfig().getUserServerId(), 10 * 365);
        synchronized (trainTimeList) {
            trainTimeList.clear();
            int trainDays = 0;
            if (null != userInfoTrainTimeDBBeanList) {
                trainDays = userInfoTrainTimeDBBeanList.size();
                trainTimeList.addAll(userInfoTrainTimeDBBeanList);
            }
            EventBus.getDefault().post(new TrainTimeUpdateStatusEvent(true, trainDays));
        }
    }


    /**
     * 获取视力复查数据
     */
    public void getReviewData() {
        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IReviewEyeSightData.MEMBERID, Config.getConfig().getUserServerId());
        bodyMap.put(IReviewEyeSightData.IMEI, DeviceUtils.getIMEI());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HttpResponseReviewEyeSightDataBean httpResponseReviewEyeSightDataBean = (HttpResponseReviewEyeSightDataBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseReviewEyeSightDataBean>() {
                });
                MLog.d("httpResponseReviewEyeSightDataBean = " + httpResponseReviewEyeSightDataBean);
                saveReviewDataInThread(httpResponseReviewEyeSightDataBean);
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.getReviewData(headerMap, bodyMap, disposableObserver);
    }

    private void saveReviewDataInThread(final HttpResponseReviewEyeSightDataBean httpResponseReviewEyeSightDataBean) {
        if (null != httpResponseReviewEyeSightDataBean
                && httpResponseReviewEyeSightDataBean.getStatus().equals(IReviewEyeSightData.SUCCESS)
                && null != httpResponseReviewEyeSightDataBean.getData()
                && httpResponseReviewEyeSightDataBean.getData().size() > 0) {
            createSingleExectorService();
            singleExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    saveReviewEyeSightDataIntoDB(httpResponseReviewEyeSightDataBean);
                }
            });
        }

    }

    /**
     * 将视力复查数据存入数据库中
     *
     * @param httpResponseReviewEyeSightDataBean
     */
    private void saveReviewEyeSightDataIntoDB(HttpResponseReviewEyeSightDataBean httpResponseReviewEyeSightDataBean) {
        List<HttpResponseReviewEyeSightDataBean.DataBean> dataBeanList = httpResponseReviewEyeSightDataBean.getData();

        List<ReviewDataEyeSightDBBean> reviewDataEyeSightDBBeanList = new ArrayList<>();

        for (int i = 0; i < dataBeanList.size(); i++) {
            HttpResponseReviewEyeSightDataBean.DataBean dataBean = dataBeanList.get(i);

            ReviewDataEyeSightDBBean reviewDataEyeSightDBBean = new ReviewDataEyeSightDBBean();
            reviewDataEyeSightDBBean.setLocalid(UUID.randomUUID().toString());

            ReviewDataEyeSightDBBean.UniqueBean uniqueBean = new ReviewDataEyeSightDBBean.UniqueBean();
            uniqueBean.setMemeberId(dataBean.getMemberId());
            uniqueBean.setReviewTimes(dataBean.getReviewCount());
            reviewDataEyeSightDBBean.setUniqueBean(uniqueBean);

            reviewDataEyeSightDBBean.setCreateTime(DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
            //reviewDataEyeSightDBBean.setServerRecoredId("");
            reviewDataEyeSightDBBean.setUserId(dataBean.getMemberId());
            //reviewDataEyeSightDBBean.setUserName("");
            //reviewDataEyeSightDBBean.setUserType("");
            reviewDataEyeSightDBBean.setReviewEyeSightDate(dataBean.getReviewDate());
            reviewDataEyeSightDBBean.setTrainTimeLong(dataBean.getTrainHours());//训练时长
            reviewDataEyeSightDBBean.setReviewEyeSightTimes(dataBean.getReviewCount());


            reviewDataEyeSightDBBean.setLeftEyeSight(dataBean.getNaked_left_eye_degree());
            reviewDataEyeSightDBBean.setRightEyeSight(dataBean.getNaked_right_eye_degree());
            reviewDataEyeSightDBBean.setDoubleEyeSight(dataBean.getNaked_binoculus_degree());
            reviewDataEyeSightDBBeanList.add(reviewDataEyeSightDBBean);
        }

        ReviewDataEyeSightBeanDaoOpe.saveListData(MyApplication.getInstance(), reviewDataEyeSightDBBeanList);
        ;
    }

    /**
     * 从数据库中获取训练日期的数据到 trainTimeList 中
     */
    public void getAllTrainTimeWithLimit() {
        if (!isGetTrainTimeData.get()) {//程序刚启动时调用，还有在点击底部按钮时也会调用，防止两个触发事件同时执行
            isGetTrainTimeData.set(true);
        } else {
            return;
        }

        createSingleExectorService();
        singleExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                if (CommonUtils.isEmpty(Config.getConfig().getUserServerId())) {
                    isGetTrainTimeData.set(false);
                    return;
                }
                if (allOperationDataHaveReported()) {
                    /**
                     * 处理第一次登陆进来，后台有训练数据，程序刚启动时，未登录，不能获取数据，此时需要请求一下后台获取数据
                     */
                    synchronized (trainTimeList) {
                        List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = UserInfoTrainTimeBeanDaoOpe.queryUserInfoTrainTimeServerIDAndLimit(MyApplication.getInstance(), Config.getConfig().getUserServerId(), 10 * 365);
                        trainTimeList.clear();
                        int trainDays = 0;
                        if (null != userInfoTrainTimeDBBeanList) {
                            trainDays = userInfoTrainTimeDBBeanList.size();
                            trainTimeList.addAll(userInfoTrainTimeDBBeanList);
                        }
                        EventBus.getDefault().post(new TrainTimeUpdateStatusEvent(true, trainDays));
                    }
                    getUserTrainTimeInfoFromServer();
                } else {
                    if (!isUploadingData.get()) {
                        synchronized (trainTimeList) {
                            List<UserInfoTrainTimeDBBean> userInfoTrainTimeDBBeanList = UserInfoTrainTimeBeanDaoOpe.queryUserInfoTrainTimeServerIDAndLimit(MyApplication.getInstance(), Config.getConfig().getUserServerId(), 10 * 365);
                            trainTimeList.clear();
                            int trainDays = 0;
                            if (null != userInfoTrainTimeDBBeanList) {
                                trainDays = userInfoTrainTimeDBBeanList.size();
                                trainTimeList.addAll(userInfoTrainTimeDBBeanList);
                            }
                            EventBus.getDefault().post(new TrainTimeUpdateStatusEvent(true, trainDays));
                        }
                        getUserTrainTimeInfoFromServer();
                    }
                }
                isGetTrainTimeData.set(false);
            }
        });
    }

    private boolean allOperationDataHaveReported() {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return false;
        }
        long unReportedDataCount = CommonFeedBackBleDataBeanDaoOpe.getAllUnReportedDataCount(MyApplication.getInstance(), serverId);
        MLog.i("unReportedDataCount = " + unReportedDataCount);
        if (unReportedDataCount == 0) {
            return true;
        }
        return false;
    }

    private void registerInnerNetBroadCastReceiver() {
        String action = MyApplication.getInstance().getPackageName() + BaseConstantString.NET_ACTION;
        IntentFilter intentFilter = new IntentFilter(action);
        LocalBroadcastManager.getInstance(MyApplication.getInstance()).registerReceiver(netBroadCastReceiver, intentFilter);
    }

    private void unRegisterInnerNetBoradCastReceiver() {
        LocalBroadcastManager.getInstance(MyApplication.getInstance()).unregisterReceiver(netBroadCastReceiver);
    }

    public List<UserInfoTrainTimeDBBean> getCurrentUserTrainTimeData() {
        return trainTimeList;
    }


    public byte[] getLastGlassesRunningParams() {
        String paramJson = FileUtils.read(MyApplication.getInstance(), GLASSES_RUNNING_PARAM_FILE_NAME);
        if (!CommonUtils.isEmpty(paramJson)) {
            HttpRequestGlassesRunParamBean httpRequestGlassesRunParamBean = GsonTools.changeGsonToBean(paramJson, HttpRequestGlassesRunParamBean.class);

            if (null != httpRequestGlassesRunParamBean) {
                HttpResponseGlassesRunParamBean responseBean = BleDataBeanConvertUtil.httpRequestBleDataBean2ResponseBean(httpRequestGlassesRunParamBean);
                SparseArray<BaseCmdBean> baseCmdBeanArray = BleDataBeanConvertUtil.httpResponseBleDataBean2BleCmdBean(responseBean);
                byte[] retByteArray = new byte[baseCmdBeanArray.size() * 20];

                for (int i = 0; i < baseCmdBeanArray.size(); i++) {
                    BaseCmdBean baseCmdBean = baseCmdBeanArray.get(i);
                    byte[] sendByte = baseCmdBean.buildCmdByteArray();
                    MLog.d("getLastGlassesRunningParams[" + i + "]" + BaseParseCmdBean.bytesToStringWithSpace(sendByte));
                    System.arraycopy(retByteArray, i * 20, sendByte, 0, 20);
                }
                return retByteArray;
            }
        }
        return null;
    }

    /**
     * 将蓝牙眼镜上传的运行参数存入本地数据库
     */
    public void insertHttpRequestRunParams2DB(HttpRequestGlassesRunParamBean httpRequestGlassesRunParamBean) {
        String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            return;
        }
        String macStr = Config.getConfig().getLastConnectBleGlassesUUID();

        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() == 0) {
            return;
        }
        UserInfoDBBean userInfoBean = userInfoDBBeanList.get(0);
        String phoneStr = userInfoBean.getPhone();
        long phoneLong = getPhoneLongNumber(phoneStr);
        MLog.d("phoneLong = " + phoneLong + "   userCode = " + httpRequestGlassesRunParamBean.getCurrentUserCode());
        if (phoneLong != httpRequestGlassesRunParamBean.getCurrentUserCode()) {
            MLog.e("phoneLong = " + phoneLong + "   userCode = " + httpRequestGlassesRunParamBean.getCurrentUserCode());
        }
        List<RunParamsBleDataDBBean> beanList = new ArrayList<>();
        RunParamsBleDataDBBean runParamsBleDataDBBean = new RunParamsBleDataDBBean();
        runParamsBleDataDBBean.setLocalid(UUID.randomUUID().toString());
        runParamsBleDataDBBean.setCurrentUserCode(httpRequestGlassesRunParamBean.getCurrentUserCode());
        runParamsBleDataDBBean.setMinMinusInterval(httpRequestGlassesRunParamBean.getMinMinusInterval());
        runParamsBleDataDBBean.setMinPlusInterval(httpRequestGlassesRunParamBean.getMinPlusInterval());
        runParamsBleDataDBBean.setCommonNumber(httpRequestGlassesRunParamBean.getCommonNumber());
        runParamsBleDataDBBean.setInterveneAccMinute(httpRequestGlassesRunParamBean.getInterveneAccMinute());
        runParamsBleDataDBBean.setWeekKeyFre(httpRequestGlassesRunParamBean.getWeekKeyFre());
        runParamsBleDataDBBean.setWeekAccMinute(httpRequestGlassesRunParamBean.getWeekAccMinute());
        runParamsBleDataDBBean.setBackWeekAccMinute0(httpRequestGlassesRunParamBean.getBackWeekAccMinute0());
        runParamsBleDataDBBean.setBackWeekAccMinute1(httpRequestGlassesRunParamBean.getBackWeekAccMinute1());
        runParamsBleDataDBBean.setBackWeekAccMinute2(httpRequestGlassesRunParamBean.getBackWeekAccMinute2());
        runParamsBleDataDBBean.setBackWeekAccMinute3(httpRequestGlassesRunParamBean.getBackWeekAccMinute3());
        runParamsBleDataDBBean.setPlusInterval(httpRequestGlassesRunParamBean.getPlusInterval());
        runParamsBleDataDBBean.setMinusInterval(httpRequestGlassesRunParamBean.getMinusInterval());
        runParamsBleDataDBBean.setPlusInc(httpRequestGlassesRunParamBean.getPlusInc());
        runParamsBleDataDBBean.setMinusInc(httpRequestGlassesRunParamBean.getMinusInc());
        runParamsBleDataDBBean.setIncPer(httpRequestGlassesRunParamBean.getIncPer());
        runParamsBleDataDBBean.setRunNumber(httpRequestGlassesRunParamBean.getRunNumber());
        runParamsBleDataDBBean.setRunSpeed(httpRequestGlassesRunParamBean.getRunSpeed());
        runParamsBleDataDBBean.setSpeedInc(httpRequestGlassesRunParamBean.getSpeedInc());
        runParamsBleDataDBBean.setSpeedSegment(httpRequestGlassesRunParamBean.getSpeedSegment());
        runParamsBleDataDBBean.setIntervalSegment(httpRequestGlassesRunParamBean.getIntervalSegment());
        runParamsBleDataDBBean.setBackSpeedSegment(httpRequestGlassesRunParamBean.getBackSpeedSegment());
        runParamsBleDataDBBean.setBackIntervalSegment(httpRequestGlassesRunParamBean.getBackIntervalSegment());
        runParamsBleDataDBBean.setSpeedKeyFre(httpRequestGlassesRunParamBean.getSpeedKeyFre());
        runParamsBleDataDBBean.setInterveneKeyFre(httpRequestGlassesRunParamBean.getInterveneKeyFre());
        runParamsBleDataDBBean.setIntervalAccMinute(httpRequestGlassesRunParamBean.getIntervalAccMinute());
        runParamsBleDataDBBean.setMinusInterval2(httpRequestGlassesRunParamBean.getMinusInterval2());
        runParamsBleDataDBBean.setPlusInterval2(httpRequestGlassesRunParamBean.getPlusInterval2());
        runParamsBleDataDBBean.setMinusInc2(httpRequestGlassesRunParamBean.getMinusInc2());
        runParamsBleDataDBBean.setPlusInc2(httpRequestGlassesRunParamBean.getPlusInc2());
        runParamsBleDataDBBean.setIncPer2(httpRequestGlassesRunParamBean.getIncPer2());
        runParamsBleDataDBBean.setRunNumber2(httpRequestGlassesRunParamBean.getRunNumber2());
        runParamsBleDataDBBean.setRunSpeed2(httpRequestGlassesRunParamBean.getRunSpeed2());
        runParamsBleDataDBBean.setSpeedSegment2(httpRequestGlassesRunParamBean.getSpeedSegment2());
        runParamsBleDataDBBean.setSpeedInc2(httpRequestGlassesRunParamBean.getSpeedInc2());
        runParamsBleDataDBBean.setIntervalSegment2(httpRequestGlassesRunParamBean.getIntervalSegment2());
        runParamsBleDataDBBean.setBackSpeedSegment2(httpRequestGlassesRunParamBean.getBackSpeedSegment2());
        runParamsBleDataDBBean.setBackIntervalSegment2(httpRequestGlassesRunParamBean.getBackIntervalSegment2());
        runParamsBleDataDBBean.setSpeedKeyFre2(httpRequestGlassesRunParamBean.getSpeedKeyFre2());
        runParamsBleDataDBBean.setInterveneKeyFre2(httpRequestGlassesRunParamBean.getInterveneKeyFre2());
        runParamsBleDataDBBean.setIntervalAccMinute2(httpRequestGlassesRunParamBean.getIntervalAccMinute2());
        runParamsBleDataDBBean.setCurrentUserNewUser(httpRequestGlassesRunParamBean.getCurrentUserNewUser());
        runParamsBleDataDBBean.setMonitorDataCMD(httpRequestGlassesRunParamBean.getMonitorDataCMD());


        runParamsBleDataDBBean.setTrainingState(httpRequestGlassesRunParamBean.getTrainingState());
        runParamsBleDataDBBean.setTrainingState2(httpRequestGlassesRunParamBean.getTrainingState2());
        runParamsBleDataDBBean.setAdjustSpeed(httpRequestGlassesRunParamBean.getAdjustSpeed());
        runParamsBleDataDBBean.setMaxRunSpeed(httpRequestGlassesRunParamBean.getMaxRunSpeed());
        runParamsBleDataDBBean.setMinRunSpeed(httpRequestGlassesRunParamBean.getMinRunSpeed());
        runParamsBleDataDBBean.setAdjustSpeed2(httpRequestGlassesRunParamBean.getAdjustSpeed2());
        runParamsBleDataDBBean.setMaxRunSpeed2(httpRequestGlassesRunParamBean.getMaxRunSpeed2());
        runParamsBleDataDBBean.setMinRunSpeed2(httpRequestGlassesRunParamBean.getMinRunSpeed2());
        runParamsBleDataDBBean.setTxByte12(httpRequestGlassesRunParamBean.getTxByte12());
        runParamsBleDataDBBean.setTxByte13(httpRequestGlassesRunParamBean.getTxByte13());
        runParamsBleDataDBBean.setTxByte14(httpRequestGlassesRunParamBean.getTxByte14());
        runParamsBleDataDBBean.setTxByte15(httpRequestGlassesRunParamBean.getTxByte15());
        runParamsBleDataDBBean.setTxByte16(httpRequestGlassesRunParamBean.getTxByte16());
        runParamsBleDataDBBean.setTxByte17(httpRequestGlassesRunParamBean.getTxByte17());
        runParamsBleDataDBBean.setTxByte18(httpRequestGlassesRunParamBean.getTxByte18());

        long currentTime = System.currentTimeMillis();
        runParamsBleDataDBBean.setReceiveLocalTime(currentTime);
        runParamsBleDataDBBean.setReceiveLocalTimeStr(DateUtil.localformatter.format(new Date(currentTime)));
        runParamsBleDataDBBean.setIsReportedServer(false);
        runParamsBleDataDBBean.setGlassesMAC(macStr);
        runParamsBleDataDBBean.setUserId(memberId);

        beanList.add(runParamsBleDataDBBean);
        RunParamsBleDataBeanDaoOpe.insertOrReplaceListData(MyApplication.getInstance(), beanList);
    }

    private long getPhoneLongNumber(String phoneStr) {
        if (!CommonUtils.isEmpty(phoneStr)) {
            if (phoneStr.startsWith("+")) {
                phoneStr = phoneStr.substring(1);
            }

            if (phoneStr.startsWith("86")) {
                phoneStr = phoneStr.substring(2);
            }

            return Long.parseLong(phoneStr);
        }
        return 0;
    }

    /**
     * 将实时反馈的数据存入数据库中
     *
     * @param receiveGlassesFeedbackBleDataBean
     */
    public void insertCommonFeedBackBleData(ReceiveGlassesFeedbackBleDataBean receiveGlassesFeedbackBleDataBean) {
        String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            return;
        }
        String macStr = Config.getConfig().getLastConnectBleGlassesUUID();

        CommonFeedBackBleDataDBBean commonFeedBackBleDataDBBean = new CommonFeedBackBleDataDBBean();
        List<CommonFeedBackBleDataDBBean> commonFeedBackBleDataDBBeanList = new ArrayList<>();
        commonFeedBackBleDataDBBean.setMearsureDistance(receiveGlassesFeedbackBleDataBean.getMearsureDistance());
        commonFeedBackBleDataDBBean.setLocalid(UUID.randomUUID().toString());
        commonFeedBackBleDataDBBean.setUserId(memberId);
        commonFeedBackBleDataDBBean.setGlassesMAC(macStr);

        commonFeedBackBleDataDBBean.setBattery(receiveGlassesFeedbackBleDataBean.getBattery());
        commonFeedBackBleDataDBBean.setTrainTimeYear(receiveGlassesFeedbackBleDataBean.getTrainTimeYear());
        commonFeedBackBleDataDBBean.setTrainTimeMonth(receiveGlassesFeedbackBleDataBean.getTrainTimeMonth());
        commonFeedBackBleDataDBBean.setTrainTimeDay(receiveGlassesFeedbackBleDataBean.getTrainTimeDay());
        commonFeedBackBleDataDBBean.setTrainTimeHour(receiveGlassesFeedbackBleDataBean.getTrainTimeHour());
        commonFeedBackBleDataDBBean.setTrainTimeMinute(receiveGlassesFeedbackBleDataBean.getTrainTimeMinute());
        commonFeedBackBleDataDBBean.setTrainTimeSecond(receiveGlassesFeedbackBleDataBean.getTrainTimeSecond());

        commonFeedBackBleDataDBBean.setInterveneAccMinute(receiveGlassesFeedbackBleDataBean.getInterveneAccMinute());
        commonFeedBackBleDataDBBean.setIntervalAccMinute(receiveGlassesFeedbackBleDataBean.getIntervalAccMinute());
        commonFeedBackBleDataDBBean.setIntervalAccMinute2(receiveGlassesFeedbackBleDataBean.getIntervalAccMinute2());
        commonFeedBackBleDataDBBean.setOperationCmd(receiveGlassesFeedbackBleDataBean.getOperationCmd());

        long currentTime = System.currentTimeMillis();
        commonFeedBackBleDataDBBean.setReceiveLocalTime(currentTime);
        commonFeedBackBleDataDBBean.setReceiveLocalTimeStr(DateUtil.localformatter.format(new Date(currentTime)));
        commonFeedBackBleDataDBBean.setIsReportedServer(false);

        commonFeedBackBleDataDBBeanList.add(commonFeedBackBleDataDBBean);
        CommonFeedBackBleDataBeanDaoOpe.insertOrReplaceListData(MyApplication.getInstance(), commonFeedBackBleDataDBBeanList);
    }

    /**
     * 将干预反馈的数据存入数据库中
     *
     * @param receiveInteveneFeedbackBleDataBean
     */
    public void insertInterveneFeedBackBleData(ReceiveInteveneFeedbackBleDataBean receiveInteveneFeedbackBleDataBean) {
        String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            return;
        }
        String macStr = Config.getConfig().getLastConnectBleGlassesUUID();

        List<InterveneFeedBackBleDataDBBean> beanList = new ArrayList<>();

        InterveneFeedBackBleDataDBBean interveneFeedBackBleDataDBBean = new InterveneFeedBackBleDataDBBean();

        interveneFeedBackBleDataDBBean.setLocalid(UUID.randomUUID().toString());
        interveneFeedBackBleDataDBBean.setInterveneYear(receiveInteveneFeedbackBleDataBean.getInterveneYear());
        interveneFeedBackBleDataDBBean.setInterveneMonth(receiveInteveneFeedbackBleDataBean.getInterveneMonth());
        interveneFeedBackBleDataDBBean.setInterveneDay(receiveInteveneFeedbackBleDataBean.getInterveneDay());
        interveneFeedBackBleDataDBBean.setInterveneHour(receiveInteveneFeedbackBleDataBean.getInterveneHour());

        interveneFeedBackBleDataDBBean.setInterveneMinute(receiveInteveneFeedbackBleDataBean.getInterveneMinute());
        interveneFeedBackBleDataDBBean.setInterveneSecond(receiveInteveneFeedbackBleDataBean.getInterveneSecond());
        interveneFeedBackBleDataDBBean.setWeekKeyFre(receiveInteveneFeedbackBleDataBean.getWeekKeyFre());
        interveneFeedBackBleDataDBBean.setSpeedKeyFre(receiveInteveneFeedbackBleDataBean.getSpeedKeyFre());
        interveneFeedBackBleDataDBBean.setInterveneKeyFre(receiveInteveneFeedbackBleDataBean.getInterveneKeyFre());
        interveneFeedBackBleDataDBBean.setSpeedKeyFre2(receiveInteveneFeedbackBleDataBean.getSpeedKeyFre2());
        interveneFeedBackBleDataDBBean.setInterveneKeyFre2(receiveInteveneFeedbackBleDataBean.getInterveneKeyFre2());
        interveneFeedBackBleDataDBBean.setWeekAccMinute(receiveInteveneFeedbackBleDataBean.getWeekAccMinute());
        interveneFeedBackBleDataDBBean.setMonitorCmd(receiveInteveneFeedbackBleDataBean.getMonitorCmd());
        interveneFeedBackBleDataDBBean.setIsReportedServer(false);

        long currentTime = System.currentTimeMillis();
        interveneFeedBackBleDataDBBean.setReceiveLocalTime(currentTime);
        interveneFeedBackBleDataDBBean.setReceiveLocalTimeStr(DateUtil.localformatter.format(new Date(currentTime)));
        interveneFeedBackBleDataDBBean.setUserId(memberId);
        interveneFeedBackBleDataDBBean.setGlassesMAC(macStr);

        beanList.add(interveneFeedBackBleDataDBBean);
        InterveneFeedBackBleDataBeanDaoOpe.insertOrReplaceListData(MyApplication.getInstance(), beanList);
    }

    /**
     * 根据运行参数的本地id删除数据
     *
     * @param localid
     */
    public void deleteRunParamsBleDataByLocalId(List<String> localid) {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return;
        }
        if (null != localid && localid.size() > 0) {
            RunParamsBleDataBeanDaoOpe.deleteByKeyListData(MyApplication.getInstance(), localid);
        }
    }

    /**
     * 根据实时反馈的本地id删除数据
     *
     * @param localid
     */
    public void deleteCommonFeedbackBleDataByLocalId(List<String> localid) {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return;
        }
        if (null != localid && localid.size() > 0) {
            CommonFeedBackBleDataBeanDaoOpe.deleteByKeyListData(MyApplication.getInstance(), localid);
        }
    }

    /**
     * 根据实时反馈的本地id删除数据
     *
     * @param localid
     */
    public void deleteInterveneFeedbackBleDataByLocalId(List<String> localid) {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return;
        }
        if (null != localid && localid.size() > 0) {
            InterveneFeedBackBleDataBeanDaoOpe.deleteByKeyListData(MyApplication.getInstance(), localid);
        }
    }

    /**
     * 根据 limitCount 查询未上报的运行参数数据
     *
     * @return
     */
    public List<RunParamsBleDataDBBean> getRunParamsUnReporteByLimitCount(int limitCount) {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return null;
        }
        return RunParamsBleDataBeanDaoOpe.queryRunParamsBleDataByReportedServer(MyApplication.getInstance(), serverId, false, limitCount);
    }

    /**
     * 根据 limitCount 查询未上报的实时反馈数据
     *
     * @return
     */
    public List<CommonFeedBackBleDataDBBean> getCommonFeedbackUnReportedDataByLimitCount(int limitCount) {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return null;
        }
        return CommonFeedBackBleDataBeanDaoOpe.queryComFeedbackBleDataByReportedServer(MyApplication.getInstance(), serverId, false, limitCount);
    }

    /**
     * 根据 limitCount 查询未上报的干预反馈数据
     *
     * @return
     */
    public List<InterveneFeedBackBleDataDBBean> getInterveneFeedbackUnReportedDataByLimitCount(int limitCount) {
        String serverId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(serverId)) {
            return null;
        }
        return InterveneFeedBackBleDataBeanDaoOpe.queryInterveneBleDataByReportedServer(MyApplication.getInstance(), serverId, false, limitCount);
    }

    /**
     * 通过运行参数数据库对象 RunParamsBleDataDBBean 获取请求后台的 HttpRequestGlassesRunParamBean
     *
     * @param runParamsBleDataDBBean
     * @return
     */
    private HttpRequestGlassesRunParamBean getHttpReqeuestRunParamByDBBean(RunParamsBleDataDBBean runParamsBleDataDBBean) {
        HttpRequestGlassesRunParamBean httpRequestGlassesRunParamBean = new HttpRequestGlassesRunParamBean();

        httpRequestGlassesRunParamBean.setMemeberId(runParamsBleDataDBBean.getUserId());
        httpRequestGlassesRunParamBean.setUtid(AppUtils.getUtdId());
        httpRequestGlassesRunParamBean.setMac(runParamsBleDataDBBean.getGlassesMAC());
        httpRequestGlassesRunParamBean.setLocalCollectTime(runParamsBleDataDBBean.getReceiveLocalTimeStr());

        httpRequestGlassesRunParamBean.setCurrentUserCode(runParamsBleDataDBBean.getCurrentUserCode());
        httpRequestGlassesRunParamBean.setMinMinusInterval(runParamsBleDataDBBean.getMinMinusInterval());
        httpRequestGlassesRunParamBean.setMinPlusInterval(runParamsBleDataDBBean.getMinPlusInterval());
        httpRequestGlassesRunParamBean.setCommonNumber(runParamsBleDataDBBean.getCommonNumber());
        httpRequestGlassesRunParamBean.setInterveneAccMinute(runParamsBleDataDBBean.getInterveneAccMinute());
        httpRequestGlassesRunParamBean.setWeekKeyFre(runParamsBleDataDBBean.getWeekKeyFre());
        httpRequestGlassesRunParamBean.setWeekAccMinute(runParamsBleDataDBBean.getWeekAccMinute());
        httpRequestGlassesRunParamBean.setBackWeekAccMinute0(runParamsBleDataDBBean.getBackWeekAccMinute0());
        httpRequestGlassesRunParamBean.setBackWeekAccMinute1(runParamsBleDataDBBean.getBackWeekAccMinute1());
        httpRequestGlassesRunParamBean.setBackWeekAccMinute2(runParamsBleDataDBBean.getBackWeekAccMinute2());
        httpRequestGlassesRunParamBean.setBackWeekAccMinute3(runParamsBleDataDBBean.getBackWeekAccMinute3());
        httpRequestGlassesRunParamBean.setPlusInterval(runParamsBleDataDBBean.getPlusInterval());
        httpRequestGlassesRunParamBean.setMinusInterval(runParamsBleDataDBBean.getMinusInterval());
        httpRequestGlassesRunParamBean.setPlusInc(runParamsBleDataDBBean.getPlusInc());
        httpRequestGlassesRunParamBean.setMinusInc(runParamsBleDataDBBean.getMinusInc());
        httpRequestGlassesRunParamBean.setIncPer(runParamsBleDataDBBean.getIncPer());
        httpRequestGlassesRunParamBean.setRunNumber(runParamsBleDataDBBean.getRunNumber());
        httpRequestGlassesRunParamBean.setRunSpeed(runParamsBleDataDBBean.getRunSpeed());
        httpRequestGlassesRunParamBean.setSpeedInc(runParamsBleDataDBBean.getSpeedInc());
        httpRequestGlassesRunParamBean.setSpeedSegment(runParamsBleDataDBBean.getSpeedSegment());
        httpRequestGlassesRunParamBean.setIntervalSegment(runParamsBleDataDBBean.getIntervalSegment());
        httpRequestGlassesRunParamBean.setBackSpeedSegment(runParamsBleDataDBBean.getBackSpeedSegment());
        httpRequestGlassesRunParamBean.setBackIntervalSegment(runParamsBleDataDBBean.getBackIntervalSegment());
        httpRequestGlassesRunParamBean.setSpeedKeyFre(runParamsBleDataDBBean.getSpeedKeyFre());
        httpRequestGlassesRunParamBean.setInterveneKeyFre(runParamsBleDataDBBean.getInterveneKeyFre());
        httpRequestGlassesRunParamBean.setIntervalAccMinute(runParamsBleDataDBBean.getIntervalAccMinute());
        httpRequestGlassesRunParamBean.setMinusInterval2(runParamsBleDataDBBean.getMinusInterval2());
        httpRequestGlassesRunParamBean.setPlusInterval2(runParamsBleDataDBBean.getPlusInterval2());
        httpRequestGlassesRunParamBean.setMinusInc2(runParamsBleDataDBBean.getMinusInc2());
        httpRequestGlassesRunParamBean.setPlusInc2(runParamsBleDataDBBean.getPlusInc2());
        httpRequestGlassesRunParamBean.setIncPer2(runParamsBleDataDBBean.getIncPer2());
        httpRequestGlassesRunParamBean.setRunNumber2(runParamsBleDataDBBean.getRunNumber2());
        httpRequestGlassesRunParamBean.setRunSpeed2(runParamsBleDataDBBean.getRunSpeed2());
        httpRequestGlassesRunParamBean.setSpeedSegment2(runParamsBleDataDBBean.getSpeedSegment2());
        httpRequestGlassesRunParamBean.setSpeedInc2(runParamsBleDataDBBean.getSpeedInc2());
        httpRequestGlassesRunParamBean.setIntervalSegment2(runParamsBleDataDBBean.getIntervalSegment2());
        httpRequestGlassesRunParamBean.setBackSpeedSegment2(runParamsBleDataDBBean.getBackSpeedSegment2());
        httpRequestGlassesRunParamBean.setBackIntervalSegment2(runParamsBleDataDBBean.getBackIntervalSegment2());
        httpRequestGlassesRunParamBean.setSpeedKeyFre2(runParamsBleDataDBBean.getSpeedKeyFre2());
        httpRequestGlassesRunParamBean.setInterveneKeyFre2(runParamsBleDataDBBean.getInterveneKeyFre2());
        httpRequestGlassesRunParamBean.setIntervalAccMinute2(runParamsBleDataDBBean.getIntervalAccMinute2());
        httpRequestGlassesRunParamBean.setCurrentUserNewUser(runParamsBleDataDBBean.getCurrentUserNewUser());
        httpRequestGlassesRunParamBean.setMonitorDataCMD(runParamsBleDataDBBean.getMonitorDataCMD());

        httpRequestGlassesRunParamBean.setTrainingState(runParamsBleDataDBBean.getTrainingState());
        httpRequestGlassesRunParamBean.setTrainingState2(runParamsBleDataDBBean.getTrainingState2());
        httpRequestGlassesRunParamBean.setAdjustSpeed(runParamsBleDataDBBean.getAdjustSpeed());
        httpRequestGlassesRunParamBean.setMaxRunSpeed(runParamsBleDataDBBean.getMaxRunSpeed());
        httpRequestGlassesRunParamBean.setMinRunSpeed(runParamsBleDataDBBean.getMinRunSpeed());
        httpRequestGlassesRunParamBean.setAdjustSpeed2(runParamsBleDataDBBean.getAdjustSpeed2());
        httpRequestGlassesRunParamBean.setMaxRunSpeed2(runParamsBleDataDBBean.getMaxRunSpeed2());
        httpRequestGlassesRunParamBean.setMinRunSpeed2(runParamsBleDataDBBean.getMinRunSpeed2());
        httpRequestGlassesRunParamBean.setTxByte12(runParamsBleDataDBBean.getTxByte12());
        httpRequestGlassesRunParamBean.setTxByte13(runParamsBleDataDBBean.getTxByte13());
        httpRequestGlassesRunParamBean.setTxByte14(runParamsBleDataDBBean.getTxByte14());
        httpRequestGlassesRunParamBean.setTxByte15(runParamsBleDataDBBean.getTxByte15());
        httpRequestGlassesRunParamBean.setTxByte16(runParamsBleDataDBBean.getTxByte16());
        httpRequestGlassesRunParamBean.setTxByte17(runParamsBleDataDBBean.getTxByte17());
        httpRequestGlassesRunParamBean.setTxByte18(runParamsBleDataDBBean.getTxByte18());

        return httpRequestGlassesRunParamBean;
    }


    /**
     * 通过实时反馈数据库对象 CommonFeedBackBleDataDBBean 获取请求后台的 HttpRequestCommonFeedbackBean
     *
     * @param commonFeedBackBleDataDBBean
     * @return
     */
    private HttpRequestCommonFeedbackBean getHttpRequestCommonFeedbackByDBBean(CommonFeedBackBleDataDBBean commonFeedBackBleDataDBBean) {
        HttpRequestCommonFeedbackBean httpRequestCommonFeedbackBean = new HttpRequestCommonFeedbackBean();
        httpRequestCommonFeedbackBean.setMemberId(commonFeedBackBleDataDBBean.getUserId());
        httpRequestCommonFeedbackBean.setGlassMacStr(commonFeedBackBleDataDBBean.getGlassesMAC());
        httpRequestCommonFeedbackBean.setUtdid(AppUtils.getUtdId());
        httpRequestCommonFeedbackBean.setUserCode(commonFeedBackBleDataDBBean.getCurrentUserCode());

        httpRequestCommonFeedbackBean.setLocalId(commonFeedBackBleDataDBBean.getLocalid());

        httpRequestCommonFeedbackBean.setCollectTime(commonFeedBackBleDataDBBean.getReceiveLocalTimeStr());
        httpRequestCommonFeedbackBean.setMearsureDistance(commonFeedBackBleDataDBBean.getMearsureDistance());
        httpRequestCommonFeedbackBean.setBattery(commonFeedBackBleDataDBBean.getBattery());
        httpRequestCommonFeedbackBean.setTrainTimeYear(commonFeedBackBleDataDBBean.getTrainTimeYear());
        httpRequestCommonFeedbackBean.setTrainTimeMonth(commonFeedBackBleDataDBBean.getTrainTimeMonth());
        httpRequestCommonFeedbackBean.setTrainTimeDay(commonFeedBackBleDataDBBean.getTrainTimeDay());
        httpRequestCommonFeedbackBean.setTrainTimeHour(commonFeedBackBleDataDBBean.getTrainTimeHour());
        httpRequestCommonFeedbackBean.setTrainTimeMinute(commonFeedBackBleDataDBBean.getTrainTimeMinute());
        httpRequestCommonFeedbackBean.setTrainTimeSecond(commonFeedBackBleDataDBBean.getTrainTimeSecond());
        httpRequestCommonFeedbackBean.setIntervalAccMinute(commonFeedBackBleDataDBBean.getIntervalAccMinute());
        httpRequestCommonFeedbackBean.setIntervalAccMinute2(commonFeedBackBleDataDBBean.getIntervalAccMinute2());
        httpRequestCommonFeedbackBean.setOperationCmd(commonFeedBackBleDataDBBean.getOperationCmd());

        return httpRequestCommonFeedbackBean;
    }

    /**
     * 通过实时反馈数据库对象 InterveneFeedBackBleDataDBBean 获取请求后台的 HttpRequestInterveneFeedbackBean
     *
     * @param interveneFeedBackBleDataDBBean
     * @return
     */
    private HttpRequestInterveneFeedbackBean getHttpRequestInterveneFeedbackByDBBean(InterveneFeedBackBleDataDBBean interveneFeedBackBleDataDBBean) {
        HttpRequestInterveneFeedbackBean httpRequestInterveneFeedbackBean = new HttpRequestInterveneFeedbackBean();
        httpRequestInterveneFeedbackBean.setMemberId(interveneFeedBackBleDataDBBean.getUserId());
        httpRequestInterveneFeedbackBean.setGlassMacStr(interveneFeedBackBleDataDBBean.getGlassesMAC());
        httpRequestInterveneFeedbackBean.setUtdid(AppUtils.getUtdId());
        httpRequestInterveneFeedbackBean.setUserCode(interveneFeedBackBleDataDBBean.getCurrentUserCode());

        httpRequestInterveneFeedbackBean.setInterveneYear(interveneFeedBackBleDataDBBean.getInterveneYear());
        httpRequestInterveneFeedbackBean.setInterveneMonth(interveneFeedBackBleDataDBBean.getInterveneMonth());
        httpRequestInterveneFeedbackBean.setInterveneDay(interveneFeedBackBleDataDBBean.getInterveneDay());
        httpRequestInterveneFeedbackBean.setInterveneHour(interveneFeedBackBleDataDBBean.getInterveneHour());
        httpRequestInterveneFeedbackBean.setInterveneMinute(interveneFeedBackBleDataDBBean.getInterveneMinute());
        httpRequestInterveneFeedbackBean.setInterveneSecond(interveneFeedBackBleDataDBBean.getInterveneSecond());

        httpRequestInterveneFeedbackBean.setWeekKeyFre(interveneFeedBackBleDataDBBean.getWeekKeyFre());
        httpRequestInterveneFeedbackBean.setSpeedKeyFre(interveneFeedBackBleDataDBBean.getSpeedKeyFre());
        httpRequestInterveneFeedbackBean.setInterveneKeyFre(interveneFeedBackBleDataDBBean.getInterveneKeyFre());
        httpRequestInterveneFeedbackBean.setSpeedKeyFre2(interveneFeedBackBleDataDBBean.getSpeedKeyFre2());
        httpRequestInterveneFeedbackBean.setInterveneKeyFre2(interveneFeedBackBleDataDBBean.getInterveneKeyFre2());
        httpRequestInterveneFeedbackBean.setWeekAccMinute(interveneFeedBackBleDataDBBean.getWeekAccMinute());
        httpRequestInterveneFeedbackBean.setMonitorCmd(interveneFeedBackBleDataDBBean.getMonitorCmd());

        return httpRequestInterveneFeedbackBean;
    }


    /**
     * 获取眼镜的初始化参数
     */
    public void getGlassesInitData() {
        final String memberId = Config.getConfig().getUserServerId();

        if (CommonUtils.isEmpty(memberId)) {
            return;
        }

        if (isGetInitMachingData.get()) {
            return;
        }
        isGetInitMachingData.set(true);


        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IgetDeviceParam.MEMBERID, memberId);
        bodyMap.put(IgetDeviceParam.UTDID, AppUtils.getUtdId());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                machineDataLock.writeLock().lock();
                try {
                    String newStringDataJson = null;
                    try {
                        newStringDataJson = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SPUtils.getInstance().put(SPUtils.KEY_DEVICE_PARAM, newStringDataJson);

                    //MLog.d("newStringDataJson = " + newStringDataJson);
                    HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBeanTmp =
                            (HttpResponseGlassInitDataBackBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseGlassInitDataBackBean>() {
                            });
                    setHttpResponseGlassInitDataBackBean(httpResponseGlassInitDataBackBeanTmp);
                    //mHttpResponseGlassInitDataBackBean = GsonTools.changeGsonToBean(newStringDataJson, HttpResponseGlassInitDataBackBean.class);
                    if (null != getHttpResponseGlassInitDataBackBean() && null != getHttpResponseGlassInitDataBackBean().getData()) {
                        HttpResponseGlassInitDataBackBean.DataBean dataBean = getHttpResponseGlassInitDataBackBean().getData();
                        dataBean.setMemberId(memberId);
                        if (BaseApplication.isDebugMode) {
                            FileUtils.write(MyApplication.getInstance(), HTTP_GLASSES_INIT_MACHINE_FILE_NAME, GsonTools.createGsonString(getHttpResponseGlassInitDataBackBean()));
                            String json = FileUtils.read(MyApplication.getInstance(), HTTP_GLASSES_INIT_MACHINE_FILE_NAME);
                            MLog.d("json = " + json);
                        }

                        checkCurrentMobilePhoneSystemTime(dataBean);
                    } else {
                        setHttpResponseGlassInitDataBackBean(null);
                    }
                    //MLog.d("Server mHttpResponseGlassInitDataBackBean = " + mHttpResponseGlassInitDataBackBean);
                } finally {
                    machineDataLock.writeLock().unlock();
                }
                isGetInitMachingData.set(false);
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();
                isGetInitMachingData.set(false);
            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.getGlassDeviceInitParam(headerMap, bodyMap, disposableObserver);

    }

    private void checkCurrentMobilePhoneSystemTime(@NonNull HttpResponseGlassInitDataBackBean.DataBean dataBean) {
        String serverTime = dataBean.getSystemTime();
        if (!CommonUtils.isEmpty(serverTime)) {
            try {
                long serverTimeLong = DateUtil.localformatter.parse(serverTime).getTime();
                if (Math.abs(serverTimeLong - System.currentTimeMillis()) > 1.5f * 60 * 1000) {
                    /**
                     * 时差超过1.5分钟，则提示手机系统时间不正确
                     */
                    Resources resources = MyApplication.getInstance().getResources();
                    ToastUtil.showLongWithMsgColor(resources.getString(R.string.mobile_phone_system_no_right), resources.getColor(R.color.red_dard_hard_color));
                    MLog.d("system time error");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 上传运行参数
     *
     * @param httpRequestGlassesRunParamBean
     */
    public void postRunParamData(final HttpRequestGlassesRunParamBean httpRequestGlassesRunParamBean) {
        if (!needReport) {
            return;
        }
        createSingleExectorService();
        singleExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                insertHttpRequestRunParams2DB(httpRequestGlassesRunParamBean);

                String serverId = Config.getConfig().getUserServerId();
                if (CommonUtils.isEmpty(serverId)) {
                    return;
                }
                if (NetUtil.isNetworkAvalible(MyApplication.getInstance())) {
                    RunParamsBleDataDBBean runParamsBleDataDBBean = RunParamsBleDataBeanDaoOpe.queryTheNewestBeanByReportedStatus(MyApplication.getInstance(), serverId, false);
                    if (null != runParamsBleDataDBBean) {
                        postRunParamsDataFromDB(runParamsBleDataDBBean);
                    }
                }
            }
        });

    }

    /**
     * 上报实时反馈数据
     *
     * @param receiveGlassesFeedbackBleDataBean
     */
    public void postCommonFeedBackBleData(final ReceiveGlassesFeedbackBleDataBean receiveGlassesFeedbackBleDataBean) {
        if (!needReport) {
            return;
        }
        if (NetUtil.isNetworkAvalible(MyApplication.getInstance())) {

            HashMap<String, Object> headerMap = new HashMap<>();
            HashMap<String, Object> bodyMap = new HashMap<>();

            final String memberId = Config.getConfig().getUserServerId();
            if (CommonUtils.isEmpty(memberId)) {
                return;
            }

            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
            if (null == userInfoDBBeanList || userInfoDBBeanList.size() <= 0) {
                return;
            }
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);


            List<HashMap<String, Object>> list = new ArrayList<>();
            String glassMacStr = Config.getConfig().getLastConnectBleGlassesUUID();
            if (null != receiveGlassesFeedbackBleDataBean) {
                //HttpRequestCommonFeedbackBean httpRequestCommonFeedbackBean = getHttpRequestCommonFeedbackByDBBean(commonFeedBackBleDataDBBean);
                HashMap<String, Object> datalistItemMap = new HashMap<>();

                datalistItemMap.put(IHttpRequestCommonFeedBackBean.mac, glassMacStr);
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.mearsureDistance, receiveGlassesFeedbackBleDataBean.getMearsureDistance());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.battery, receiveGlassesFeedbackBleDataBean.getBattery());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeYear, receiveGlassesFeedbackBleDataBean.getTrainTimeYear());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeMonth, receiveGlassesFeedbackBleDataBean.getTrainTimeMonth());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeDay, receiveGlassesFeedbackBleDataBean.getTrainTimeDay());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeHour, receiveGlassesFeedbackBleDataBean.getTrainTimeHour());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeMinute, receiveGlassesFeedbackBleDataBean.getTrainTimeMinute());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeSecond, receiveGlassesFeedbackBleDataBean.getTrainTimeSecond());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.interveneAccMinute, receiveGlassesFeedbackBleDataBean.getInterveneAccMinute());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.intervalAccMinute, receiveGlassesFeedbackBleDataBean.getIntervalAccMinute());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.intervalAccMinute2, receiveGlassesFeedbackBleDataBean.getIntervalAccMinute2());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.operationCmd, receiveGlassesFeedbackBleDataBean.getOperationCmd());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.controlRun, receiveGlassesFeedbackBleDataBean.getControlCmd());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.userCode, GlassesBleDataModel.getInstance().getCurrentUserId());
                datalistItemMap.put(IHttpRequestCommonFeedBackBean.mobileBluetoothTime, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
                //datalistItemMap.put(IHttpRequestCommonFeedBackBean.locamobileRealTimeId, "");
                list.add(datalistItemMap);
            }


            bodyMap.put(IHttpRequestCommonFeedBackBean.memberId, memberId);
            bodyMap.put(IHttpRequestCommonFeedBackBean.login_name, userInfoDBBean.getLogin_name());
            bodyMap.put(IHttpRequestCommonFeedBackBean.deviceId, glassMacStr);
            bodyMap.put(IHttpRequestCommonFeedBackBean.platform, IBaseRequest.PLATFORM_VALUE_ANDROID);
            //bodyMap.put(IHttpRequestCommonFeedBackBean.start_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
            //bodyMap.put(IHttpRequestCommonFeedBackBean.collect_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
            bodyMap.put(IHttpRequestCommonFeedBackBean.utdid, AppUtils.getUtdId());
            bodyMap.put(IHttpRequestCommonFeedBackBean.areaCode, userInfoDBBean.getAreaCode());
            bodyMap.put(IHttpRequestCommonFeedBackBean.dataList, list);

            DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    httpDispList.remove(this);
                    String jsonStr = null;

                    try {
                        jsonStr = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    boolean postSuccess = false;

                    if (!CommonUtils.isEmpty(jsonStr)) {
                        HttpResponseCommonFeedbackParamsBean httpResponseCommonFeedbackParamsBean = (HttpResponseCommonFeedbackParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponseCommonFeedbackParamsBean>() {
                        });
                        if (null != httpResponseCommonFeedbackParamsBean && httpResponseCommonFeedbackParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                            postSuccess = true;
                        }
                    }

                    if (!postSuccess) {
                        createSingleExectorService();
                        singleExecutorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                insertCommonFeedBackBleData(receiveGlassesFeedbackBleDataBean);
                            }
                        });
                    } else {
                        /**
                         * 更新报表数据
                         */
                        updateReportFormData();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    httpDispList.remove(this);
                    e.printStackTrace();
                    createSingleExectorService();
                    singleExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            insertCommonFeedBackBleData(receiveGlassesFeedbackBleDataBean);
                        }
                    });
                }

                @Override
                public void onComplete() {

                }
            };
            httpDispList.add(disposableObserver);
            TrainSuscribe.postCommonFeedBackData(headerMap, bodyMap, disposableObserver);

        } else {
            createSingleExectorService();
            singleExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    insertCommonFeedBackBleData(receiveGlassesFeedbackBleDataBean);
                }
            });
        }
    }

    /**
     * 上报干预反馈
     *
     * @param receiveInteveneFeedbackBleDataBean
     */
    public void postInterveneFeedBackBleData(final ReceiveInteveneFeedbackBleDataBean receiveInteveneFeedbackBleDataBean) {
        if (!needReport) {
            return;
        }

        if (NetUtil.isNetworkAvalible(MyApplication.getInstance())) {

            final String memberId = Config.getConfig().getUserServerId();
            if (CommonUtils.isEmpty(memberId)) {
                return;
            }

            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
            if (null == userInfoDBBeanList || userInfoDBBeanList.size() <= 0) {
                return;
            }
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);

            List<HashMap<String, Object>> list = new ArrayList<>();
            String glassMacStr = Config.getConfig().getLastConnectBleGlassesUUID();

            if (null != receiveInteveneFeedbackBleDataBean) {

                HashMap<String, Object> datalistItemMap = new HashMap<>();

                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneYear, receiveInteveneFeedbackBleDataBean.getInterveneYear());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneMonth, receiveInteveneFeedbackBleDataBean.getInterveneMonth());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneDay, receiveInteveneFeedbackBleDataBean.getInterveneDay());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneHour, receiveInteveneFeedbackBleDataBean.getInterveneHour());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneMinute, receiveInteveneFeedbackBleDataBean.getInterveneMinute());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneSecond, receiveInteveneFeedbackBleDataBean.getInterveneSecond());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.weekKeyFre, receiveInteveneFeedbackBleDataBean.getWeekKeyFre());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.speedKeyFre, receiveInteveneFeedbackBleDataBean.getSpeedKeyFre());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneKeyFre, receiveInteveneFeedbackBleDataBean.getInterveneKeyFre());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.speedKeyFre2, receiveInteveneFeedbackBleDataBean.getSpeedKeyFre2());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneKeyFre2, receiveInteveneFeedbackBleDataBean.getInterveneKeyFre2());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.weekAccMinute, receiveInteveneFeedbackBleDataBean.getWeekAccMinute());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.monitorCmd, receiveInteveneFeedbackBleDataBean.getMonitorCmd());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.userCode, GlassesBleDataModel.getInstance().getCurrentUserId());
                datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.mobileBluetoothTime, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
                //datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.locamobileRealTimeId, httpRequestInterveneFeedbackBean.getLocalId());
                list.add(datalistItemMap);
            }

            HashMap<String, Object> headerMap = new HashMap<>();
            final HashMap<String, Object> bodyMap = new HashMap<>();

            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.memberId, memberId);
            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.login_name, userInfoDBBean.getLogin_name());
            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.deviceId, glassMacStr);
            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.platform, IBaseRequest.PLATFORM_VALUE_ANDROID);

            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.start_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.collect_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.areaCode, userInfoDBBean.getAreaCode());

            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.utdid, AppUtils.getUtdId());
            bodyMap.put(IHttpInterveneFeedBackFeedBackBean.dataList, list);

            DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    httpDispList.remove(this);
                    String jsonStr = null;

                    try {
                        jsonStr = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    boolean postSuccess = false;

                    if (!CommonUtils.isEmpty(jsonStr)) {
                        HttpResponseInterveneFeedbackParamsBean httpResponseInterveneFeedbackParamsBean = (HttpResponseInterveneFeedbackParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponseInterveneFeedbackParamsBean>() {
                        });
                        if (null != httpResponseInterveneFeedbackParamsBean && httpResponseInterveneFeedbackParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                            postSuccess = true;
                        }
                    }

                    if (!postSuccess) {
                        createSingleExectorService();
                        singleExecutorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                insertInterveneFeedBackBleData(receiveInteveneFeedbackBleDataBean);
                            }
                        });
                    }
                }

                @Override
                public void onError(Throwable e) {
                    httpDispList.remove(this);
                    e.printStackTrace();
                    createSingleExectorService();
                    singleExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            insertInterveneFeedBackBleData(receiveInteveneFeedbackBleDataBean);
                        }
                    });
                }

                @Override
                public void onComplete() {

                }
            };
            httpDispList.add(disposableObserver);
            TrainSuscribe.postInterveneFeedBackData(headerMap, bodyMap, disposableObserver);

        } else {
            createSingleExectorService();
            singleExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    insertInterveneFeedBackBleData(receiveInteveneFeedbackBleDataBean);
                }
            });
        }
    }

    private void createSingleExectorService() {
        if (null == singleExecutorService || singleExecutorService.isShutdown() || singleExecutorService.isTerminated()) {
            singleExecutorService = ThreadPoolUtilsLocal.newSingleThreadPool();
        }
    }


    public void handleBleDataInDB2Server() {

        if (!NetUtil.isNetworkAvalible(MyApplication.getInstance())) {
            return;
        }

        final String userId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(userId)) {
            return;
        }

        createSingleExectorService();
        singleExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                MLog.i("handler db 2 server");
                RunParamsBleDataDBBean runParamsBleDataDBBean = RunParamsBleDataBeanDaoOpe.queryTheNewestBeanByReportedStatus(MyApplication.getInstance(), userId, false);
                if (null != runParamsBleDataDBBean) {
                    MLog.i("handler runparams");
                    postRunParamsDataFromDB(runParamsBleDataDBBean);
                }

                List<CommonFeedBackBleDataDBBean> commonFeedBackBleDataDBBeanList = CommonFeedBackBleDataBeanDaoOpe.queryComFeedbackBleDataByReportedServer(MyApplication.getInstance(), userId, false, 30);
                if (null != commonFeedBackBleDataDBBeanList && commonFeedBackBleDataDBBeanList.size() > 0) {
                    MLog.i("handler commonfeed " + commonFeedBackBleDataDBBeanList.size());
                    postCommonFeedBackDataFromDB(commonFeedBackBleDataDBBeanList);
                }

                List<InterveneFeedBackBleDataDBBean> interveneFeedBackBleDataDBBeanList = InterveneFeedBackBleDataBeanDaoOpe.queryInterveneBleDataByReportedServer(MyApplication.getInstance(), userId, false, 30);
                if (null != interveneFeedBackBleDataDBBeanList && interveneFeedBackBleDataDBBeanList.size() > 0) {
                    MLog.i("handler intervenefeed " + interveneFeedBackBleDataDBBeanList.size());
                    postInterveneFeedBackDataFromDB(interveneFeedBackBleDataDBBeanList);
                }

            }
        });
    }

    /**
     * 运行参数只用上报最后的一个
     *
     * @param runParamsBleDataDBBean
     */
    private void postRunParamsDataFromDB(RunParamsBleDataDBBean runParamsBleDataDBBean) {
        HttpRequestGlassesRunParamBean httpRequestGlassesRunParamBean = getHttpReqeuestRunParamByDBBean(runParamsBleDataDBBean);
        UserInfoDBBean userInfoDBBean = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), httpRequestGlassesRunParamBean.getMemeberId()).get(0);


        List<HashMap<String, Object>> list = new ArrayList<>();
        /**
         * 目前只上传一个运行参数
         */
        for (int i = 0; i < 1; i++) {
            HashMap<String, Object> datalistItemMap = new HashMap<>();
            datalistItemMap.put(IHttpRequestRunParasmBean.mac, httpRequestGlassesRunParamBean.getMac());
            datalistItemMap.put(IHttpRequestRunParasmBean.utdid, httpRequestGlassesRunParamBean.getUtid());
            datalistItemMap.put(IHttpRequestRunParasmBean.localCollectTime, httpRequestGlassesRunParamBean.getLocalCollectTime());
            datalistItemMap.put(IHttpRequestRunParasmBean.userCode, httpRequestGlassesRunParamBean.getCurrentUserCode());
            datalistItemMap.put(IHttpRequestRunParasmBean.minMinusInterval, httpRequestGlassesRunParamBean.getMinMinusInterval());
            datalistItemMap.put(IHttpRequestRunParasmBean.minPlusInterval, httpRequestGlassesRunParamBean.getMinPlusInterval());
            datalistItemMap.put(IHttpRequestRunParasmBean.commonNumber, httpRequestGlassesRunParamBean.getCommonNumber());
            datalistItemMap.put(IHttpRequestRunParasmBean.interveneAccMinute, httpRequestGlassesRunParamBean.getInterveneAccMinute());
            datalistItemMap.put(IHttpRequestRunParasmBean.weekKeyFre, httpRequestGlassesRunParamBean.getWeekKeyFre());
            datalistItemMap.put(IHttpRequestRunParasmBean.weekAccMinute, httpRequestGlassesRunParamBean.getWeekAccMinute());
            datalistItemMap.put(IHttpRequestRunParasmBean.monitorDataCMD, httpRequestGlassesRunParamBean.getMonitorDataCMD());
            datalistItemMap.put(IHttpRequestRunParasmBean.backWeekAccMinute0, httpRequestGlassesRunParamBean.getBackWeekAccMinute0());
            datalistItemMap.put(IHttpRequestRunParasmBean.backWeekAccMinute1, httpRequestGlassesRunParamBean.getBackWeekAccMinute1());
            datalistItemMap.put(IHttpRequestRunParasmBean.backWeekAccMinute2, httpRequestGlassesRunParamBean.getBackWeekAccMinute2());
            datalistItemMap.put(IHttpRequestRunParasmBean.backWeekAccMinute3, httpRequestGlassesRunParamBean.getBackWeekAccMinute3());
            datalistItemMap.put(IHttpRequestRunParasmBean.plusInterval, httpRequestGlassesRunParamBean.getPlusInterval());
            datalistItemMap.put(IHttpRequestRunParasmBean.minusInterval, httpRequestGlassesRunParamBean.getMinusInterval());
            datalistItemMap.put(IHttpRequestRunParasmBean.plusInc, httpRequestGlassesRunParamBean.getPlusInc());
            datalistItemMap.put(IHttpRequestRunParasmBean.minusInc, httpRequestGlassesRunParamBean.getMinusInc());
            datalistItemMap.put(IHttpRequestRunParasmBean.incPer, httpRequestGlassesRunParamBean.getIncPer());
            datalistItemMap.put(IHttpRequestRunParasmBean.runNumber, httpRequestGlassesRunParamBean.getRunNumber());
            datalistItemMap.put(IHttpRequestRunParasmBean.runSpeed, httpRequestGlassesRunParamBean.getRunSpeed());
            datalistItemMap.put(IHttpRequestRunParasmBean.speedInc, httpRequestGlassesRunParamBean.getSpeedInc());
            datalistItemMap.put(IHttpRequestRunParasmBean.speedSegment, httpRequestGlassesRunParamBean.getSpeedSegment());
            datalistItemMap.put(IHttpRequestRunParasmBean.intervalSegment, httpRequestGlassesRunParamBean.getIntervalSegment());
            datalistItemMap.put(IHttpRequestRunParasmBean.backSpeedSegment, httpRequestGlassesRunParamBean.getBackSpeedSegment());
            datalistItemMap.put(IHttpRequestRunParasmBean.backIntervalSegment, httpRequestGlassesRunParamBean.getBackIntervalSegment());
            datalistItemMap.put(IHttpRequestRunParasmBean.speedKeyFre, httpRequestGlassesRunParamBean.getSpeedKeyFre());
            datalistItemMap.put(IHttpRequestRunParasmBean.interveneKeyFre, httpRequestGlassesRunParamBean.getInterveneKeyFre());
            datalistItemMap.put(IHttpRequestRunParasmBean.intervalAccMinute, httpRequestGlassesRunParamBean.getIntervalAccMinute());
            datalistItemMap.put(IHttpRequestRunParasmBean.minusInterval2, httpRequestGlassesRunParamBean.getMinusInterval2());
            datalistItemMap.put(IHttpRequestRunParasmBean.plusInterval2, httpRequestGlassesRunParamBean.getPlusInterval2());
            datalistItemMap.put(IHttpRequestRunParasmBean.minusInc2, httpRequestGlassesRunParamBean.getMinusInc2());
            datalistItemMap.put(IHttpRequestRunParasmBean.plusInc2, httpRequestGlassesRunParamBean.getPlusInc2());
            datalistItemMap.put(IHttpRequestRunParasmBean.incPer2, httpRequestGlassesRunParamBean.getIncPer2());
            datalistItemMap.put(IHttpRequestRunParasmBean.runNumber2, httpRequestGlassesRunParamBean.getRunNumber2());
            datalistItemMap.put(IHttpRequestRunParasmBean.runSpeed2, httpRequestGlassesRunParamBean.getRunSpeed2());
            datalistItemMap.put(IHttpRequestRunParasmBean.speedSegment2, httpRequestGlassesRunParamBean.getSpeedSegment2());
            datalistItemMap.put(IHttpRequestRunParasmBean.speedInc2, httpRequestGlassesRunParamBean.getSpeedInc2());
            datalistItemMap.put(IHttpRequestRunParasmBean.intervalSegment2, httpRequestGlassesRunParamBean.getIntervalSegment2());
            datalistItemMap.put(IHttpRequestRunParasmBean.backSpeedSegment2, httpRequestGlassesRunParamBean.getBackSpeedSegment2());
            datalistItemMap.put(IHttpRequestRunParasmBean.backIntervalSegment2, httpRequestGlassesRunParamBean.getBackIntervalSegment2());
            datalistItemMap.put(IHttpRequestRunParasmBean.speedKeyFre2, httpRequestGlassesRunParamBean.getSpeedKeyFre2());
            datalistItemMap.put(IHttpRequestRunParasmBean.interveneKeyFre2, httpRequestGlassesRunParamBean.getInterveneKeyFre2());
            datalistItemMap.put(IHttpRequestRunParasmBean.intervalAccMinute2, httpRequestGlassesRunParamBean.getIntervalAccMinute2());
            datalistItemMap.put(IHttpRequestRunParasmBean.currentUserNewUser, httpRequestGlassesRunParamBean.getCurrentUserNewUser());
            datalistItemMap.put(IHttpRequestRunParasmBean.trainingState, httpRequestGlassesRunParamBean.getTrainingState());
            datalistItemMap.put(IHttpRequestRunParasmBean.trainingState2, httpRequestGlassesRunParamBean.getTrainingState2());
            datalistItemMap.put(IHttpRequestRunParasmBean.adjustSpeed, httpRequestGlassesRunParamBean.getAdjustSpeed());
            datalistItemMap.put(IHttpRequestRunParasmBean.maxRunSpeed, httpRequestGlassesRunParamBean.getMaxRunSpeed());
            datalistItemMap.put(IHttpRequestRunParasmBean.minRunSpeed, httpRequestGlassesRunParamBean.getMinRunSpeed());
            datalistItemMap.put(IHttpRequestRunParasmBean.adjustSpeed2, httpRequestGlassesRunParamBean.getAdjustSpeed2());
            datalistItemMap.put(IHttpRequestRunParasmBean.maxRunSpeed2, httpRequestGlassesRunParamBean.getMaxRunSpeed2());
            datalistItemMap.put(IHttpRequestRunParasmBean.minRunSpeed2, httpRequestGlassesRunParamBean.getMinRunSpeed2());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte12, httpRequestGlassesRunParamBean.getTxByte12());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte13, httpRequestGlassesRunParamBean.getTxByte13());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte14, httpRequestGlassesRunParamBean.getTxByte14());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte15, httpRequestGlassesRunParamBean.getTxByte15());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte16, httpRequestGlassesRunParamBean.getTxByte16());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte17, httpRequestGlassesRunParamBean.getTxByte17());
            datalistItemMap.put(IHttpRequestRunParasmBean.txByte18, httpRequestGlassesRunParamBean.getTxByte18());

            list.add(datalistItemMap);
        }


        final String localId = runParamsBleDataDBBean.getLocalid();
        final HashMap<String, Object> headerMap = new HashMap<>();
        final HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IHttpRequestRunParasmBean.dataList, list);

        bodyMap.put(IHttpRequestRunParasmBean.memberId, httpRequestGlassesRunParamBean.getMemeberId());
        bodyMap.put(IHttpRequestRunParasmBean.login_name, userInfoDBBean.getLogin_name());
        bodyMap.put(IHttpRequestRunParasmBean.start_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        bodyMap.put(IHttpRequestRunParasmBean.collect_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        bodyMap.put(IHttpRequestRunParasmBean.deviceId, httpRequestGlassesRunParamBean.getMac());
        bodyMap.put(IHttpRequestRunParasmBean.platform, IBaseRequest.PLATFORM_VALUE_ANDROID);
        bodyMap.put(IHttpRequestRunParasmBean.areaCode, userInfoDBBean.getAreaCode());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                String jsonStr = null;

                try {
                    jsonStr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean postSuccess = false;

                if (!CommonUtils.isEmpty(jsonStr)) {
                    HttpResponsePostRunParamsBean httpResponsePostRunParamsBean = (HttpResponsePostRunParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponsePostRunParamsBean>() {
                    });
                    if (null != httpResponsePostRunParamsBean && httpResponsePostRunParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        postSuccess = true;
                    }
                }


                if (postSuccess) {

                    /**
                     * 修改状态
                     */
                    createSingleExectorService();
                    singleExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            prepareGlassesTrainData(true, true, true);
                            String userId = Config.getConfig().getUserServerId();
                            if (!CommonUtils.isEmpty(userId)) {
                                List<String> localIdList = new ArrayList<>();
                                localIdList.add(localId);
                                RunParamsBleDataBeanDaoOpe.updateRunParamsReportStatus(MyApplication.getInstance(), localIdList, true, userId);
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();

            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.postRunParamData(headerMap, bodyMap, disposableObserver);

    }

    /**
     * 实时反馈数据，从数据库中记录上报到云端
     *
     * @param commonFeedBackBleDataDBBeanList
     */
    private void postCommonFeedBackDataFromDB(final List<CommonFeedBackBleDataDBBean> commonFeedBackBleDataDBBeanList) {
        final String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            return;
        }

        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() <= 0) {
            return;
        }
        UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);


        List<HashMap<String, Object>> list = new ArrayList<>();
        String glassMacStr = "";
        for (CommonFeedBackBleDataDBBean commonFeedBackBleDataDBBean : commonFeedBackBleDataDBBeanList) {
            HttpRequestCommonFeedbackBean httpRequestCommonFeedbackBean = getHttpRequestCommonFeedbackByDBBean(commonFeedBackBleDataDBBean);
            HashMap<String, Object> datalistItemMap = new HashMap<>();
            if (CommonUtils.isEmpty(glassMacStr) && !CommonUtils.isEmpty(httpRequestCommonFeedbackBean.getGlassMacStr())) {
                glassMacStr = httpRequestCommonFeedbackBean.getGlassMacStr();
            }
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.mac, httpRequestCommonFeedbackBean.getGlassMacStr());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.mearsureDistance, httpRequestCommonFeedbackBean.getMearsureDistance());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.battery, httpRequestCommonFeedbackBean.getBattery());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeYear, httpRequestCommonFeedbackBean.getTrainTimeYear());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeMonth, httpRequestCommonFeedbackBean.getTrainTimeMonth());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeDay, httpRequestCommonFeedbackBean.getTrainTimeDay());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeHour, httpRequestCommonFeedbackBean.getTrainTimeHour());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeMinute, httpRequestCommonFeedbackBean.getTrainTimeMinute());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.trainTimeSecond, httpRequestCommonFeedbackBean.getTrainTimeSecond());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.interveneAccMinute, httpRequestCommonFeedbackBean.getInterveneAccMinute());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.intervalAccMinute, httpRequestCommonFeedbackBean.getIntervalAccMinute());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.intervalAccMinute2, httpRequestCommonFeedbackBean.getIntervalAccMinute2());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.operationCmd, httpRequestCommonFeedbackBean.getOperationCmd());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.userCode, httpRequestCommonFeedbackBean.getUserCode());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.mobileBluetoothTime, httpRequestCommonFeedbackBean.getCollectTime());
            datalistItemMap.put(IHttpRequestCommonFeedBackBean.mobileInterveneId, httpRequestCommonFeedbackBean.getLocalId());
            list.add(datalistItemMap);
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IHttpRequestCommonFeedBackBean.memberId, memberId);
        bodyMap.put(IHttpRequestCommonFeedBackBean.login_name, userInfoDBBean.getLogin_name());
        bodyMap.put(IHttpRequestCommonFeedBackBean.deviceId, glassMacStr);
        bodyMap.put(IHttpRequestCommonFeedBackBean.platform, IBaseRequest.PLATFORM_VALUE_ANDROID);
        bodyMap.put(IHttpRequestCommonFeedBackBean.areaCode, userInfoDBBean.getAreaCode());

        bodyMap.put(IHttpRequestRunParasmBean.start_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        bodyMap.put(IHttpRequestRunParasmBean.collect_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));

        bodyMap.put(IHttpRequestCommonFeedBackBean.utdid, AppUtils.getUtdId());
        bodyMap.put(IHttpRequestCommonFeedBackBean.dataList, list);

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                String jsonStr = null;

                try {
                    jsonStr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean postSuccess = false;

                if (!CommonUtils.isEmpty(jsonStr)) {
                    HttpResponseCommonFeedbackParamsBean httpResponseCommonFeedbackParamsBean = (HttpResponseCommonFeedbackParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponseCommonFeedbackParamsBean>() {
                    });
                    if (null != httpResponseCommonFeedbackParamsBean && httpResponseCommonFeedbackParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        postSuccess = true;
                    }
                }

                if (postSuccess) {
                    createSingleExectorService();
                    singleExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            CommonFeedBackBleDataBeanDaoOpe.deleteByKeyListDBDataBean(MyApplication.getInstance(), commonFeedBackBleDataDBBeanList);

                        }
                    });

                    /**
                     * 更新报表数据
                     */
                    updateReportFormData();
                }
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();

            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.postCommonFeedBackData(headerMap, bodyMap, disposableObserver);

    }


    /**
     * 干预反馈数据，从数据库中记录上报到云端
     *
     * @param interveneFeedBackBleDataDBBeanList
     */
    private void postInterveneFeedBackDataFromDB(final List<InterveneFeedBackBleDataDBBean> interveneFeedBackBleDataDBBeanList) {

        final String memberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memberId)) {
            return;
        }

        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memberId);
        if (null == userInfoDBBeanList || userInfoDBBeanList.size() <= 0) {
            return;
        }
        UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);

        List<HashMap<String, Object>> list = new ArrayList<>();
        String glassMacStr = "";

        for (InterveneFeedBackBleDataDBBean interveneFeedBackBleDataDBBean : interveneFeedBackBleDataDBBeanList) {
            HttpRequestInterveneFeedbackBean httpRequestInterveneFeedbackBean = getHttpRequestInterveneFeedbackByDBBean(interveneFeedBackBleDataDBBean);
            HashMap<String, Object> datalistItemMap = new HashMap<>();
            if (CommonUtils.isEmpty(glassMacStr) && !CommonUtils.isEmpty(interveneFeedBackBleDataDBBean.getGlassesMAC())) {
                glassMacStr = interveneFeedBackBleDataDBBean.getGlassesMAC();
            }

            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneYear, httpRequestInterveneFeedbackBean.getInterveneYear());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneMonth, httpRequestInterveneFeedbackBean.getInterveneMonth());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneDay, httpRequestInterveneFeedbackBean.getInterveneDay());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneHour, httpRequestInterveneFeedbackBean.getInterveneHour());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneMinute, httpRequestInterveneFeedbackBean.getInterveneMinute());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneSecond, httpRequestInterveneFeedbackBean.getInterveneSecond());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.weekKeyFre, httpRequestInterveneFeedbackBean.getWeekKeyFre());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.speedKeyFre, httpRequestInterveneFeedbackBean.getSpeedKeyFre());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneKeyFre, httpRequestInterveneFeedbackBean.getInterveneKeyFre());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.speedKeyFre2, httpRequestInterveneFeedbackBean.getSpeedKeyFre2());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.interveneKeyFre2, httpRequestInterveneFeedbackBean.getInterveneKeyFre2());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.weekAccMinute, httpRequestInterveneFeedbackBean.getWeekAccMinute());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.monitorCmd, httpRequestInterveneFeedbackBean.getMonitorCmd());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.userCode, httpRequestInterveneFeedbackBean.getUserCode());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.mobileBluetoothTime, httpRequestInterveneFeedbackBean.getCollectTime());
            datalistItemMap.put(IHttpInterveneFeedBackFeedBackBean.locamobileRealTimeId, httpRequestInterveneFeedbackBean.getLocalId());
            list.add(datalistItemMap);
        }

        HashMap<String, Object> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();


        bodyMap.put(IHttpRequestCommonFeedBackBean.deviceId, glassMacStr);

        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.memberId, memberId);
        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.login_name, userInfoDBBean.getLogin_name());
        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.deviceId, glassMacStr);
        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.platform, IBaseRequest.PLATFORM_VALUE_ANDROID);
        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.areaCode, userInfoDBBean.getAreaCode());

        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.start_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));
        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.collect_time, DateUtil.localformatter.format(new Date(System.currentTimeMillis())));

        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.utdid, AppUtils.getUtdId());
        bodyMap.put(IHttpInterveneFeedBackFeedBackBean.dataList, list);

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                String jsonStr = null;

                try {
                    jsonStr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean postSuccess = false;

                if (!CommonUtils.isEmpty(jsonStr)) {
                    HttpResponseInterveneFeedbackParamsBean httpResponseInterveneFeedbackParamsBean = (HttpResponseInterveneFeedbackParamsBean) JsonUtil.json2objectWithDataCheck(jsonStr, new TypeReference<HttpResponseInterveneFeedbackParamsBean>() {
                    });
                    if (null != httpResponseInterveneFeedbackParamsBean && httpResponseInterveneFeedbackParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        postSuccess = true;
                    }
                }

                if (postSuccess) {
                    createSingleExectorService();
                    singleExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            InterveneFeedBackBleDataBeanDaoOpe.deleteByListBeanData(MyApplication.getInstance(), interveneFeedBackBleDataDBBeanList);
                        }
                    });

                }
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();

            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.postInterveneFeedBackData(headerMap, bodyMap, disposableObserver);

    }

    public void queryNewestRunParamBleDataFromServer() {
        final String memeberId = Config.getConfig().getUserServerId();

        if (CommonUtils.isEmpty(memeberId)) {
            return;
        }

        if (isGetRunParamsData.get()) {
            return;
        }
        isGetRunParamsData.set(true);

        HashMap<String, Object> headerMap = new HashMap<>();

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IrecentlyTrainingDetail.MEMBERID, memeberId);
        bodyMap.put(IrecentlyTrainingDetail.UTDID, AppUtils.getUtdId());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                runParamDataLock.writeLock().lock();
                try {
                    String newStringDataJson = null;
                    try {
                        newStringDataJson = new String(responseBody.bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //MLog.d("queryNewestRunParamBleDataFromServer newStringDataJson  = " + newStringDataJson);

                    HttpResponseGetRunParamsBean httpResponseGetRunParamsBean = (HttpResponseGetRunParamsBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseGetRunParamsBean>() {
                    });

                    if (null != httpResponseGetRunParamsBean && httpResponseGetRunParamsBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                        setHttpResponseGlassesRunParamBean(httpResponseGetRunParamsBean.getData());
                        if (null == getHttpResponseGlassesRunParamBean()) {
                            setHttpResponseGlassesRunParamBean(new HttpResponseGlassesRunParamBean());
                        }
                        if (BaseApplication.isDebugMode) {
                            FileUtils.write(MyApplication.getInstance(), HTTP_GLASSES_RUNNING_PARAM_FILE_NAME, GsonTools.createGsonString(getHttpResponseGlassesRunParamBean()));
                            String json = FileUtils.read(MyApplication.getInstance(), HTTP_GLASSES_RUNNING_PARAM_FILE_NAME);
                            MLog.d("json" + json);
                        }

                    }
                    //MLog.d("Server mHttpResponseGlassesRunParamBean = " + mHttpResponseGlassesRunParamBean);
                } finally {
                    runParamDataLock.writeLock().unlock();
                }
                isGetRunParamsData.set(false);
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                e.printStackTrace();
                isGetRunParamsData.set(false);
            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.queryRunParamData(headerMap, bodyMap, disposableObserver);
    }

    public HttpResponseGlassInitDataBackBean getHttpResponseGlassInitDataBackBean() {
        machineDataLock.readLock().lock();
        try {
            return mHttpResponseGlassInitDataBackBean;
        } finally {
            machineDataLock.readLock().unlock();
        }
    }

    public void setHttpResponseGlassInitDataBackBean(HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBean) {
        this.mHttpResponseGlassInitDataBackBean = httpResponseGlassInitDataBackBean;
    }

    public HttpResponseGlassesRunParamBean getHttpResponseGlassesRunParamBean() {
        runParamDataLock.readLock().lock();
        try {
            return mHttpResponseGlassesRunParamBean;
        } finally {
            runParamDataLock.readLock().unlock();
        }
    }

    public void setHttpResponseGlassesRunParamBean(HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean) {
        this.mHttpResponseGlassesRunParamBean = httpResponseGlassesRunParamBean;
    }

    public UserInfoDBBean getUserInfoDBBean() {
        userInfoDBDataLock.readLock().lock();
        try {
            return mUserInfoDBBean;
        } finally {
            userInfoDBDataLock.readLock().unlock();
        }
    }

    public void setUserInfoDBBean(UserInfoDBBean userInfoDBBean) {
        userInfoDBDataLock.writeLock().lock();
        try {
            mUserInfoDBBean = userInfoDBBean;
        } finally {
            userInfoDBDataLock.writeLock().unlock();
        }
    }

    public void prepareGlassesTrainData(boolean forceUpdateInitMachine, boolean forceUpdateRunParamsData, boolean forceUpdateUserInfo) {
        if (NetUtil.isNetworkAvalible(MyApplication.getInstance())) {
            /** 获取眼镜运行所需要的数据
             *
             * */
            if (null == getHttpResponseGlassInitDataBackBean() || forceUpdateInitMachine) {
                TrainModel.getInstance().getGlassesInitData();
            }

            getReviewData();

            if (null == getHttpResponseGlassesRunParamBean() || forceUpdateRunParamsData) {
                TrainModel.getInstance().queryNewestRunParamBleDataFromServer();
            }
        } else {
            //ToastUtil.showLong("网络未连接，无法获取训练数据");
        }

        if (null == getUserInfoDBBean() || forceUpdateUserInfo) {
            String userId = Config.getConfig().getUserServerId();
            if (null != userId) {
                List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), userId);
                if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
                    setUserInfoDBBean(userInfoDBBeanList.get(0));
                }
            }
        }
    }

    public boolean glasseTrainDataPreparseSuccess() {
        if (null != getHttpResponseGlassInitDataBackBean()
                && null != getHttpResponseGlassesRunParamBean()
                && null != getUserInfoDBBean()) {
            return true;
        }
        return false;
    }

    /**
     * 构建用户数据的蓝牙信息
     *
     * @param trainMode
     * @param newUser
     * @param userLens
     * @return
     */
    public SendUserInfoBleCmdBean createUserInfoBleHexDataForTrain(int trainMode, int newUser, int userLens) {

        SendUserInfoBleCmdBean sendUserInfoBleCmdBean = new SendUserInfoBleCmdBean();

        if (null == getUserInfoDBBean()) {
            String serverId = Config.getConfig().getUserServerId();
            if (!CommonUtils.isEmpty(serverId)) {
                List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), serverId);
                if (null != userInfoDBBeanList && userInfoDBBeanList.size() > 0) {
                    setUserInfoDBBean(userInfoDBBeanList.get(0));
                } else {
                    ToastUtil.showShort("数据库查询异常！");
                }
            }
        }
        UserInfoDBBean userInfoDBBeanTmp = null;
        if (null != (userInfoDBBeanTmp = getUserInfoDBBean())) {
            sendUserInfoBleCmdBean.setUserId(GlassesBleDataModel.convertPhone2Long(userInfoDBBeanTmp.getPhone()));
            sendUserInfoBleCmdBean.setUserAge(userInfoDBBeanTmp.getAge());
            sendUserInfoBleCmdBean.setUserLeftEyeSightDegree((int) Math.abs(userInfoDBBeanTmp.getLeft_eye_train_degree()));
            sendUserInfoBleCmdBean.setUserRightSightDegree((int) Math.abs(userInfoDBBeanTmp.getRight_eye_train_degree()));
            sendUserInfoBleCmdBean.setUserEyeSightType(userInfoDBBeanTmp.getDiopter_state());
            sendUserInfoBleCmdBean.setUserTrainMode(trainMode);
            sendUserInfoBleCmdBean.setNewUser(newUser);
            sendUserInfoBleCmdBean.setUserLens(userLens);
        }

        return sendUserInfoBleCmdBean;
    }

    /**
     * 构建机器数据的蓝牙信息
     *
     * @return
     */
    public SendMachineBleCmdBeaan createSendMachineBleHexDataForTrain() {
        SendMachineBleCmdBeaan sendMachineBleCmdBeaan = new SendMachineBleCmdBeaan();

        long currentTime = System.currentTimeMillis();

        String[] yearMonth = DateUtil.localformatterDay.format(new Date(currentTime)).split("-");
        String[] hourMinute = DateUtil.hourMinuteSecondFormat.format(new Date(currentTime)).split("\\:");

        sendMachineBleCmdBeaan.setYear(Integer.parseInt(yearMonth[0]));
        sendMachineBleCmdBeaan.setMonth(Integer.parseInt(yearMonth[1]));
        sendMachineBleCmdBeaan.setDay(Integer.parseInt(yearMonth[2]));

        sendMachineBleCmdBeaan.setHour(Integer.parseInt(hourMinute[0]));
        sendMachineBleCmdBeaan.setMinute(Integer.parseInt(hourMinute[1]));
        sendMachineBleCmdBeaan.setSeconds(Integer.parseInt(hourMinute[2]));

        HttpResponseGlassInitDataBackBean.DataBean dataBean = getHttpResponseGlassInitDataBackBean().getData();//
        if (null != dataBean) {
            sendMachineBleCmdBeaan.setMaxRunNumber(dataBean.getMaxRunNumber());
            sendMachineBleCmdBeaan.setStartSpeed(dataBean.getStartSpeed());
            sendMachineBleCmdBeaan.setSetSpeedInc(dataBean.getSpeedInc());
            sendMachineBleCmdBeaan.setStopSpeed(dataBean.getStopSpeed());
            sendMachineBleCmdBeaan.setCommonSpeed(dataBean.getCommonSpeed());

            sendMachineBleCmdBeaan.setMachineData6(dataBean.getMachineData6());
            sendMachineBleCmdBeaan.setMachineData7(dataBean.getMachineData7());
            sendMachineBleCmdBeaan.setMachineData8(dataBean.getMachineData8());
            sendMachineBleCmdBeaan.setMachineData9(dataBean.getMachineData9());
        }

        return sendMachineBleCmdBeaan;
    }

    /**
     * 构建运行参数的蓝牙数据
     *
     * @return
     */
    public SparseArray<BaseCmdBean> createSendRunParamsBleHexData() {
        return BleDataBeanConvertUtil.httpResponseBleDataBean2BleCmdBean(getHttpResponseGlassesRunParamBean());
    }


    public HttpResponseGlassesRunParamBean getRunParamFromFile() {
        String json = FileUtils.read(MyApplication.getInstance(), HTTP_GLASSES_RUNNING_PARAM_FILE_NAME);
        MLog.d("json" + json);
        HttpResponseGlassesRunParamBean httpResponseGlassesRunParamBean = GsonTools.changeGsonToBean(json, HttpResponseGlassesRunParamBean.class);
        MLog.d("mHttpResponseGlassesRunParamBean = " + httpResponseGlassesRunParamBean);
        return httpResponseGlassesRunParamBean;
    }

    public HttpResponseGlassInitDataBackBean getInitMachineDataFromFile() {
        String json = FileUtils.read(MyApplication.getInstance(), HTTP_GLASSES_INIT_MACHINE_FILE_NAME);
        MLog.d("json" + json);
        HttpResponseGlassInitDataBackBean httpResponseGlassInitDataBackBean = GsonTools.changeGsonToBean(json, HttpResponseGlassInitDataBackBean.class);
        MLog.d("mHttpResponseGlassInitDataBackBean = " + httpResponseGlassInitDataBackBean);
        return httpResponseGlassInitDataBackBean;
    }

    public void checkBindDeviceStatusInBackGround() {
        String mac = Config.getConfig().getLastConnectBleGlassesMac();
        if (!CommonUtils.isEmpty(mac)) {
            checkDeviceBindStatus(mac, null, true, null);
        }
    }

    /**
     * 检查眼镜是否已经被绑定
     */
    public void checkDeviceBindStatus(final String mac, String sn, final boolean doInBackground, final CheckBleMacByServerCallBack checkBleMacByServerCallBack) {
        String memeberId = Config.getConfig().getUserServerId();
        if (CommonUtils.isEmpty(memeberId)) {
            if (!doInBackground) {
                ToastUtil.showShort(R.string.can_not_connect_glass_with_no_login);
            }
            return;
        }

        if (isCheckGlassDevice.get()) {
            return;
        }
        isCheckGlassDevice.set(true);

        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(MyApplication.getInstance(), memeberId);
        UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);

        HashMap<String, Object> headerMap = new HashMap<>();

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put(IBindDevice.MEMBERID, memeberId);
        bodyMap.put(IBindDevice.UTDID, AppUtils.getUtdId());
        bodyMap.put(IBindDevice.MAC, mac);
        bodyMap.put(IBindDevice.SN, sn);
        bodyMap.put(IBindDevice.LOGIN_NAME, userInfoDBBean.getLogin_name());

        DisposableObserver disposableObserver = new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                httpDispList.remove(this);
                isCheckGlassDevice.set(false);

                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HttpResponseBindDeviceBean httpResponseBindDeviceBean = null;
                if (!CommonUtils.isEmpty(newStringDataJson)) {
                    httpResponseBindDeviceBean = (HttpResponseBindDeviceBean) JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<HttpResponseBindDeviceBean>() {
                    });
                }
                boolean avaliable = false;

//                ToastUtil.showLong(newStringDataJson+"");

                if (null != httpResponseBindDeviceBean) {
                    if (httpResponseBindDeviceBean.getStatus().equals(IBaseRequest.SUCCESS)) {

//                        if (!glasseTrainDataPreparseSuccess()) {
//                            if (!doInBackground) {
//                                ToastUtil.showLong(R.string.get_glasses_param_error_try_again_later);
//                            }
//                            prepareGlassesTrainData(false, false, false);
//                        } else {
//                            avaliable = true;
//                        }

                        Config.getConfig().saveLastConnectBleGlassesUUID(httpResponseBindDeviceBean.getData());

                        avaliable = true;

                    } else {
                        ToastUtil.showShort(httpResponseBindDeviceBean.getMessage());

                        if (httpResponseBindDeviceBean.getStatus().equals(IBindDevice.BIND_STATUS_BINDED)) {
                            /**
                             * 如果设备以及被其他用户绑定，则清空本地保存的mac信息
                             */
                            Config.getConfig().saveLastConnectBleGlassesMac(null);
                            avaliable = false;
                        } else if (httpResponseBindDeviceBean.getStatus().equals(IBindDevice.BIND_STATUS_NO_DEVICE)) {
                            avaliable = false;
                        }
                    }
                } else {
                    Config.getConfig().saveLastConnectBleGlassesMac(null);
                    avaliable = false;
                }

                if (null != checkBleMacByServerCallBack) {
                    checkBleMacByServerCallBack.checkStatus(avaliable, mac);
                }
                MLog.i("mac avaliable = " + avaliable);
                if (!avaliable) {
                    BleDeviceManager.getInstance().stopScan();
                    BleDeviceManager.getInstance().stopScanByMac();
                }
            }

            @Override
            public void onError(Throwable e) {
                httpDispList.remove(this);
                isCheckGlassDevice.set(false);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
        httpDispList.add(disposableObserver);
        TrainSuscribe.bindDevice(headerMap, bodyMap, disposableObserver);
    }

    public void clearTrainModelData() {
        closeTimer();
        disposeAllHttp();
        setHttpResponseGlassesRunParamBean(null);
        setHttpResponseGlassInitDataBackBean(null);
        setUserInfoDBBean(null);
        if (null != singleExecutorService) {
            singleExecutorService.shutdownNow();
        }
        restoreHttpStatus();
    }

    private void disposeAllHttp() {
        synchronized (httpDispList) {
            for (DisposableObserver disposableObserver : httpDispList) {
                if (null != disposableObserver && !disposableObserver.isDisposed()) {
                    disposableObserver.dispose();
                }
            }
            httpDispList.clear();
        }
    }

    private void restoreHttpStatus() {
        isUploadingData.set(false);
        isGetTrainTimeData.set(false);
        isGetInitMachingData.set(false);
        isGetRunParamsData.set(false);
        isCheckGlassDevice.set(false);
    }

    public void createTimerAndTask() {
        closeTimer();
        long periodTime = 1L * 1000 * 15;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                MLog.i("timerTask id = " + Thread.currentThread().getId());
                if (BleDeviceManager.getInstance().isGlassesBleDeviceConnected()) {
                    GlassesBleDataModel.getInstance().queryGlassCurrentStatusByTimer();
                } else {
                    closeTimer();
                }
            }
        };
        timer.schedule(timerTask, periodTime, periodTime);
    }

    public void closeTimer() {
        if (null != timerTask) {
            timerTask.cancel();
        }

        if (null != timer) {
            timer.cancel();
        }
        MLog.i("timerTask closeTimer ");
    }

}
