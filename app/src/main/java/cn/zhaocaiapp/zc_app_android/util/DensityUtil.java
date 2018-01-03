package cn.zhaocaiapp.zc_app_android.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 像素转换
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class DensityUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue   DisplayMetrics类中属性density
     * @return
     */
    public static int px2dip(Context context,final float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context
     * @param dipValue  DisplayMetrics类中属性density
     * @return
     */
    public static int dip2px(Context context,final float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue   DisplayMetrics类中属性scaledDensity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int px2sp(Context context,final float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     *
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue   DisplayMetrics类中属性scaledDensity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int sp2px(Context context,final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕像素x
     *
     * @param activity
     * @return
     */
    public static int getXScreenpx(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕像素y
     * @param activity
     * @return
     */
    public static int getYScreenpx(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
