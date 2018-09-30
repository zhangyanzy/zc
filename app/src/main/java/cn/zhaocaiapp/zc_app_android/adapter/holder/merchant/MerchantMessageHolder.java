package cn.zhaocaiapp.zc_app_android.adapter.holder.merchant;

import android.view.View;
import android.widget.TextView;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.ClickableViewHolder;
import cn.zhaocaiapp.zc_app_android.bean.response.common.MerchantActivityEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.MerchantMessageEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;

/**
 * Created by admin on 2018/9/13.
 */

public class MerchantMessageHolder extends ClickableViewHolder {

    private TextView mMessageName;
    private TextView mMessageContent;
    private TextView mMessageTime;


    public MerchantMessageHolder(View itemView) {
        super(itemView);
        mMessageName = itemView.findViewById(R.id.merchant_message_name);
        mMessageContent = itemView.findViewById(R.id.merchant_message);
        mMessageTime = itemView.findViewById(R.id.merchant_message_time);
    }

    public void createItem(MessageResp entity) {
        if (entity != null) {
            mMessageName.setText(entity.getTitle());
            mMessageTime.setText((CharSequence) entity.getCreateTime());
            mMessageContent.setText(entity.getContent());
        }

    }
}
