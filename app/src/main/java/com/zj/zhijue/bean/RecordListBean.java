package com.zj.zhijue.bean;

import java.util.List;

/**
 * Date:2020/6/21
 * Time:18:30
 * Des:
 * Author:Sonne
 */
public class RecordListBean {

    private List<SurplusTimeBean> addTimeLogs;
    private List<SurplusTimeBean> useTimeLogs;

    public List<SurplusTimeBean> getAddTimeLogs() {
        return addTimeLogs;
    }

    public void setAddTimeLogs(List<SurplusTimeBean> addTimeLogs) {
        this.addTimeLogs = addTimeLogs;
    }

    public List<SurplusTimeBean> getUseTimeLogs() {
        return useTimeLogs;
    }

    public void setUseTimeLogs(List<SurplusTimeBean> useTimeLogs) {
        this.useTimeLogs = useTimeLogs;
    }

    public class  SurplusTimeBean{
        /**
         *  "id": "ecdc88ea-bedf-4e63-afb9-dfa06e4be629",
         *                 "memberId": "039b93538ce450592afc532fe2db84a7",
         *                 "money": 100.00,//充值金额
         *                 "time": 1000, //充值秒数
         *                 "createBy": "039b93538ce450592afc532fe2db84a7",
         *                 "createDate": "2020-06-17 17:02:42" //充值时间
         */

        private String id;
        private String memberId;
        private String money;
        private long time;
        private String createBy;
        private String createDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
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


}
