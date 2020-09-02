package com.zj.zhijue.bean.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

@Keep
public class HttpTrainInfoResponseBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String memberId;//: "1e3d6471-9b99-4533-b1d0-2c31e3d12f66"

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "memberId='" + memberId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HttpTrainInfoResponseBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
