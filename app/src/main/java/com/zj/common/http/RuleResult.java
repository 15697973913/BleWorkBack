package com.zj.common.http;

public class RuleResult<T> {
    private static final String RESULT_FAIL = "1";
    private static final String RESULT_SUCCESS = "0";
    private String code;
    private String description;
    private T rows;
    private int total;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getRows() {
        return this.rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return RESULT_SUCCESS.equals(this.code);
    }

    public String toString() {
        return "RuleResult{code='" + this.code + '\'' + ", description='" + this.description + '\'' + ", rows=" + this.rows + ", total=" + this.total + '}';
    }

    public static RuleResult getDefaultError(String msg) {
        RuleResult result = new RuleResult();
        result.setDescription(msg);
        result.setCode(RESULT_FAIL);
        result.setRows(null);
        return result;
    }
}
