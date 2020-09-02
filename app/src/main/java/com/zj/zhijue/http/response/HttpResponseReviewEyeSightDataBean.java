package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

import java.util.List;

/**
 * 视力复查 HTTP 响应 Bean
 */
@Keep
public class HttpResponseReviewEyeSightDataBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    @Keep
    public class DataBean {
       private String memberId;//
       private String reviewDate;//
       private int reviewCount;
       private float trainHours;//": 15,
       private double naked_left_eye_degree;
       private double naked_right_eye_degree;
       private double naked_binoculus_degree;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(String reviewDate) {
            this.reviewDate = reviewDate;
        }

        public int getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
        }

        public float getTrainHours() {
            return trainHours;
        }

        public void setTrainHours(float trainHours) {
            this.trainHours = trainHours;
        }

        public double getNaked_left_eye_degree() {
            return naked_left_eye_degree;
        }

        public void setNaked_left_eye_degree(double naked_left_eye_degree) {
            this.naked_left_eye_degree = naked_left_eye_degree;
        }

        public double getNaked_right_eye_degree() {
            return naked_right_eye_degree;
        }

        public void setNaked_right_eye_degree(double naked_right_eye_degree) {
            this.naked_right_eye_degree = naked_right_eye_degree;
        }

        public double getNaked_binoculus_degree() {
            return naked_binoculus_degree;
        }

        public void setNaked_binoculus_degree(double naked_binoculus_degree) {
            this.naked_binoculus_degree = naked_binoculus_degree;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "memberId='" + memberId + '\'' +
                    ", reviewDate='" + reviewDate + '\'' +
                    ", reviewCount=" + reviewCount +
                    ", trainHours=" + trainHours +
                    ", naked_left_eye_degree=" + naked_left_eye_degree +
                    ", naked_right_eye_degree=" + naked_right_eye_degree +
                    ", naked_binoculus_degree=" + naked_binoculus_degree +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HttpResponseReviewEyeSightDataBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
