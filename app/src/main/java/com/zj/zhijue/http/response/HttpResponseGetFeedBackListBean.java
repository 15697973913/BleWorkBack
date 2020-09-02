package com.zj.zhijue.http.response;

import androidx.annotation.Keep;


import java.util.List;

@Keep
public class HttpResponseGetFeedBackListBean{
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
      private int status;//": null,
      private String platform;//": 0,
      private String createBy;//": "api",
      private String createDate;//": "2019-07-17T09:25:47.000+0000",
      private String content;//": "condsafsa",
      private List<FeedBackBean> feedbackReplyList;//":

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<FeedBackBean> getFeedbackReplyList() {
            return feedbackReplyList;
        }

        public void setFeedbackReplyList(List<FeedBackBean> feedbackReplyList) {
            this.feedbackReplyList = feedbackReplyList;
        }

        public String getAllReplyContent() {
            if (null != feedbackReplyList && feedbackReplyList.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < feedbackReplyList.size(); i++) {
                    FeedBackBean feedBackBean = feedbackReplyList.get(i);

                    if (i == 0) {
                        stringBuilder.append("回复:\n" + feedBackBean.getContent());
                    } else {
                        stringBuilder.append("回复"+ (i + 1)  +":\n" + feedBackBean.getContent());
                    }

                }
            }
            return "";
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", status=" + status +
                    ", platform='" + platform + '\'' +
                    ", createBy='" + createBy + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", content='" + content + '\'' +
                    ", feedbackReplyList=" + feedbackReplyList +
                    '}';
        }
    }

    @Keep
    public class CursorBean {
       private int totalCount;//": 1,
       private int pageNo;//": 1,
       private int pageSize;//": 1
       private boolean haveRequested = false;//是否用于过请求加载更多

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

        public boolean isHaveRequested() {
            return haveRequested;
        }

        public void setHaveRequested(boolean haveRequested) {
            this.haveRequested = haveRequested;
        }

        @Override
        public String toString() {
            return "CursorBean{" +
                    "totalCount=" + totalCount +
                    ", pageNo=" + pageNo +
                    ", pageSize=" + pageSize +
                    '}';
        }

    }

    @Keep
    public class FeedBackBean {
        private String id;//": "daer4ee0a0e0-d5d4-409e-bdae-47ad380e98b7dasfdsaf",
        private String feedbackId;// "feedbackId": "4ee0a0e0-d5d4-409e-bdae-47ad380e98b7",
        private String parentId;//": null,
        private String content;//": "aaaaaaa"

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFeedbackId() {
            return feedbackId;
        }

        public void setFeedbackId(String feedbackId) {
            this.feedbackId = feedbackId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    @Override
    public String toString() {
        return "HttpResponseGetFeedBackListBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
