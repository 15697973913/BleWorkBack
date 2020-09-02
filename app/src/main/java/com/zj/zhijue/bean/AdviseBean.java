package com.zj.zhijue.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AdviseBean implements Parcelable{
    private int titleIndex;
    private String questionTitle;
    private String questionContent;
    private String answer;
    private boolean isReplyed;
    private int status;

    public AdviseBean() {
    }

    public boolean isReplyed() {
        return isReplyed;
    }

    public void setReplyed(boolean replyed) {
        isReplyed = replyed;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTitleIndex() {
        return titleIndex;
    }

    public void setTitleIndex(int titleIndex) {
        this.titleIndex = titleIndex;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(titleIndex);
        parcel.writeString(questionTitle);
        parcel.writeString(questionContent);
        parcel.writeString(answer);
        parcel.writeBooleanArray(new boolean[]{isReplyed});

    }
    public static final Creator<AdviseBean> CREATOR = new Creator<AdviseBean>() {
        @Override
        public AdviseBean createFromParcel(Parcel parcel) {
            return new AdviseBean(parcel);
        }

        @Override
        public AdviseBean[] newArray(int i) {
            return new AdviseBean[i];
        }
    };

    public AdviseBean(Parcel parcel) {
        titleIndex = parcel.readInt();
        questionTitle = parcel.readString();
        questionContent = parcel.readString();
        answer = parcel.readString();
        boolean[] byteArray = new boolean[1];
        parcel.readBooleanArray(byteArray);
        isReplyed = byteArray[0];
    }

    @Override
    public String toString() {
        return "AdviseBean{" +
                "titleIndex=" + titleIndex +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", answer='" + answer + '\'' +
                ", isReplyed=" + isReplyed +
                '}';
    }
}
