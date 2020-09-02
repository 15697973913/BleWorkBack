package com.zj.zhijue.bean.bledata;

import androidx.annotation.Keep;

/**
 * 上报训练数据给后台 *
 */
@Keep
public class HttpRequestTrainJsonListItemBean {
    private String localId;//本地数据库id
    private String start_time;//点击开始按钮的时间
    private String  collect_time;//	是	date	采集时间
    private int left_counts;//";//	是	int	左眼运行次数
    private int left_speed;//";//	是	int	左眼运行速度
    private double left_range_up;//";//	是	double	左眼运行区间上限
    private double left_range_low;//";//	是	double	左眼运行区间下限
    private int right_counts;//";//	是	int	右眼运行次数
    private int right_speed;//";//	是	int	右眼运行速度
    private double right_range_up;//";//	是	double	右眼运行区间上限
    private double right_range_low;//";//	是	double	右眼运行区间下限


    public String getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }

    public int getLeft_counts() {
        return left_counts;
    }

    public void setLeft_counts(int left_counts) {
        this.left_counts = left_counts;
    }

    public int getLeft_speed() {
        return left_speed;
    }

    public void setLeft_speed(int left_speed) {
        this.left_speed = left_speed;
    }

    public double getLeft_range_up() {
        return left_range_up;
    }

    public void setLeft_range_up(double left_range_up) {
        this.left_range_up = left_range_up;
    }

    public double getLeft_range_low() {
        return left_range_low;
    }

    public void setLeft_range_low(double left_range_low) {
        this.left_range_low = left_range_low;
    }

    public int getRight_counts() {
        return right_counts;
    }

    public void setRight_counts(int right_counts) {
        this.right_counts = right_counts;
    }

    public int getRight_speed() {
        return right_speed;
    }

    public void setRight_speed(int right_speed) {
        this.right_speed = right_speed;
    }

    public double getRight_range_up() {
        return right_range_up;
    }

    public void setRight_range_up(double right_range_up) {
        this.right_range_up = right_range_up;
    }

    public double getRight_range_low() {
        return right_range_low;
    }

    public void setRight_range_low(double right_range_low) {
        this.right_range_low = right_range_low;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @Override
    public String toString() {
        return "HttpRequestTrainJsonListItemBean{" +
                "localId='" + localId + '\'' +
                ", start_time='" + start_time + '\'' +
                ", collect_time='" + collect_time + '\'' +
                ", left_counts=" + left_counts +
                ", left_speed=" + left_speed +
                ", left_range_up=" + left_range_up +
                ", left_range_low=" + left_range_low +
                ", right_counts=" + right_counts +
                ", right_speed=" + right_speed +
                ", right_range_up=" + right_range_up +
                ", right_range_low=" + right_range_low +
                '}';
    }
}
