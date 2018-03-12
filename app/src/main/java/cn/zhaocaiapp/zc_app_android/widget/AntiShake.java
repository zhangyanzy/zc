package cn.zhaocaiapp.zc_app_android.widget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class AntiShake {
    private List<OneClickUtil> utils = new ArrayList<>();

    public boolean check(Object o) {
        String flag = null;
        if (o == null)
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        else
            flag = o.toString();
        if (utils.size() > 0) {
            for (OneClickUtil clickUtil : utils) {
                if (flag.equals(clickUtil.getMethodName())) {
                    return clickUtil.check();
                }
            }
        }
        OneClickUtil clickUtil = new OneClickUtil(flag);
        utils.add(clickUtil);
        return clickUtil.check();
    }

    public boolean check() {
        return check(null);
    }
}
