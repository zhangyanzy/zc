package cn.zhaocaiapp.zc_app_android.bean.response;

/**
 * Created by admin on 2018/9/11.
 */

public class CheckIsCommerclal {

    private int auditStatus;


    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public String toString() {
        return "CheckIsCommerclal{" +
                "auditStatus=" + auditStatus +
                '}';
    }
}
