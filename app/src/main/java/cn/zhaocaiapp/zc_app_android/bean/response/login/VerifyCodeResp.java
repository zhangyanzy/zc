package cn.zhaocaiapp.zc_app_android.bean.response.login;

/**
 * Created by Administrator on 2018/1/16.
 */

public class VerifyCodeResp {
    /**
     * "desc": "验证码超时无效",
     "result": "success",
     "status": "false"
     * */
    private String desc;
    private String result;
    private boolean status;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
                ", result='" + result + '\'' +
                ", status=" + status +
                '}';
    }
}
