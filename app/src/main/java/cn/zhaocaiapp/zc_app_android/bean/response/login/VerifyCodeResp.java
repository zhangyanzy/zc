package cn.zhaocaiapp.zc_app_android.bean.response.login;

/**
 * Created by Administrator on 2018/1/16.
 */

public class VerifyCodeResp {
    /**
     * "desc": "验证码超时无效",
     * "result": "success",
     * "status": "false"
     * "kid":        Integer     ID
     * "nickname": String      昵称
     * "phone":    String      手机号
     * "token":     String      token
     */
    private String desc;
    private boolean result;
    private boolean status;
    private long kid;
    private String nickname;
    private String phone;
    private String token;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VerifyCodeResp{" +
                "desc='" + desc + '\'' +
                ", result=" + result +
                ", status=" + status +
                ", kid=" + kid +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
