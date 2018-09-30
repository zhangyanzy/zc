package cn.zhaocaiapp.zc_app_android.bean.response.merchant;

import java.util.Map;

/**
 * Created by admin on 2018/9/14.
 */

public class PayResult {

    private String resultStatus;
    private String result;
    private String memo;

    public PayResult(Map<String, String> obj) {


    }


    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "PayResult{" +
                "resultStatus='" + resultStatus + '\'' +
                ", result='" + result + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
