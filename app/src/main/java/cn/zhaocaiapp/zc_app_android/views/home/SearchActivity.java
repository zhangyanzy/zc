package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
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
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.SearchRecommendResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
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
    @BindView(R.id.search_city)
    LinearLayout search_city;
    @BindView(R.id.search_town)
    LinearLayout search_town;
    @BindView(R.id.search_city_text)
    TextView search_city_text;
    @BindView(R.id.search_town_text)
    TextView search_town_text;
    @BindView(R.id.search_history_clear)
    ImageView search_history_clear;
    @BindView(R.id.search_edit_top_limit)
    EditText search_edit_top_limit;
    @BindView(R.id.search_edit_limit)
    EditText search_edit_limit;
    @BindView(R.id.search_btn_off)
    Button search_btn_off;
    @BindView(R.id.search_btn_on)
    Button search_btn_on;
    @BindView(R.id.layout_history)
    LinearLayout layout_history;


    private List<SearchRecommendResp> searchRecommendRespList; //推荐活动
    private TagAdapter tagAdapter;
    private TagAdapter hisTagAdapter;
    private List<String> historyList;//搜索历史
    private String activityType = "";//活动分类 0单体活动 1串联活动 2协同活动
    private String activityForm = "";//活动类型 0线下活动 1视频活动 2问卷活动
    private String activityMoney = "";//金额
    private String topLimit = "";//金额上限
    private String limit = "";//金额下限
    private List<LocationResp> provinces;
    private List<LocationResp> citys;
    private List<List<LocationResp>> towns;
    private OptionsPickerView optionsPickerView;
    private long city, town;
    private Map<String, String> params = new HashMap<>();


    @Override
    protected void onRestart() {
        super.onRestart();
        getHistory();
    }

    @Override
    public int getContentViewResId() {
        return R.layout.search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ActivityUtil.addActivity(this);

        //初始化城市列表
        historyList = new ArrayList<>();
        searchRecommendRespList = new ArrayList<>();
        citys = new ArrayList<>();
        towns = new ArrayList<>();
        initView();
        getAreasList();
        initData();
        getHistory();

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
                EBLog.i("tag", result.toString());
                searchRecommendRespList.clear();
                searchRecommendRespList.addAll(result);
                tagAdapter.notifyDataChanged();
            }

            @Override
            public void error(Response<List<SearchRecommendResp>> response) {
                EBLog.i("tag", response.toString());
            }

        });


    }

    private void initView() {

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
                search_edit.setText(historyList.get(position));
                EBLog.i("tag", historyList.get(position));
                return true;
            }
        });

        /**
         * 获取热门推荐
         */
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
                search_edit.setText(searchRecommendRespList.get(position).getName());
                EBLog.i("tag", searchRecommendRespList.get(position).toString());
                return true;
            }
        });


        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //保存搜索历史
                    saveHistory(search_edit.getText().toString());
                    //TODO回车键按下时要执行的操作
                    goSearch();
                }
                return false;
            }
        });

        //输入框改变 终止金额
        search_edit_top_limit.addTextChangedListener(new TextWatcher() {
            //内容改变前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //内容改变
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onMoney(5);

            }

            //内容改变后
            @Override
            public void afterTextChanged(Editable s) {
                topLimit = String.valueOf(s);
            }
        });

        //输入框改变 起始金额
        search_edit_limit.addTextChangedListener(new TextWatcher() {
            //内容改变前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //内容改变
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onMoney(5);

            }

            //内容改变后
            @Override
            public void afterTextChanged(Editable s) {
                limit = String.valueOf(s);
            }
        });
    }

    /**
     * 确认搜索
     */
    private void goSearch() {
        Bundle bd = new Bundle();
        bd.putString("name", search_edit.getText().toString());
        bd.putString("activityType", activityType);
        bd.putString("activityForm", activityForm);
        bd.putString("topLimit", topLimit);
        bd.putString("limit", limit);
        bd.putString("cityCode", city == 0 ? "" : String.valueOf(city));
        bd.putString("areaCode", town == 0 ? "" : String.valueOf(town));
        openActivity(SearchResulfActivity.class, bd);
    }

    /**
     * 重置
     */
    private void goClear() {
        search_edit.setText("");
        onClass(3);
        onType(3);
        onMoney(5);
        topLimit = "";
        limit = "";
        search_edit_top_limit.setText(topLimit);
        search_edit_limit.setText(limit);
        city = 0;
        town = 0;
        search_city_text.setText("不限");
        search_town_text.setText("不限");
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
        getHistory();
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
        historyList.clear();
        historyList.addAll(stringList1);
        if (historyList.size() == 0) {
            layout_history.setVisibility(View.GONE);
        }else {
            layout_history.setVisibility(View.VISIBLE);
        }
        hisTagAdapter.notifyDataChanged();
    }

    /**
     * 清除搜索历史
     */
    private void clearHistory() {
        SpUtils.put(Constants.SPREF.SEARCH_HISTORY, "");
        historyList.clear();
        if (historyList.size() == 0) {
            layout_history.setVisibility(View.GONE);
        }
        hisTagAdapter.notifyDataChanged();
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
            R.id.search_money_4,
            R.id.search_city,
            R.id.search_town,
            R.id.search_history_clear,
            R.id.search_btn_on,
            R.id.search_btn_off,

    })
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.search_cancel:
                ActivityUtil.finishActivity(this);
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
            //区域 市
            case R.id.search_city:
                optionsPickerView.show();
                break;
            //区域 区
            case R.id.search_town:
                optionsPickerView.show();
                break;
            //清楚搜索历史
            case R.id.search_history_clear:
                clearHistory();
                break;
            //完成
            case R.id.search_btn_on:
                //保存搜索历史
                saveHistory(search_edit.getText().toString());
                //TODO回车键按下时要执行的操作
                goSearch();
                break;
            //重置
            case R.id.search_btn_off:
                goClear();
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
            case 3:
                activityType = "";
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
            case 3:
                activityForm = "";
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
     * 控制奖励金额
     */
    private void onMoney(int k) {
        switch (k) {
            case 0:
                activityMoney = activityMoney.equals("0") ? "" : "0";
                topLimit = "5";
                limit = "0";
                break;
            case 1:
                activityMoney = activityMoney.equals("1") ? "" : "1";
                topLimit = "10";
                limit = "5";
                break;
            case 2:
                activityMoney = activityMoney.equals("2") ? "" : "2";
                topLimit = "15";
                limit = "10";
                break;
            case 3:
                activityMoney = activityMoney.equals("3") ? "" : "3";
                topLimit = "20";
                limit = "15";
                break;
            case 4:
                activityMoney = activityMoney.equals("4") ? "" : "4";
                topLimit = "";
                limit = "20";
                break;
            case 5:
                activityMoney = "";
                break;
        }
        //如果都没有选中清除上下线金额
        if (activityMoney.equals("")) {
            topLimit = "";
            limit = "";
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

    //城市选择器初始化和设置
    private void setCityPicker() {
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                EBLog.i("tag", "options1:" + String.valueOf(options1));
                EBLog.i("tag", "options2:" + String.valueOf(options2));
                EBLog.i("tag", "options3:" + String.valueOf(options3));
                city = citys.get(options1).getAreaCode();
                search_city_text.setText(citys.get(options1).getAreaName());
                if (options2 == 0) {
                    town = 0;
                    search_town_text.setText("不限");
                } else {
                    town = citys.get(options1).getAreaList().get(options2 - 1).getAreaCode();
                    search_town_text.setText(citys.get(options1).getAreaList().get(options2 - 1).getAreaName());
                }
            }
        }).setTitleText("城市选择")
                .build();
        if (GeneralUtils.isNotNullOrZeroSize(provinces) && GeneralUtils.isNotNullOrZeroSize(citys) && GeneralUtils.isNotNullOrZeroSize(towns))
            optionsPickerView.setPicker(citys, towns);
    }

    //获取省、市、区三级列表
    private void getAreasList() {
        provinces = ZcApplication.getProvinces();
        LocationResp citysDefault = new LocationResp();
        citysDefault.setAreaName("不限");
        citysDefault.setAreaCode((long) 0);
        citys.add(citysDefault);
        List<LocationResp> townsDefault = new ArrayList<>();
        townsDefault.add(citysDefault);
        towns.add(townsDefault);

        for (int i = 0; i < provinces.size(); i++) {
            List<LocationResp> cityList = provinces.get(i).getAreaList();//该省的城市列表（第二级）
            List<List<LocationResp>> province_townList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int j = 0; j < cityList.size(); j++) {
                List<LocationResp> city_townList = new ArrayList<>();
                city_townList.add(citysDefault);
                city_townList.addAll(cityList.get(j).getAreaList());
                province_townList.add(city_townList);
            }
            citys.addAll(cityList);
            towns.addAll(province_townList);
        }
        setCityPicker();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
