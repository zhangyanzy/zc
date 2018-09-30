package cn.zhaocaiapp.zc_app_android.adapter.merchant;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.holder.merchant.MerchantMessageHolder;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.MerchantMessageEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;

/**
 * Created by admin on 2018/9/13.
 */

public class MerchantMessageAdapter extends BaseRecyclerViewAdapter<MessageResp, MerchantMessageHolder> {


    public MerchantMessageAdapter() {
        super(R.layout.item_merchant_message);
    }

    @Override
    protected void convert(MerchantMessageHolder helper, MessageResp item, int position) {
        if (item != null) {
            helper.createItem(item);
        }
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
