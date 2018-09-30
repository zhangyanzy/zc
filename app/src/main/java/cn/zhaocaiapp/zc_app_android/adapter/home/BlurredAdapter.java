package cn.zhaocaiapp.zc_app_android.adapter.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.home.BlurredEntity;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.common.InformationDetailActivity;

/**
 * Created by admin on 2018/8/28.
 */

public class BlurredAdapter extends BaseRecyclerViewAdapter<BlurredEntity, BlurredHolder> {

    private static String TAG = "BlurredAdapter";


    public BlurredAdapter() {
        super(R.layout.item_blurred_secrch);
    }

    @Override
    protected void convert(BlurredHolder helper, BlurredEntity item, int position) {
        if (item != null) {
            helper.createItem(item);
            helper.mItemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (list.get(position).getActivityForm() == 3 || list.get(position).getActivityForm() == 4) {
                        intent = new Intent(context, InformationDetailActivity.class);//竞猜  咨询
                    } else {
                        intent = new Intent(context, ActivityDetailActivity.class);//线下 视频
                    }
                    long id = list.get(position).getActivity_id();
                    intent.putExtra("id", id);
                    intent.putExtra("userType", "0");//表示用户端
                    context.startActivity(intent);
                }
            });

        }
        Log.i(TAG, "convert: " + item);

    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
