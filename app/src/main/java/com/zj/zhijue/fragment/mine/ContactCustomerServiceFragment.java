package com.zj.zhijue.fragment.mine;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.android.common.baselibrary.log.MLog;
import com.android.common.baselibrary.util.ToastUtil;
import com.zj.common.http.retrofit.netsubscribe.LoginSubscribe;
import com.zj.zhijue.R;
import com.zj.zhijue.base.BaseFragment;
import com.zj.zhijue.bean.ConfigInfoBean;
import com.zj.zhijue.util.jsonutil.JsonUtil;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class ContactCustomerServiceFragment extends BaseFragment {
    @BindView(R.id.function_item_title_tv)
    TextView functionItemTextView;

    @BindView(R.id.function_item_backlayout)
    LinearLayout functionItemBackLayout;

    @BindView(R.id.contactcustomerphonetv)
    AppCompatTextView contactCustomPhoneTextView;
    private ConfigInfoBean infoBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_customer_service_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        functionItemTextView.setText(getResources().getText(R.string.contact_customer_service_text));


        postConfigInfo();
    }

    protected void initListener() {
        functionItemBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        contactCustomPhoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + contactCustomPhoneTextView.getText().toString());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取协议信息
     */
    private void postConfigInfo() {
        final HashMap<String, String> headerMap = new HashMap<>(1);
        final HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("type", "8");

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
                setcontaceCustomPhone(infoBean.getData().getRemark());
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

    private void setcontaceCustomPhone(String phone) {
        SpannableString msp2 = new SpannableString(phone);
        msp2.setSpan(new UnderlineSpan(), 0, phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp2.setSpan(new ForegroundColorSpan(Color.rgb(55, 160, 225)), 0, phone.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contactCustomPhoneTextView.setText(msp2);
        contactCustomPhoneTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
