package cn.zhaocaiapp.zc_app_android.adapter.holder.merchant;

import android.view.View;
import android.widget.TextView;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.ClickableViewHolder;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.PayBillEntity;

/**
 * Created by admin on 2018/9/26.
 */

public class PayBillHolder extends ClickableViewHolder {

    TextView mName;
    TextView mTime;
    TextView mAmount;


    public PayBillHolder(View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.item_bill_name);
        mTime = itemView.findViewById(R.id.item_bill_time);
        mAmount = itemView.findViewById(R.id.item_bill_amount);
    }

    public void createItem(PayBillEntity entity) {
        if (entity != null) {
            mTime.setText(entity.getCreateTime()+"");
            mAmount.setText("+" + entity.getAmount());
        }
    }
}
