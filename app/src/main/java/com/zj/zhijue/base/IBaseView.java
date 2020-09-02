package com.zj.zhijue.base;

public interface IBaseView {
    void dismissLoading();

    void showLoading();

    void showLoading(String str);

    void showLoading(boolean z);

    void toastMsg(String str);
}