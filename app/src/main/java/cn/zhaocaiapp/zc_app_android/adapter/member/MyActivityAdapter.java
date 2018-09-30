package cn.zhaocaiapp.zc_app_android.adapter.member;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseRecyclerViewAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.common.InformationDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberDetailActivity;

/**
 * Created by admin on 2018/9/17.
 */

public class MyActivityAdapter extends BaseRecyclerViewAdapter<ActivityResp, MyActivityHolder> {


    public MyActivityAdapter() {
        super(R.layout.item_my_activity);
    }

    private ArrayList<ActivityResp> items = new ArrayList<>();

    @Override
    protected void convert(MyActivityHolder helper, ActivityResp item, int position) {
        helper.createItem(item);
        items.add(item);
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


        //商家图片
        PictureLoadUtil.loadPicture(context, item.getActivityImage1(), helper.mPhotoTitle);
        //活动类型
        helper.mActivityName.setText(getActivityFormString(item.getActivityForm()));
        //活动城市
        if (item.getActivityForm() == 0) {
            helper.mActivitylocation.setText(item.getCityName());
            helper.mActivityAreaIcon.setVisibility(View.VISIBLE);
            helper.mActivitylocation.setVisibility(View.VISIBLE);
        } else {
            helper.mActivityAreaIcon.setVisibility(View.GONE);
            helper.mActivitylocation.setVisibility(View.GONE);
        }
        PictureLoadUtil.loadPicture(context, item.getMemberImg(), helper.mActivityUserPhoto);
        helper.mActivityUserName.setText(item.getMemberName());
        helper.mActivityTitle.setText(item.getName());
        //进度条
        double actualUser = item.getActualUser().intValue();
        double maxUser = item.getMaxUser().intValue();//最大参与人数
        double pra = (actualUser / maxUser) * 100;
        helper.mActivityBar.setProgress((int) pra);
        helper.mActivityBarNum.setText((int) pra + "%");
        helper.mActivityNum.setText(item.getRewardAmount() + "元/人");

        if (item.getActivityStatus() == 0) {
            helper.delivery_root.setVisibility(View.VISIBLE);
        } else {
            helper.delivery_root.setVisibility(View.GONE);
        }


        /**
         * 计算活动剩余天数
         */
        long residueEndTime = item.getEndTime().getTime();
        long residueStart = item.getStartTime().getTime();
        long residueTime = residueEndTime - residueStart;
        long l = residueTime / (24 * 3600000);
        helper.mActivityOverDay.setText(l + "/天");

        /**
         * 计算活动是否结束
         */
        if (l <= 0) {
            helper.mActivityFinish.setVisibility(View.VISIBLE);
        } else {
            helper.mActivityFinish.setVisibility(View.GONE);
        }
    }





    /**
     * 返回活动类型
     *
     * @param t
     * @return
     */
    private String getActivityFormString(Integer t) {
        String type = "";
        switch (t) {
            case 0:
                type = context.getString(R.string.activity_type_0);
                break;
            case 1:
                type = context.getString(R.string.activity_type_1);
                break;
            case 2:
                type = context.getString(R.string.activity_type_2);
                break;
            case 3:
                type = context.getString(R.string.activity_type_3);
                break;
            case 4:
                type = context.getString(R.string.activity_type_4);
                break;
        }
        return type;
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }

}
