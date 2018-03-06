package cn.zhaocaiapp.zc_app_android.bean.response.my;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/3/6.
 */

public class IncomeResp {
    private BigDecimal amount;//金额
    private int billType;//0-收入  1-提现
    private long businessId;//业务id
    private String businessName;//业务类型
    private int businessType;//0系统任务 1活动 2提现
    private int cashStatus;//0-已提交, 1-已通过, 2-未通过, 3-转账中, 4-已到账, 5-转账失败
    private String timeStr;//时间
    private String payNo;//支付凭证
    private long kid;
    private long userId;

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(int cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomeResp{" +
                "amount=" + amount +
                ", billType=" + billType +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", businessType=" + businessType +
                ", cashStatus=" + cashStatus +
                ", timeStr='" + timeStr + '\'' +
                ", payNo='" + payNo + '\'' +
                ", kid=" + kid +
                ", userId=" + userId +
                '}';
    }
}
