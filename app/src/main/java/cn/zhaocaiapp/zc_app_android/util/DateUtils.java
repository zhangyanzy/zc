package cn.zhaocaiapp.zc_app_android.util;

import android.util.Log;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangyan on 2018/9/17.
 */

public class DateUtils {

//    private static final String TAG = "DateUtils";
//
//    private final static String MIN_DATE_STR = "1901-01-01";
//    private final static String DATE_FORMAT_DATE = "yyyy-MM-dd";
//    private final static String DATE_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
//    private final static String DATE_FORMAT_TIME = "HH:mm:ss";
//    private final static String DATE_FORMAT_STANDARD = "yyyy-MM-dd'T'HH:mm:ss";
//
//    public static Calendar convertToCalendar(Date date) {
//        try {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            return cal;
//        } catch (Exception e) {
//            Log.i(TAG, "convertToCalendar: " + e);
//        }
//        return null;
//    }


//    public static DateTime today() {
//        DateTime now = DateTime.now(DefaultValues.TIME_ZONE);
//
//        return now.withTimeAtStartOfDay();
//    }
    public static void main(String[] args){
        System.out.println(System.currentTimeMillis());
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(new Date().getTime());

    }
}
