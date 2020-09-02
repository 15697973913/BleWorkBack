package com.zj.zhijue.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.app.PayTask;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.CommonUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zj.common.http.retrofit.netapi.URLConstant;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.bean.ConfigInfoBean;
import com.zj.zhijue.bean.RechargeTimeBean;
import com.zj.zhijue.bean.event.PortraitUpdateEventBean;
import com.zj.zhijue.event.CallbackDataEvent;
import com.zj.zhijue.event.EventConstant;
import com.zj.zhijue.event.PayStateEventBean;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.pay.PaymentUtil;
import com.zj.zhijue.pay.alipay.PayActivity;
import com.zj.zhijue.pay.alipay.PayResult;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.Json2Util;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 充值时长
 *
 * @author xp
 * 2020-6-24 15:58:00
 */
public class RechargeTimeActivity extends BaseActivity {

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView functionItemTitleTv;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvNormalPrice)
    TextView tvNormalPrice;
    @BindView(R.id.tvFirstPrice)
    TextView tvFirstPrice;
    @BindView(R.id.tvServiceName)
    TextView tvServiceName;
    @BindView(R.id.rlWechatPay)
    RelativeLayout rlWechatPay;
    @BindView(R.id.rlAlipayPay)
    RelativeLayout rlAlipayPay;
    @BindView(R.id.ivWechatPay)
    ImageView ivWechatPay;
    @BindView(R.id.ivAlipayPay)
    ImageView ivAlipayPay;
    @BindView(R.id.tvPayAgreement)
    TextView tvPayAgreement;
    @BindView(R.id.tvToPay)
    TextView tvToPay;

    //支付类型 0微信 ，1支付宝
    private int mPayType = 0;
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 0x2121;


    private UserInfoDBBean mUserInfoDBBean;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_recharge_time);
        super.onCreate(bundle);
        ButterKnife.bind(this);

        initView();

        postProductInfo();
        postConfigInfo();
    }

    private void initView() {
        functionItemTitleTv.setText(R.string.res_recharge_time);

        mPayType = 0;
        ivWechatPay.setImageResource(R.drawable.ic_select);
        ivAlipayPay.setImageResource(R.drawable.ic_unselect);

        String serverId = Config.getConfig().getUserServerId();
        if (!CommonUtils.isEmpty(serverId)) {
            List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryRawUserInfoByServerID(MyApplication.getInstance(), serverId);
            mUserInfoDBBean = userInfoDBBeanList.get(0);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @OnClick({R.id.function_item_backlayout, R.id.rlWechatPay, R.id.rlAlipayPay, R.id.tvPayAgreement, R.id.tvToPay})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.function_item_backlayout:
                finish();
                break;
            case R.id.rlWechatPay:
                mPayType = 0;
                ivWechatPay.setImageResource(R.drawable.ic_select);
                ivAlipayPay.setImageResource(R.drawable.ic_unselect);
                break;
            case R.id.rlAlipayPay:
                mPayType = 1;
                ivWechatPay.setImageResource(R.drawable.ic_unselect);
                ivAlipayPay.setImageResource(R.drawable.ic_select);
                break;
            case R.id.tvPayAgreement:
                if (null != infoBean && null != infoBean.getData()) {
                    Intent intent = new Intent(RechargeTimeActivity.this, AgreementInfoActivity.class);
                    intent.putExtra("mHtmlText", infoBean.getData().getRemark());
                    startActivity(intent);
                } else {
                    postConfigInfo();
                }
                break;
            case R.id.tvToPay:
                postCreateOrder();
                break;
            default:
                break;
        }
    }


    /**
     * 获取充值服务信息
     */
    private void postProductInfo() {
        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();
        //bodyMap.put(IMemberTrainTime.VERSION_CODE, String.valueOf(AppUtils.getVersionCode()));

        LoginSubscribe.postProductInfo(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                    Log.e("====postProductInfo ", newStringDataJson);

                    JSONObject base = new JSONObject(newStringDataJson);
                    JSONObject data = base.getJSONObject("data");
                    RechargeTimeBean.DataBean dataBean = new RechargeTimeBean.DataBean();
                    dataBean.setFirstPrice(Json2Util.getString(data, "firstPrice"));
                    dataBean.setProductName(Json2Util.getString(data, "productName"));
                    dataBean.setProductUrl(Json2Util.getString(data, "productUrl"));
                    dataBean.setPrice(Json2Util.getString(data, "price"));
                    dataBean.setServerDuration(Json2Util.getString(data, "serverDuration"));
                    dataBean.setIsNew(Json2Util.getInt(data, "isNew"));
                    dataBean.setBargainTotalPrice(Json2Util.getString(data, "bargainTotalPrice"));

                    RechargeTimeBean timeBean = new RechargeTimeBean();
                    timeBean.setData(dataBean);

                    if (null != timeBean && null != timeBean.getData()) {

//                        ViewAdapter.setImage(ivImage, timeBean.getData().getProductUrl());
                        Glide.with(RechargeTimeActivity.this)
                                .load(com.zj.zhijue.util.CommonUtils.diffAvatar(URLConstant.BASE_URL_ZJFD+"/zj",timeBean.getData().getProductUrl()))
                                .thumbnail(0.2f)
                                .apply( new RequestOptions().placeholder(R.mipmap.qidongtubiao).error(R.mipmap.qidongtubiao))
                                .into(ivImage);
                        tvServiceName.setText(timeBean.getData().getProductName());
                        tvNormalPrice.setText(String.format(getString(R.string.res_recharge_time_normal_price),
                                timeBean.getData().getPrice(), timeBean.getData().getServerDuration()));
                        if(timeBean.getData().getIsNew() == 0){
                            tvFirstPrice.setText(String.format(getString(R.string.res_recharge_time_first_price),
                                    timeBean.getData().getFirstPrice()));
                        }else{
                            tvFirstPrice.setText(String.format(getString(R.string.res_recharge_time_bargain_price), timeBean.getData().getBargainTotalPrice()));
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private ConfigInfoBean infoBean;

    /**
     * 获取协议信息
     */
    private void postConfigInfo() {
        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("type", "7");

        LoginSubscribe.postConfigInfo(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                    MLog.e(newStringDataJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                infoBean = (ConfigInfoBean)
                        JsonUtil.json2objectWithDataCheck(newStringDataJson, new TypeReference<ConfigInfoBean>() {
                        });

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 下单
     */
    private void postCreateOrder() {
        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("buyType", "1");//1普通充值，2砍价充值
        bodyMap.put("payType", String.valueOf(mPayType));//0微信，1支付宝

        LoginSubscribe.postCreateOrder(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                String newStringDataJson = null;
                try {
                    newStringDataJson = new String(responseBody.bytes());
                    MLog.e(newStringDataJson);

                    if (mPayType == 0) {
                        JSONObject base = new JSONObject(newStringDataJson);
                       JSONObject data = base.getJSONObject("data");

                        String packageStr = Json2Util.getString(data, "package");
                        String appid = Json2Util.getString(data, "appid");
                        String sign = Json2Util.getString(data, "sign");
                        String partnerid = Json2Util.getString(data, "partnerid");
                        String prepayid = Json2Util.getString(data, "prepayid");
                        String noncestr = Json2Util.getString(data, "noncestr");
                        String timestamp = Json2Util.getString(data, "timestamp");

                        PayReq req = new PayReq();
                        req.appId = appid;
                        req.partnerId = partnerid;
                        req.prepayId = prepayid;
                        req.nonceStr = noncestr;
                        req.timeStamp = timestamp;
                        req.packageValue = packageStr;
                        req.sign = sign;

                        api = WXAPIFactory.createWXAPI(RechargeTimeActivity.this, null);
                        api.registerApp(PaymentUtil.WECHAT_APP_ID);
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                    } else {
                        JSONObject base = new JSONObject(newStringDataJson);
                        String parameter = base.get("data").toString();

                        if (!StringUtils.isEmpty(parameter)) {
                            toAliPay(parameter);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShortToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 进行alipay支付
     */
    public void toAliPay(final String parameter) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeTimeActivity.this);
                Map<String, String> result = alipay.payV2(parameter, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 处理更新头像的操作
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payStateChange(PayStateEventBean payStateEventBean) {
        if (payStateEventBean.payState== PaymentUtil.PAYMENT_SUCCESS&&payStateEventBean.payType==0){
            finish();
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功
                        finish();
                        PayStateEventBean updateEventBean = new PayStateEventBean(PaymentUtil.PAYMENT_SUCCESS,mPayType);
                        EventBus.getDefault().post(updateEventBean);

                    } else {
                        ToastUtil.showShort(getString(R.string.string_payfail));
                    }
                    MLog.e(resultInfo + "---" + resultStatus);

                    break;

                default:
                    break;
            }
        }

    };
}
