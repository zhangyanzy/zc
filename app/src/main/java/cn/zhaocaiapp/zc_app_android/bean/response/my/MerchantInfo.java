package cn.zhaocaiapp.zc_app_android.bean.response.my;

import java.math.BigDecimal;

/**
 * Created by admin on 2018/9/13.
 */

public class MerchantInfo {
    private String name;//商户名称
    private String logo;//商户logo
    private String phone;//商家电话
    private String provinceName;//省份
    private String cityName;//城市
    private String areaName;//区县
    private String addressDetail;//详细地址
    private String license;//营业执照地址
    private BigDecimal amount;//商户余额
    private Long provinceCode;//省份code
    private Long cityCode;//城市code
    private Long areaCode;//区域code


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }


    @Override
    public String   toString() {
        return "MerchantInfo{" +
                "name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", phone='" + phone + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", license='" + license + '\'' +
                ", amount=" + amount +
                ", provinceCode=" + provinceCode +
                ", cityCode=" + cityCode +
                ", areaCode=" + areaCode +
                '}';
    }
}
