package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * 活动列表
 */
public class ActivityListActivity extends BaseActivity {

    @BindView(R.id.active_list) ListView listView;
    private List<String> activityList;

    {
        activityList.add("1");
        activityList.add("2");
        activityList.add("3");
        activityList.add("4");
        activityList.add("5");
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return activityList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;

                LayoutInflater inflater = ActivityListActivity.this.getLayoutInflater();
                view = inflater.inflate(R.layout.activity_activity_scene_detail,null);

                TextView textView = view.findViewById(R.id.textView);
                textView.setText(activityList.get(position));

                return view;
            }
        });
    }
}
