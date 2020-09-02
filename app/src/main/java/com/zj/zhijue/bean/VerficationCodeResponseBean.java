package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * Created by Administrator on 2018/12/1.
 * 请求验证码
 */
@Keep
public class VerficationCodeResponseBean extends BaseBean {
    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String verficationCode;
        private String phonenum;
        private String smsType;

        public String getVerficationCode() {
            return verficationCode;
        }

        public void setVerficationCode(String verficationCode) {
            this.verficationCode = verficationCode;
        }

       public String getPhonenum() {
           return phonenum;
       }

       public void setPhonenum(String phonenum) {
           this.phonenum = phonenum;
       }

        public String getSmsType() {
            return smsType;
        }

        public void setSmsType(String smsType) {
            this.smsType = smsType;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "verficationCode='" + verficationCode + '\'' +
                    ", phonenum='" + phonenum + '\'' +
                    ", smsType='" + smsType + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VerficationCodeResponseBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
