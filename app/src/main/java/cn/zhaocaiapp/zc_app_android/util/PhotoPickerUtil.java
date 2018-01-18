package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnOperItemClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.ActionSheetDialog;


/**
 * Created by Administrator on 2017/7/24.
 */

public class PhotoPickerUtil {
    private static ActionSheetDialog sheetDialog;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void setContent(String title, String[] contents, View animateView) {
        sheetDialog = new ActionSheetDialog(mContext, contents, animateView);
        if (title != null && !title.equals("")) {
            sheetDialog.title(title).titleTextSize_SP(14.5f);
        }
        sheetDialog.setCanceledOnTouchOutside(true);
        //设置弹窗内容的显示动画,默认有动画
//        sheetDialog.layoutAnimation(null);
    }

    public static void show(OnItemClickListener listener) {
        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    listener.onItemClick(position);
                    sheetDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sheetDialog.show();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
