package cn.zhaocaiapp.zc_app_android.bean.response.my;

/**
 * Created by Administrator on 2018/1/23.
 */

public class UserLabelResp {
    /**
     * "name":    标签名称
     * "tagId":   标签ID
     * "times":   标签次数
     * "type":    标签类型  0  可选类型  1  非可选
     */

    private long tagId;
    private String name;
    private int times;
    private int type;

    public UserLabelResp(long tagId, String name, int times) {
        this.tagId = tagId;
        this.name = name;
        this.times = times;
    }

    public UserLabelResp(long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserLabelResp{" +
                "tagId=" + tagId +
                ", name='" + name + '\'' +
                ", times=" + times +
                ", type=" + type +
                '}';
    }
}
