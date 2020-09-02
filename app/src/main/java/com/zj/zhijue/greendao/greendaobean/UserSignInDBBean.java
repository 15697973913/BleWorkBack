package com.zj.zhijue.greendao.greendaobean;

import org.greenrobot.greendao.annotation.Entity;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户签到记录
 */
@Entity(nameInDb = "usersignindb_tab")
public class UserSignInDBBean {

    @Unique
    @Id
    @Property(nameInDb = "signinserverid")
    private String signinserverid;
    
    @Property(nameInDb = "member_id")
    private String member_id;

    @Property(nameInDb = "login_name")
    private String login_name;

    @Property(nameInDb = "nick_name")
    private String nick_name;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "sign_time")
    private String sign_time;

    @Property(nameInDb = "sign_time_long")
    private Long sign_time_long;

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

    @Property(nameInDb = "reservedDouble0")
    private double reservedDouble0;

    @Property(nameInDb = "reservedDouble1")
    private double reservedDouble1;

    @Generated(hash = 1755002984)
    public UserSignInDBBean(String signinserverid, String member_id,
            String login_name, String nick_name, String name, String sign_time,
            Long sign_time_long, String reservedStr0, String reservedStr1,
            String reservedStr2, String reservedStr3, long reservedLong0,
            long reservedLong1, double reservedDouble0, double reservedDouble1) {
        this.signinserverid = signinserverid;
        this.member_id = member_id;
        this.login_name = login_name;
        this.nick_name = nick_name;
        this.name = name;
        this.sign_time = sign_time;
        this.sign_time_long = sign_time_long;
        this.reservedStr0 = reservedStr0;
        this.reservedStr1 = reservedStr1;
        this.reservedStr2 = reservedStr2;
        this.reservedStr3 = reservedStr3;
        this.reservedLong0 = reservedLong0;
        this.reservedLong1 = reservedLong1;
        this.reservedDouble0 = reservedDouble0;
        this.reservedDouble1 = reservedDouble1;
    }

    @Generated(hash = 806558061)
    public UserSignInDBBean() {
    }

    public String getSigninserverid() {
        return this.signinserverid;
    }

    public void setSigninserverid(String signinserverid) {
        this.signinserverid = signinserverid;
    }

    public String getMember_id() {
        return this.member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getLogin_name() {
        return this.login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getNick_name() {
        return this.nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign_time() {
        return this.sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public Long getSign_time_long() {
        return this.sign_time_long;
    }

    public void setSign_time_long(Long sign_time_long) {
        this.sign_time_long = sign_time_long;
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

    public double getReservedDouble0() {
        return this.reservedDouble0;
    }

    public void setReservedDouble0(double reservedDouble0) {
        this.reservedDouble0 = reservedDouble0;
    }

    public double getReservedDouble1() {
        return this.reservedDouble1;
    }

    public void setReservedDouble1(double reservedDouble1) {
        this.reservedDouble1 = reservedDouble1;
    }




}
