package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

    private List<SearchRecommendResp> searchRecommendRespList;
    private TagAdapter tagAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        searchRecommendRespList = new ArrayList<>();
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

    public static class ViewHolder {
        @BindView(R.id.search_item_text)
        TextView search_item_text;

        View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
