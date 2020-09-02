package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

@Keep
public class HttpResponseUploadExceptionFileBean extends BaseBean {
    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
