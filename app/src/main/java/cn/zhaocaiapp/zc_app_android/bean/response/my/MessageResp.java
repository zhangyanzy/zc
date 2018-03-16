package cn.zhaocaiapp.zc_app_android.bean.response.my;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MessageResp {
    /**
     *"content": ",    String    消息内容
     "messageId":,  Long     消息ID
     "readStatus":   Integer   读的状态0未读 1已读
     * */
    private String title;
    private String content;
    private long messageId;
    private int readStatus;
    private Date createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    @Override
    public String toString() {
        return "MessageResp{" +
                "content='" + content + '\'' +
                ", messageId=" + messageId +
                ", readStatus=" + readStatus +
                ", createTime=" + createTime +
                '}';
    }
}
