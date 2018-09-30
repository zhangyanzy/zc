package cn.zhaocaiapp.zc_app_android.bean.request.commerclal;

import java.math.BigDecimal;

/**
 * Created by admin on 2018/9/27.
 */

public class GetActivityStatus {

    private Long kid;//主键
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private String name;//昵称
    private BigDecimal totalAmount;//活动总金额
    private BigDecimal rewardAmount;//每人活动金额
    private String maxUser;//活动人数
    private String contentRich;//活动要求
    private String activityImageOutUrl;//活动封面
    private String activityVideoOutUrl;//活动视频
    private String financeAuditStatus;//审核状态 1待审核 3审核未通过

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(String maxUser) {
        this.maxUser = maxUser;
    }

    public String getContentRich() {
        return contentRich;
    }

    public void setContentRich(String contentRich) {
        this.contentRich = contentRich;
    }

    public String getActivityImageOutUrl() {
        return activityImageOutUrl;
    }

    public void setActivityImageOutUrl(String activityImageOutUrl) {
        this.activityImageOutUrl = activityImageOutUrl;
    }

    public String getActivityVideoOutUrl() {
        return activityVideoOutUrl;
    }

    public void setActivityVideoOutUrl(String activityVideoOutUrl) {
        this.activityVideoOutUrl = activityVideoOutUrl;
    }

    public String getFinanceAuditStatus() {
        return financeAuditStatus;
    }

    public void setFinanceAuditStatus(String financeAuditStatus) {
        this.financeAuditStatus = financeAuditStatus;
    }

    @Override
    public String toString() {
        return "GetActivityStatus{" +
                "kid=" + kid +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", name='" + name + '\'' +
                ", totalAmount=" + totalAmount +
                ", rewardAmount=" + rewardAmount +
                ", maxUser='" + maxUser + '\'' +
                ", contentRich='" + contentRich + '\'' +
                ", activityImageOutUrl='" + activityImageOutUrl + '\'' +
                ", activityVideoOutUrl='" + activityVideoOutUrl + '\'' +
                ", financeAuditStatus='" + financeAuditStatus + '\'' +
                '}';
    }
}
