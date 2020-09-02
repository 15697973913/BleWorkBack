package com.zj.zhijue.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.common.baselibrary.log.MLog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zj.zhijue.R;
import com.zj.zhijue.event.PayStateEventBean;
import com.zj.zhijue.pay.PaymentUtil;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(PaymentUtil.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);

        MLog.e("微信支付--onCreate>" + PaymentUtil.WECHAT_APP_ID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        MLog.e("微信支付--onReq>" + req.getType() + "--" + req.openId);

    }

    @Override
    public void onResp(BaseResp resp) {

        MLog.e("微信支付--onResp>" + resp.errStr + "--->" + resp.getType() + "--->" + resp.errCode);

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            default:
                break;
        }
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode == BaseResp.ErrCode.ERR_OK) {
            //支付成功
            PayStateEventBean updateEventBean = new PayStateEventBean(PaymentUtil.PAYMENT_SUCCESS,0);
            EventBus.getDefault().post(updateEventBean);

        } else {
            PayStateEventBean updateEventBean = new PayStateEventBean(PaymentUtil.PAYMENT_FAIL,0);
            EventBus.getDefault().post(updateEventBean);
        }
        finish();

    }
}