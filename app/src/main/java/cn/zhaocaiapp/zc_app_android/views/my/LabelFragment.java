package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserLabelResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

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

    private List<UserLabelResp> labels;
    private boolean isShowDel = false;
    private TagAdapter tagAdapter;
    private int deletePosition;

    private static final String TAG = "个人标签";

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_label_fragment, container, false);
    }

    @Override
    public void init() {
//        labels = new ArrayList<>();
//        labels.add(new UserLabelResp(1, "篮球", 30));
//        labels.add(new UserLabelResp(2, "看电影", 20));
//        labels.add(new UserLabelResp(3, "cosplay", 10));
//        labels.add(new UserLabelResp(4, "旅行", 40));
//
//        initLabel();
    }

    @Override
    public void loadData() {
        HttpUtil.get(Constants.URL.GET_USER_LABEL).subscribe(new BaseResponseObserver<List<UserLabelResp>>() {

            @Override
            public void success(List<UserLabelResp> UserLabelResps) {
                EBLog.i(TAG, UserLabelResps.toString());
                labels = UserLabelResps;
                initLabel();
            }

            @Override
            public void error(Response<List<UserLabelResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    //显示标签数据
    private void initLabel() {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        if (labels != null) {
            label_list.setAdapter(tagAdapter = new TagAdapter<UserLabelResp>(labels) {

                @Override
                public View getView(FlowLayout parent, int position, UserLabelResp UserLabelResp) {
                    View view = mInflater.inflate(R.layout.my_label_item, label_list, false);
                    ViewHolder holder = new ViewHolder(view);

                    holder.tv_label_name.setText(UserLabelResp.getName());
                    holder.tv_label_number.setText("(" + UserLabelResp.getTimes() + ")");
                    if (isShowDel) holder.delete_label.setVisibility(View.VISIBLE);
                    else holder.delete_label.setVisibility(View.GONE);

                    setLabelGrade(UserLabelResp.getTimes() / 10, holder);

                    EBLog.i(TAG, UserLabelResp.toString());
                    return view;
                }
            });
            label_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (isShowDel) {
                        deletePosition = position;
                        view.setVisibility(View.GONE);
                        labels.remove(position);
                        tagAdapter.notifyDataChanged();
                    }
                    return false;
                }
            });
        }
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

    @OnClick({R.id.iv_add, R.id.iv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                openActivity(AddLabelActivity.class);
                break;
            case R.id.iv_delete:
                if (isShowDel) isShowDel = false;
                else isShowDel = true;
                tagAdapter.notifyDataChanged();
                break;
        }
    }

    private void deleteLabel(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("ids", id);
        HttpUtil.delete(Constants.URL.DELETE_LABEL, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(getActivity(), commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    public static class ViewHolder {
        @BindView(R.id.tv_label_name)
        TextView tv_label_name;
        @BindView(R.id.tv_label_number)
        TextView tv_label_number;
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
