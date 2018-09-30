package cn.zhaocaiapp.zc_app_android.bean.response.merchant;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2018/9/26.
 */

public class PayBillEntity {

    private String kid;//主键
    private BigDecimal amount;//充值金额
    private Date createTime;//创建时间


    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PayBillEntity{" +
                "kid='" + kid + '\'' +
                ", amount=" + amount +
                ", createTime=" + createTime +
                '}';
    }
}
