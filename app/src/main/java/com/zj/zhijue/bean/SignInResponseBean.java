package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * 签到
 */
@Keep
public class SignInResponseBean extends BaseBean {
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

        private String id;//	string	签到记录ID
        private String member_id;//	string	用户ID
        private String login_name;//	string	登录用户名
        private String nick_name;//	string	昵称
        private String name;//	string	真实姓名
        private String sign_time;//签到时间

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        @Override
        public String toString() {
            return "EntityBean{" +
                    "id='" + id + '\'' +
                    ", member_id='" + member_id + '\'' +
                    ", login_name='" + login_name + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", name='" + name + '\'' +
                    ", sign_time='" + sign_time + '\'' +
                    '}';
        }
    }


}
