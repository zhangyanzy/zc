package cn.zhaocaiapp.zc_app_android.views.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserLabelResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityLabelBinding;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.my.AddLabelActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class LabelActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityLabelBinding binding;

    private List<UserLabelResp> labels;
    private boolean isShowDel = false;
    private TagAdapter tagAdapter;
    private NormalDialog dialog;
    private NavigationTopBar mNavigationTopBar;
    private TagFlowLayout mlabelList;

    private static final int ADD_LABEL_CODE = 2001;

    private static final String TAG = "个人标签";


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_label);
        binding.setPresenter(new Presenter());
        mlabelList = findViewById(R.id.label_list);
        mNavigationTopBar = findViewById(R.id.top_bar);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setCenterTitleText("个人标签");
        mNavigationTopBar.setRightImageResource(R.mipmap.delete_icon);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        getLabel();
    }


    private void getLabel() {
        HttpUtil.get(Constants.URL.GET_USER_LABEL).subscribe(new BaseResponseObserver<List<UserLabelResp>>() {

            @Override
            public void success(List<UserLabelResp> UserLabelResps) {
                labels = UserLabelResps;
                initLabel();
            }

            @Override
            public void error(Response<List<UserLabelResp>> response) {
                ToastUtil.makeText(LabelActivity.this, response.getDesc());
            }
        });
    }


    //显示标签数据
    private void initLabel() {
        LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
        if (labels != null) {
            mlabelList.setAdapter(tagAdapter = new TagAdapter<UserLabelResp>(labels) {
                @Override
                public View getView(FlowLayout parent, int position, UserLabelResp UserLabelResp) {
                    View view = mInflater.inflate(R.layout.my_label_item, mlabelList, false);
                    ViewHolder holder = new ViewHolder(view);
                    holder.tv_label_name.setText(UserLabelResp.getName());
                    holder.mIcon.setVisibility(View.GONE);
                    holder.mLabelNum.setVisibility(View.VISIBLE);
                    holder.mLabelNum.setText("(" + UserLabelResp.getTimes() + ")");
                    if (isShowDel) holder.delete_label.setVisibility(View.VISIBLE);
                    else holder.delete_label.setVisibility(View.GONE);
                    setLabelGrade(UserLabelResp.getTimes() / 10, holder);
                    return view;
                }
            });
            mlabelList.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (isShowDel) {
                        if (labels.get(position).getType() == 0) { //非可选标签
                            String content = getString(R.string.delete_label_confirm);
                            dialog = DialogUtil.showDialogTwoBut(LabelActivity.this, null, content, "取消", "删除");
                            dialog.isTitleShow(false);
                            setDialogListener(view, position);
                        } else deleteLabel(view, position);
                    }
                    return false;
                }
            });
        }
    }

    //设置对话框的监听事件
    private void setDialogListener(View view, int position) {
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                deleteLabel(view, position);
                dialog.dismiss();
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

    private void deleteLabel(View view, int position) {
        String ids = String.valueOf(labels.get(position).getTagId());
        Map<String, String> params = new HashMap<>();
        params.put("ids", ids);
        HttpUtil.delete(Constants.URL.DELETE_LABEL, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(LabelActivity.this, commonResp.getDesc());
                labels.remove(position);
                tagAdapter.notifyDataChanged();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(LabelActivity.this, response.getDesc());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_LABEL_CODE && resultCode == AddLabelActivity.RESULT_CODE) {
            getLabel();
        }
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {
        if (isShowDel) isShowDel = false;
        else isShowDel = true;
        tagAdapter.notifyDataChanged();

    }

    @Override
    public void alignRightLeftImageClick() {

    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_label_icon:
                    openActivityForResult(FirstAddLabelActivity.class, ADD_LABEL_CODE);
                    finish();
                    break;
                default:
                    break;
            }
        }

    }

    public static class ViewHolder {
        @BindView(R.id.tv_label_name)
        TextView tv_label_name;
        @BindView(R.id.delete_label)
        ImageView delete_label;
        @BindView(R.id.layout_label)
        LinearLayout layout_label;
        @BindView(R.id.tv_label_num)
        TextView mLabelNum;
        @BindView(R.id.tv_label_icon)
        TextView mIcon;

        View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }


}
