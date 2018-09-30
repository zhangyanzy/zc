package cn.zhaocaiapp.zc_app_android.adapter.holder.merchant;

import android.view.View;
import android.widget.TextView;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.ClickableViewHolder;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.UserComplete;

/**
 * Created by admin on 2018/9/28.
 */

public class BillHolder extends ClickableViewHolder {

    TextView mName;
    TextView mTime;
    TextView mAmount;

    public BillHolder(View itemView) {
        super(itemView);

        mName = itemView.findViewById(R.id.item_bill_name);
        mTime = itemView.findViewById(R.id.item_bill_time);
        mAmount = itemView.findViewById(R.id.item_bill_amount);
    }

    public void createItem(UserComplete entity) {
        if (entity != null) {
            mName.setText(entity.getName());
            mAmount.setText(entity.getRewardAmount() + "");
            mTime.setText(entity.getCreateTime() + "");
        }
    }
}
