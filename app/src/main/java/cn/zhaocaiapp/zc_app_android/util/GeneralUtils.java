package cn.zhaocaiapp.zc_app_android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <通用工具类>
 *
 * @author caoyinfei
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]
 * @since [V1]
 */
public final class GeneralUtils {

    /**
     * 判断对象是否为null , 为null返回true,否则返回false
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return (null == obj) ? true : false;
    }

    /**
     * 判断对象是否为null , 为null返回false,否则返回true
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str) {
        return (null == str || "".equals(str.trim())) ? true : false;
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回false,否则返回true
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNotNullOrZeroLenght(String str) {
        return !isNullOrZeroLenght(str);
    }

    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回true ,否则返回false
     *
     * @param c collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNullOrZeroSize(Collection<? extends Object> c) {
        return isNull(c) || c.isEmpty();
    }

    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回false, 否则返回true
     *
     * @param c collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNotNullOrZeroSize(Collection<? extends Object> c) {
        return !isNullOrZeroSize(c);
    }

    /**
     * 判断数字类型是否为null或者0，如果是返回true，否则返回false
     *
     * @param number 被判断的数字
     * @return boolean
     */
    public static boolean isNullOrZero(Number number) {
        if (GeneralUtils.isNotNull(number)) {
            return (number.intValue() != 0) ? false : true;
        }
        return true;
    }

    /**
     * 判断数字类型是否不为null或者0，如果是返回true，否则返回false
     *
     * @param number 被判断的数字
     * @return boolean
     */
    public static boolean isNotNullOrZero(Number number) {
        return !isNullOrZero(number);
    }

    /**
     * <保留两位有效数字> <功能详细描述>
     *
     * @param num String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String retained2SignificantFigures(String num) {
        return new BigDecimal(num).setScale(2, RoundingMode.HALF_UP).toString();
    }

    /**
     * 保留小数点后两位，仅保留有效位
     */
    public static String getBigDecimalToTwo(BigDecimal bigDecimal) {
        DecimalFormat df2 = new DecimalFormat("0.##");
        String str2 = df2.format(bigDecimal);
        return str2;
    }

    /**
     * <减法运算并保留两位有效数字> <功能详细描述>
     *
     * @param subt1 String
     * @param subt2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String subtract(String subt1, String subt2) {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <加法运算并保留两位有效数字> <功能详细描述>
     *
     * @param addend1
     * @param addend2
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String add(String addend1, String addend2) {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <乘法运算并保留两位有效数字> <功能详细描述>
     *
     * @param factor1 String
     * @param factor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String multiply(String factor1, String factor2) {
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String divide(String divisor1, String divisor2) {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <获取当前日期 格式 yyyy-MM-dd HH:mm:ss> <功能详细描述>
     *
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getNowDateString() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitTodate(String str) {
        String strs = "";
        if (isNotNullOrZeroLenght(str)) {
            strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        }
        return strs;
    }

    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY/MM/DD> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitTodateSprit(String str) {
        String strs = "";
        if (isNotNullOrZeroLenght(str)) {
            strs = str.substring(0, 4) + "/" + str.substring(4, 6) + "/" + str.substring(6, 8);
        }
        return strs;
    }

    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD hh:mm> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */

    public static String splitToMinute(String str) {
        if (isNullOrZeroLenght(str) || str.length() != 14) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " "
                + str.substring(8, 10) + ":" + str.substring(10, 12);
        return strs;
    }

    /**
     * <将YYYY-MM-DD 转换为 YYYYMMDD> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToMinuteNoLine(String str) {
        if (!str.contains("-")) {
            return str;
        }
        String strs = "";
        String[] strsArr = str.split("-");
        if (strsArr[1].length() == 1) {
            strsArr[1] = "0" + strsArr[1];
        }

        if (strsArr[2].length() == 1) {
            strsArr[2] = "0" + strsArr[2];
        }

        strs = strsArr[0] + strsArr[1] + strsArr[2];
        return strs;
    }

    /**
     * <将YYYYMMDDHHmmss 转换为 YYYY-MM-DD hh:mm:ss> <功能详细描述>
     *
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String splitToSecond(String str) {
        if (isNullOrZeroLenght(str) || str.length() != 14) {
            return str;
        }

        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " " + str.substring(8, 10)
                        + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
        return strs;
    }

    /**
     * <邮箱判断>
     * <功能详细描述>
     *
     * @param email
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmail(String email) {
        String str =
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * <手机号码判断>
     *
     * @param tel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isTel(String tel) {
        String str = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * <邮编判断>
     * <功能详细描述>
     *
     * @param post
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isPost(String post) {
        String patrn = "^[1-9][0-9]{5}$";
        Pattern p = Pattern.compile(patrn);
        Matcher m = p.matcher(post);
        return m.matches();
    }

    /**
     * <密码规则判断>
     * <功能详细描述>
     * <p>
     * 6-20位数字+字母
     *
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean IsPassword(String password) {
        String s = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(s);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * <获取imei>
     * <功能详细描述>
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return telephonyManager.getDeviceId();
    }

    /**
     * 数组转字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 字符串转数组
     *
     * @param strs
     * @return
     */
    public static List<String> stringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }

    /**
     * 根据用户名的不同长度来进行替换 ，达到保密效果
     *
     * @param userName 用户名
     * @return 替换后的用户名
     */
    public static String userNameReplaceWithStar(String userName) {
        String userNameAfterReplaced = "";

        if (userName == null) {
            userName = "";
        }

        int nameLength = userName.length();

        if (nameLength <= 1) {
            userNameAfterReplaced = "*";
        } else if (nameLength == 2) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{0})\\d(?=\\d{1})");
        } else if (nameLength >= 3 && nameLength <= 6) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{1})");
        } else if (nameLength == 7) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{2})");
        } else if (nameLength == 8) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{2})");
        } else if (nameLength == 9) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{3})");
        } else if (nameLength == 10) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{3})");
        } else if (nameLength >= 11) {
            userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{4})");
        }
        return userNameAfterReplaced;
    }

    /**
     * 身份证号替换，保留前四位和后四位
     * <p>
     * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param idCard 身份证号
     * @return
     */
    public static String idCardReplaceWithStar(String idCard) {

        if (idCard.isEmpty() || idCard == null) {
            return null;
        } else {
            return replaceAction(idCard, "(?<=\\d{4})\\d(?=\\d{4})");
        }
    }

    /**
     * 银行卡号替换，保留后四位
     * <p>
     * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param bankCard 银行卡号
     * @return
     */
    public static String bankCardReplaceWithStar(String bankCard) {

        if (bankCard.isEmpty() || bankCard == null) {
            return null;
        } else {
            String str = replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= str.length(); i++) {
                sb.append(str.charAt(i - 1));
                if (i % 4 == 0) {
                    sb.append("  ");
                }
            }
            return sb.toString();
        }
    }

    /**
     * 实际替换动作
     *
     * @param number  number
     * @param regular 正则
     * @return
     */
    private static String replaceAction(String number, String regular) {
        return number.replaceAll(regular, "*");
    }

}
