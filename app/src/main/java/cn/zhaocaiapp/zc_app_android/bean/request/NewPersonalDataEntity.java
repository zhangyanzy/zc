package cn.zhaocaiapp.zc_app_android.bean.request;

/**
 * Created by admin on 2018/9/5.
 */

public class NewPersonalDataEntity {

    private String nickname;
    private String avatar;

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

    @Override
    public String toString() {
        return "NewPersonalDataEntity{" +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
