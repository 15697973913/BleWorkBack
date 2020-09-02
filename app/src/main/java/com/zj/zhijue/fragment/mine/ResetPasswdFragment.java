package com.zj.zhijue.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.jnative.SecUtil;
import com.android.common.baselibrary.util.ActivityStackUtil;
import com.android.common.baselibrary.util.comutil.security.Md5Util;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.login.PhoneLoginActivity;
import com.zj.zhijue.base.BaseActivity;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.MemberUpdatePasswdResponseBean;
import com.zj.zhijue.ble.BleDeviceManager;
import com.zj.zhijue.greendao.greendaobean.UserInfoDBBean;
import com.zj.zhijue.greendao.greenddaodb.UserInfoBeanDaoOpe;
import com.zj.zhijue.http.request.IBaseRequest;
import com.zj.zhijue.http.request.IMemberUpdatePasswd;
import com.zj.zhijue.http.request.ISendVerficationCode;
import com.zj.zhijue.model.GlassesBleDataModel;
import com.zj.zhijue.model.TrainModel;
import com.zj.zhijue.util.Config;
import com.zj.zhijue.util.jsonutil.JsonUtil;
import com.zj.zhijue.util.view.ui.DeviceUtils;
import com.android.common.baselibrary.util.ToastUtil;
import com.android.common.baselibrary.util.comutil.CommonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class ResetPasswdFragment extends BaseFragment {

    @BindView(R.id.function_item_backlayout)
    LinearLayout backLayout;

    @BindView(R.id.function_item_title_tv)
    AppCompatTextView titleTextView;

    @BindView(R.id.integralconversionrecordlayout)
    LinearLayout integralConversionRecordLayout;

    @BindView(R.id.old_passwd_edittext)
    AppCompatEditText oldPasswdEditText;

    @BindView(R.id.new_first_passwd_edittext)
    AppCompatEditText newFirstEditText;

    @BindView(R.id.new_confirm_passwd_edittext)
    AppCompatEditText newConfirmPasswdEditText;

    @BindView(R.id.resetpasswdsurebtn)
    Button resetPasswdSureBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resetpasswd_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        integralConversionRecordLayout.setVisibility(View.GONE);
        titleTextView.setText("修改密码");
    }

    private void initData() {

    }

    protected void initListener() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        resetPasswdSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.isEmpty(getOldPasswdText())) {
                    ToastUtil.showShortToast("旧密码不能为空");
                } else {
                    if (verifyPasswd()) {
                        queryUserInfoAndUpdatePasswd();
                        ToastUtil.showShortToast("修改密码中");
                    } else {
                        ToastUtil.showShortToast("新密码输入不一致");
                    }
                }
            }
        });
    }

    private String getOldPasswdText() {
        return oldPasswdEditText.getText().toString();
    }

    private String getFirstNewPasswdString() {
        return newFirstEditText.getText().toString();
    }

    private String getNewPasswdConfirmText() {
        return newConfirmPasswdEditText.getText().toString();
    }

    private boolean verifyPasswd() {
        String oldPasswd = getOldPasswdText();
        String firstNewPasswd = getFirstNewPasswdString();
        String confirmPasswd = getNewPasswdConfirmText();
        if (!CommonUtils.isEmpty(oldPasswd)
                && !CommonUtils.isEmpty(firstNewPasswd)
                && !CommonUtils.isEmpty(confirmPasswd)
                && firstNewPasswd.equals(confirmPasswd)) {
            return true;
        }
        return false;
    }

    private void queryUserInfoAndUpdatePasswd() {
        String serverId = Config.getConfig().getUserServerId();
        List<UserInfoDBBean> userInfoDBBeanList = UserInfoBeanDaoOpe.queryUserInfoByServerID(getActivity(), serverId);
        if (null != userInfoDBBeanList && userInfoDBBeanList.size() == 1) {
            UserInfoDBBean userInfoDBBean = userInfoDBBeanList.get(0);
            updateUserPasswd(userInfoDBBean.getLogin_name(), userInfoDBBean.getPhone(), getOldPasswdText(), getFirstNewPasswdString());
        } else {
            ActivityStackUtil.getInstance().finishAllActivity();
            Intent mIntent = new Intent(MyApplication.getInstance(), PhoneLoginActivity.class);
            startActivity(mIntent);
        }

    }

    private void updateUserPasswd(final String account, String phoneNum, String oldPasswd, String newPasswd) {
        final HashMap<String, String> bodyMap = new HashMap<>();

        //bodyMap.put(IMemberFindPasswd.VERSION_CODE, AppUtils.getVersionCode2Str());
        bodyMap.put(IMemberUpdatePasswd.LOGINNAME, account);
        bodyMap.put(IMemberUpdatePasswd.PHONENUM, phoneNum);
        bodyMap.put(IMemberUpdatePasswd.OLDPASSWORD, Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(oldPasswd)));
        bodyMap.put(IMemberUpdatePasswd.NEWPASSWORD, Md5Util.getMD5String(SecUtil.getRSAsinatureWithLogin(newPasswd)));
//        bodyMap.put(IMemberUpdatePasswd.OLDPASSWORD,oldPasswd);
//        bodyMap.put(IMemberUpdatePasswd.NEWPASSWORD,newPasswd);
                bodyMap.put(IMemberUpdatePasswd.IMEI, DeviceUtils.getIMEI());
        bodyMap.put(IMemberUpdatePasswd.PLATFORM, String.valueOf(ISendVerficationCode.PLATFORM_VALUE_ANDROID));
        //bodyMap.put(IMemberFindPasswd.UPDATER, Config.getConfig().getUserServerId());

        HashMap<String, String> headerMap = new HashMap<>();

        LoginSubscribe.updatePasswd(headerMap, bodyMap, new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {

                String jsonBody = null;
                try {
                    jsonBody = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //SdLogUtil.writeCommonLog(" onNext responseBody = " + jsonBody);

                MemberUpdatePasswdResponseBean memberUpdatePasswdResponseBean = (MemberUpdatePasswdResponseBean) JsonUtil.json2objectWithDataCheck(jsonBody, new TypeReference<MemberUpdatePasswdResponseBean>() {
                });
                //SdLogUtil.writeCommonLog(" onNext MemberUpdatePasswdResponseBean = " + memberUpdatePasswdResponseBean);
                if (null != memberUpdatePasswdResponseBean && memberUpdatePasswdResponseBean.getStatus().equals(IBaseRequest.SUCCESS)) {
                    ToastUtil.showShort("修改成功！");
                    getActivity().finish();
//                    Config.getConfig().savePasswd(null);
//                    Config.getConfig().saveFreshToken(null);
//                    Config.getConfig().saveAccessToken(null);
//                    BleDeviceManager.getInstance().stopScan();
//                    BleDeviceManager.getInstance().stopScanByMac();
//                    BleDeviceManager.getInstance().disconnectGlassesBleDevice(true);
//                    GlassesBleDataModel.getInstance().clearModelData();
//                    TrainModel.getInstance().clearTrainModelData();
//                    //要求用户直接登录
//                    BaseActivity.GotoLoginActivity();

                } else {
                    ToastUtil.showShort("修改失败！");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }




}
