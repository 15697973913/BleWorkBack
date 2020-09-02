package com.zj.zhijue.bean;

/**
 * Created by Administrator on 2018/8/11.
 */

public class BaseBean {
    protected String status;
    protected String message;
    protected String cursor;

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

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
