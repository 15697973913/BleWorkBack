package com.zj.zhijue.bean;

import java.util.List;

/**
 * Date:2020/6/21
 * Time:16:57
 * Des:
 * Author:Sonne
 */
public class TeamBean {

    private String status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        private TeamInfoBean inviteAccount;
        private List<TeamInfoBean> inviteTeam;

        public TeamInfoBean getInviteAccount() {
            return inviteAccount;
        }

        public void setInviteAccount(TeamInfoBean inviteAccount) {
            this.inviteAccount = inviteAccount;
        }

        public List<TeamInfoBean> getInviteTeam() {
            return inviteTeam;
        }

        public void setInviteTeam(List<TeamInfoBean> inviteTeam) {
            this.inviteTeam = inviteTeam;
        }
    }

}
