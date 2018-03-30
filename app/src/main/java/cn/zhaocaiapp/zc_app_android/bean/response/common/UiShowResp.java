package cn.zhaocaiapp.zc_app_android.bean.response.common;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/3/30.
 */

public class UiShowResp {
    /**
     * appVersion = "0.0.5";
     * appVersionCode = 5;
     * content = "{\"uiShow\":true}";
     * createTime = "2018-03-26 14:07:47";
     * isDelete = 0;
     * kid = 105;
     * updateTime = "2018-03-30 10:07:59";
     */
    private String appVersion;
    private int appVersionCode;
    private String createTime;
    private int isDelete;
    private long kid;
    private String updateTime;
    private String content;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UiShowResp{" +
                "appVersion='" + appVersion + '\'' +
                ", appVersionCode=" + appVersionCode +
                ", createTime='" + createTime + '\'' +
                ", isDelete=" + isDelete +
                ", kid=" + kid +
                ", updateTime='" + updateTime + '\'' +
                ", content=" + content +
                '}';
    }

//    public class ContentBean{
//        private boolean uiShow;
//
//        public boolean isUiShow() {
//            return uiShow;
//        }
//
//        public void setUiShow(boolean uiShow) {
//            this.uiShow = uiShow;
//        }
//
//        @Override
//        public String toString() {
//            return "ContentBean{" +
//                    "uiShow=" + uiShow +
//                    '}';
//        }
//    }

}
