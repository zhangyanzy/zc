package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.LabelResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class LabelFragment extends BaseFragment {
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.label_list)
    TagFlowLayout label_list;

    private List<LabelResp> labels;

    private static final String TAG = "个人标签";

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_label_fragment, container, false);
    }

    @Override
    public void init() {
        labels = new ArrayList<>();
    }

    @Override
    public void loadData() {
        HttpUtil.get(Constants.URL.GET_USER_LABEL).subscribe(new BaseResponseObserver<List<LabelResp>>() {

            @Override
            public void success(List<LabelResp> labelResps) {
                labels = labelResps;
            }

            @Override
            public void error(Response<List<LabelResp>> response) {

            }
        });

        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        label_list.setAdapter(new TagAdapter<LabelResp>(labels) {

            @Override
            public View getView(FlowLayout parent, int position, LabelResp labelResp) {
                View view = mInflater.inflate(R.layout.my_label_item, label_list, false);
                ViewHolder holder = new ViewHolder(view);
                holder.tv_label_name.setText(labelResp.getName());
                holder.tv_labek_number.setText(labelResp.getTimes() + "");
                setLabelGrade(labelResp.getTimes() / 10, holder);

                EBLog.i(TAG, labelResp.toString());
                return view;
            }
        });
    }

    private void setLabelGrade(int grade, ViewHolder holder) {
        switch (grade) {
            case 1:
                holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha1));
                break;
            case 2:
                holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha2));
                break;
            case 3:
                holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha3));
                break;
            case 4:
                holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha4));
                break;
            case 5:
                holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha5));
                break;
            case 6:
                holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha6));
                break;
        }
    }

    public static class ViewHolder {
        @BindView(R.id.tv_label_name)
        TextView tv_label_name;
        @BindView(R.id.tv_labek_number)
        TextView tv_labek_number;
        @BindView(R.id.delete_label)
        ImageView delete_label;
        @BindView(R.id.layout_label)
        LinearLayout layout_label;

        View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
