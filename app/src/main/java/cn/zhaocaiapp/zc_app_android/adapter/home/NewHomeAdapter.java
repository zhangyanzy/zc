package cn.zhaocaiapp.zc_app_android.adapter.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.autonavi.rtbt.IFrameForRTBT;

import java.text.ParseException;
import java.util.ArrayList;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.common.InformationDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberDetailActivity;

/**
 * Created by admin on 2018/8/24.
 * 首页活动view
 */

public class NewHomeAdapter extends BaseRecyclerViewAdapter<ActivityResp, NewHomeHolder> {


    private static String TAG = "NewHomeAdapter";

    public NewHomeAdapter() {
        super(R.layout.item_new_home_activity);
    }

    @Override
    protected void convert(NewHomeHolder helper, ActivityResp item, int position) {
        helper.createItem(item);
        //商家头像
        helper.mActivityUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getMemberId() != null) {
                    Intent intent = new Intent(context, MemberDetailActivity.class);
                    intent.putExtra("memberId", list.get(position).getMemberId());
                    context.startActivity(intent);
                }
            }
        });
        helper.mPhotoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (list.get(position).getActivityForm() == 3 || list.get(position).getActivityForm() == 4) {
                    intent = new Intent(context, InformationDetailActivity.class);//竞猜  咨询
                } else {
                    intent = new Intent(context, ActivityDetailActivity.class);//线下 视频
                }
                intent.putExtra("id", list.get(position).getKid());
                intent.putExtra("title", list.get(position).getName());
                intent.putExtra("isNeedQRCode", list.get(position).getIfCheck());
                intent.putExtra("userType", "0");//表示用户端
                intent.putExtra("memberId", list.get(position).getMemberId());
                intent.putExtra("activityForm", list.get(position).getActivityForm());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
