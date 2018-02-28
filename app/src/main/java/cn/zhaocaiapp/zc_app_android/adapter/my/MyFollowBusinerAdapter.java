package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;

/**
 * Created by Administrator on 2018/1/11.
 */

public class MyFollowBusinerAdapter extends RecyclerView.Adapter<MyFollowBusinerAdapter.ViewHolder> {
    private Context context;
    private OnItemCliclkListener listene;
    private List<MemberResp> members;

    public MyFollowBusinerAdapter(Context context, List<MemberResp> members) {
        this.context = context;
        this.members = members;
    }

    public void refresh(List<MemberResp> members) {
        this.members = members;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_follow_businer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_businer_name.setText(members.get(position).getName());
        holder.tv_activity_count.setText("共" + members.get(position).getTotal() + "个活动");
        PictureLoadUtil.loadPicture(context, members.get(position).getLogo(), holder.iv_logo);
        if (members.get(position).getIsFollow() == 1){ // 已关注

        }
        if (members.get(position).getIsFollow() == 0){ // 未关注

        }

        //点击item，跳转商家详情
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listene.onItemCliclk(holder.getLayoutPosition(), holder.itemView);
            }
        });

        //点击已关注按钮，取消关注
        holder.tv_followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listene.onItemCliclk(holder.getLayoutPosition(), holder.tv_followed);
            }
        });

    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public interface OnItemCliclkListener {
        void onItemCliclk(int position, View view);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listene = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView iv_logo;
        @BindView(R.id.tv_businer_name)
        TextView tv_businer_name;
        @BindView(R.id.tv_activity_count)
        TextView tv_activity_count;
        @BindView(R.id.tv_followed)
        TextView tv_followed;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
