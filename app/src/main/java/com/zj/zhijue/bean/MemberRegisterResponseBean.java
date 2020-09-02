package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * Created by Administrator on 2018/12/1.
 * 注册
 */
@Keep
public class MemberRegisterResponseBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String phonenum;
        private String memberId;
        private String loginname;

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "phonenum='" + phonenum + '\'' +
                    ", memberId='" + memberId + '\'' +
                    ", loginname='" + loginname + '\'' +
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
