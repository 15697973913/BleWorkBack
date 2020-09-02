package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

/**
 * 录入复查数据响应结果
 */
@Keep
public class HttpResponseInputReviewDataBean extends BaseBean {
  private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponseBindDeviceBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
