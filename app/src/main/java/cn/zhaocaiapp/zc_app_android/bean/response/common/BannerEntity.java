package cn.zhaocaiapp.zc_app_android.bean.response.common;

/**
 * Created by admin on 2018/8/27.
 */

public class BannerEntity {

    /**
     * 主键
     */
    private Long kid;
    private String member_img;
    private String activity_image1;
    private String address_detail;
    private String activity_vedio;
    private int if_check;
    /**
     * 活动类型 0线下活动 1视频活动 2问卷活动
     */
    private Integer  activity_form;

    public int getIf_check() {
        return if_check;
    }

    public void setIf_check(int if_check) {
        this.if_check = if_check;
    }


    public Integer getActivity_form() {
        return activity_form;
    }

    public void setActivity_form(Integer activity_form) {
        this.activity_form = activity_form;
    }

    /**
     * 活动名称
     */
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }


    public String getActivity_vedio() {
        return activity_vedio;
    }

    public void setActivity_vedio(String activity_vedio) {
        this.activity_vedio = activity_vedio;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public String getMember_img() {
        return member_img;
    }

    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }

    public String getActivity_image1() {
        return activity_image1;
    }

    public void setActivity_image1(String activity_image1) {
        this.activity_image1 = activity_image1;
    }

    @Override
    public String toString() {
        return "BannerEntity{" +
                "kid=" + kid +
                ", member_img='" + member_img + '\'' +
                ", activity_image1='" + activity_image1 + '\'' +
                ", address_detail='" + address_detail + '\'' +
                ", activity_vedio='" + activity_vedio + '\'' +
                ", activity_form=" + activity_form +
                ", name='" + name + '\'' +
                '}';
    }
}
