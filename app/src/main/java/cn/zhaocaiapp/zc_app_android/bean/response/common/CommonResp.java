package cn.zhaocaiapp.zc_app_android.bean.response.common;

/**
 * @author 林子
 * @filename CommonResp.java
 * @data 2018-01-03 11:42
 */
public class CommonResp {
    /**
     * "desc": "更新成功",
     * "result": "success"
     */
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
        return "CommonResp{" +
                "desc='" + desc + '\'' +
                ", result=" + result +
                '}';
    }
}
