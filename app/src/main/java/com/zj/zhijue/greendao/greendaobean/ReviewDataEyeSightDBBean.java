package com.zj.zhijue.greendao.greendaobean;

import androidx.annotation.Keep;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * 视力复查 Bean
 */
@Entity(nameInDb = "reviewdataeyesightdb_tab")
public class ReviewDataEyeSightDBBean {

    @Id
    @Index
    @Property(nameInDb = "localid")
    private String localid;

    @Property(nameInDb = "serverrecoredId")
    private String serverRecoredId;//服务端记录的某一次复查记录

    @NotNull
    @Property(nameInDb = "userId")
    private String userId;

    @Property(nameInDb = "userName")
    private String userName;

    @Property(nameInDb = "userType")//用户类型，监护人，使用者
    private String userType;

    @Property(nameInDb = "leftEyeSight")
    private double leftEyeSight;

    @Property(nameInDb = "rightEyeSight")
    private double rightEyeSight;

    @Property(nameInDb = "doubleEyeSight")
    private double doubleEyeSight;

    @Property(nameInDb = "reviewEyeSightTimes")
    private int reviewEyeSightTimes;//复查次数（第几次）

    @Property(nameInDb = "reviewEyeSightDate")
    private String reviewEyeSightDate;//复查日期

    @Property(nameInDb = "trainTimeLong")//训练时长
    private float trainTimeLong;

    @Property(nameInDb = "createTime")
    private String createTime;

    @Property(nameInDb = "reservedStr0")
    private String reservedStr0;

    @Property(nameInDb = "reservedStr1")
    private String reservedStr1;

    @Property(nameInDb = "reservedStr2")
    private String reservedStr2;

    @Property(nameInDb = "reservedStr3")
    private String reservedStr3;

    @Property(nameInDb = "reservedLong0")
    private long reservedLong0;

    @Property(nameInDb = "reservedLong1")
    private long reservedLong1;

    @Property(nameInDb = "reservedInt0")
    private int reservedInt0;

    @Property(nameInDb = "reservedInt1")
    private int reservedInt1;

    @Property(nameInDb = "reservedInt2")
    private int reservedInt2;

    @Property(nameInDb = "reservedInt3")
    private int reservedInt3;

    @Property(nameInDb = "reservedInt4")
    private int reservedInt4;

    @Unique
    @NotNull
    @Convert(converter = ReviewDataEyeSightDBBean.UniqueBeanConverter.class, columnType = String.class)
    @Property(nameInDb = "uniquebean")
    private ReviewDataEyeSightDBBean.UniqueBean uniqueBean;

    @Generated(hash = 1274501051)
    public ReviewDataEyeSightDBBean(String localid, String serverRecoredId, @NotNull String userId, String userName,
            String userType, double leftEyeSight, double rightEyeSight, double doubleEyeSight, int reviewEyeSightTimes,
            String reviewEyeSightDate, float trainTimeLong, String createTime, String reservedStr0, String reservedStr1,
            String reservedStr2, String reservedStr3, long reservedLong0, long reservedLong1, int reservedInt0,
            int reservedInt1, int reservedInt2, int reservedInt3, int reservedInt4,
            @NotNull ReviewDataEyeSightDBBean.UniqueBean uniqueBean) {
        this.localid = localid;
        this.serverRecoredId = serverRecoredId;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.leftEyeSight = leftEyeSight;
        this.rightEyeSight = rightEyeSight;
        this.doubleEyeSight = doubleEyeSight;
        this.reviewEyeSightTimes = reviewEyeSightTimes;
        this.reviewEyeSightDate = reviewEyeSightDate;
        this.trainTimeLong = trainTimeLong;
        this.createTime = createTime;
        this.reservedStr0 = reservedStr0;
        this.reservedStr1 = reservedStr1;
        this.reservedStr2 = reservedStr2;
        this.reservedStr3 = reservedStr3;
        this.reservedLong0 = reservedLong0;
        this.reservedLong1 = reservedLong1;
        this.reservedInt0 = reservedInt0;
        this.reservedInt1 = reservedInt1;
        this.reservedInt2 = reservedInt2;
        this.reservedInt3 = reservedInt3;
        this.reservedInt4 = reservedInt4;
        this.uniqueBean = uniqueBean;
    }

    @Generated(hash = 431306879)
    public ReviewDataEyeSightDBBean() {
    }

    public String getLocalid() {
        return this.localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getLeftEyeSight() {
        return this.leftEyeSight;
    }

    public void setLeftEyeSight(double leftEyeSight) {
        this.leftEyeSight = leftEyeSight;
    }

    public double getRightEyeSight() {
        return this.rightEyeSight;
    }

    public void setRightEyeSight(double rightEyeSight) {
        this.rightEyeSight = rightEyeSight;
    }

    public double getDoubleEyeSight() {
        return this.doubleEyeSight;
    }

    public void setDoubleEyeSight(double doubleEyeSight) {
        this.doubleEyeSight = doubleEyeSight;
    }

    public int getReviewEyeSightTimes() {
        return this.reviewEyeSightTimes;
    }

    public void setReviewEyeSightTimes(int reviewEyeSightTimes) {
        this.reviewEyeSightTimes = reviewEyeSightTimes;
    }

    public String getReviewEyeSightDate() {
        return this.reviewEyeSightDate;
    }

    public void setReviewEyeSightDate(String reviewEyeSightDate) {
        this.reviewEyeSightDate = reviewEyeSightDate;
    }

    public float getTrainTimeLong() {
        return this.trainTimeLong;
    }

    public void setTrainTimeLong(float trainTimeLong) {
        this.trainTimeLong = trainTimeLong;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getServerRecoredId() {
        return this.serverRecoredId;
    }

    public void setServerRecoredId(String serverRecoredId) {
        this.serverRecoredId = serverRecoredId;
    }

    public static class UniqueBeanConverter implements PropertyConverter<ReviewDataEyeSightDBBean.UniqueBean, String> {
        @Override
        public ReviewDataEyeSightDBBean.UniqueBean convertToEntityProperty(String databaseValue) {
            if (TextUtils.isEmpty(databaseValue)) {
                return  null;
            }
            return new Gson().fromJson(databaseValue, ReviewDataEyeSightDBBean.UniqueBean.class);
        }

        @Override
        public String convertToDatabaseValue(ReviewDataEyeSightDBBean.UniqueBean entityProperty) {
            if (null == entityProperty) {
                return  null;
            }
            return new Gson().toJson(entityProperty);
        }
    }

    @Keep
    public static class UniqueBean {
        private String memeberId;
        private int reviewTimes;

        public UniqueBean() {
        }

        public String getMemeberId() {
            return memeberId;
        }

        public void setMemeberId(String memeberId) {
            this.memeberId = memeberId;
        }

        public int getReviewTimes() {
            return reviewTimes;
        }

        public void setReviewTimes(int reviewTimes) {
            this.reviewTimes = reviewTimes;
        }
    }

    public ReviewDataEyeSightDBBean.UniqueBean getUniqueBean() {
        return this.uniqueBean;
    }

    public void setUniqueBean(ReviewDataEyeSightDBBean.UniqueBean uniqueBean) {
        this.uniqueBean = uniqueBean;
    }

    public String getReservedStr0() {
        return this.reservedStr0;
    }

    public void setReservedStr0(String reservedStr0) {
        this.reservedStr0 = reservedStr0;
    }

    public String getReservedStr1() {
        return this.reservedStr1;
    }

    public void setReservedStr1(String reservedStr1) {
        this.reservedStr1 = reservedStr1;
    }

    public String getReservedStr2() {
        return this.reservedStr2;
    }

    public void setReservedStr2(String reservedStr2) {
        this.reservedStr2 = reservedStr2;
    }

    public String getReservedStr3() {
        return this.reservedStr3;
    }

    public void setReservedStr3(String reservedStr3) {
        this.reservedStr3 = reservedStr3;
    }

    public long getReservedLong0() {
        return this.reservedLong0;
    }

    public void setReservedLong0(long reservedLong0) {
        this.reservedLong0 = reservedLong0;
    }

    public long getReservedLong1() {
        return this.reservedLong1;
    }

    public void setReservedLong1(long reservedLong1) {
        this.reservedLong1 = reservedLong1;
    }

    public int getReservedInt0() {
        return this.reservedInt0;
    }

    public void setReservedInt0(int reservedInt0) {
        this.reservedInt0 = reservedInt0;
    }

    public int getReservedInt1() {
        return this.reservedInt1;
    }

    public void setReservedInt1(int reservedInt1) {
        this.reservedInt1 = reservedInt1;
    }

    public int getReservedInt2() {
        return this.reservedInt2;
    }

    public void setReservedInt2(int reservedInt2) {
        this.reservedInt2 = reservedInt2;
    }

    public int getReservedInt3() {
        return this.reservedInt3;
    }

    public void setReservedInt3(int reservedInt3) {
        this.reservedInt3 = reservedInt3;
    }

    public int getReservedInt4() {
        return this.reservedInt4;
    }

    public void setReservedInt4(int reservedInt4) {
        this.reservedInt4 = reservedInt4;
    }
}
