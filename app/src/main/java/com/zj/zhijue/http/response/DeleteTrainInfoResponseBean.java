package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

@Keep
public class DeleteTrainInfoResponseBean {
    private String status;//": "011",
    private String message;//": "",
    private String data;//": null,
    private String cursor;//": null

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}
