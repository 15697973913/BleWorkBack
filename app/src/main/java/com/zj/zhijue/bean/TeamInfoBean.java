package com.zj.zhijue.bean;

/**
 * Date:2020/6/23
 * Time:16:44
 * Des:
 * Author:Sonne
 */
public class TeamInfoBean {
    /**
     * "searchValue": null,
     *                 "createBy": "",
     *                 "createTime": "2020-05-27 15:24:43",
     *                 "updateBy": "",
     *                 "updateTime": "2020-05-27 15:24:43",
     *                 "remark": null,
     *                 "params": {},
     *                 "id": 8,
     *                 "name": "qqqq",
     *                 "nickname": "qqqq",
     *                 "phone": "18629247355",
     *                 "age": null,
     *                 "sex": "0",
     *                 "accountNo": "202005271524431906",
     *                 "inviteNo": "59039832",
     *                 "inviteAccountNo": "202005271526243396",
     *                 "bargainPrice": null,
     *                 "cardNo": null,
     *                 "isNew": 0,
     *                 "totalMoney": 0.00,
     *                 "usedMoney": 0,
     *                 "totalTime": "1000",
     *                 "usedTime": "",
     *                 "surplusTime": null,
     *                 "delFlag": "0",
     *                 "memberId": null
     */

    private int id;
    private String name;
    private String nickname;
    private String phone;
    private String accountNo;
    private String totalTime;
    private String face;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
