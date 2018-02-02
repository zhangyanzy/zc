package cn.zhaocaiapp.zc_app_android.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.member.MemberAdapter;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    protected Context context;
    protected List<LocationResp> list;
    protected LayoutInflater mInflater;
    private OnItemCliclkListener listene;

    public LocationAdapter(Context context, List<LocationResp> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    public List<LocationResp> getDatas() {
        return list;
    }

    public void updata(List<LocationResp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.home_location_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final LocationAdapter.ViewHolder holder, final int position) {
        holder.home_location_item.setText(list.get(position).getAreaName());
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

    public interface OnItemCliclkListener {
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listene = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_location_item)
        TextView home_location_item;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
