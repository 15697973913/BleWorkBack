package com.zj.zhijue.event;


public class TrainTimeUpdateStatusEvent {
    private boolean updateSuccess;
    private int trainDay;

    public TrainTimeUpdateStatusEvent(boolean updateSuccess, int trainDay) {
        this.updateSuccess = updateSuccess;
        this.trainDay = trainDay;
    }

    public TrainTimeUpdateStatusEvent(boolean updateSuccess) {
        this.updateSuccess = updateSuccess;
    }

    public boolean isUpdateSuccess() {
        return updateSuccess;
    }

    public void setUpdateSuccess(boolean updateSuccess) {
        this.updateSuccess = updateSuccess;
    }

    public int getTrainDay() {
        return trainDay;
    }

    public void setTrainDay(int trainDay) {
        this.trainDay = trainDay;
    }

    @Override
    public String toString() {
        return "TrainTimeUpdateStatusEvent{" +
                "updateSuccess=" + updateSuccess +
                ", trainDay=" + trainDay +
                '}';
    }
}
