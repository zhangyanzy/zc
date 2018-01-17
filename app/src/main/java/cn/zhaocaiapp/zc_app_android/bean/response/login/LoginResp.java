package cn.zhaocaiapp.zc_app_android.bean.response.login;

/**
 * @author 林子
 * @filename OrderResp.java
 * @data 2018-01-03 11:42
 */
public class LoginResp {
    /**
     * token
     */
    private String token;

    private String avatar;

    private String desc;

    private String nickname;

    private String phone;

    private String result;

    private String type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginResp{" +
                "token='" + token + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + desc + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", result='" + result + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
