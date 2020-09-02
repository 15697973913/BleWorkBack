package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

@Keep
public class HttpResponseInterveneFeedbackParamsBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String memberId;
        private String systemTime;
    }
}
