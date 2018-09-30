package cn.zhaocaiapp.zc_app_android.adapter.holder.merchant;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.UserComplete;

/**
 * Created by admin on 2018/9/28.
 */

public class BillAdapter extends BaseRecyclerViewAdapter<UserComplete, BillHolder> {


    public BillAdapter() {
        super(R.layout.item_pay_bill);
    }

    @Override
    protected void convert(BillHolder helper, UserComplete item, int position) {
        if (item != null) {
            helper.createItem(item);
        }
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
