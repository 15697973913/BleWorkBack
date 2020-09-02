package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * 9.获取时长提醒时间
 */
@Keep
public class RemindTimeBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    @Keep
    public class DataBean {
        private String remindTime;//提醒时间（分钟）
        private String isEnable;//是否启用(Y启用，N不启用)

        public String getRemindTime() {
            return remindTime;
        }

        public void setRemindTime(String remindTime) {
            this.remindTime = remindTime;
        }

        public String getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(String isEnable) {
            this.isEnable = isEnable;
        }
    }

    @Override
    public String toString() {
        return "MemberLoginResponseBean{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
