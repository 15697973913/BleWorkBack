package com.zj.common.http;

import java.io.Serializable;

public class FileUploadResult implements Serializable {
    private String errors;
    private String file;
    private String message;
    private String path;
    private int statusCode;

    public String getErrors() {
        return this.errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static FileUploadResult getDefaultError(String msg) {
        FileUploadResult result = new FileUploadResult();
        result.setMessage(msg);
        result.setStatusCode(1);
        return result;
    }

    public String toString() {
        return "FileUploadResult{errors='" + this.errors + '\'' + ", file='" + this.file + '\'' + ", path='" + this.path + '\'' + ", message='" + this.message + '\'' + ", statusCode=" + this.statusCode + '}';
    }
}
