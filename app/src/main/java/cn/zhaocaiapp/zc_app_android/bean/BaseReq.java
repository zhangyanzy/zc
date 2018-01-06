package cn.zhaocaiapp.zc_app_android.bean;

/**
 * <网络请求返回体>
 *
 * @author 林子
 * @filename BaseReq.java
 * @data 2018-01-03 11:35
 */
public class BaseReq {
    /**
     * 返回状态码
     */
    protected int retcode;

    /**
     * 返回信息描述
     */
    protected String retinfo;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetinfo() {
        return retinfo;
    }

    public void setRetinfo(String retinfo) {
        this.retinfo = retinfo;
    }
}
