package com.zj.zhijue.exception;

/**
 * AuthorType 为null的异常
 */
public class AuthorTypeNullException extends RuntimeException {
    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 消息是否为属性文件中的key
     */
    private boolean propertiesKey = true;

    public AuthorTypeNullException(String messgae) {
        super(messgae);
    }

    public AuthorTypeNullException(String errorCode, String message) {
        this(errorCode, message, true);
    }

    public AuthorTypeNullException(String errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    public AuthorTypeNullException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, true);
    }

    public AuthorTypeNullException(String errorCode, String message, Throwable cause, boolean propertiesKey) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }
}
