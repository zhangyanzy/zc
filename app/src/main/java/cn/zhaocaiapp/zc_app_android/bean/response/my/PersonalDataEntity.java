package cn.zhaocaiapp.zc_app_android.bean.response.my;

/**
 * Created by admin on 2018/9/4.
 */

public class PersonalDataEntity {

    private String phone;//用户手机号码
    private String nickname;//用户昵称
    private String companyStatus;//用户公司地址填写状态 1填写 0未填写
    private String avatar;//用户头像
    private String realInfoAuditStatus;//用户实名认证状态 0未认证 1待审核 2已认证 3 未通过
    private String familyStatus;//用户家庭地址填写状态 1填写 0未填写
    private String homeProvinceName;//家庭省份
    private String homeProvinceCode;//家庭城市编码
    private String homeCityName;//家庭城市
    private String homeCityCode;//家庭城市编码
    private String homeAreaName;//家庭区县
    private String homeAreaCode;//家庭区县编码
    private String homeAddressDetail;//家庭详细地址
    private String companyProvinceName;//工作省份
    private String companyProvinceCode;//工作省份编码
    private String companyCityName;//工作城市
    private String companyCityCode;//工作城市编码
    private String companyAreaName;//工作区县
    private String companyAreaCode;//工作区县编码
    private String companyAddressDetail;//工作详细地址
    private String name;//真实姓名
    private String idCard;//身份证号
    private String educational;//学历
    private String job;//工作
    private String  educationalCode;//学历code
    private String jobCode;//工作code

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealInfoAuditStatus() {
        return realInfoAuditStatus;
    }

    public void setRealInfoAuditStatus(String realInfoAuditStatus) {
        this.realInfoAuditStatus = realInfoAuditStatus;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getHomeProvinceName() {
        return homeProvinceName;
    }

    public void setHomeProvinceName(String homeProvinceName) {
        this.homeProvinceName = homeProvinceName;
    }

    public String getHomeProvinceCode() {
        return homeProvinceCode;
    }

    public void setHomeProvinceCode(String homeProvinceCode) {
        this.homeProvinceCode = homeProvinceCode;
    }

    public String getHomeCityName() {
        return homeCityName;
    }

    public void setHomeCityName(String homeCityName) {
        this.homeCityName = homeCityName;
    }

    public String getHomeCityCode() {
        return homeCityCode;
    }

    public void setHomeCityCode(String homeCityCode) {
        this.homeCityCode = homeCityCode;
    }

    public String getHomeAreaName() {
        return homeAreaName;
    }

    public void setHomeAreaName(String homeAreaName) {
        this.homeAreaName = homeAreaName;
    }

    public String getHomeAreaCode() {
        return homeAreaCode;
    }

    public void setHomeAreaCode(String homeAreaCode) {
        this.homeAreaCode = homeAreaCode;
    }

    public String getHomeAddressDetail() {
        return homeAddressDetail;
    }

    public void setHomeAddressDetail(String homeAddressDetail) {
        this.homeAddressDetail = homeAddressDetail;
    }

    public String getCompanyProvinceName() {
        return companyProvinceName;
    }

    public void setCompanyProvinceName(String companyProvinceName) {
        this.companyProvinceName = companyProvinceName;
    }

    public String getCompanyProvinceCode() {
        return companyProvinceCode;
    }

    public void setCompanyProvinceCode(String companyProvinceCode) {
        this.companyProvinceCode = companyProvinceCode;
    }

    public String getCompanyCityName() {
        return companyCityName;
    }

    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName;
    }

    public String getCompanyCityCode() {
        return companyCityCode;
    }

    public void setCompanyCityCode(String companyCityCode) {
        this.companyCityCode = companyCityCode;
    }

    public String getCompanyAreaName() {
        return companyAreaName;
    }

    public void setCompanyAreaName(String companyAreaName) {
        this.companyAreaName = companyAreaName;
    }

    public String getCompanyAreaCode() {
        return companyAreaCode;
    }

    public void setCompanyAreaCode(String companyAreaCode) {
        this.companyAreaCode = companyAreaCode;
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEducational() {
        return educational;
    }

    public void setEducational(String educational) {
        this.educational = educational;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducationalCode() {
        return educationalCode;
    }

    public void setEducationalCode(String educationalCode) {
        this.educationalCode = educationalCode;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    @Override
    public String toString() {
        return "PersonalDataEntity{" +
                "phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", companyStatus='" + companyStatus + '\'' +
                ", avatar='" + avatar + '\'' +
                ", realInfoAuditStatus='" + realInfoAuditStatus + '\'' +
                ", familyStatus='" + familyStatus + '\'' +
                ", homeProvinceName='" + homeProvinceName + '\'' +
                ", homeProvinceCode='" + homeProvinceCode + '\'' +
                ", homeCityName='" + homeCityName + '\'' +
                ", homeCityCode='" + homeCityCode + '\'' +
                ", homeAreaName='" + homeAreaName + '\'' +
                ", homeAreaCode='" + homeAreaCode + '\'' +
                ", homeAddressDetail='" + homeAddressDetail + '\'' +
                ", companyProvinceName='" + companyProvinceName + '\'' +
                ", companyProvinceCode='" + companyProvinceCode + '\'' +
                ", companyCityName='" + companyCityName + '\'' +
                ", companyCityCode='" + companyCityCode + '\'' +
                ", companyAreaName='" + companyAreaName + '\'' +
                ", companyAreaCode='" + companyAreaCode + '\'' +
                ", companyAddressDetail='" + companyAddressDetail + '\'' +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", educational='" + educational + '\'' +
                ", job='" + job + '\'' +
                ", educationalCode='" + educationalCode + '\'' +
                ", jobCode='" + jobCode + '\'' +
                '}';
    }
}
