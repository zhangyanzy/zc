package cn.zhaocaiapp.zc_app_android.adapter.common;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.joooonho.SelectableRoundedImageView;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.Gps;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberDetailActivity;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<ActivityResp> list;
    private Context context;
    private OnItemCliclkListener listene;
    private int type; //1首页默认列表 2商家详情列表
    private MemberResp memberResp; //商家详情
    private int k;

    public ActivityAdapter(Context context, List<ActivityResp> list) {
        this.list = list;
        this.context = context;
        this.type = 1;
    }

    public ActivityAdapter(Context context, List<ActivityResp> list, MemberResp memberResp) {
        this.list = list;
        this.context = context;
        this.type = 2;
        this.memberResp = memberResp;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EBLog.i("tag", String.valueOf(viewType));
        View view;
        ViewHolder viewHolder;
        if (type == 2 && viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.member_detail_header, parent, false);
            viewHolder = new ViewHolderMember(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
            viewHolder = new ViewHolderActivity(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        k = position;
        //商家详情
        if (type == 2 && position == 0) {
            ViewHolderMember viewHolderMember = (ViewHolderMember) holder;
            //商家头像
            PictureLoadUtil.loadPicture(context, memberResp.getLogo(), viewHolderMember.member_detail_logo);
            //商家名称
            viewHolderMember.member_detail_name.setText(memberResp.getName());
            //商家电话
            viewHolderMember.member_detail_phone.setText(memberResp.getPhone());
            //商家地址
            viewHolderMember.member_detail_area.setText(memberResp.getProvinceName() + memberResp.getCityName() + memberResp.getAreaName() + memberResp.getAddressDetail());
            //商家活动 全部
            viewHolderMember.member_detail_state.setText(String.valueOf(memberResp.getTotal()));
            //商家活动 未开始
            viewHolderMember.member_detail_state_0.setText(String.valueOf(memberResp.getBeforeLine()));
            //商家活动 进行中
            viewHolderMember.member_detail_state_1.setText(String.valueOf(memberResp.getOnLine()));
            //商家活动 已结束
            viewHolderMember.member_detail_state_2.setText(String.valueOf(memberResp.getOffLine()));
        }
        //活动列表
        else {

            //商家第一条空出
            if (type == 2) {
                k = k - 1;
            }
            ViewHolderActivity viewHolderActivity = (ViewHolderActivity) holder;
            //商家图片
            PictureLoadUtil.loadPicture(context, list.get(k).getMemberImg(), viewHolderActivity.activity_item_member_logo);
            //商家名称
            viewHolderActivity.activity_item_member_name.setText(list.get(k).getMemberName());
            //活动区域
            viewHolderActivity.activity_item_member_area.setText(list.get(k).getCityName());
            //活动图片
            PictureLoadUtil.loadPicture(context, list.get(k).getActivityImage1(), viewHolderActivity.activity_item_img_i);
            //活动状态
            viewHolderActivity.activity_item_img_state.setText(getOnlineString(list.get(k).getOnline()));
            //活动类型
            viewHolderActivity.activity_item_img_type.setText(getActivityFormString(list.get(k).getActivityForm()));
            //活动名称
            SpannableStringBuilder spannableString = new SpannableStringBuilder("#" + getActivityFormString(list.get(k).getActivityForm()) + "#" + list.get(k).getName());
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolderActivity.activity_item_text_title.setText(spannableString);
            //视频活动播放
            if (list.get(k).getActivityForm() == 1) {
                viewHolderActivity.activity_item_img_vide.setVisibility(View.VISIBLE);
            }
            //参与人头像
            if (GeneralUtils.isNotNull(list.get(k).getUserList()) && list.get(k).getUserList().size() > 0) {
                if (list.get(k).getUserList().size() >= 1) {
                    viewHolderActivity.activity_item_text_user0.setVisibility(View.VISIBLE);
                    PictureLoadUtil.loadPicture(context, list.get(k).getUserList().get(0).getAvatar(), viewHolderActivity.activity_item_text_user0);
                }
                if (list.get(k).getUserList().size() >= 2) {
                    viewHolderActivity.activity_item_text_user1.setVisibility(View.VISIBLE);
                    PictureLoadUtil.loadPicture(context, list.get(k).getUserList().get(1).getAvatar(), viewHolderActivity.activity_item_text_user1);
                }
                if (list.get(k).getUserList().size() == 3) {
                    viewHolderActivity.activity_item_text_user2.setVisibility(View.VISIBLE);
                    PictureLoadUtil.loadPicture(context, list.get(k).getUserList().get(2).getAvatar(), viewHolderActivity.activity_item_text_user2);
                }
            }
            //剩余额度
            viewHolderActivity.activity_item_text_amount.setText(GeneralUtils.getBigDecimalToTwo(list.get(k).getLeftAmount()));
            //已领取人数
            viewHolderActivity.activity_item_text_number.setText(String.valueOf(list.get(k).getActualUser()));
            //剩余额度进度条
            double amount = list.get(k).getLeftAmount().divide(list.get(k).getTotalAmount(), BigDecimal.ROUND_UP).doubleValue();
            viewHolderActivity.activity_item_text_amount_progress.setProgress((int) amount);
            //已领取人数进度条
            viewHolderActivity.activity_item_text_number_progress.setProgress(list.get(k).getActualUser() / list.get(k).getMaxUser());
            //地址logo 距离
            if (list.get(k).getActivityForm() == 0 && LocationUtil.getGps().getOpen()) {
                viewHolderActivity.activity_item_text_area_logo.setVisibility(View.VISIBLE);
                viewHolderActivity.activity_item_text_area_text.setVisibility(View.VISIBLE);
                //起始位置 我的位置
                DPoint startGps = new DPoint();
                startGps.setLatitude(LocationUtil.getGps().getLatitude());
                startGps.setLongitude(LocationUtil.getGps().getLongitude());
                //结束位置 活动位置
                DPoint stopGps = new DPoint();
                stopGps.setLatitude(list.get(k).getLatitude().doubleValue());
                stopGps.setLongitude(list.get(k).getLongitude().doubleValue());
                //两点距离
                int areaText = (int) CoordinateConverter.calculateLineDistance(startGps, stopGps);
                viewHolderActivity.activity_item_text_area_text.setText(areaText > 1000 ? (areaText / 1000) + "KM" : areaText + "M");
            }
            //收藏
            if (GeneralUtils.isNotNull((String) SpUtils.get(Constants.SPREF.TOKEN, "")) && list.get(k).getFollow()) {
                viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_on);
            }
            //奖励金额
            viewHolderActivity.activity_item_text_reward.setText(GeneralUtils.getBigDecimalToTwo(list.get(k).getRewardAmount()));


            //商家图片 点击
            viewHolderActivity.activity_item_member_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MemberDetailActivity.class);
                    intent.putExtra("memberId", list.get(k).getMemberId());
                    context.startActivity(intent);
                }
            });
            //商家名称 点击
            viewHolderActivity.activity_item_member_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MemberDetailActivity.class);
                    intent.putExtra("memberId", list.get(k).getMemberId());
                    context.startActivity(intent);
                }
            });
            //活动图片 点击
            viewHolderActivity.activity_item_img_i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityDetailActivity.class);
                    intent.putExtra("id", list.get(k).getKid());
                    context.startActivity(intent);
                }
            });
            //活动名称 点击
            viewHolderActivity.activity_item_text_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityDetailActivity.class);
                    intent.putExtra("id", list.get(k).getKid());
                    context.startActivity(intent);
                }
            });
            //收藏 点击
            viewHolderActivity.activity_item_text_collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //未登录
                    if (GeneralUtils.isNotNull((String) SpUtils.get(Constants.SPREF.TOKEN, ""))) {
                        //已经收藏
                        if (list.get(k).getFollow()) {
                            //获取商家活动列表
                            Map<String, String> params = new HashMap<>();
                            params.put("follow", "0");
                            EBLog.i("tag", params.toString());
                            EBLog.i("tag", list.get(k).getKid().toString());

                            HttpUtil.post(String.format(Constants.URL.POST_ACTIVITY_FOLLOW, list.get(k).getKid()), params).subscribe(new BaseResponseObserver<String>() {
                                @Override
                                public void success(String result) {
                                    list.get(k).setFollow(false);
                                    viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_off);
                                    EBLog.i("tag", result.toString());
                                }

                                @Override
                                public void error(Response<String> response) {

                                }

                            });
                        }
                        //未收藏
                        else {
                            //获取商家活动列表
                            Map<String, String> params = new HashMap<>();
                            params.put("follow", "1");
                            EBLog.i("tag", params.toString());
                            EBLog.i("tag", list.get(k).getKid().toString());

                            HttpUtil.post(String.format(Constants.URL.POST_ACTIVITY_FOLLOW, list.get(k).getKid()), params).subscribe(new BaseResponseObserver<String>() {
                                @Override
                                public void success(String result) {
                                    list.get(k).setFollow(true);
                                    viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_on);
                                    EBLog.i("tag", result.toString());
                                }

                                @Override
                                public void error(Response<String> response) {

                                }

                            });
                        }
                    }
                    //登录
                    else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public void updata(List<ActivityResp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void updataMember(MemberResp memberResp) {
        this.memberResp = memberResp;
        notifyDataSetChanged();
    }

    public interface OnItemCliclkListener {
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listene = listener;
    }

    /**
     * 返回活动状态
     *
     * @param t
     * @return
     */
    private String getOnlineString(Integer t) {
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
        String type;
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
            default:
                type = context.getString(R.string.activity_type_0);
        }
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public class ViewHolderMember extends ViewHolder {

        //商家头像
        @BindView(R.id.member_detail_logo)
        SelectableRoundedImageView member_detail_logo;
        //商家名称
        @BindView(R.id.member_detail_name)
        TextView member_detail_name;
        //商家电话
        @BindView(R.id.member_detail_phone)
        TextView member_detail_phone;
        //商家地址
        @BindView(R.id.member_detail_area)
        TextView member_detail_area;
        //商家活动 全部
        @BindView(R.id.member_detail_state)
        TextView member_detail_state;
        //商家活动 未开始
        @BindView(R.id.member_detail_state_0)
        TextView member_detail_state_0;
        //商家活动 进行中
        @BindView(R.id.member_detail_state_1)
        TextView member_detail_state_1;
        //商家活动 已结束
        @BindView(R.id.member_detail_state_2)
        TextView member_detail_state_2;


        View itemView;

        public ViewHolderMember(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public class ViewHolderActivity extends ViewHolder {

        //商家图片
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
        //参与人0
        @BindView(R.id.activity_item_text_user0)
        ImageView activity_item_text_user0;
        //参与人1
        @BindView(R.id.activity_item_text_user1)
        ImageView activity_item_text_user1;
        //参与人2
        @BindView(R.id.activity_item_text_user2)
        ImageView activity_item_text_user2;
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


        View itemView;

        public ViewHolderActivity(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }


}
