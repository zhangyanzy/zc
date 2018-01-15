package cn.zhaocaiapp.zc_app_android.bean.response.home;


import java.util.Date;

/**
 * @author 林子
 * @filename LocationResp.java
 * @data 2018-01-03 11:41
 */
public class LocationResp {
    /**
     * 主键
     */
    private Long id;

    /**
     * 地区编码
     */
    private Long areaCode;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 短名称
     */
    private String shortName;

    /**
     * 区域等级
     */
    private Integer areaLevel;

    /**
     * 电话区号
     */
    private String phoneCode;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 完整级别
     */
    private String mergerName;

    /**
     * 经度
     */
    private Long lng;

    /**
     * 纬度
     */
    private Long lat;

    /**
     * 地区拼音
     */
    private String pinyin;

    /**
     * 创建人
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode == null ? null : phoneCode.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName == null ? null : mergerName.trim();
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
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

    @Override
    public String toString() {
        return "LocationResp{" +
                "id=" + id +
                ", areaCode=" + areaCode +
                ", areaName='" + areaName + '\'' +
                ", parentId=" + parentId +
                ", shortName='" + shortName + '\'' +
                ", areaLevel=" + areaLevel +
                ", phoneCode='" + phoneCode + '\'' +
                ", postCode='" + postCode + '\'' +
                ", mergerName='" + mergerName + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", pinyin='" + pinyin + '\'' +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }
}
