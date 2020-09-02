package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * Created by Administrator on 2018/12/1.
 * 注册
 */
@Keep
public class MemberFindPasswdResponseBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String msg;
        private int state;
        private String memberId;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", state=" + state +
                    ", memberId='" + memberId + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "MemberRegisterResponseBean{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
