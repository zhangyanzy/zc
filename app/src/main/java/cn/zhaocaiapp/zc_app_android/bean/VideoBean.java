package cn.zhaocaiapp.zc_app_android.bean;

/**
 * Created by Administrator on 2018/3/20.
 */

public class VideoBean {
    private float currentTime;
    private String src;

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "currentTime=" + currentTime +
                ", src='" + src + '\'' +
                '}';
    }
}
