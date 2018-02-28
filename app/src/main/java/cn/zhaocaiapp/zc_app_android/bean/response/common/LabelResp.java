package cn.zhaocaiapp.zc_app_android.bean.response.common;

import java.util.Date;

/**
 * Created by Administrator on 2018/2/2.
 */

public class LabelResp {
    /**
     * "createTime": "2018-01-10 05:53:47",
     * "kid": 2,
     * "isDelete": 0,
     * "isSelect": 1,
     * "name": "足球a",
     * “isSelected”:1,
     * "updateTime": "2018-01-10 08:50:50"
     */
    private Date createTime;
    private int kid;
    private int isDelete;
    private int isSelected;
    private String name;
    private Date updateTime;

    private boolean isChecked;// 是否选中

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "LabelResp{" +
                "createTime=" + createTime +
                ", kid=" + kid +
                ", isDelete=" + isDelete +
                ", isSelected=" + isSelected +
                ", name='" + name + '\'' +
                ", updateTime=" + updateTime +
                ", isChecked=" + isChecked +
                '}';
    }
}
