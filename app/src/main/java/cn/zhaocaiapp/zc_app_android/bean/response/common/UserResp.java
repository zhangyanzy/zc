package cn.zhaocaiapp.zc_app_android.bean.response.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 林子
 * @filename UserResp.java
 * @data 2018-02-03 12:38
 */
public class UserResp {
    /**
     * 主键
     */
    private Long kid;

    /**
     * 会员昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户状态 0激活 1禁用
     */
    private Integer accountStatus;

    /**
     * 基本信息变动次数
     */
    private Integer baseInfoAlterCount;

    /**
     * 实名认证状态 0未认证 1待审核 2已认证 3 未通过
     */
    private Integer realInfoAuditStatus;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 性别 0男 1女
     */
    private Integer sex;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 身份证反面路径
     */
    private String idCardPath;

    /**
     * 家庭省份编码
     */
    private Long homeProvinceCode;

    /**
     * 家庭省份
     */
    private String homeProvinceName;

    /**
     * 家庭城市编码
     */
    private Long homeCityCode;

    /**
     * 家庭城市
     */
    private String homeCityName;

    /**
     * 家庭区县编码
     */
    private Long homeAreaCode;

    /**
     * 家庭区县
     */
    private String homeAreaName;

    /**
     * 家庭详细地址
     */
    private String homeAddressDetail;

    private Date joinTime;

    /**
     * 工作省份编码
     */
    private Long companyProvinceCode;

    /**
     * 工作省份
     */
    private String companyProvinceName;

    /**
     * 工作城市编码
     */
    private Long companyCityCode;

    /**
     * 工作城市
     */
    private String companyCityName;

    /**
     * 工作区县编码
     */
    private Long companyAreaCode;

    /**
     * 工作区县
     */
    private String companyAreaName;

    /**
     * 工作详细地址
     */
    private String companyAddressDetail;

    /**
     * 实名认证变动次数
     */
    private Integer realInfoAlterCount;

    /**
     * 活动信息状态 0未变动 1变动一次 2变动两次 3待审核 4审核通过 5审核未通过
     */
    private Integer activtiyInfoAudit;

    /**
     * 学历
     */
    private Integer educationalCode;

    /**
     * 职业
     */
    private Integer jobCode;

    /**
     * 用户活动信息变动次数
     */
    private Integer activityInfoAlterCount;

    /**
     * 微信用户唯一标识
     */
    private String wxUid;

    /**
     * QQ用户唯一标识
     */
    private String qqUid;

    /**
     * 微博用户唯一标识
     */
    private String sinaUid;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 总收入
     */
    private BigDecimal grossIncomeAmount;

    /**
     * 账户余额
     */
    private BigDecimal accountBalanceAmount;

    /**
     * 是否完成新手任务 0未完成 1完成
     */
    private Integer isFinishActivity;

    /**
     * 主动取消次数
     */
    private Integer initiativeCancelCount;

    /**
     * 被动取消次数
     */
    private Integer passiveCancellCount;

    /**
     * 主动取消惩罚到期时间
     */
    private Date initiativePunishedTime;

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
     * Token
     */
    private String token;

    /**
     * 类型  0 手机号码 1 微信 2 QQ 3 微博
     */
    private Integer type;

    /**
     * 第三方登录唯一标识
     */
    private String uid;

    /**
     * 登录结果
     */
    private String result;

    /**
     * 描述
     */
    private String desc;

    /**
     * 注册修改手机验证码
     */
    private String code;

    private String job;

    private String educational;

    private Boolean updateIs;

    private String birthdayStr;

    private Integer infoType;

    private BigDecimal newbieAmount;


    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getBaseInfoAlterCount() {
        return baseInfoAlterCount;
    }

    public void setBaseInfoAlterCount(Integer baseInfoAlterCount) {
        this.baseInfoAlterCount = baseInfoAlterCount;
    }

    public Integer getRealInfoAuditStatus() {
        return realInfoAuditStatus;
    }

    public void setRealInfoAuditStatus(Integer realInfoAuditStatus) {
        this.realInfoAuditStatus = realInfoAuditStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCardPath() {
        return idCardPath;
    }

    public void setIdCardPath(String idCardPath) {
        this.idCardPath = idCardPath == null ? null : idCardPath.trim();
    }

    public Long getHomeProvinceCode() {
        return homeProvinceCode;
    }

    public void setHomeProvinceCode(Long homeProvinceCode) {
        this.homeProvinceCode = homeProvinceCode;
    }

    public String getHomeProvinceName() {
        return homeProvinceName;
    }

    public void setHomeProvinceName(String homeProvinceName) {
        this.homeProvinceName = homeProvinceName == null ? null : homeProvinceName.trim();
    }

    public Long getHomeCityCode() {
        return homeCityCode;
    }

    public void setHomeCityCode(Long homeCityCode) {
        this.homeCityCode = homeCityCode;
    }

    public String getHomeCityName() {
        return homeCityName;
    }

    public void setHomeCityName(String homeCityName) {
        this.homeCityName = homeCityName == null ? null : homeCityName.trim();
    }

    public Long getHomeAreaCode() {
        return homeAreaCode;
    }

    public void setHomeAreaCode(Long homeAreaCode) {
        this.homeAreaCode = homeAreaCode;
    }

    public String getHomeAreaName() {
        return homeAreaName;
    }

    public void setHomeAreaName(String homeAreaName) {
        this.homeAreaName = homeAreaName == null ? null : homeAreaName.trim();
    }

    public String getHomeAddressDetail() {
        return homeAddressDetail;
    }

    public void setHomeAddressDetail(String homeAddressDetail) {
        this.homeAddressDetail = homeAddressDetail == null ? null : homeAddressDetail.trim();
    }

    public Long getCompanyProvinceCode() {
        return companyProvinceCode;
    }

    public void setCompanyProvinceCode(Long companyProvinceCode) {
        this.companyProvinceCode = companyProvinceCode;
    }

    public String getCompanyProvinceName() {
        return companyProvinceName;
    }

    public void setCompanyProvinceName(String companyProvinceName) {
        this.companyProvinceName = companyProvinceName == null ? null : companyProvinceName.trim();
    }

    public Long getCompanyCityCode() {
        return companyCityCode;
    }

    public void setCompanyCityCode(Long companyCityCode) {
        this.companyCityCode = companyCityCode;
    }

    public String getCompanyCityName() {
        return companyCityName;
    }

    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName == null ? null : companyCityName.trim();
    }

    public Long getCompanyAreaCode() {
        return companyAreaCode;
    }

    public void setCompanyAreaCode(Long companyAreaCode) {
        this.companyAreaCode = companyAreaCode;
    }

    public String getCompanyAreaName() {
        return companyAreaName;
    }

    public void setCompanyAreaName(String companyAreaName) {
        this.companyAreaName = companyAreaName == null ? null : companyAreaName.trim();
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail == null ? null : companyAddressDetail.trim();
    }

    public Integer getRealInfoAlterCount() {
        return realInfoAlterCount;
    }

    public void setRealInfoAlterCount(Integer realInfoAlterCount) {
        this.realInfoAlterCount = realInfoAlterCount;
    }

    public Integer getActivtiyInfoAudit() {
        return activtiyInfoAudit;
    }

    public void setActivtiyInfoAudit(Integer activtiyInfoAudit) {
        this.activtiyInfoAudit = activtiyInfoAudit;
    }

    public Integer getEducationalCode() {
        return educationalCode;
    }

    public void setEducationalCode(Integer educationalCode) {
        this.educationalCode = educationalCode;
    }

    public Integer getJobCode() {
        return jobCode;
    }

    public void setJobCode(Integer jobCode) {
        this.jobCode = jobCode;
    }

    public Integer getActivityInfoAlterCount() {
        return activityInfoAlterCount;
    }

    public void setActivityInfoAlterCount(Integer activityInfoAlterCount) {
        this.activityInfoAlterCount = activityInfoAlterCount;
    }

    public String getWxUid() {
        return wxUid;
    }

    public void setWxUid(String wxUid) {
        this.wxUid = wxUid == null ? null : wxUid.trim();
    }

    public String getQqUid() {
        return qqUid;
    }

    public void setQqUid(String qqUid) {
        this.qqUid = qqUid == null ? null : qqUid.trim();
    }

    public String getSinaUid() {
        return sinaUid;
    }

    public void setSinaUid(String sinaUid) {
        this.sinaUid = sinaUid == null ? null : sinaUid.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
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

    public Integer getIsFinishActivity() {
        return isFinishActivity;
    }

    public void setIsFinishActivity(Integer isFinishActivity) {
        this.isFinishActivity = isFinishActivity;
    }

    public Integer getInitiativeCancelCount() {
        return initiativeCancelCount;
    }

    public void setInitiativeCancelCount(Integer initiativeCancelCount) {
        this.initiativeCancelCount = initiativeCancelCount;
    }

    public Integer getPassiveCancellCount() {
        return passiveCancellCount;
    }

    public void setPassiveCancellCount(Integer passiveCancellCount) {
        this.passiveCancellCount = passiveCancellCount;
    }

    public Date getInitiativePunishedTime() {
        return initiativePunishedTime;
    }

    public void setInitiativePunishedTime(Date initiativePunishedTime) {
        this.initiativePunishedTime = initiativePunishedTime;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational;
    }

    public Boolean getUpdateIs() {
        return updateIs;
    }

    public void setUpdateIs(Boolean updateIs) {
        this.updateIs = updateIs;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public BigDecimal getNewbieAmount() {
        return newbieAmount;
    }

    public void setNewbieAmount(BigDecimal newbieAmount) {
        this.newbieAmount = newbieAmount;
    }
}
