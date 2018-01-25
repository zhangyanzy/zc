package cn.zhaocaiapp.zc_app_android.bean.response.home;

/**
 * 定位对象
 *
 * @author 林子
 * @filename Location.java
 * @data 2018-01-25 14:23
 */
public class Gps {
    /**
     * 是否启动gps
     */
    private Boolean isOpen;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 区
     */
    private String district;

    /**
     * 区域 码
     */
    private String adCode;

    /**
     * 地址
     */
    private String address;

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Gps{" +
                "isOpen=" + isOpen +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                ", adCode='" + adCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
