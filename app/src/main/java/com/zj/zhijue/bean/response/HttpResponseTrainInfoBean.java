package com.zj.zhijue.bean.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

@Keep
public class HttpResponseTrainInfoBean extends BaseBean {

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
        private double left_eye_degrees;//": 300.0,
        private double right_eye_degrees;//": 300.0,
        private double left_astigmatism_degree;//": 50.0,
        private double right_astigmatism_degree;//": 60.0,
        private double astigmatism_degree;//": 55.0,
        private double left_upper_limit;//": 300.0,
        private double left_lower_limit;//": -250.0,
        private int left_frequency;//": 10,
        private int left_speed;//": 2,
        private double right_upper_limit;//": 800.0,
        private double right_lower_limit;//": -150.0,
        private int right_frequency;//": 5,
        private int right_speed;//": null

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public double getLeft_eye_degrees() {
            return left_eye_degrees;
        }

        public void setLeft_eye_degrees(double left_eye_degrees) {
            this.left_eye_degrees = left_eye_degrees;
        }

        public double getRight_eye_degrees() {
            return right_eye_degrees;
        }

        public void setRight_eye_degrees(double right_eye_degrees) {
            this.right_eye_degrees = right_eye_degrees;
        }

        public double getLeft_astigmatism_degree() {
            return left_astigmatism_degree;
        }

        public void setLeft_astigmatism_degree(double left_astigmatism_degree) {
            this.left_astigmatism_degree = left_astigmatism_degree;
        }

        public double getRight_astigmatism_degree() {
            return right_astigmatism_degree;
        }

        public void setRight_astigmatism_degree(double right_astigmatism_degree) {
            this.right_astigmatism_degree = right_astigmatism_degree;
        }

        public double getAstigmatism_degree() {
            return astigmatism_degree;
        }

        public void setAstigmatism_degree(double astigmatism_degree) {
            this.astigmatism_degree = astigmatism_degree;
        }

        public double getLeft_upper_limit() {
            return left_upper_limit;
        }

        public void setLeft_upper_limit(double left_upper_limit) {
            this.left_upper_limit = left_upper_limit;
        }

        public double getLeft_lower_limit() {
            return left_lower_limit;
        }

        public void setLeft_lower_limit(double left_lower_limit) {
            this.left_lower_limit = left_lower_limit;
        }

        public int getLeft_frequency() {
            return left_frequency;
        }

        public void setLeft_frequency(int left_frequency) {
            this.left_frequency = left_frequency;
        }

        public int getLeft_speed() {
            return left_speed;
        }

        public void setLeft_speed(int left_speed) {
            this.left_speed = left_speed;
        }

        public double getRight_upper_limit() {
            return right_upper_limit;
        }

        public void setRight_upper_limit(double right_upper_limit) {
            this.right_upper_limit = right_upper_limit;
        }

        public double getRight_lower_limit() {
            return right_lower_limit;
        }

        public void setRight_lower_limit(double right_lower_limit) {
            this.right_lower_limit = right_lower_limit;
        }

        public int getRight_frequency() {
            return right_frequency;
        }

        public void setRight_frequency(int right_frequency) {
            this.right_frequency = right_frequency;
        }

        public int getRight_speed() {
            return right_speed;
        }

        public void setRight_speed(int right_speed) {
            this.right_speed = right_speed;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "memberId='" + memberId + '\'' +
                    ", left_eye_degrees=" + left_eye_degrees +
                    ", right_eye_degrees=" + right_eye_degrees +
                    ", left_astigmatism_degree=" + left_astigmatism_degree +
                    ", right_astigmatism_degree=" + right_astigmatism_degree +
                    ", astigmatism_degree=" + astigmatism_degree +
                    ", left_upper_limit=" + left_upper_limit +
                    ", left_lower_limit=" + left_lower_limit +
                    ", left_frequency=" + left_frequency +
                    ", left_speed=" + left_speed +
                    ", right_upper_limit=" + right_upper_limit +
                    ", right_lower_limit=" + right_lower_limit +
                    ", right_frequency=" + right_frequency +
                    ", right_speed=" + right_speed +
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
