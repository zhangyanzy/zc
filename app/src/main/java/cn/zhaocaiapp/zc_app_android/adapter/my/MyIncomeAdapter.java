package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jph.takephoto.permission.PermissionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.my.IncomeResp;

/**
 * Created by Administrator on 2018/3/6.
 */

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.ViewHolder> {
    private Context context;
    private List<IncomeResp> items;
    private int type;
    private int viewType;


    public MyIncomeAdapter(Context context, List<IncomeResp> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    public void refresh(List<IncomeResp> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.size() <= 0) return -1;
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        if (viewType == -1) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_no_data, parent, false);
            holder = new EmptyViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.my_income_item, parent, false);
            holder = new IncomeViewHolder(view);
        }
        this.viewType = viewType;
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        if (viewType != -1 && viewholder instanceof IncomeViewHolder) {
            IncomeViewHolder holder = (IncomeViewHolder) viewholder;

            holder.tv_describe.setText(items.get(position).getBusinessName());
            holder.tv_time.setText(items.get(position).getTimeStr());

            if (type == 1) {
                if (items.get(position).getCashStatus() == 0) {//已提交
                    holder.tv_state.setText("已提交");
                } else if (items.get(position).getCashStatus() == 1) {//提现中
                    holder.tv_state.setText("提现中");
                } else if (items.get(position).getCashStatus() == 2) {//提现成功
                    holder.tv_state.setText("提现成功");
                } else if (items.get(position).getCashStatus() == 3) {//提现失败
                    holder.tv_state.setText("提现失败");
                }
            }
            if (items.get(position).getBillType() == 0) {//收入
                holder.tv_income.setText("+" + items.get(position).getAmount());
                holder.tv_income.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
            if (items.get(position).getBillType() == 1) {//提现
                holder.tv_income.setText("-" + items.get(position).getAmount());
                holder.tv_income.setTextColor(context.getResources().getColor(R.color.colorSuccess));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size() > 0 ? items.size() : 1;
    }

    public class IncomeViewHolder extends ViewHolder {
        @BindView(R.id.tv_describe)
        TextView tv_describe;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_income)
        TextView tv_income;
        @BindView(R.id.tv_state)
        TextView tv_state;

        public IncomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class EmptyViewHolder extends ViewHolder {
        View itemView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

}
