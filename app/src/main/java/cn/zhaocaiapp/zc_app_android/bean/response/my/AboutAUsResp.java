package cn.zhaocaiapp.zc_app_android.bean.response.my;

/**
 * Created by Administrator on 2018/2/5.
 */

public class AboutAUsResp {
    /**
     * "address": "辽宁省大连市甘井子区奥斯卡小区54号",
     * "email": "1231235@qq.com",
     * "id": 0,
     * "isDelete": 0,
     * "phone": "13224112266",
     */
    private String address;
    private String email;
    private String phone;
    private long id;
    private int isDelete;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "AboutAUsResp{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", isDelete=" + isDelete +
                '}';
    }
}
