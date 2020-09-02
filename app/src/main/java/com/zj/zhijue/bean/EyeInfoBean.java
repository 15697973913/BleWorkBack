package com.zj.zhijue.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/8/12.
 */

public class EyeInfoBean implements Parcelable {
    private String memberId;

    private String name;//姓名
    private String credentials_card;//	是	string	证件号（被改信息用户的）
    private String left_eye_degree;//string	左眼屈光度
    private String right_eye_degree;//string 右眼屈光度
    private String correct_left_eye_degree;//string	左眼最佳矫正视力
    private String correct_right_eye_degree;//string 右眼最佳矫正视力
    private String correct_binoculus_degree;//string 双眼最佳矫正视力
    private String contrast_left_eye_degree;//string 左眼对比度视力
    private String contrast_right_eye_degree;//string 右眼对比度视力
    private String contrast_binoculus_degree;//string 双眼对比度视力
    private String naked_left_eye_degree;//string 左眼裸眼视力
    private String naked_right_eye_degree;//string 右眼裸眼视力
    private String naked_binoculus_degree;//string	双眼裸眼视力
    private String interpupillary;//string 瞳距

    private String left_astigmatism_degree;//";//	是	float	左眼散光度
    private String right_astigmatism_degree;//";//	是	float	右眼散光度

    public EyeInfoBean() {

    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLeft_eye_degree() {
        return left_eye_degree;
    }

    public void setLeft_eye_degree(String left_eye_degree) {
        this.left_eye_degree = left_eye_degree;
    }

    public String getRight_eye_degree() {
        return right_eye_degree;
    }

    public void setRight_eye_degree(String right_eye_degree) {
        this.right_eye_degree = right_eye_degree;
    }

    public String getCorrect_left_eye_degree() {
        return correct_left_eye_degree;
    }

    public void setCorrect_left_eye_degree(String correct_left_eye_degree) {
        this.correct_left_eye_degree = correct_left_eye_degree;
    }

    public String getCorrect_right_eye_degree() {
        return correct_right_eye_degree;
    }

    public void setCorrect_right_eye_degree(String correct_right_eye_degree) {
        this.correct_right_eye_degree = correct_right_eye_degree;
    }

    public String getCorrect_binoculus_degree() {
        return correct_binoculus_degree;
    }

    public void setCorrect_binoculus_degree(String correct_binoculus_degree) {
        this.correct_binoculus_degree = correct_binoculus_degree;
    }

    public String getContrast_left_eye_degree() {
        return contrast_left_eye_degree;
    }

    public void setContrast_left_eye_degree(String contrast_left_eye_degree) {
        this.contrast_left_eye_degree = contrast_left_eye_degree;
    }

    public String getContrast_right_eye_degree() {
        return contrast_right_eye_degree;
    }

    public void setContrast_right_eye_degree(String contrast_right_eye_degree) {
        this.contrast_right_eye_degree = contrast_right_eye_degree;
    }

    public String getContrast_binoculus_degree() {
        return contrast_binoculus_degree;
    }

    public void setContrast_binoculus_degree(String contrast_binoculus_degree) {
        this.contrast_binoculus_degree = contrast_binoculus_degree;
    }

    public String getNaked_left_eye_degree() {
        return naked_left_eye_degree;
    }

    public void setNaked_left_eye_degree(String naked_left_eye_degree) {
        this.naked_left_eye_degree = naked_left_eye_degree;
    }

    public String getNaked_right_eye_degree() {
        return naked_right_eye_degree;
    }

    public void setNaked_right_eye_degree(String naked_right_eye_degree) {
        this.naked_right_eye_degree = naked_right_eye_degree;
    }

    public String getNaked_binoculus_degree() {
        return naked_binoculus_degree;
    }

    public void setNaked_binoculus_degree(String naked_binoculus_degree) {
        this.naked_binoculus_degree = naked_binoculus_degree;
    }

    public String getInterpupillary() {
        return interpupillary;
    }

    public void setInterpupillary(String interpupillary) {
        this.interpupillary = interpupillary;
    }

    public String getLeft_astigmatism_degree() {
        return left_astigmatism_degree;
    }

    public void setLeft_astigmatism_degree(String left_astigmatism_degree) {
        this.left_astigmatism_degree = left_astigmatism_degree;
    }

    public String getRight_astigmatism_degree() {
        return right_astigmatism_degree;
    }

    public void setRight_astigmatism_degree(String right_astigmatism_degree) {
        this.right_astigmatism_degree = right_astigmatism_degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredentials_card() {
        return credentials_card;
    }

    public void setCredentials_card(String credentials_card) {
        this.credentials_card = credentials_card;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(memberId);
        parcel.writeString(left_eye_degree);
        parcel.writeString(right_eye_degree);
        parcel.writeString(correct_left_eye_degree);
        parcel.writeString(correct_right_eye_degree);
        parcel.writeString(correct_binoculus_degree);
        parcel.writeString(contrast_left_eye_degree);
        parcel.writeString(contrast_right_eye_degree);
        parcel.writeString(contrast_binoculus_degree);
        parcel.writeString(naked_left_eye_degree);
        parcel.writeString(naked_right_eye_degree);
        parcel.writeString(naked_binoculus_degree);
        parcel.writeString(interpupillary);
        parcel.writeString(left_astigmatism_degree);
        parcel.writeString(right_astigmatism_degree);
        parcel.writeString(name);
        parcel.writeString(credentials_card);
    }

    public static final Creator<EyeInfoBean> CREATOR = new Creator<EyeInfoBean>() {
        @Override
        public EyeInfoBean createFromParcel(Parcel parcel) {
            return new EyeInfoBean(parcel);
        }

        @Override
        public EyeInfoBean[] newArray(int i) {
            return new EyeInfoBean[i];
        }
    };

    public EyeInfoBean(Parcel in) {
        memberId = in.readString();
        left_eye_degree = in.readString();
        right_eye_degree = in.readString();
        correct_left_eye_degree = in.readString();
        correct_right_eye_degree = in.readString();
        correct_binoculus_degree = in.readString();
        contrast_left_eye_degree = in.readString();
        contrast_right_eye_degree = in.readString();
        contrast_binoculus_degree = in.readString();
        naked_left_eye_degree = in.readString();
        naked_right_eye_degree = in.readString();
        naked_binoculus_degree = in.readString();
        interpupillary = in.readString();
        left_astigmatism_degree = in.readString();
        right_astigmatism_degree = in.readString();
        name = in.readString();
        credentials_card = in.readString();
    }

}
