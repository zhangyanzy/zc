package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/15.
 */

public class WithdrawFragment extends BaseFragment {
    @BindView(R.id.list)
    RecyclerView list;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list, container, false);
    }

    @Override
    public void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        list.setAdapter(new WithdrawAdapter());

    }

    @Override
    public void loadData() {

    }

    private class WithdrawAdapter extends RecyclerView.Adapter<WithdrawFragment.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.my_income_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_describe)
        TextView tv_describe;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_income)
        TextView tv_income;

        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
