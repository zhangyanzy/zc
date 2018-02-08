package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import cn.zhaocaiapp.zc_app_android.adapter.member.MemberSearchAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberSearchResp;
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
    @BindView(R.id.list_null)
    LinearLayout list_null;

    private List<MemberResp> memberRespList = new ArrayList<>(); //商家数据
    private MemberAdapter memberAdapter;
    private MemberSearchAdapter memberSearchAdapter;
    private String name = "";
    private List<MemberSearchResp> searchAssociationList = new ArrayList<>();//商家搜索联想

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

        member_search_association.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberSearchAdapter = new MemberSearchAdapter(getActivity(), searchAssociationList);
        member_search_association.setAdapter(memberSearchAdapter);
        memberSearchAdapter.setOnItemCliclkListener(listener2);

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

                    HttpUtil.get(Constants.URL.GET_MEMBER_ASSOCIATE, params).subscribe(new BaseResponseObserver<List<MemberSearchResp>>() {
                        @Override
                        public void success(List<MemberSearchResp> result) {
                            if (result.size() > 0) {
                                member_search_association.setVisibility(View.VISIBLE);
                            } else {

                            }
                            searchAssociationList = result;
                            memberSearchAdapter.updata(searchAssociationList);
                            EBLog.i("tag", result.toString());
                        }

                        @Override
                        public void error(Response<List<MemberSearchResp>> response) {

                        }

                    });
                } else {
                    member_search_association.setVisibility(View.INVISIBLE);
                }

            }
        });
        iv_top_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    name = String.valueOf(v.getText());
                    loadData();
                }
                return false;
            }
        });
    }

    @Override
    public void loadData() {
        member_recycler_view.scrollToPosition(0);//回到顶部
        member_refresh_layout.autoRefresh();//自动刷新
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        HttpUtil.get(Constants.URL.GET_MEMBER_QUERY, params).subscribe(new BaseResponseObserver<List<MemberResp>>() {
            @Override
            public void success(List<MemberResp> result) {
                if (result.size() == 0) {
                    list_null.setVisibility(View.VISIBLE);
                } else {
                    list_null.setVisibility(View.GONE);
                }
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
    private MemberSearchAdapter.OnItemCliclkListener listener2 = new MemberSearchAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            iv_top_edit.setText(searchAssociationList.get(position).getName());
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

}
