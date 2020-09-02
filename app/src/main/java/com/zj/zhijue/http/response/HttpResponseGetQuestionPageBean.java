package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import java.util.List;

@Keep
public class HttpResponseGetQuestionPageBean {
    protected String status;
    protected String message;
    private List<DataBean> data;
    private CursorBean cursor;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public CursorBean getCursor() {
        return cursor;
    }

    public void setCursor(CursorBean cursor) {
        this.cursor = cursor;
    }

    @Keep
    public class DataBean {
        private String id;//": "4ee0a0e0-d5d4-409e-bdae-47ad380e98b7",
        private String title;//": "test",
        private String questionTypeId;//": null,
        private String content;//": "condsafsa",
        private String createBy;//": "api",
        private String createDate;//": "2019-07-17T09:25:47.000+0000",

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuestionTypeId() {
            return questionTypeId;
        }

        public void setQuestionTypeId(String questionTypeId) {
            this.questionTypeId = questionTypeId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }

    @Keep
    public class CursorBean {
        private int totalCount;//": 1,
        private int pageNo;//": 1,
        private int pageSize;//": 1
        private boolean isRequesting = false;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public boolean isRequesting() {
            return isRequesting;
        }

        public void setRequesting(boolean requesting) {
            isRequesting = requesting;
        }
    }
}
