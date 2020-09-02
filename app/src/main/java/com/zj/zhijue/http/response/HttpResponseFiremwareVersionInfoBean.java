package com.zj.zhijue.http.response;

import androidx.annotation.Keep;

import com.zj.zhijue.bean.BaseBean;

/**
 * 固件版本信息
 */
@Keep
public class HttpResponseFiremwareVersionInfoBean extends BaseBean {
    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public class  DataBean {
        String memberId;
        String version;
        String filePath;
        String fileName;
        int fileSize;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "memberId='" + memberId + '\'' +
                    ", version='" + version + '\'' +
                    ", filePath='" + filePath + '\'' +
                    ", fileSize=" + fileSize +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HttpResponseGetLastedSystemUpdateBean{" +
                "data=" + data +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
