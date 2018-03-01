package cn.zhaocaiapp.zc_app_android.bean.response.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 林子
 * @filename ActivityResp.java
 * @data 2018-01-19 13:49
 */
public class ActivityResp {
    /**
     * 主键
     */
    private Long kid;

    /**
     * 活动分类 0单体活动 1串联活动 2协同活动
     */
    private Integer activityType;

    /**
     * 活动分类名称
     */
    private String activityTypeName;

    /**
     * 子活动
     */
    private List<SeriesActivityResp> seriesActivity;

    /**
     *  提交用户
     */
    private List<FinishUserResp> userList;

    /**
     * 完成人数
     */
    private Integer finishCount;

    /**
     * 进行状态
     */
    private Integer online;

    /**
     *  分页信息 起始条数(不包含)
     */
    private Integer currentResult;

    /**
     * 分页信息 每页条数
     */
    private Integer pageSize;

    /**
     * 是否参加
     */
    private Boolean join;

    /**
     * 是否关注
     */
    private Boolean follow;

    /**
     * 排序规则
     */
    private String sortType;

    /**
     * 活动类型 0线下活动 1视频活动 2问卷活动
     */
    private Integer activityForm;

    /**
     * 活动类型
     */
    private String activityFormName;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动标签
     */
    private String activityLabel;

    /**
     * 活动二维码
     */
    private String activityCode;

    /**
     * 是否检查二维码
     */
    private Integer ifCheck;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 完成周期 单位分
     */
    private Integer requireTime;

    /**
     * 通知时间 单位分
     */
    private Integer notifyTime;

    /**
     * 活动额度
     */
    private BigDecimal totalAmount;

    /**
     * 剩余额度
     */
    private BigDecimal leftAmount;

    /**
     * 金钱奖励 元每人
     */
    private BigDecimal rewardAmount;

    /**
     * 最大参与人数
     */
    private Integer maxUser;

    /**
     * 实际参与人数
     */
    private Integer actualUser;

    /**
     * 商家ID
     */
    private Long memberId;

    /**
     * 商家名称
     */
    private String memberName;

    /**
     * 商家图片
     */
    private String memberImg;

    /**
     * 参与条件 JSON格式
     */
    private String joinCondition;

    /**
     * 特定范围 JSON格式
     */
    private String pushRange;

    /**
     * 活动描述
     */
    private String contentRich;

    /**
     * 活动图片1
     */
    private String activityImage1;

    /**
     * 置顶 0否 1是
     */
    private Integer isStick;

    /**
     * 推送范围类型 0不推送 1全部 2特定范围
     */
    private Integer pushType;

    /**
     * 省份编码
     */
    private Long provinceCode;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 城市编码
     */
    private Long cityCode;

    /**
     * 城市
     */
    private String cityName;

    /**
     * 区县编码
     */
    private Long areaCode;

    /**
     * 区县
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String addressDetail;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 参考图片1
     */
    private String referImage1;

    /**
     * 问卷关联ID
     */
    private Long questionId;

    /**
     * 问卷类型
     */
    private Integer questionType;


    /**
     * 问卷是否必填
     */
    private Integer questionRequest;

    /**
     * 问卷奖励金额 每人元
     */
    private BigDecimal questionRewardAmount;


    /**
     * 问卷状态 0待回答 1已完成 2已取消
     */
    private Integer questionStatus;


    /**
     * 用户活动状态
     */
    private Integer activityStatus;

    /**
     * 活动视频
     */
    private String activityVedio;

    /**
     * 活动视频外链
     */
    private String activityOuterVedio;

    /**
     * 协同设定人数
     */
    private Integer coopCount;

    /**
     * 内容审核状态 0待提交 1待审核 2已通过 3未通过
     */
    private Integer contentAuditStatus;

    /**
     * 内容审核状态名称
     */
    private String contentAuditStatusName;

    /**
     * 内容审核备注
     */
    private String contentAuditMemo;

    /**
     * 财务审核状态 0待提交 1待审核 2已通过 3未通过
     */
    private Integer financeAuditStatus;

    /**
     * 财务审核状态名称
     */
    private String financeAuditStatusName;

    /**
     * 活动发布状态 0未发布 1已发布
     */
    private Integer releaseStatus;

    /**
     * 发布时间
     */
    private Date releaseTime;
    /**
     * 财务审核备注
     */
    private String financeAuditMemo;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标记 0未删除 1已删除
     */
    private Integer isDelete;

    /**
     * 活动分类名称
     */
    private String typeName;

    /**
     * 置顶状态名称
     */
    private String stickName;

    /**
     * 活动状态名称
     */
    private String actName;

    /**
     * 发布状态名称
     */
    private String statusName;

    /**
     * 现在时间
     */
    private Date nowDate;

    /**
     * 是否关闭
     */
    private Integer closed;

    /**
     * 交付截止时间
     */
    private Date deadLine;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public List<SeriesActivityResp> getSeriesActivity() {
        return seriesActivity;
    }

    public void setSeriesActivity(List<SeriesActivityResp> seriesActivity) {
        this.seriesActivity = seriesActivity;
    }

    public Integer getActivityForm() {
        return activityForm;
    }

    public void setActivityForm(Integer activityForm) {
        this.activityForm = activityForm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getRequireTime() {
        return requireTime;
    }

    public void setRequireTime(Integer requireTime) {
        this.requireTime = requireTime;
    }

    public Integer getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Integer notifyTime) {
        this.notifyTime = notifyTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Integer getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(Integer maxUser) {
        this.maxUser = maxUser;
    }

    public Integer getActualUser() {
        return actualUser;
    }

    public void setActualUser(Integer actualUser) {
        this.actualUser = actualUser;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    public String getPushRange() {
        return pushRange;
    }

    public void setPushRange(String pushRange) {
        this.pushRange = pushRange;
    }

    public String getContentRich() {
        return contentRich;
    }

    public void setContentRich(String contentRich) {
        this.contentRich = contentRich;
    }

    public String getActivityImage1() {
        return activityImage1;
    }

    public void setActivityImage1(String activityImage1) {
        this.activityImage1 = activityImage1;
    }

    public Integer getIsStick() {
        return isStick;
    }

    public void setIsStick(Integer isStick) {
        this.isStick = isStick;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public Long getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getReferImage1() {
        return referImage1;
    }

    public void setReferImage1(String referImage1) {
        this.referImage1 = referImage1;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public BigDecimal getQuestionRewardAmount() {
        return questionRewardAmount;
    }

    public void setQuestionRewardAmount(BigDecimal questionRewardAmount) {
        this.questionRewardAmount = questionRewardAmount;
    }

    public Integer getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    public Integer getQuestionRequest() {
        return questionRequest;
    }

    public void setQuestionRequest(Integer questionRequest) {
        this.questionRequest = questionRequest;
    }

    public String getActivityVedio() {
        return activityVedio;
    }

    public void setActivityVedio(String activityVedio) {
        this.activityVedio = activityVedio;
    }

    public String getActivityOuterVedio() {
        return activityOuterVedio;
    }

    public void setActivityOuterVedio(String activityOuterVedio) {
        this.activityOuterVedio = activityOuterVedio;
    }

    public Integer getCoopCount() {
        return coopCount;
    }

    public void setCoopCount(Integer coopCount) {
        this.coopCount = coopCount;
    }

    public Integer getContentAuditStatus() {
        return contentAuditStatus;
    }

    public void setContentAuditStatus(Integer contentAuditStatus) {
        this.contentAuditStatus = contentAuditStatus;
    }

    public String getContentAuditMemo() {
        return contentAuditMemo;
    }

    public void setContentAuditMemo(String contentAuditMemo) {
        this.contentAuditMemo = contentAuditMemo;
    }

    public Integer getFinanceAuditStatus() {
        return financeAuditStatus;
    }

    public void setFinanceAuditStatus(Integer financeAuditStatus) {
        this.financeAuditStatus = financeAuditStatus;
    }

    public String getFinanceAuditMemo() {
        return financeAuditMemo;
    }

    public void setFinanceAuditMemo(String financeAuditMemo) {
        this.financeAuditMemo = financeAuditMemo;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Boolean getJoin() {
        return join;
    }

    public void setJoin(Boolean join) {
        this.join = join;
    }

    public Integer getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(Integer currentResult) {
        this.currentResult = currentResult;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }

    public List<FinishUserResp> getUserList() {
        return userList;
    }

    public void setUserList(List<FinishUserResp> userList) {
        this.userList = userList;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getContentAuditStatusName() {
        return contentAuditStatusName;
    }

    public void setContentAuditStatusName(String contentAuditStatusName) {
        this.contentAuditStatusName = contentAuditStatusName;
    }

    public String getFinanceAuditStatusName() {
        return financeAuditStatusName;
    }

    public void setFinanceAuditStatusName(String financeAuditStatusName) {
        this.financeAuditStatusName = financeAuditStatusName;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public String getActivityFormName() {
        return activityFormName;
    }

    public void setActivityFormName(String activityFormName) {
        this.activityFormName = activityFormName;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getIfCheck() {
        return ifCheck;
    }

    public void setIfCheck(Integer ifCheck) {
        this.ifCheck = ifCheck;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getStickName() {
        return stickName;
    }

    public String getActName() {
        return actName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setStickName(String stickName) {
        this.stickName = stickName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getClosed() {
        return closed;
    }

    public void setClosed(Integer closed) {
        this.closed = closed;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public String toString() {
        return "ActivityResp{" +
                "kid=" + kid +
                ", activityType=" + activityType +
                ", activityTypeName='" + activityTypeName + '\'' +
                ", seriesActivity=" + seriesActivity +
                ", userList=" + userList +
                ", finishCount=" + finishCount +
                ", online=" + online +
                ", currentResult=" + currentResult +
                ", pageSize=" + pageSize +
                ", join=" + join +
                ", follow=" + follow +
                ", sortType='" + sortType + '\'' +
                ", activityForm=" + activityForm +
                ", activityFormName='" + activityFormName + '\'' +
                ", name='" + name + '\'' +
                ", activityLabel='" + activityLabel + '\'' +
                ", activityCode='" + activityCode + '\'' +
                ", ifCheck=" + ifCheck +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", requireTime=" + requireTime +
                ", notifyTime=" + notifyTime +
                ", totalAmount=" + totalAmount +
                ", leftAmount=" + leftAmount +
                ", rewardAmount=" + rewardAmount +
                ", maxUser=" + maxUser +
                ", actualUser=" + actualUser +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", memberImg='" + memberImg + '\'' +
                ", joinCondition='" + joinCondition + '\'' +
                ", pushRange='" + pushRange + '\'' +
                ", contentRich='" + contentRich + '\'' +
                ", activityImage1='" + activityImage1 + '\'' +
                ", isStick=" + isStick +
                ", pushType=" + pushType +
                ", provinceCode=" + provinceCode +
                ", provinceName='" + provinceName + '\'' +
                ", cityCode=" + cityCode +
                ", cityName='" + cityName + '\'' +
                ", areaCode=" + areaCode +
                ", areaName='" + areaName + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", referImage1='" + referImage1 + '\'' +
                ", questionId=" + questionId +
                ", questionType=" + questionType +
                ", questionRequest=" + questionRequest +
                ", questionRewardAmount=" + questionRewardAmount +
                ", questionStatus=" + questionStatus +
                ", activityStatus=" + activityStatus +
                ", activityVedio='" + activityVedio + '\'' +
                ", activityOuterVedio='" + activityOuterVedio + '\'' +
                ", coopCount=" + coopCount +
                ", contentAuditStatus=" + contentAuditStatus +
                ", contentAuditStatusName='" + contentAuditStatusName + '\'' +
                ", contentAuditMemo='" + contentAuditMemo + '\'' +
                ", financeAuditStatus=" + financeAuditStatus +
                ", financeAuditStatusName='" + financeAuditStatusName + '\'' +
                ", releaseStatus=" + releaseStatus +
                ", releaseTime=" + releaseTime +
                ", financeAuditMemo='" + financeAuditMemo + '\'' +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", typeName='" + typeName + '\'' +
                ", stickName='" + stickName + '\'' +
                ", actName='" + actName + '\'' +
                ", statusName='" + statusName + '\'' +
                ", nowDate=" + nowDate +
                ", closed=" + closed +
                ", deadLine=" + deadLine +
                '}';
    }
}
