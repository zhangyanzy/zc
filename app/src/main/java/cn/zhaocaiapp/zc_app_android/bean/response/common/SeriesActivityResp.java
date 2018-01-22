package cn.zhaocaiapp.zc_app_android.bean.response.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 林子
 * @filename SeriesActivityResp.java
 * @data 2018-01-22 11:24
 */
public class SeriesActivityResp {

    /**
     * 主键
     */
    private Long kid;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 串联活动名
     */
    private String serialActivityName;

    /**
     * 序号
     */
    private Integer serialNumber;

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

    private Integer lastOne;

    /**
     * 活动描述
     */
    private String contentRich;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getSerialActivityName() {
        return serialActivityName;
    }

    public void setSerialActivityName(String serialActivityName) {
        this.serialActivityName = serialActivityName;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getContentRich() {
        return contentRich;
    }

    public void setContentRich(String contentRich) {
        this.contentRich = contentRich;
    }

    public Integer getLastOne() {
        return lastOne;
    }

    public void setLastOne(Integer lastOne) {
        this.lastOne = lastOne;
    }
}
