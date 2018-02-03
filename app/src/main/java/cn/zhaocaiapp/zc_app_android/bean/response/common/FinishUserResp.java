package cn.zhaocaiapp.zc_app_android.bean.response.common;

/**
 * @author 林子
 * @filename FinishUserResp.java
 * @data 2018-01-19 13:52
 */
public class FinishUserResp {
    /**
     * 用户ID
     */
    private Long kid;

    /**
     * 头像
     */
    private String avatar;

    private String joinTime;

    private Long userActivityId;

    private Integer coopRoleType;

    /**
     * 会员昵称
     */
    private String nickname;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(Long userActivityId) {
        this.userActivityId = userActivityId;
    }

    public Integer getCoopRoleType() {
        return coopRoleType;
    }

    public void setCoopRoleType(Integer coopRoleType) {
        this.coopRoleType = coopRoleType;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}
