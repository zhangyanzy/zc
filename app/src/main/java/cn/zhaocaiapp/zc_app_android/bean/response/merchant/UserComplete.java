package cn.zhaocaiapp.zc_app_android.bean.response.merchant;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2018/9/28.
 */

public class UserComplete {

    private Date createTime;//完成时间
    private String name;//活动名称
    private BigDecimal rewardAmount;//获得钱数
    private String nickName;//用户名字

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "UserComplete{" +
                "createTime=" + createTime +
                ", name='" + name + '\'' +
                ", rewardAmount=" + rewardAmount +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
