package com.zj.zhijue.bean;

import androidx.annotation.Keep;

/**
 * 绑定设备
 */
@Keep
public class MemberBindDeviceResponseBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private int age;//: 30
        private String leftHandleDegree;//: 102.1
        private String memberId;//: "1e3d6471-9b99-4533-b1d0-2c31e3d12f66"
        private String areaCode;
        private String rightHandleDegree;//: 102.15

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getLeftHandleDegree() {
            return leftHandleDegree;
        }

        public void setLeftHandleDegree(String leftHandleDegree) {
            this.leftHandleDegree = leftHandleDegree;
        }

        public String getRightHandleDegree() {
            return rightHandleDegree;
        }

        public void setRightHandleDegree(String rightHandleDegree) {
            this.rightHandleDegree = rightHandleDegree;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "age=" + age +
                    ", left_handle_degree='" + leftHandleDegree + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", memberId='" + memberId + '\'' +
                    ", right_handle_degree='" + rightHandleDegree + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MemberBindDeviceResponseBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
