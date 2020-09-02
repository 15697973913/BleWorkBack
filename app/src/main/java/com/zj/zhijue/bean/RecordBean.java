package com.zj.zhijue.bean;

import java.util.List;

/**
 * Date:2020/6/21
 * Time:18:27
 * Des:
 * Author:Sonne
 */
public class RecordBean {

    private String status;
    private String message;
    private RecordListBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RecordListBean getData() {
        return data;
    }

    public void setData(RecordListBean data) {
        this.data = data;
    }
}
