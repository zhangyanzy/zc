package cn.zhaocaiapp.zc_app_android.bean.response.home;

/**
 * Created by admin on 2018/8/28.
 */

public class BlurredEntity {

    private String name;
    private int activity_id;
    private int activityForm;


    public BlurredEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public int getActivityForm() {
        return activityForm;
    }

    public void setActivityForm(int activityForm) {
        this.activityForm = activityForm;
    }

    @Override
    public String toString() {
        return "BlurredEntity{" +
                "name='" + name + '\'' +
                ", activity_id=" + activity_id +
                ", activityForm=" + activityForm +
                '}';
    }
}
