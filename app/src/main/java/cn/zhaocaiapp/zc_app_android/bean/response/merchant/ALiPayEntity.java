package cn.zhaocaiapp.zc_app_android.bean.response.merchant;

/**
 * Created by admin on 2018/9/14.
 */

public class ALiPayEntity {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ALiPayEntity{" +
                "data='" + data + '\'' +
                '}';
    }
}
