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
import android.widget.ImageView;
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
import butterknife.OnClick;
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
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

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
    @BindView(R.id.search_clear)
    ImageView search_clear;

    private View rootView;

    private List<MemberResp> memberRespList = new ArrayList<>(); //商家数据
    private MemberAdapter memberAdapter;
    private MemberSearchAdapter memberSearchAdapter;
    private String name = "";
    private List<MemberSearchResp> searchAssociationList = new ArrayList<>();//商家搜索联想
    private boolean isOnChanage = false;//是否是点击联想列表

    private static final String TAG = "商家首页";

    @Nullable
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.member_main, container, false);
        return rootView;
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

        setEditChangeListener();

        //监听点击空白处，隐藏软键盘
        rootView.setOnTouchListener(onTouchListener);
    }

    @Override
    public void loadData() {
        /**
         * 会导致首次进入时重复加载数据
         * */
        member_refresh_layout.autoRefresh();//进入时自动刷新
    }

    /**
     * 解决因懒加载和进入时的自动刷新导致的重复加载
     * <p>
     * 加载网络数据
     */
    private void initNetData() {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        HttpUtil.get(Constants.URL.GET_MEMBER_QUERY, params).subscribe(new BaseResponseObserver<List<MemberResp>>() {
            @Override
            public void success(List<MemberResp> result) {
                EBLog.i(TAG, result.toString());
                if (result.size() == 0) {
                    list_null.setVisibility(View.VISIBLE);
                } else {
                    list_null.setVisibility(View.GONE);
                }
                memberRespList = result;
                memberAdapter.updata(memberRespList);
                if (member_refresh_layout.isRefreshing())
                    member_refresh_layout.finishRefresh();
            }

            @Override
            public void error(Response<List<MemberResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    /**
     * 监听输入框内容变化
     */
    private void setEditChangeListener() {
        iv_top_edit.addTextChangedListener(new TextWatcher() {
            //内容改变前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                EBLog.i(TAG, "内容改变前");
                EBLog.i(TAG, s.toString());
            }

            //内容改变
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EBLog.i(TAG, "内容改变");
                EBLog.i(TAG, s.toString());
            }

            //内容改变后
            @Override
            public void afterTextChanged(Editable s) {
                EBLog.i(TAG, "内容改变后");
                getAssociationData(s.toString());
            }
        });

        iv_top_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    name = String.valueOf(v.getText());
                    isOnChanage = true;
                    member_search_association.setVisibility(View.INVISIBLE);//关闭联系下拉菜单
                    initNetData();
                }
                return false;
            }
        });
    }

    //获取联想搜索的内容
    private void getAssociationData(String s) {
        if (GeneralUtils.isNotNullOrZeroLenght(s)) {
            search_clear.setVisibility(View.VISIBLE);
            Map<String, String> params = new HashMap<>();
            params.put("name", s);
            params.put("pageSize", "6");
            params.put("currentResult", "0");

            HttpUtil.get(Constants.URL.GET_MEMBER_ASSOCIATE, params).subscribe(new BaseResponseObserver<List<MemberSearchResp>>() {
                @Override
                public void success(List<MemberSearchResp> result) {
                    if (result.size() > 0 && !isOnChanage) {
                        member_search_association.setVisibility(View.VISIBLE);
                    }
                    searchAssociationList = result;
                    memberSearchAdapter.updata(searchAssociationList);
                    isOnChanage = false;
                    EBLog.i(TAG, result.toString());
                }

                @Override
                public void error(Response<List<MemberSearchResp>> response) {
                    EBLog.e(TAG, response.getCode() + "");
                    ToastUtil.makeText(getActivity(), response.getDesc());
                }
            });
        } else {
            search_clear.setVisibility(View.INVISIBLE);
            member_search_association.setVisibility(View.INVISIBLE);
        }
    }

    private MemberAdapter.OnItemCliclkListener listener = new MemberAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            Bundle bd = new Bundle();
            bd.putLong("memberId", memberRespList.get(position).getKid());
            openActivity(MemberDetailActivity.class, bd);
            EBLog.i(TAG, "您点击了第" + position + "条");
        }
    };
    private MemberSearchAdapter.OnItemCliclkListener listener2 = new MemberSearchAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            isOnChanage = true;
            member_search_association.setVisibility(View.INVISIBLE);//关闭联系下拉菜单
            iv_top_edit.setText(searchAssociationList.get(position).getName());
            name = String.valueOf(searchAssociationList.get(position).getName());
            initNetData();
            EBLog.i(TAG, "您点击了第" + position + "条");
        }
    };

    /**
     * 刷新操作
     *
     * @param refreshlayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        initNetData();
    }

    @OnClick({R.id.search_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            //清空
            case R.id.search_clear:
                iv_top_edit.setText("");
                name = "";
                break;
        }
    }

}
