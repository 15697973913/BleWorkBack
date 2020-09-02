package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;
import com.zj.zhijue.bean.response.HttpResponseGlassesRunParamBean;

/**
 * 获取运行参数
 */
@Keep
public class HttpResponseGetRunParamsBean extends BaseBean {
    private HttpResponseGlassesRunParamBean data;

    public HttpResponseGlassesRunParamBean getData() {
        return data;
    }

    public void setData(HttpResponseGlassesRunParamBean data) {
        this.data = data;
    }
}
