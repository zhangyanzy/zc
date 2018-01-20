package cn.zhaocaiapp.zc_app_android.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<ActivityResp> list;
    private Context context;
    private OnItemCliclkListener listene;

    public ActivityAdapter(Context context, List<ActivityResp> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int k = position;
        //商家图片
        Glide.with(context)
                .load(list.get(k).getMemberImg())
                .into(holder.activity_item_member_logo);
        //商家名称
        holder.activity_item_member_name.setText(list.get(k).getMemberName());
        //活动区域
        holder.activity_item_member_area.setText(list.get(k).getCityName());
        //活动图片
        Glide.with(context)
                .load(list.get(k).getActivityImage1())
                .into(holder.activity_item_img_i);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listene.onItemCliclk(holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public void updata(List<ActivityResp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemCliclkListener {
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listene = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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


        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }


}
