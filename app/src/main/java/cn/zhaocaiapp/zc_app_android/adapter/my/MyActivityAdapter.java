package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.joooonho.SelectableRoundedImageView;

import java.math.BigDecimal;
import java.text.BreakIterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.FinishUserResp;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;

/**
 * Created by Administrator on 2018/2/2.
 */

public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityAdapter.ViewHolder> {
    public static final int MYACTIVITY_ALL = 1001;
    public static final int MYACTIVITY_DELIVER = 1002;
    public static final int MYACTIVITY_VERIFY = 1003;
    public static final int MYACTIVITY_REWARD = 1004;
    public static final int MYACTIVITY_UNPASS = 1005;

    private Context context;
    private List<ActivityResp> items;
    private int type;
    private OnItemClickListener listener;

    public MyActivityAdapter(Context context, List<ActivityResp> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_activitys_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityResp activity = items.get(position);
        //商家logo
        PictureLoadUtil.loadPicture(context, activity.getMemberImg(), holder.iv_logo);
        //活动广告图
        PictureLoadUtil.loadPicture(context, activity.getActivityImage1(), holder.activity_item_img);
        //商家名称
        holder.tv_name.setText(activity.getMemberName());
        //活动进行状态
        holder.item_img_state.setText(getOnlineState(activity.getOnline()));
        //活动类型
        holder.item_img_type.setText(getActivityType(activity.getActivityForm()));
        //活动名称
        SpannableStringBuilder spannableString = new SpannableStringBuilder("#" + getActivityType(activity.getActivityForm()) + "#" + activity.getName());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.item_text_title.setText(spannableString);
        //活动地点距当前距离
        holder.item_text_area_text.setText(getDistance(activity));
        //活动剩余额度
        holder.item_text_amount.setText(GeneralUtils.getBigDecimalToTwo(activity.getLeftAmount()));
        //剩余额度进度条
        int amount = activity.getLeftAmount().divide(activity.getTotalAmount(), BigDecimal.ROUND_UP).intValue();
        holder.item_text_amount_progress.setProgress(amount);
        //已领取人数
        holder.item_text_number.setText(activity.getFinishCount() + "");
        //已领取人数进度条
        int acount = activity.getFinishCount() / activity.getMaxUser();
        holder.item_text_number_progress.setProgress(acount);
        //活动奖励金额
        holder.item_text_reward.setText(GeneralUtils.getBigDecimalToTwo(activity.getRewardAmount()));
        if ((boolean) SpUtils.get(Constants.SPREF.IS_LOGIN, false)) {
            if (activity.getFollow())
                holder.item_text_collection.setImageResource(R.mipmap.collection_on);
            else
                holder.item_text_collection.setImageResource(R.mipmap.collection_off);
        }
        isContentVisible(activity.getActivityForm(), holder);
        showUserPhoto(context, activity.getUserList(), holder);

        holder.activity_item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), v.getId());
            }
        });

        holder.tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), v.getId());
            }
        });

        holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), v.getId());
            }
        });

        holder.tv_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), v.getId());
            }
        });

        holder.activity_item_text_centent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refresh(List<ActivityResp> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private void showUserPhoto(Context context, List<FinishUserResp> userList, ViewHolder holder) {
        for (int i = 0; i < userList.size(); i++) {
            ImageView imageView = (ImageView) holder.layout_user.getChildAt(i);
            imageView.setVisibility(View.VISIBLE);
            if (GeneralUtils.isNotNullOrZeroLenght(userList.get(i).getAvatar())) {
                PictureLoadUtil.loadPicture(context, userList.get(i).getAvatar(), imageView);
            }
        }
    }

    //计算活动地点距当前距离
    private String getDistance(ActivityResp activity) {
        //起始位置 我的位置
        DPoint startGps = new DPoint();
        startGps.setLatitude(LocationUtil.getGps().getLatitude());
        startGps.setLongitude(LocationUtil.getGps().getLongitude());
        //结束位置 活动位置
        DPoint stopGps = new DPoint();
        stopGps.setLatitude(activity.getLatitude().doubleValue());
        stopGps.setLongitude(activity.getLongitude().doubleValue());
        //两点距离
        float areaText = CoordinateConverter.calculateLineDistance(startGps, stopGps);
        return (areaText > 1000 ? String.format("%.1f", (areaText / 1000)) + "km" : String.format("%.1f", (areaText)) + "m");
    }

    //根据活动类型判断控件内容是否显示
    private void isContentVisible(int type, ViewHolder holder) {
        switch (type) {
            case 0: //线下活动
                holder.item_text_area_logo.setVisibility(View.VISIBLE);
                holder.item_text_area_text.setVisibility(View.VISIBLE);
                break;
            case 1: //视频活动，显示播放按钮
                holder.item_img_vide.setVisibility(View.VISIBLE);
                break;
            case 2: //问卷活动

                break;
        }
    }

    //返回活动进行状态
    private String getOnlineState(int i) {
        String state = "";
        switch (i) {
            case 0:
                state = context.getString(R.string.activity_state_0);
                break;
            case 1:
                state = context.getString(R.string.activity_state_1);
                break;
            case 2:
                state = context.getString(R.string.activity_state_2);
                break;
            default:
                state = context.getString(R.string.activity_state_0);
        }
        return state;
    }

    //返回活动类型
    private String getActivityType(int i) {
        String type = "";
        switch (i) {
            case 0:
                type = context.getString(R.string.activity_type_0);
                break;
            case 1:
                type = context.getString(R.string.activity_type_1);
                break;
            case 2:
                type = context.getString(R.string.activity_type_2);
                break;
            default:
                type = context.getString(R.string.activity_type_0);
        }
        return type;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, int viewId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)//商家logo
                CircleImageView iv_logo;
        @BindView(R.id.tv_name)//商家名称
                TextView tv_name;
        @BindView(R.id.tv_state)//用户活动交付状态
                TextView tv_state;
        @BindView(R.id.activity_item_img_i)//活动广告图
                SelectableRoundedImageView activity_item_img;
        @BindView(R.id.activity_item_img_state)//活动进行状态
                TextView item_img_state;
        @BindView(R.id.activity_item_img_type)//活动类型
                TextView item_img_type;
        @BindView(R.id.activity_item_img_vide)//视频活动播放按钮
                ImageView item_img_vide;
        @BindView(R.id.activity_item_text_title)//活动名称
                TextView item_text_title;
        @BindView(R.id.layout_user)//参与用户
                LinearLayout layout_user;
        @BindView(R.id.activity_item_text_user0)//参与用户头像
                CircleImageView item_text_user0;
        @BindView(R.id.activity_item_text_user1)//参与用户头像
                CircleImageView item_text_user1;
        @BindView(R.id.activity_item_text_user2)//参与用户头像
                CircleImageView item_text_user2;
        @BindView(R.id.activity_item_text_area_logo)//活动定位标识
                ImageView item_text_area_logo;
        @BindView(R.id.activity_item_text_area_text)//活动地点距当前距离
                TextView item_text_area_text;
        @BindView(R.id.activity_item_text_collection)//关注
                ImageView item_text_collection;
        @BindView(R.id.activity_item_text_share)//分享
                ImageView item_text_share;
        @BindView(R.id.activity_item_text_amount)//活动剩余额度
                TextView item_text_amount;
        @BindView(R.id.activity_item_text_amount_progress)//剩余额度进度条
                ProgressBar item_text_amount_progress;
        @BindView(R.id.activity_item_text_number)//已领取活动人数
                TextView item_text_number;
        @BindView(R.id.activity_item_text_number_progress)//已领取人数进度条
                ProgressBar item_text_number_progress;
        @BindView(R.id.activity_item_text_reward)//活动奖励金额
                TextView item_text_reward;
        @BindView(R.id.count_down_time)//活动截止倒计时
                CountdownView count_down_time;
        @BindView(R.id.tv_submit)//提交
                TextView tv_submit;
        @BindView(R.id.tv_cancel)//取消
                TextView tv_cancel;
        @BindView(R.id.tv_reward)//领钱
                TextView tv_reward;
        //进度条
        @BindView(R.id.activity_item_text_centent)
        LinearLayout activity_item_text_centent;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
