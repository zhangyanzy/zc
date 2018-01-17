package cn.zhaocaiapp.zc_app_android.bean.response.login;

/**
 * Created by Administrator on 2018/1/16.
 */

public class CheckPhoneResp {
    /**
     * "exist": false,
     * "msg": "手机号未注册",
     * "result": "success"
     */
    private boolean exist;
    private String msg;
    private String result;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CheckPhoneResp{" +
                "exist=" + exist +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
