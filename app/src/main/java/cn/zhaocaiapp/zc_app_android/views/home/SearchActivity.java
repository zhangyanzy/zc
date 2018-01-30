package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;

/**
 * @author 林子
 * @filename SearchActivity.java
 * @data 2018-01-24 10:15
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_recommend_list)
    TagFlowLayout search_recommend_list;
    @BindView(R.id.search_history_list)
    TagFlowLayout search_history_list;
    @BindView(R.id.search_cancel)
    TextView search_cancel;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.activity_class_0)
    TextView activity_class_0;
    @BindView(R.id.activity_class_1)
    TextView activity_class_1;
    @BindView(R.id.activity_class_2)
    TextView activity_class_2;
    @BindView(R.id.activity_type_0)
    TextView activity_type_0;
    @BindView(R.id.activity_type_1)
    TextView activity_type_1;
    @BindView(R.id.activity_type_2)
    TextView activity_type_2;
    @BindView(R.id.search_money_0)
    TextView search_money_0;
    @BindView(R.id.search_money_1)
    TextView search_money_1;
    @BindView(R.id.search_money_2)
    TextView search_money_2;
    @BindView(R.id.search_money_3)
    TextView search_money_3;
    @BindView(R.id.search_money_4)
    TextView search_money_4;


    private List<SearchRecommendResp> searchRecommendRespList; //推荐活动
    private TagAdapter tagAdapter;
    private TagAdapter hisTagAdapter;
    private List<String> historyList = new ArrayList<String>();//搜索历史
    private String activityType = "";//活动分类 0单体活动 1串联活动 2协同活动
    private String activityForm = "";//活动类型 0线下活动 1视频活动 2问卷活动
    private String activityMoney = "";//金额
    private String topLimit = "";//金额上限
    private String limit = "";//金额下限


    @Override
    public int getContentViewResId() {
        return R.layout.search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        searchRecommendRespList = new ArrayList<>();
        initView();
        getHistory();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        EBLog.i("tag", "活动");
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

        /**
         * 获取搜索历史
         */
        final LayoutInflater mInflater1 = LayoutInflater.from(SearchActivity.this);
        hisTagAdapter = new TagAdapter<String>(historyList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = mInflater1.inflate(R.layout.search_item, search_history_list, false);
                ViewHolder holder = new ViewHolder(view);
                holder.search_item_text.setText(s);
                EBLog.i("tag", s);
                return view;
            }

        };
        search_history_list.setAdapter(hisTagAdapter);
        search_history_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                EBLog.i("tag", historyList.get(position));
                return true;
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
        /*search_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    saveHistory(search_edit.getText().toString());
                    Bundle bd = new Bundle();
                    bd.putString("name", search_edit.getText().toString());
                    openActivity(SearchResulfActivity.class, bd);
                    return true;
                }
                return false;
            }
        });*/
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    saveHistory(search_edit.getText().toString());
                    Bundle bd = new Bundle();
                    bd.putString("name", search_edit.getText().toString());
                    openActivity(SearchResulfActivity.class, bd);

                }
                return false;
            }
        });

    }

    /**
     * 保存历史记录
     */
    private void saveHistory(String content) {
        if (GeneralUtils.isNullOrZeroLenght(content)) {
            return;
        }
        List<String> stringList = new ArrayList(GeneralUtils.stringToList((String) SpUtils.get(Constants.SPREF.SEARCH_HISTORY, "")));
        //排除重复
        for (int i = 0; i < stringList.size(); i++) {
            if (content.equals(stringList.get(i))) {
                stringList.remove(i);
            }
        }
        //添加
        stringList.add(content);
        SpUtils.put(Constants.SPREF.SEARCH_HISTORY, GeneralUtils.listToString(stringList));

    }

    /**
     * 获取历史记录
     */
    private void getHistory() {
        List<String> stringList = new ArrayList(GeneralUtils.stringToList((String) SpUtils.get(Constants.SPREF.SEARCH_HISTORY, "")));
        List<String> stringList1 = new ArrayList<>();
        for (int i = stringList.size() - 1; i >= 0 && stringList1.size() < 10; i--) {
            if (GeneralUtils.isNullOrZeroLenght(stringList.get(i))) {
                continue;
            }
            stringList1.add(stringList.get(i));
        }
        historyList = stringList1;
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
            R.id.search_clear,
            R.id.activity_class_0,
            R.id.activity_class_1,
            R.id.activity_class_2,
            R.id.activity_type_0,
            R.id.activity_type_1,
            R.id.activity_type_2,
            R.id.search_money_0,
            R.id.search_money_1,
            R.id.search_money_2,
            R.id.search_money_3,
            R.id.search_money_4

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
            //单体活动
            case R.id.activity_class_0:
                onClass(0);
                break;
            //串联活动
            case R.id.activity_class_1:
                onClass(1);
                break;
            //协同活动
            case R.id.activity_class_2:
                onClass(2);
                break;
            //线下活动
            case R.id.activity_type_0:
                onType(0);
                break;
            //视频活动
            case R.id.activity_type_1:
                onType(1);
                break;
            //问卷活动
            case R.id.activity_type_2:
                onType(2);
                break;
            //0-5元
            case R.id.search_money_0:
                onMoney(0);
                break;
            //5-10元
            case R.id.search_money_1:
                onMoney(1);
                break;
            //10-15元
            case R.id.search_money_2:
                onMoney(2);
                break;
            //15-20元
            case R.id.search_money_3:
                onMoney(3);
                break;
            //20元
            case R.id.search_money_4:
                onMoney(4);
                break;

        }
    }

    /**
     * 控制活动分类
     */
    private void onClass(int k) {
        switch (k) {
            case 0:
                activityType = activityType.equals("0") ? "" : "0";
                break;
            case 1:
                activityType = activityType.equals("1") ? "" : "1";
                break;
            case 2:
                activityType = activityType.equals("2") ? "" : "2";
                break;
        }
        if (activityType.equals("0")) {
            activity_class_0.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            activity_class_0.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            activity_class_0.setTextColor(this.getResources().getColor(R.color.colorFont6));
            activity_class_0.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityType.equals("1")) {
            activity_class_1.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            activity_class_1.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            activity_class_1.setTextColor(this.getResources().getColor(R.color.colorFont6));
            activity_class_1.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityType.equals("2")) {
            activity_class_2.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            activity_class_2.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            activity_class_2.setTextColor(this.getResources().getColor(R.color.colorFont6));
            activity_class_2.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
    }

    /**
     * 控制活动类型
     */
    private void onType(int k) {
        switch (k) {
            case 0:
                activityForm = activityForm.equals("0") ? "" : "0";
                break;
            case 1:
                activityForm = activityForm.equals("1") ? "" : "1";
                break;
            case 2:
                activityForm = activityForm.equals("2") ? "" : "2";
                break;
        }
        if (activityForm.equals("0")) {
            activity_type_0.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            activity_type_0.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            activity_type_0.setTextColor(this.getResources().getColor(R.color.colorFont6));
            activity_type_0.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityForm.equals("1")) {
            activity_type_1.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            activity_type_1.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            activity_type_1.setTextColor(this.getResources().getColor(R.color.colorFont6));
            activity_type_1.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityForm.equals("2")) {
            activity_type_2.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            activity_type_2.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            activity_type_2.setTextColor(this.getResources().getColor(R.color.colorFont6));
            activity_type_2.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
    }

    /**
     * 控制活动类型
     */
    private void onMoney(int k) {
        switch (k) {
            case 0:
                activityMoney = activityMoney.equals("0") ? "" : "0";
                break;
            case 1:
                activityMoney = activityMoney.equals("1") ? "" : "1";
                break;
            case 2:
                activityMoney = activityMoney.equals("2") ? "" : "2";
                break;
            case 3:
                activityMoney = activityMoney.equals("3") ? "" : "3";
                break;
            case 4:
                activityMoney = activityMoney.equals("4") ? "" : "4";
                break;
            case 5:
                activityMoney = "";
                break;
        }
        if (activityMoney.equals("0")) {
            search_money_0.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            search_money_0.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            search_money_0.setTextColor(this.getResources().getColor(R.color.colorFont6));
            search_money_0.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityMoney.equals("1")) {
            search_money_1.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            search_money_1.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            search_money_1.setTextColor(this.getResources().getColor(R.color.colorFont6));
            search_money_1.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityMoney.equals("2")) {
            search_money_2.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            search_money_2.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            search_money_2.setTextColor(this.getResources().getColor(R.color.colorFont6));
            search_money_2.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityMoney.equals("3")) {
            search_money_3.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            search_money_3.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            search_money_3.setTextColor(this.getResources().getColor(R.color.colorFont6));
            search_money_3.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }
        if (activityMoney.equals("4")) {
            search_money_4.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            search_money_4.setBackground(this.getResources().getDrawable(R.drawable.search_class_on));
        } else {
            search_money_4.setTextColor(this.getResources().getColor(R.color.colorFont6));
            search_money_4.setBackground(this.getResources().getDrawable(R.drawable.search_class_off));
        }

    }
}
