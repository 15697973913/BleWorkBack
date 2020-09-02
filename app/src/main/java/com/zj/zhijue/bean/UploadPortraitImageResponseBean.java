package com.zj.zhijue.bean;

import androidx.annotation.Keep;


/**
 * Created by Administrator on 2019/1/12.
 */

@Keep
public class UploadPortraitImageResponseBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class DataBean {
        private String memberId;//": "00459522",
        private String filePath;//": "group1/M00/00/00/wKgKNFz3iteABEQUAAGg8KhR38M238.png22.png",
        private String fileSize;//": 106736

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "memberId='" + memberId + '\'' +
                    ", filePath='" + filePath + '\'' +
                    ", fileSize='" + fileSize + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UploadPortraitImageResponseBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
