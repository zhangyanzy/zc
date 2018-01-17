package cn.zhaocaiapp.zc_app_android.bean.response.login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class ObtainCodeResp {
    /**
     * "desc": "", 接口说明
     * "fee": "",费用
     * "result": "",状态
     * "send_id": "",发送ID
     * "sms_credits": SMS
     */
    private String desc;
    private double fee;
    private String result;
    private String send_id;
    private String sms_credits;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getSms_credits() {
        return sms_credits;
    }

    public void setSms_credits(String sms_credits) {
        this.sms_credits = sms_credits;
    }

    @Override
    public String toString() {
        return "ObtainCodeResp{" +
                "desc='" + desc + '\'' +
                ", fee=" + fee +
                ", result='" + result + '\'' +
                ", send_id='" + send_id + '\'' +
                ", sms_credits='" + sms_credits + '\'' +
                '}';
    }

}
