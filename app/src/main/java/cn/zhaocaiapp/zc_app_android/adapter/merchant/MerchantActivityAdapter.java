package cn.zhaocaiapp.zc_app_android.adapter.merchant;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.holder.merchant.MerchantActivityHolder;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.MerchantActivityEntity;

/**
 * Created by admin on 2018/9/12.
 */

public class MerchantActivityAdapter extends BaseRecyclerViewAdapter<ActivityResp, MerchantActivityHolder> {


    public MerchantActivityAdapter() {
        super(R.layout.item_merchant_activity);
    }

    @Override
    protected void convert(MerchantActivityHolder helper, ActivityResp item, int position) {
        helper.createItem(item);
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
