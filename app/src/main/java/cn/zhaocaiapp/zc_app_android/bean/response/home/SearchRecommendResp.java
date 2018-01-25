package cn.zhaocaiapp.zc_app_android.bean.response.home;

import java.util.Date;

/**
 * 首页搜索推荐
 *
 * @author 林子
 * @filename SearchRecommendResp.java
 * @data 2018-01-24 15:25
 */
public class SearchRecommendResp {
    /**
     * 主键
     */
    private Long kid;

    /**
     * 搜索推荐名称
     */
    private String name;

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
        return "SearchRecommendResp{" +
                "kid=" + kid +
                ", name='" + name + '\'' +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }
}
