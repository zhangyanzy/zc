package cn.zhaocaiapp.zc_app_android.bean.response.login;

/**
 * Created by Administrator on 2018/1/16.
 */

public class SignupResp {
    /**
     *"avatar": String    头像
     "desc":  String    接口描述
     "kid":     Integer   主键
     "nickname": String  昵称
     "phone": String    手机
     "token": String    token
     * */
    private String avatar;
    private String desc;
    private long kid;
    private String nickname;
    private String phone;
    private String token;

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SignupResp{" +
                "avatar='" + avatar + '\'' +
                ", desc='" + desc + '\'' +
                ", kid=" + kid +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
