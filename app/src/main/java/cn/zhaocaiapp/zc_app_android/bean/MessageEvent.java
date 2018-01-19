package cn.zhaocaiapp.zc_app_android.bean;

/**
 * Created by ASUS on 2017/12/15.
 */

public class MessageEvent<T> {
    private T message;

    public MessageEvent(T message){
        this.message = message;
    }

    public T getMessage(){
        return message;
    }
}
