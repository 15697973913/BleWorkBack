package com.zj.zhijue.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class UpdateInfoBean implements Parcelable{
    private boolean haveNewVersion;
    private boolean needStopDownLoad = false;
    private String versionCode;
    private String verifyMd5;
    private String packageName;
    private String downloadUrl;

    public UpdateInfoBean() {

    }

    public UpdateInfoBean(boolean haveNewVersion) {
        this.haveNewVersion = haveNewVersion;
    }

    public boolean isHaveNewVersion() {
        return haveNewVersion;
    }

    public void setHaveNewVersion(boolean haveNewVersion) {
        this.haveNewVersion = haveNewVersion;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVerifyMd5() {
        return verifyMd5;
    }

    public void setVerifyMd5(String verifyMd5) {
        this.verifyMd5 = verifyMd5;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isNeedStopDownLoad() {
        return needStopDownLoad;
    }

    public void setNeedStopDownLoad(boolean needStopDownLoad) {
        this.needStopDownLoad = needStopDownLoad;
    }

   @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBooleanArray(new boolean[]{haveNewVersion, needStopDownLoad});
        parcel.writeString(versionCode);
        parcel.writeString(verifyMd5);
        parcel.writeString(packageName);
        parcel.writeString(downloadUrl);

    }
    public static final Creator<UpdateInfoBean> CREATOR = new Creator<UpdateInfoBean>() {
        @Override
        public UpdateInfoBean createFromParcel(Parcel parcel) {
            return new UpdateInfoBean(parcel);
        }

        @Override
        public UpdateInfoBean[] newArray(int i) {
            return new UpdateInfoBean[i];
        }
    };

    public UpdateInfoBean(Parcel in) {
        boolean[] byteArray = new boolean[2];
        in.readBooleanArray(byteArray);
        haveNewVersion = byteArray[0];
        needStopDownLoad = byteArray[1];
        versionCode = in.readString();
        verifyMd5 = in.readString();
        packageName = in.readString();
        downloadUrl = in.readString();
    }

    @Override
    public String toString() {
        return "UpdateInfoBean{" +
                "haveNewVersion=" + haveNewVersion +
                ", needStopDownLoad=" + needStopDownLoad +
                ", versionCode='" + versionCode + '\'' +
                ", verifyMd5='" + verifyMd5 + '\'' +
                ", packageName='" + packageName + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
