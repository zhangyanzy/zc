package cn.zhaocaiapp.zc_app_android.adapter.holder.merchant;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.ClickableViewHolder;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.MerchantActivityEntity;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;

/**
 * Created by admin on 2018/9/12.
 */

public class MerchantActivityHolder extends ClickableViewHolder {

    ImageView mPhotoTitle;//封面照片
    TextView mActivityName;//活动名称
    TextView mActivitylocation;//活动城市
    ImageView mActivityUserPhoto;//用户头像
    TextView mActivityUserName;//用户名
    TextView mActivityTitle;//活动标题
    ImageView mActivityFinish;//活动结束图标
    ProgressBar mActivityBar;//活动进度条
    TextView mActivityBarNum;//活动进度数
    TextView mActivityNum;//奖励金额
    TextView mActivityOverDay;//剩余天数
    ImageView mActivityAreaIcon;


    public MerchantActivityHolder(View itemView) {
        super(itemView);
        mPhotoTitle = $(R.id.item_activity_photo);
        mActivityName = $(R.id.item_activity_name);
        mActivitylocation = $(R.id.item_activity_location_text);
        mActivityUserPhoto = $(R.id.item_activity_user_photo);
        mActivityUserName = $(R.id.item_activity_user_name);
        mActivityTitle = $(R.id.item_activity_title);
        mActivityFinish = $(R.id.item_activity_is_finish);
        mActivityBar = $(R.id.item_activity_text_amount_progress);
        mActivityBarNum = $(R.id.item_activity_text_amount_progress_num);
        mActivityNum = $(R.id.item_activity_reward_num);
        mActivityOverDay = $(R.id.item_activity_over_day);
        mActivityAreaIcon = $(R.id.activity_area_area);
    }


    public void createItem(ActivityResp entity) {
        if (entity != null) {
            //商家图片
            PictureLoadUtil.loadPicture(context, entity.getActivityImage1(), mPhotoTitle);
            //活动类型
            mActivityName.setText(getActivityFormString(entity.getActivityForm()));
            //活动城市
            if (entity.getActivityForm() == 0) {
                mActivitylocation.setText(entity.getCityName());
                mActivityAreaIcon.setVisibility(View.VISIBLE);
                mActivitylocation.setVisibility(View.VISIBLE);
            } else {
                mActivityAreaIcon.setVisibility(View.GONE);
                mActivitylocation.setVisibility(View.GONE);
            }
            PictureLoadUtil.loadPicture(context, entity.getMemberImg(), mActivityUserPhoto);
            mActivityUserName.setText(entity.getMemberName());
            mActivityTitle.setText(entity.getName());
            //进度条
            double actualUser = entity.getActualUser().intValue();
            double maxUser = entity.getMaxUser().intValue();//最大参与人数
            double pra = (actualUser / maxUser) * 100;
            mActivityBar.setProgress((int) pra);
            mActivityBarNum.setText((int) pra + "%");

            int i = entity.getRewardAmount().intValue();
            mActivityNum.setText(i + "元/人");

            //任务是否完成
            DateTime end = new DateTime(entity.getEndTime());
            DateTime now = new DateTime(entity.getNowDate());
            int lastDate = Days.daysBetween(end, now).getDays();

            /**
             * 计算活动剩余天数
             */
            long residueEndTime = entity.getEndTime().getTime();
            long residueStart = entity.getStartTime().getTime();
            long residueTime = residueEndTime - residueStart;
            long l = residueTime / (24 * 3600000);
            mActivityOverDay.setText(l + "天");


            /**
             * 计算活动是否结束
             */
//            if (l <= 0) {
//                mActivityFinish.setVisibility(View.VISIBLE);
//            } else {
//                mActivityFinish.setVisibility(View.GONE);
//            }

            long nowDateStr = Long.parseLong(entity.getNowDateStr());

            if (nowDateStr > entity.getEndTime().getTime()) {
                mActivityFinish.setVisibility(View.VISIBLE);
            } else {
                mActivityFinish.setVisibility(View.GONE);
            }
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

}
