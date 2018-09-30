package cn.zhaocaiapp.zc_app_android.bean.response.merchant;

/**
 * Created by admin on 2018/9/14.
 */

public class RealNameEntity {

    private int realInfoAuditStatus;//用户实名认证状态 0未认证 1待审核 2已认证 3 未通过
    private String name;//真实姓名
    private String idCard;//身份证号码
    private String educationalCode;//学历code
    private String jobCode;//工作code
    private String  educational;//学历
    private String job;//工作

    public int getRealInfoAuditStatus() {
        return realInfoAuditStatus;
    }

    public void setRealInfoAuditStatus(int realInfoAuditStatus) {
        this.realInfoAuditStatus = realInfoAuditStatus;
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


    @Override
    public String toString() {
        return "RealNameEntity{" +
                "realInfoAuditStatus=" + realInfoAuditStatus +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", educationalCode=" + educationalCode +
                ", jobCode=" + jobCode +
                ", educational='" + educational + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
