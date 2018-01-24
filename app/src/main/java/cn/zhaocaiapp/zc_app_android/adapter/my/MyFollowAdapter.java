package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;

/**
 * Created by Administrator on 2018/1/11.
 */

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHolder> {
    private Context context;
    private OnItemCliclkListener listene;

    public MyFollowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_follow_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listene.onItemCliclk(holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public interface OnItemCliclkListener{
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener){
        this.listene = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView iv_logo;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
