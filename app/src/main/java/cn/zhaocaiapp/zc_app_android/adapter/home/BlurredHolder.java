package cn.zhaocaiapp.zc_app_android.adapter.home;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.ClickableViewHolder;
import cn.zhaocaiapp.zc_app_android.bean.response.home.BlurredEntity;

/**
 * Created by admin on 2018/8/28.
 */

public class BlurredHolder extends ClickableViewHolder {

    TextView mText;
    RelativeLayout mItemRoot;


    public BlurredHolder(View itemView) {
        super(itemView);
        mText = $(R.id.item_blurred_text);
        mItemRoot = $(R.id.item_root);
    }

    public void createItem(final BlurredEntity entity) {
        if (entity != null) {
            mText.setText(entity.getName());
        }

    }
}
