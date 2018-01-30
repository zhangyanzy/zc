package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.SearchRecommendResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * @author 林子
 * @filename SearchActivity.java
 * @data 2018-01-24 10:15
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_recommend_list)
    TagFlowLayout search_recommend_list;
    @BindView(R.id.search_cancel)
    TextView search_cancel;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_clear)
    ImageView search_clear;

    private List<SearchRecommendResp> searchRecommendRespList;
    private TagAdapter tagAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        searchRecommendRespList = new ArrayList<>();
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        /**
         * 获取推荐活动
         */
        HttpUtil.get(Constants.URL.GET_SEARCH_RECOMMEND).subscribe(new BaseResponseObserver<List<SearchRecommendResp>>() {
            @Override
            public void success(List<SearchRecommendResp> result) {
                searchRecommendRespList = result;
                final LayoutInflater mInflater = LayoutInflater.from(SearchActivity.this);
                tagAdapter = new TagAdapter<SearchRecommendResp>(searchRecommendRespList) {

                    @Override
                    public View getView(FlowLayout parent, int position, SearchRecommendResp searchRecommendResp) {
                        View view = mInflater.inflate(R.layout.search_item, search_recommend_list, false);
                        ViewHolder holder = new ViewHolder(view);
                        holder.search_item_text.setText(searchRecommendResp.getName());
                        EBLog.i("tag", searchRecommendResp.toString());
                        return view;
                    }
                };
                search_recommend_list.setAdapter(tagAdapter);
                search_recommend_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        EBLog.i("tag", searchRecommendRespList.get(position).toString());
                        return true;
                    }
                });
                EBLog.i("tag", result.toString());
            }

            @Override
            public void error(Response<List<SearchRecommendResp>> response) {

            }

        });
    }

    private void initView() {
        //输入框改变
        /*search_edit.addTextChangedListener(new TextWatcher() {
            //内容改变前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //内容改变
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //内容改变后
            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        //输入框 输入法确认
        search_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    Bundle bd = new Bundle();
                    bd.putString("name", search_edit.getText().toString());
                    openActivity(SearchResulfActivity.class, bd);
                    return true;
                }
                return false;
            }
        });


    }


    public static class ViewHolder {
        @BindView(R.id.search_item_text)
        TextView search_item_text;

        View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    @OnClick({
            R.id.search_cancel,
            R.id.search_clear
    })
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.search_cancel:
                finish();
                break;
            //清空
            case R.id.search_clear:
                search_edit.setText("");
                break;
        }
    }

}
