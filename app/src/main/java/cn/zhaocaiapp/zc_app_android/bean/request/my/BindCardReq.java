package cn.zhaocaiapp.zc_app_android.bean.request.my;

/**
 * Created by Administrator on 2018/2/3.
 */

public class BindCardReq {
    /**
     * type”    :       Integer     类型       0 支付宝  1 微信   2 银行卡（必填）
     * "alipayNo":      String     支付宝账号               （选填，对应类型信息必填，以下类同）
     * “alipayOpenId”   String      支付宝openid
     * “wechatNo”      String      微信
     * “wechatOpenId”  String      微信openid
     * “name”          String      银行卡用户名
     * “bankCard”      String      银行卡号
     * “bankName”     String      银行卡名
     * “bankNameBranch”   String  银行分支名称
     */
    private int type;
    private String alipayNo;
    private String alipayOpenId;
    private String wechatNo;
    private String wechatOpenId;
    private String name;
    private String bankCard;
    private String bankName;
    private String bankNameBranch;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "BindCardReq{" +
                "type=" + type +
                ", alipayNo='" + alipayNo + '\'' +
                ", alipayOpenId='" + alipayOpenId + '\'' +
                ", wechatNo='" + wechatNo + '\'' +
                ", wechatOpenId='" + wechatOpenId + '\'' +
                ", name='" + name + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankNameBranch='" + bankNameBranch + '\'' +
                '}';
    }
}
