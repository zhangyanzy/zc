package cn.zhaocaiapp.zc_app_android.bean.response.member;


import java.util.Date;

/**
 * @author 林子
 * @filename StoreResp.java
 * @data 2018-01-03 11:21
 */
public class MemberResp {
    /**
     * 主键
     */
    private Long kid;

    /**
     * 商家id
     */
    private Long memberId;

    /**
     * 商家名称
     */
    private String name;

    /**
     * 商家状态 0未提交 1待审核 2已通过 3未通过
     */
    private Integer memberStatus;

    /**
     * 商家状态名称
     */
    private String statusName;

    /**
     * logo地址
     */
    private String logo;

    /**
     * 商家电话
     */
    private String phone;

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
     * 审核备注
     */
    private String auditMemo;

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

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
        this.provinceName = provinceName == null ? null : provinceName.trim();
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
        this.cityName = cityName == null ? null : cityName.trim();
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
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public String getAuditMemo() {
        return auditMemo;
    }

    public void setAuditMemo(String auditMemo) {
        this.auditMemo = auditMemo == null ? null : auditMemo.trim();
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "MemberResp{" +
                "kid=" + kid +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", memberStatus=" + memberStatus +
                ", statusName='" + statusName + '\'' +
                ", logo='" + logo + '\'' +
                ", phone='" + phone + '\'' +
                ", provinceCode=" + provinceCode +
                ", provinceName='" + provinceName + '\'' +
                ", cityCode=" + cityCode +
                ", cityName='" + cityName + '\'' +
                ", areaCode=" + areaCode +
                ", areaName='" + areaName + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", auditMemo='" + auditMemo + '\'' +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }
}
