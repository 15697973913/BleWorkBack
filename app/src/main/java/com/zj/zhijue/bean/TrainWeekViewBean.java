package com.zj.zhijue.bean;

public class TrainWeekViewBean {
    private float trainTime;
    private String dateStr;

    public float getTrainTime() {
        /**
         * 超过10个小时，则最长显示10个小时
         */
        return trainTime > 10 * 60 * 60 ? 10 * 60 * 60 : trainTime;
    }

    public void setTrainTime(float trainTime) {
        this.trainTime = trainTime;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
