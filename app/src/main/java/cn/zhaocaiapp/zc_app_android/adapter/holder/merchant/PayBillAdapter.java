package cn.zhaocaiapp.zc_app_android.adapter.holder.merchant;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.PayBillEntity;

/**
 * Created by admin on 2018/9/26.
 */

public class PayBillAdapter extends BaseRecyclerViewAdapter<PayBillEntity,PayBillHolder> {


    public PayBillAdapter() {
        super(R.layout.item_pay_bill);
    }

    @Override
    protected void convert(PayBillHolder helper, PayBillEntity item, int position) {
        helper.createItem(item);
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
