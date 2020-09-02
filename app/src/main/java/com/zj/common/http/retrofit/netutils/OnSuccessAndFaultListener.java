package com.zj.common.http.retrofit.netutils;


public interface OnSuccessAndFaultListener {
    void onSuccess(String result);

    void onFault(String errorMsg);
}
