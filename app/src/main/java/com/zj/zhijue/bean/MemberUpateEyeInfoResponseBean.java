package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * 更新视力信息
 */
@Keep
public class MemberUpateEyeInfoResponseBean extends BaseBean {
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
        private MemberBean member;

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

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", state=" + state +
                    ", member=" + member +
                    '}';
        }
    }

    @Keep
    public class MemberBean {
         private String member_id;//": "399a3eb3-d24b-4eec-bddc-b2ef2de73225",
         private float left_eye_train_degree;//": 1.45,
         private float right_eye_train_degree;//": 1.35

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public float getLeft_eye_train_degree() {
            return left_eye_train_degree;
        }

        public void setLeft_eye_train_degree(float left_eye_train_degree) {
            this.left_eye_train_degree = left_eye_train_degree;
        }

        public float getRight_eye_train_degree() {
            return right_eye_train_degree;
        }

        public void setRight_eye_train_degree(float right_eye_train_degree) {
            this.right_eye_train_degree = right_eye_train_degree;
        }

        @Override
        public String toString() {
            return "MemberBean{" +
                    "member_id='" + member_id + '\'' +
                    ", left_eye_train_degree=" + left_eye_train_degree +
                    ", right_eye_train_degree=" + right_eye_train_degree +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "MemberUpateEyeInfoResponseBean{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
