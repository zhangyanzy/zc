package cn.zhaocaiapp.zc_app_android.bean.message;

import java.util.Date;

/**
 * Created by jinxunmediapty.ltd on 2018/1/5.
 */

public class Message {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 消息体
     */
    private String msg;

    /**
     * 创建时间
     */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
