package cn.zhaocaiapp.zc_app_android.adapter.common;

import android.content.Context;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.FinishUserResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.common.InformationDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class MemberActivityAdapter extends RecyclerView.Adapter<MemberActivityAdapter.ViewHolder> {
    private List<ActivityResp> list;
    private Context context;
    private OnItemClickListener listener;

    public MemberActivityAdapter(Context context, List<ActivityResp> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //商家logo
        PictureLoadUtil.loadPicture(context, list.get(position).getMemberImg(), holder.activity_item_member_logo);
        //商家名称
        holder.activity_item_member_name.setText(list.get(position).getMemberName());
        //活动区域
        holder.activity_item_member_area.setText(list.get(position).getCityName());
        //活动图片
        PictureLoadUtil.loadPicture(context, list.get(position).getActivityImage1(), holder.activity_item_img_i);
        //活动状态
        holder.activity_item_img_state.setText(getOnlineString(list.get(position).getOnline()));
        //活动类型
        holder.activity_item_img_type.setText(getActivityFormString(list.get(position).getActivityForm()));
        //活动名称
        SpannableStringBuilder spannableString = new SpannableStringBuilder("#" + getActivityFormString(list.get(position).getActivityForm()) + "#" + list.get(position).getName());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.activity_item_text_title.setText(spannableString);
        //剩余额度
        holder.activity_item_text_amount.setText(GeneralUtils.getBigDecimalToTwo(list.get(position).getLeftAmount()));
        //已领取人数
        holder.activity_item_text_number.setText(String.valueOf(list.get(position).getActualUser()));
        //剩余额度进度条
        double leftAmount = list.get(position).getLeftAmount().doubleValue();
        double totalAmount = list.get(position).getTotalAmount().doubleValue();
        double amount = (leftAmount / totalAmount) * 100;
        holder.activity_item_text_amount_progress.setProgress((int) amount);
        //已领取人数进度条
        double actualUser = list.get(position).getActualUser().intValue();
        double getMaxUser = list.get(position).getMaxUser().intValue();
        double pra = (actualUser / getMaxUser) * 100;
        holder.activity_item_text_number_progress.setProgress((int) pra);
        //地址logo 距离
        if (list.get(position).getActivityForm() == 0 && LocationUtil.getGps().getOpen()) {
            //起始位置 我的位置
            DPoint startGps = new DPoint();
            startGps.setLatitude(LocationUtil.getGps().getLatitude());
            startGps.setLongitude(LocationUtil.getGps().getLongitude());
            //结束位置 活动位置
            DPoint stopGps = new DPoint(31.235221, 121.499508);
            if (GeneralUtils.isNotNull(list.get(position).getLatitude()) && GeneralUtils.isNotNull(list.get(position).getLongitude())) {
                stopGps.setLatitude(list.get(position).getLatitude().doubleValue());
                stopGps.setLongitude(list.get(position).getLongitude().doubleValue());
            }
            //两点距离
            float areaText = CoordinateConverter.calculateLineDistance(startGps, stopGps);
            holder.activity_item_text_area_text.setText(areaText > 1000 ? String.format("%.1f", (areaText / 1000)) + "km" : String.format("%.1f", (areaText)) + "m");
        }
        if (list.get(position).getFollow()) {
            holder.activity_item_text_collection.setImageResource(R.mipmap.collection_on);
        } else {
            holder.activity_item_text_collection.setImageResource(R.mipmap.collection_off);
        }
        //奖励金额
        holder.activity_item_text_reward.setText(GeneralUtils.getBigDecimalToTwo(list.get(position).getRewardAmount()));
        //是否显示控件
        isContentVisible(list.get(position).getActivityForm(), holder);
        //参与人头像
        showUserPhoto(list.get(position).getUserList(), holder);

        //活动图片 点击
        holder.activity_item_img_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (list.get(position).getActivityForm() == 3) { //资讯活动
                    intent = new Intent(context, InformationDetailActivity.class);
                } else {
                    intent = new Intent(context, ActivityDetailActivity.class);
                }
                intent.putExtra("id", list.get(position).getKid());
                intent.putExtra("title", list.get(position).getName());
                intent.putExtra("isNeedQRCode", list.get(position).getIfCheck());
                context.startActivity(intent);
            }
        });
        //活动内容点击
        holder.layout_activity_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (list.get(position).getActivityForm() == 3) { //资讯活动
                    intent = new Intent(context, InformationDetailActivity.class);
                } else {
                    intent = new Intent(context, ActivityDetailActivity.class);
                }
                intent.putExtra("id", list.get(position).getKid());
                intent.putExtra("title", list.get(position).getName());
                intent.putExtra("isNeedQRCode", list.get(position).getIfCheck());
                context.startActivity(intent);
            }
        });
        //收藏 点击
        holder.activity_item_text_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)) {
                    if (list.get(position).getFollow()) { //已经收藏
                        //取消收藏
                        doFollow(position, 0, holder);
                    } else {  //未收藏
                        //收藏
                        doFollow(position, 1, holder);
                    }
                } else { //未登录
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        // 分享
        holder.activity_item_text_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //根据活动类型和状态判断控件内容是否显示
    private void isContentVisible(int activityType, ViewHolder holder) {
        switch (activityType) {
            case 0: //线下活动
                holder.activity_item_text_area_logo.setVisibility(View.VISIBLE);
                holder.activity_item_text_area_text.setVisibility(View.VISIBLE);
                holder.activity_item_img_vide.setVisibility(View.GONE);
                holder.tv_member_area_logo.setVisibility(View.VISIBLE);
                holder.activity_item_member_area.setVisibility(View.VISIBLE);
                break;
            case 1: //视频活动
                holder.activity_item_text_area_logo.setVisibility(View.GONE);
                holder.activity_item_text_area_text.setVisibility(View.GONE);
                holder.activity_item_img_vide.setVisibility(View.VISIBLE);
                holder.tv_member_area_logo.setVisibility(View.GONE);
                holder.activity_item_member_area.setVisibility(View.GONE);
                break;
            case 2: //问卷活动
                holder.activity_item_text_area_logo.setVisibility(View.GONE);
                holder.activity_item_text_area_text.setVisibility(View.GONE);
                holder.activity_item_img_vide.setVisibility(View.GONE);
                holder.tv_member_area_logo.setVisibility(View.GONE);
                holder.activity_item_member_area.setVisibility(View.GONE);
                break;
            case 3: //资讯活动
                holder.activity_item_text_area_logo.setVisibility(View.GONE);
                holder.activity_item_text_area_text.setVisibility(View.GONE);
                holder.activity_item_img_vide.setVisibility(View.GONE);
                holder.tv_member_area_logo.setVisibility(View.GONE);
                holder.activity_item_member_area.setVisibility(View.GONE);
                break;
        }
    }

    //显示报名用户的额头像
    private void showUserPhoto(List<FinishUserResp> userList, ViewHolder holder) {
        for (int i = 0; i < userList.size(); i++) {
            ImageView imageView = (ImageView) holder.layout_user.getChildAt(i);
            imageView.setVisibility(View.VISIBLE);
            if (GeneralUtils.isNotNullOrZeroLenght(userList.get(i).getAvatar())) {
                PictureLoadUtil.loadPicture(context, userList.get(i).getAvatar(), imageView);
            }
        }
    }

    //关注/取消关注
    private void doFollow(int position, int state, ViewHolder holder) {
        Map<String, String> params = new HashMap<>();
        params.put("follow", state + "");
        EBLog.i("tag", params.toString());

        HttpUtil.post(String.format(Constants.URL.POST_ACTIVITY_FOLLOW, list.get(position).getKid()), params).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String result) {
                if (state == 1) { //收藏
                    list.get(position).setFollow(true);
                    holder.activity_item_text_collection.setImageResource(R.mipmap.collection_on);
                } else if (state == 0) { //取消收藏
                    list.get(position).setFollow(false);
                    holder.activity_item_text_collection.setImageResource(R.mipmap.collection_off);
                }
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e("tag", response.getCode() + "");
                ToastUtil.makeText(context, response.getDesc());
            }
        });
    }

    public void updata(List<ActivityResp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 返回活动状态
     *
     * @param t
     * @return
     */
    private String getOnlineString(int t) {
        String online;
        switch (t) {
            case 0:
                online = context.getString(R.string.activity_state_0);
                break;
            case 1:
                online = context.getString(R.string.activity_state_1);
                break;
            case 2:
                online = context.getString(R.string.activity_state_2);
                break;
            default:
                online = context.getString(R.string.activity_state_0);
        }
        return online;
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
        }
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //商家logo
        @BindView(R.id.activity_item_member_logo)
        ImageView activity_item_member_logo;
        //商家名称
        @BindView(R.id.activity_item_member_name)
        TextView activity_item_member_name;
        //活动区域
        @BindView(R.id.activity_item_member_area)
        TextView activity_item_member_area;
        //活动图片
        @BindView(R.id.activity_item_img_i)
        SelectableRoundedImageView activity_item_img_i;
        //活动状态
        @BindView(R.id.activity_item_img_state)
        TextView activity_item_img_state;
        //活动类型
        @BindView(R.id.activity_item_img_type)
        TextView activity_item_img_type;
        //活动名称
        @BindView(R.id.activity_item_text_title)
        TextView activity_item_text_title;
        //视频活动播放
        @BindView(R.id.activity_item_img_vide)
        ImageView activity_item_img_vide;
        //剩余额度
        @BindView(R.id.activity_item_text_amount)
        TextView activity_item_text_amount;
        //已领取人数
        @BindView(R.id.activity_item_text_number)
        TextView activity_item_text_number;
        //剩余额度进度条
        @BindView(R.id.activity_item_text_amount_progress)
        ProgressBar activity_item_text_amount_progress;
        //已领取人数进度条
        @BindView(R.id.activity_item_text_number_progress)
        ProgressBar activity_item_text_number_progress;
        //地址logo
        @BindView(R.id.activity_item_text_area_logo)
        ImageView activity_item_text_area_logo;
        //地址距离
        @BindView(R.id.activity_item_text_area_text)
        TextView activity_item_text_area_text;
        //收藏
        @BindView(R.id.activity_item_text_collection)
        ImageView activity_item_text_collection;
        @BindView(R.id.activity_item_text_reward)
        TextView activity_item_text_reward;
        //分享
        @BindView(R.id.activity_item_text_share)
        ImageView activity_item_text_share;
        //活动地区
        @BindView(R.id.tv_member_area_logo)
        TextView tv_member_area_logo;
        //活动内容
        @BindView(R.id.layout_activity_content)
        LinearLayout layout_activity_content;
        //报名的用户头像
        @BindView(R.id.layout_user)
        LinearLayout layout_user;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

}
