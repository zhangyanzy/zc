package cn.zhaocaiapp.zc_app_android.views.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.handler.UMAPIShareHandler;
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
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.LabelResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserLabelResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityFirstAddLabelBinding;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.my.AddLabelActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class FirstAddLabelActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityFirstAddLabelBinding binding;
    private TagAdapter tagAdapter;
    private List<LabelResp> labels;
    List<UserLabelResp> ids = new ArrayList<>();
    private boolean isFirstAdd; //是否首次添加标签

    public static final int RESULT_CODE = 2011;
    private NavigationTopBar mTopBar;
    private TagFlowLayout mlabelList;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_add_label);
        binding.setPresenter(new Presenter());
        mTopBar = findViewById(R.id.add_label_top_bar);
        mlabelList = findViewById(R.id.label_list);
        isFirstAdd = getIntent().getBooleanExtra("isFirstAdd", false);
        if (isFirstAdd) {//首次进入
            mTopBar.setVisibility(View.GONE);
            binding.firstIntoMain.setVisibility(View.VISIBLE);
            binding.firstAddLabelTitle.setVisibility(View.VISIBLE);
        } else {
            mTopBar.setVisibility(View.VISIBLE);
            binding.firstAddLabelTitle.setVisibility(View.GONE);
            binding.firstIntoMain.setVisibility(View.GONE);
        }
    }

    private void getLabels() {
        startProgressDialog();
        HttpUtil.get(Constants.URL.GET_LABELS).subscribe(new BaseResponseObserver<List<LabelResp>>() {
            @Override
            public void success(List<LabelResp> labelResps) {
                stopProgressDialog();
                labels = labelResps;
                initLabelTag();

            }

            @Override
            public void error(Response<List<LabelResp>> response) {

            }
        });
    }

    private void initLabelTag() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        mlabelList.setAdapter(tagAdapter = new TagAdapter<LabelResp>(labels) {
            @Override
            public View getView(FlowLayout parent, int position, LabelResp labelResp) {
                View view = mInflater.inflate(R.layout.my_label_item, mlabelList, false);
                ViewHolder holder = new ViewHolder(view);
                holder.mLabelName.setText(labelResp.getName());
                if (labelResp.getIsSelected() == 1) {
                    holder.mLayoutLabel.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha));
                    holder.itemView.setEnabled(false);
                }
                return view;
            }
        });
        mlabelList.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                LabelResp label = labels.get(position);
                if (label.getIsSelected() == 0) {//未添加
                    View labelView = view.findViewById(R.id.layout_label);
                    if (label.isChecked()) {
                        labelView.setBackground(getResources().getDrawable(R.drawable.button_shape_orange_alpha1));
                        label.setChecked(false);
                        for (int i = 0; i < ids.size(); i++) {
                            if (label.getKid() == ids.get(i).getTagId()) {
                                ids.remove(i);
                            }
                        }
                    } else {//选择中
                        labelView.setBackground(getResources().getDrawable(R.drawable.add_label_bg));
                        label.setChecked(true);
                        UserLabelResp userLabel = new UserLabelResp(label.getKid());
                        ids.add(userLabel);
                    }
                }
                return false;
            }
        });
        mlabelList.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

            }
        });

    }


    private void addLabel(List<UserLabelResp> ids) {
        startProgressDialog();
        Map<String, Object[]> map = new HashMap<>();
        map.put("tagids", ids.toArray());
        HttpUtil.put(Constants.URL.ADD_LABEL, map).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(FirstAddLabelActivity.this, commonResp.getDesc());
                stopProgressDialog();

                if (isFirstAdd) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    openActivity(MainActivity.class, bundle);
                    finish();
                } else {
                    setResult(RESULT_CODE);
                    finish();
                }
            }

            @Override
            public void error(Response<CommonResp> response) {
                ToastUtil.makeText(FirstAddLabelActivity.this, response.getDesc());
                stopProgressDialog();
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mTopBar = findViewById(R.id.add_label_top_bar);
        mTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mTopBar.setCenterTitleText("个人标签");
        mTopBar.setNavigationTopBarClickListener(this);
        mTopBar.setRightImageResource(R.mipmap.change_sure);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        getLabels();
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {
        if (ids.size() > 0) {
            addLabel(ids);
        } else {
            ToastUtil.makeText(FirstAddLabelActivity.this, getString(R.string.add_label));
        }


    }

    @Override
    public void alignRightLeftImageClick() {

    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.first_into_main:
                    if (ids.size() > 0) {
                        addLabel(ids);
                    } else {
                        ToastUtil.makeText(FirstAddLabelActivity.this, getString(R.string.add_label));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static class ViewHolder {
        @BindView(R.id.layout_label)
        LinearLayout mLayoutLabel;
        @BindView(R.id.tv_label_name)
        TextView mLabelName;
        @BindView(R.id.tv_label_icon)
        TextView mlabelicon;
        @BindView(R.id.delete_label)
        ImageView mLabelDelete;

        View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isFinishing())
            finish();
    }
}
