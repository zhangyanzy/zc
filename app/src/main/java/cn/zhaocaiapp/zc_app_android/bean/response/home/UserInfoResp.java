package cn.zhaocaiapp.zc_app_android.bean.response.home;

import java.math.BigDecimal;

/**
 * @author 林子
 * @filename UserInfoResp.java
 * @data 2018-01-26 13:22
 */
public class UserInfoResp {
    private Long count;

    /**
     * 待提交
     */
    private Integer submit;

    /**
     * 待审核
     */
    private Integer audit;

    /**
     * 待提现
     */
    private Integer pay;
    /**
     * 未通过
     */
    private Integer unPass;

    /**
     * 活动状态
     */
    private Integer activityStatus;

    /**
     * 未读信息
     */
    private Integer message;

    /**
     * 总收入
     */
    private BigDecimal grossIncomeAmount;

    /**
     * 账户余额
     */
    private BigDecimal accountBalanceAmount;

    /**
     * 邀请码
     *
     * @return
     */
    private String inviteCode;

    /**
     * 邀请奖励
     *
     * @return
     */
    private String inviteMessage;

    /**
     * 客服电话
     *
     * @return
     */
    private String customerPhone;

    /**
     * MAIL
     *
     * @return
     */
    private String email;

    /**
     * 实名认证状态 0未认证 1待审核 2已认证 3 未通过
     */
    private Integer realInfoAuditStatus;

    /**
     * 实名认证状态
     */
    private String realInfoAudit;

    /**
     * 昵称
     *
     * @return
     */
    private String nickname;

    /**
     * 图像
     *
     * @return
     */
    private String avatar;

    public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getUnPass() {
        return unPass;
    }

    public void setUnPass(Integer unPass) {
        this.unPass = unPass;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public BigDecimal getGrossIncomeAmount() {
        return grossIncomeAmount;
    }

    public void setGrossIncomeAmount(BigDecimal grossIncomeAmount) {
        this.grossIncomeAmount = grossIncomeAmount;
    }

    public BigDecimal getAccountBalanceAmount() {
        return accountBalanceAmount;
    }

    public void setAccountBalanceAmount(BigDecimal accountBalanceAmount) {
        this.accountBalanceAmount = accountBalanceAmount;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteMessage() {
        return inviteMessage;
    }

    public void setInviteMessage(String inviteMessage) {
        this.inviteMessage = inviteMessage;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRealInfoAuditStatus() {
        return realInfoAuditStatus;
    }

    public void setRealInfoAuditStatus(Integer realInfoAuditStatus) {
        this.realInfoAuditStatus = realInfoAuditStatus;
    }

    public String getRealInfoAudit() {
        return realInfoAudit;
    }

    public void setRealInfoAudit(String realInfoAudit) {
        this.realInfoAudit = realInfoAudit;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserInfoResp{" +
                "count=" + count +
                ", submit=" + submit +
                ", audit=" + audit +
                ", pay=" + pay +
                ", unPass=" + unPass +
                ", activityStatus=" + activityStatus +
                ", message=" + message +
                ", grossIncomeAmount=" + grossIncomeAmount +
                ", accountBalanceAmount=" + accountBalanceAmount +
                ", inviteCode='" + inviteCode + '\'' +
                ", inviteMessage='" + inviteMessage + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", email='" + email + '\'' +
                ", realInfoAuditStatus=" + realInfoAuditStatus +
                ", realInfoAudit='" + realInfoAudit + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
