package cn.zhaocaiapp.zc_app_android.bean.request.commerclal;

/**
 * Created by admin on 2018/9/11.
 */

public class PhotoImages {

    private String cover;
    private String logo;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "PhotoImages{" +
                "cover='" + cover + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
