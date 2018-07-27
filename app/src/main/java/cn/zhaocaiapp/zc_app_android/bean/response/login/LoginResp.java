package cn.zhaocaiapp.zc_app_android.bean.response.login;

import android.support.annotation.Nullable;

/**
 * @author 林子
 * @filename OrderResp.java
 * @data 2018-01-03 11:42
 */
public class LoginResp {
    /**
     * "avatar":  头像
     * "kid": Long  ID
     * "desc": 接口功能说明
     * "nickname":  会员昵称
     * "phone": 手机号
     * "result":  返回数据状态 success :成功
     * "token": token
     * "type":  登录类型
     * "uid": 第三方登录唯一标识
     * alias
     */
    private String token;
    private String avatar;
    private String nickname;
    private String phone;
    private int type;
    private long kid;
    private String uid;
    private String alias;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginResp{" +
                "token='" + token + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", kid=" + kid +
                ", uid='" + uid + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
