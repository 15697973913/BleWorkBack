package com.zj.zhijue.bean.bledata;

import java.util.List;

/**
 * 解析蓝牙数据之后，封装的对象
 */
public class UserTrainInfoBleDataBean {
    private String memberId;//";//	是	string	用户ID
    private String login_name;//";//是	string	用户账号
    private String deviceId;//";//	是	string	设备编号
    private int platform;//平台
    private int operationType;//操作 开始，干预，运行

    private List<HttpRequestTrainJsonListItemBean> dataList;//
/*
    private String collect_time;//";//	是	date	采集时间
    private int left_counts;//";//	是	int	左眼运行次数
    private int left_speed;//";//	是	int	左眼运行速度
    private double left_range_up;//";//	是	double	左眼运行区间上限
    private double left_range_low;//";//	是	double	左眼运行区间下限
    private int right_counts;//";//	是	int	右眼运行次数
    private int right_speed;//";//	是	int	右眼运行速度
    private double right_range_up;//";//	是	double	右眼运行区间上限
    private double right_range_low;//";//	是	double	右眼运行区间下限*/

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<HttpRequestTrainJsonListItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<HttpRequestTrainJsonListItemBean> dataList) {
        this.dataList = dataList;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "UserTrainInfoBleDataBean{" +
                "memberId='" + memberId + '\'' +
                ", login_name='" + login_name + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", platform=" + platform +
                ", operationType=" + operationType +
                ", dataList=" + dataList +
                '}';
    }
}
