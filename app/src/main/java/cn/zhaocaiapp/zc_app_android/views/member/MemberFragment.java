package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.member.MemberAdapter;
import cn.zhaocaiapp.zc_app_android.adapter.member.MemberDecoration;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class MemberFragment extends BaseFragment implements OnRefreshListener {

    @BindView(R.id.member_refresh_layout)
    RefreshLayout member_refresh_layout;
    @BindView(R.id.member_recycler_view)
    RecyclerView member_recycler_view;
    @BindView(R.id.member_search_association)
    RecyclerView member_search_association;
    @BindView(R.id.iv_top_edit)
    EditText iv_top_edit;

    private List<MemberResp> memberRespList = new ArrayList<>(); //商家数据
    private MemberAdapter memberAdapter;
    private String name = "";
    private List<SearchAssociation> searchAssociationList = new ArrayList<>();//商家搜索联想

    @Nullable
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_main, container, false);
        return view;
    }

    @Override
    public void init() {
        member_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 5));

        member_recycler_view.addItemDecoration(new MemberDecoration(getActivity(), 10, 10));

        memberAdapter = new MemberAdapter(getActivity(), memberRespList);
        member_recycler_view.setAdapter(memberAdapter);
        memberAdapter.setOnItemCliclkListener(listener);


        member_refresh_layout.setOnRefreshListener(this);
        member_refresh_layout.setEnableLoadmore(false);

        /**
         * 输入框
         */
        iv_top_edit.addTextChangedListener(new TextWatcher() {
            //内容改变前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                EBLog.i("tag", "内容改变前");
            }

            //内容改变
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EBLog.i("tag", "内容改变");
                EBLog.i("tag", s.toString());
            }

            //内容改变后
            @Override
            public void afterTextChanged(Editable s) {
                EBLog.i("tag", "内容改变后");
                if (GeneralUtils.isNotNullOrZeroLenght(s.toString())) {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", s.toString());
                    params.put("pageSize", "6");
                    params.put("currentResult", "0");

                    HttpUtil.get(Constants.URL.GET_MEMBER_ASSOCIATE, params).subscribe(new BaseResponseObserver<List<SearchAssociation>>() {
                        @Override
                        public void success(List<SearchAssociation> result) {
                            searchAssociationList = result;
                            EBLog.i("tag", result.toString());
                        }

                        @Override
                        public void error(Response<List<SearchAssociation>> response) {

                        }

                    });
                }

            }
        });
    }

    @Override
    public void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        HttpUtil.get(Constants.URL.GET_MEMBER_QUERY, params).subscribe(new BaseResponseObserver<List<MemberResp>>() {
            @Override
            public void success(List<MemberResp> result) {
                memberRespList = result;
                memberAdapter.updata(memberRespList);
                EBLog.i("tag", result.toString());
                member_refresh_layout.finishRefresh();
            }

            @Override
            public void error(Response<List<MemberResp>> response) {

            }

        });
    }

    private MemberAdapter.OnItemCliclkListener listener = new MemberAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            Bundle bd = new Bundle();
            bd.putLong("memberId", memberRespList.get(position).getKid());
            openActivity(MemberDetailActivity.class, bd);
            EBLog.i("tag", "您点击了第" + position + "条");
        }
    };

    /**
     * 刷新操作
     *
     * @param refreshlayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        loadData();
    }

    static class SearchAssociation {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
