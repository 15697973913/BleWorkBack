package com.zj.zhijue.bean;

import androidx.annotation.Keep;

import java.util.List;

/**
 * 最近一周的签到信息
 */
@Keep
public class SignInRecentResponseBean extends BaseBean {
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
        private int state;//state	int	签到状态（0-接口异常，1-查询成功）
        private EntityBean entity;

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

        public EntityBean getEntity() {
            return entity;
        }

        public void setEntity(EntityBean entity) {
            this.entity = entity;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", state=" + state +
                    ", entity=" + entity +
                    '}';
        }
    }

    @Keep
    public class EntityBean {

        private String member_id;//	string	用户ID
        private String total;//	int	总签到数
        private List<String> recentWeeks;//	array	最近一周签到日期 "2018-12-18T00:00:00"

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<String> getRecentWeeks() {
            return recentWeeks;
        }

        public void setRecentWeeks(List<String> recentWeeks) {
            this.recentWeeks = recentWeeks;
        }

        @Override
        public String toString() {
            return "EntityBean{" +
                    "member_id='" + member_id + '\'' +
                    ", total='" + total + '\'' +
                    ", recentWeeks=" + recentWeeks +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SignInRecentResponseBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
