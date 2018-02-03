package cn.zhaocaiapp.zc_app_android.bean.response.my;

/**
 * Created by Administrator on 2018/2/3.
 */

public class AccountResp {
    /**
     * "alipayIs": true,
     * "alipayNo": "",
     * "alipayOpenId": "",
     * "bankCard": "",
     * "bankIs": true,
     * "bankName": "",
     * "bankNameBranch": "",
     * "wechatIs": true,
     * "wechatNo": "",
     * "wechatOpenId": ""
     */
    private String alipayNo;
    private String alipayOpenId;
    private Boolean alipayIs;
    private String bankCard;
    private String bankName;
    private String bankNameBranch;
    private Boolean bankIs;
    private String wechatNo;
    private Boolean wechatIs;
    private String wechatOpenId;

    public String getAlipayOpenId() {
        return alipayOpenId;
    }

    public void setAlipayOpenId(String alipayOpenId) {
        this.alipayOpenId = alipayOpenId;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    public Boolean getAlipayIs() {
        return alipayIs;
    }

    public void setAlipayIs(Boolean alipayIs) {
        this.alipayIs = alipayIs;
    }

    public Boolean getBankIs() {
        return bankIs;
    }

    public void setBankIs(Boolean bankIs) {
        this.bankIs = bankIs;
    }

    public Boolean getWechatIs() {
        return wechatIs;
    }

    public void setWechatIs(Boolean wechatIs) {
        this.wechatIs = wechatIs;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNameBranch() {
        return bankNameBranch;
    }

    public void setBankNameBranch(String bankNameBranch) {
        this.bankNameBranch = bankNameBranch;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    @Override
    public String toString() {
        return "AccountResp{" +
                "alipayNo='" + alipayNo + '\'' +
                ", alipayOpenId='" + alipayOpenId + '\'' +
                ", alipayIs=" + alipayIs +
                ", bankCard='" + bankCard + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNameBranch='" + bankNameBranch + '\'' +
                ", bankIs=" + bankIs +
                ", wechatNo='" + wechatNo + '\'' +
                ", wechatIs=" + wechatIs +
                ", wechatOpenId='" + wechatOpenId + '\'' +
                '}';
    }
}
