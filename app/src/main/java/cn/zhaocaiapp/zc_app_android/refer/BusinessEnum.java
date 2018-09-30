package cn.zhaocaiapp.zc_app_android.refer;

/**
 * Created by admin on 2018/9/20.
 */

public enum BusinessEnum {
    SUCCESS(0, "OK"),
    FAILED(-1, "FAILED"),
    USER_NAME_NOT_NULL(-1, "账号不能为空"),
    USER_PASSWORD_NOT_NULL(-1, "密码不能为空"),
    TOKEN_NULL(-1, "Token非法，无法获取登录用户"),
    USER_ID_NULL(-1, "用户ID为空，无法注销账号"),
    VERYFI_CODE_ERROR(500, "验证码错误"),
    USER_DIS_ENABLED(3, "用户已停用"),
    USER_NOT_EXIST(-1, "用户不存在"),
    SEVENDAY_SECOND(604800, "七天的秒数"),
    AUTH_NOT(-1, "该用户无权访问"),
    NO_AVAIL(-3, "token失效");
    // 业务码
    private Integer code;
    // 描述
    private String desc;
    BusinessEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public Integer getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
