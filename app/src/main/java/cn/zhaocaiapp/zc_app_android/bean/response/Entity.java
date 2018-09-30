package cn.zhaocaiapp.zc_app_android.bean.response;

/**
 * Created by admin on 2018/9/11.
 */

public class Entity {

    private int code;
    private String desc;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
