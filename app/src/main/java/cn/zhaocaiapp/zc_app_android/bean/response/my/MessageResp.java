package cn.zhaocaiapp.zc_app_android.bean.response.my;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MessageResp {
    /**
     *"content": ",    String    消息内容
     "messageId":,  Long     消息ID
     "readStatus":   Integer   读的状态0未读 1已读
     * */
    private String content;
    private long messageId;
    private int readStatus;

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
                '}';
    }
}
