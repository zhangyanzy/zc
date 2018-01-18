package cn.zhaocaiapp.zc_app_android.bean.response.login;

/**
 * Created by Administrator on 2018/1/18.
 */

public class LoginOutResp {
    /**
     * "description": "登出成功",
     "result": "success"
     * */
    private String desc;
    private boolean result;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "LoginOutResp{" +
                "desc='" + desc + '\'' +
                ", result=" + result +
                '}';
    }
}
