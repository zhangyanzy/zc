package cn.zhaocaiapp.zc_app_android.bean.response.my;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/24.
 */

public class UserDetailResp {
    private ActivityInfoBean activtiyInfo;
    private BaseInfoBean baseInfo;
    private RealInfoBean realInfo;

    public ActivityInfoBean getActivtiyInfo() {
        return activtiyInfo;
    }

    public void setActivtiyInfo(ActivityInfoBean activtiyInfo) {
        this.activtiyInfo = activtiyInfo;
    }

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public RealInfoBean getRealInfo() {
        return realInfo;
    }

    public void setRealInfo(RealInfoBean realInfo) {
        this.realInfo = realInfo;
    }

    public class ActivityInfoBean {
        /**
         * "educationalCode": Long    学历ID
         * "jobCode": Long   职业ID
         * "job”：   String     职业
         * "educational”:  String  学历
         * “activityInfoAlterCount”: Integer  更改次数
         * “UpdateIs”:   Boolean  true 可更改，false 不可
         * "activtiyInfoAudit": Integer 0-未变动 1-变动1次 2-变动2次 3-待审核 4-审核通过 5-审核未通过
         */
        private long educationalCode;
        private long jobCode;
        private String job;
        private String educational;
        private int activityInfoAlterCount;
        private boolean UpdateIs;
        private Integer activtiyInfoAudit;

        public Integer getActivtiyInfoAudit() {
            return activtiyInfoAudit;
        }

        public void setActivtiyInfoAudit(Integer activtiyInfoAudit) {
            this.activtiyInfoAudit = activtiyInfoAudit;
        }

        public long getEducationalCode() {
            return educationalCode;
        }

        public void setEducationalCode(long educationalCode) {
            this.educationalCode = educationalCode;
        }

        public long getJobCode() {
            return jobCode;
        }

        public void setJobCode(long jobCode) {
            this.jobCode = jobCode;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getEducational() {
            return educational;
        }

        public void setEducational(String educational) {
            this.educational = educational;
        }

        public int getActivityInfoAlterCount() {
            return activityInfoAlterCount;
        }

        public void setActivityInfoAlterCount(int activityInfoAlterCount) {
            this.activityInfoAlterCount = activityInfoAlterCount;
        }

        public boolean isUpdateIs() {
            return UpdateIs;
        }

        public void setUpdateIs(boolean updateIs) {
            UpdateIs = updateIs;
        }

        @Override
        public String toString() {
            return "ActivityInfoBean{" +
                    "educationalCode=" + educationalCode +
                    ", jobCode=" + jobCode +
                    ", job='" + job + '\'' +
                    ", educational='" + educational + '\'' +
                    ", activityInfoAlterCount=" + activityInfoAlterCount +
                    ", UpdateIs=" + UpdateIs +
                    ", activtiyInfoAudit=" + activtiyInfoAudit +
                    '}';
        }
    }

    public class BaseInfoBean {
        /**
         * "avatar":           String   图像
         * "companyAddressDetail": String   公司详细地址
         * "companyAreaCode": Long   工作区县编码,
         * "companyAreaName": String   工作区县,
         * "companyCityCode":  Long   工作城市编码
         * "companyCityName":  String   工作城市
         * "companyProvinceCode": Long   工作省份编码
         * "companyProvinceName": String   工作省份
         * "homeAddressDetail": String      家庭详细地址
         * "homeAreaCode": Long    家庭区县编码
         * "homeAreaName": String   家庭区县
         * "homeCityCode": Long   家庭城市编码
         * "homeCityName": String   家庭城市
         * "homeProvinceCode": Long   家庭省份编码
         * "homeProvinceName": Long   家庭省份
         * "nickname": String   会员昵称
         * "phone": String   手机号码
         */
        private String avatar;
        private String companyAddressDetail;
        private long companyAreaCode;
        private String companyAreaName;
        private long companyCityCode;
        private String companyCityName;
        private long companyProvinceCode;
        private String companyProvinceName;
        private String homeAddressDetail;
        private long homeAreaCode;
        private String homeAreaName;
        private long homeCityCode;
        private String homeCityName;
        private long homeProvinceCode;
        private String homeProvinceName;
        private String nickname;
        private String phone;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCompanyAddressDetail() {
            return companyAddressDetail;
        }

        public void setCompanyAddressDetail(String companyAddressDetail) {
            this.companyAddressDetail = companyAddressDetail;
        }

        public long getCompanyAreaCode() {
            return companyAreaCode;
        }

        public void setCompanyAreaCode(long companyAreaCode) {
            this.companyAreaCode = companyAreaCode;
        }

        public String getCompanyAreaName() {
            return companyAreaName;
        }

        public void setCompanyAreaName(String companyAreaName) {
            this.companyAreaName = companyAreaName;
        }

        public long getCompanyCityCode() {
            return companyCityCode;
        }

        public void setCompanyCityCode(long companyCityCode) {
            this.companyCityCode = companyCityCode;
        }

        public String getCompanyCityName() {
            return companyCityName;
        }

        public void setCompanyCityName(String companyCityName) {
            this.companyCityName = companyCityName;
        }

        public long getCompanyProvinceCode() {
            return companyProvinceCode;
        }

        public void setCompanyProvinceCode(long companyProvinceCode) {
            this.companyProvinceCode = companyProvinceCode;
        }

        public String getCompanyProvinceName() {
            return companyProvinceName;
        }

        public void setCompanyProvinceName(String companyProvinceName) {
            this.companyProvinceName = companyProvinceName;
        }

        public String getHomeAddressDetail() {
            return homeAddressDetail;
        }

        public void setHomeAddressDetail(String homeAddressDetail) {
            this.homeAddressDetail = homeAddressDetail;
        }

        public long getHomeAreaCode() {
            return homeAreaCode;
        }

        public void setHomeAreaCode(long homeAreaCode) {
            this.homeAreaCode = homeAreaCode;
        }

        public String getHomeAreaName() {
            return homeAreaName;
        }

        public void setHomeAreaName(String homeAreaName) {
            this.homeAreaName = homeAreaName;
        }

        public long getHomeCityCode() {
            return homeCityCode;
        }

        public void setHomeCityCode(long homeCityCode) {
            this.homeCityCode = homeCityCode;
        }

        public String getHomeCityName() {
            return homeCityName;
        }

        public void setHomeCityName(String homeCityName) {
            this.homeCityName = homeCityName;
        }

        public long getHomeProvinceCode() {
            return homeProvinceCode;
        }

        public void setHomeProvinceCode(long homeProvinceCode) {
            this.homeProvinceCode = homeProvinceCode;
        }

        public String getHomeProvinceName() {
            return homeProvinceName;
        }

        public void setHomeProvinceName(String homeProvinceName) {
            this.homeProvinceName = homeProvinceName;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "BaseInfoBean{" +
                    "avatar='" + avatar + '\'' +
                    ", companyAddressDetail='" + companyAddressDetail + '\'' +
                    ", companyAreaCode=" + companyAreaCode +
                    ", companyAreaName='" + companyAreaName + '\'' +
                    ", companyCityCode=" + companyCityCode +
                    ", companyCityName='" + companyCityName + '\'' +
                    ", companyProvinceCode=" + companyProvinceCode +
                    ", companyProvinceName='" + companyProvinceName + '\'' +
                    ", homeAddressDetail='" + homeAddressDetail + '\'' +
                    ", homeAreaCode=" + homeAreaCode +
                    ", homeAreaName='" + homeAreaName + '\'' +
                    ", homeCityCode=" + homeCityCode +
                    ", homeCityName='" + homeCityName + '\'' +
                    ", homeProvinceCode=" + homeProvinceCode +
                    ", homeProvinceName='" + homeProvinceName + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    public class RealInfoBean {
        /**
         * "birthday": Date    生日
         * "idCard": String   身份证号码
         * "idCardPath": String    身份证反面路径
         * "name": String   姓名
         * "realInfoAuditStatus": Integer  实名认证状态实名认证状态 0未认证 1待审核 2已认证 3 未通过
         * "realInfoAlterCount": Integer  更新次数
         * “UpdateIs”:   Boolean  true 可更改，false 不可
         * "sex": Integer    性别
         */
        private String birthdayStr;
        private String idCard;
        private String idCardPath;
        private String name;
        private int realInfoAuditStatus;
        private int realInfoAlterCount;
        private boolean UpdateIs;
        private int sex;

        public String getBirthdayStr() {
            return birthdayStr;
        }

        public void setBirthdayStr(String birthdayStr) {
            this.birthdayStr = birthdayStr;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCardPath() {
            return idCardPath;
        }

        public void setIdCardPath(String idCardPath) {
            this.idCardPath = idCardPath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRealInfoAuditStatus() {
            return realInfoAuditStatus;
        }

        public void setRealInfoAuditStatus(int realInfoAuditStatus) {
            this.realInfoAuditStatus = realInfoAuditStatus;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getRealInfoAlterCount() {
            return realInfoAlterCount;
        }

        public void setRealInfoAlterCount(int realInfoAlterCount) {
            this.realInfoAlterCount = realInfoAlterCount;
        }

        public boolean isUpdateIs() {
            return UpdateIs;
        }

        public void setUpdateIs(boolean updateIs) {
            UpdateIs = updateIs;
        }

        @Override
        public String toString() {
            return "RealInfoBean{" +
                    "birthdayStr='" + birthdayStr + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", idCardPath='" + idCardPath + '\'' +
                    ", name='" + name + '\'' +
                    ", realInfoAuditStatus=" + realInfoAuditStatus +
                    ", realInfoAlterCount=" + realInfoAlterCount +
                    ", UpdateIs=" + UpdateIs +
                    ", sex=" + sex +
                    '}';
        }
    }

}
