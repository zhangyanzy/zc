package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.LabelResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserLabelResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/2/2.
 */

public class AddLabelActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_back;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.label_list)
    TagFlowLayout label_list;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private TagAdapter tagAdapter;
    private List<LabelResp> labels;
    List<UserLabelResp> ids = new ArrayList<>();
    public static final int RESULT_CODE = 2011;

    private static final String TAG = "添加个人标签";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_addlabel_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_title.setText("添加个人标签");

        getLabels();
    }

    private void getLabels() {
        HttpUtil.get(Constants.URL.GET_LABELS).subscribe(new BaseResponseObserver<List<LabelResp>>() {

            @Override
            public void success(List<LabelResp> LabelResps) {
                EBLog.i(TAG, LabelResps.toString());
                labels = LabelResps;
                initLabelTag();
            }

            @Override
            public void error(Response<List<LabelResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(AddLabelActivity.this, response.getDesc());
            }
        });
    }

    private void initLabelTag() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        label_list.setAdapter(tagAdapter = new TagAdapter<LabelResp>(labels) {

            @Override
            public View getView(FlowLayout parent, int position, LabelResp LabelResp) {
                View view = mInflater.inflate(R.layout.my_label_item, label_list, false);
                ViewHolder holder = new ViewHolder(view);

                holder.tv_label_name.setText(LabelResp.getName());
                holder.tv_label_number.setVisibility(View.GONE);
                if (LabelResp.getIsSelected() == 1) { //已添加
                    holder.layout_label.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha));
                    holder.itemView.setEnabled(false);
                }
                return view;
            }
        });

        label_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                LabelResp label = labels.get(position);
                if (label.getIsSelected() == 0) { // 未添加
                    View labelView = view.findViewById(R.id.layout_label);
                    if (label.isChecked()){ // 已选中
                        labelView.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha1));
                        label.setChecked(false);
                        ids.remove(label);
                    }else { // 未选中
                        labelView.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha6));
                        label.setChecked(true);
                        ids.add(new UserLabelResp(label.getKid()));
                    }
                }
                return false;
            }
        });

        label_list.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                EBLog.i(TAG, selectPosSet.toString());
            }
        });
    }

    private void addLabel(List<UserLabelResp> ids) {
        Map<String, Object[]> map = new HashMap<>();
        map.put("tagids", ids.toArray());
        HttpUtil.put(Constants.URL.ADD_LABEL, map).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                ToastUtil.makeText(AddLabelActivity.this, commonResp.getDesc());
                setResult(RESULT_CODE);
                finish();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.i(TAG, response.getCode() + "");
                ToastUtil.makeText(AddLabelActivity.this, response.getDesc());
            }
        });
    }

    @OnClick({R.id.iv_top_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_submit:
                addLabel(ids);
                break;
        }
    }

//    private void getSelectedLabel() {
//        Set<Integer> posSet = label_list.getSelectedList();
//        List<UserLabelResp> ids = new ArrayList<>();
//        for (int i : posSet) {
//            UserLabelResp label = new UserLabelResp(labels.get(i).getKid());
//            ids.add(label);
//        }
//        addLabel(ids.toArray());
//    }

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
