package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

import java.util.List;

/**
 * 训练时间信息
 */
@Keep
public class HttpResponseMemberTrainTimeBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String memberId;//	string	用户id
        /*private String trainingDate;//	date	训练日期
        private float trainingHours;//	int	训练小时数
        private int trainingCount;//	int	训练佩戴次数*/
        private String trainingDate; // 训练日期"2019-06-17 00:00:00"
        private int trainTimeYear;
        private int trainTimeMonth;
        private int trainTimeDay;
        private int trainTimeHour;
        private int trainTimeMinute;
        private int trainTimeSecond;
        private int trainTotalCount;

        private int trainingHours;//训练小时数
        private int trainingCount;// 训练佩戴次数

        public int getTrainingHours() {
            return trainingHours;
        }

        public void setTrainingHours(int trainingHours) {
            this.trainingHours = trainingHours;
        }

        public int getTrainingCount() {
            return trainingCount;
        }

        public void setTrainingCount(int trainingCount) {
            this.trainingCount = trainingCount;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getTrainingDate() {
            return trainingDate;
        }

        public void setTrainingDate(String trainingDate) {
            this.trainingDate = trainingDate;
        }

        public int getTrainTimeYear() {
            return trainTimeYear;
        }

        public void setTrainTimeYear(int trainTimeYear) {
            this.trainTimeYear = trainTimeYear;
        }

        public int getTrainTimeMonth() {
            return trainTimeMonth;
        }

        public void setTrainTimeMonth(int trainTimeMonth) {
            this.trainTimeMonth = trainTimeMonth;
        }

        public int getTrainTimeDay() {
            return trainTimeDay;
        }

        public void setTrainTimeDay(int trainTimeDay) {
            this.trainTimeDay = trainTimeDay;
        }

        public int getTrainTimeHour() {
            return trainTimeHour;
        }

        public void setTrainTimeHour(int trainTimeHour) {
            this.trainTimeHour = trainTimeHour;
        }

        public int getTrainTimeMinute() {
            return trainTimeMinute;
        }

        public void setTrainTimeMinute(int trainTimeMinute) {
            this.trainTimeMinute = trainTimeMinute;
        }

        public int getTrainTimeSecond() {
            return trainTimeSecond;
        }

        public void setTrainTimeSecond(int trainTimeSecond) {
            this.trainTimeSecond = trainTimeSecond;
        }

        public int getTrainTotalCount() {
            return trainTotalCount;
        }

        public void setTrainTotalCount(int trainTotalCount) {
            this.trainTotalCount = trainTotalCount;
        }

        /**
         * 返回秒时长
         *
         * @return
         */
        public float getTrainingSeconds() {
            return trainTimeHour * 60 * 60 + trainTimeMinute * 60 + trainTimeSecond;
        }
    }

    @Override
    public String toString() {
        return "HttpResponseMemberTrainTimeBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
