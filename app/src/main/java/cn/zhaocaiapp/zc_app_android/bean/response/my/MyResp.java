package cn.zhaocaiapp.zc_app_android.bean.response.my;


import java.math.BigDecimal;

/**
 * @author 林子
 * @filename UserResp.java
 * @data 2018-01-03 11:23
 */
public class MyResp {
    /**
     * "accountBalanceAmount": BigDecimal   账户剩余金额
     "audit":                    Intger     待审核信息条数
     "avatar":                   String     图像
     "customerPhone":           String    客服电话
     "email":                     String    邮箱
     "grossIncomeAmount":   BigDecimal   总收入
     "inviteCode":            String      邀请码
     "inviteMessage":         String     邀请奖励信息
     "message":              Integer    信息条数
     "nickname":              String     昵称
     "pay":                   String     待领钱
     "realInfoAudit":           String     实名认证状态
     "realInfoAuditStatus":     Integer    实名认证状态码
     "submit":                Integer    待交付
     "unPass":               Intger     未通过
     * */
    private BigDecimal accountBalanceAmount;
    private int audit;
    private String avatar;
    private String customerPhone;
    private String email;
    private BigDecimal grossIncomeAmount;
    private String inviteCode;
    private String inviteMessage;
    private int message;
    private String nickname;
    private int pay;
    private String realInfoAudit;
    private int realInfoAuditStatus;
    private int submit;
    private int unPass;

    public BigDecimal getAccountBalanceAmount() {
        return accountBalanceAmount;
    }

    public void setAccountBalanceAmount(BigDecimal accountBalanceAmount) {
        this.accountBalanceAmount = accountBalanceAmount;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public BigDecimal getGrossIncomeAmount() {
        return grossIncomeAmount;
    }

    public void setGrossIncomeAmount(BigDecimal grossIncomeAmount) {
        this.grossIncomeAmount = grossIncomeAmount;
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

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getRealInfoAudit() {
        return realInfoAudit;
    }

    public void setRealInfoAudit(String realInfoAudit) {
        this.realInfoAudit = realInfoAudit;
    }

    public int getRealInfoAuditStatus() {
        return realInfoAuditStatus;
    }

    public void setRealInfoAuditStatus(int realInfoAuditStatus) {
        this.realInfoAuditStatus = realInfoAuditStatus;
    }

    public int getSubmit() {
        return submit;
    }

    public void setSubmit(int submit) {
        this.submit = submit;
    }

    public int getUnPass() {
        return unPass;
    }

    public void setUnPass(int unPass) {
        this.unPass = unPass;
    }

    @Override
    public String toString() {
        return "MyResp{" +
                "accountBalanceAmount=" + accountBalanceAmount +
                ", audit=" + audit +
                ", avatar='" + avatar + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", email='" + email + '\'' +
                ", grossIncomeAmount=" + grossIncomeAmount +
                ", inviteCode='" + inviteCode + '\'' +
                ", inviteMessage='" + inviteMessage + '\'' +
                ", message=" + message +
                ", nickname='" + nickname + '\'' +
                ", pay=" + pay +
                ", realInfoAudit='" + realInfoAudit + '\'' +
                ", realInfoAuditStatus=" + realInfoAuditStatus +
                ", submit=" + submit +
                ", unPass=" + unPass +
                '}';
    }
}
