package cn.zhaocaiapp.zc_app_android.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.zhaocaiapp.zc_app_android.R;

/**
 * activity基础类，所有activity继承
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @BindView(R.id.toolbar)
    protected Toolbar header;   //header

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        mUnbinder = ButterKnife.bind(this);

        //设置header转成actionBar
        setSupportActionBar(header);

        //初始化工程
        init(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResId){
        super.setContentView(layoutResId);
        //Butter Knife初始化
        ButterKnife.bind(this);
    }


    @Override
    public void setContentView(View view){
        super.setContentView(view);
        //Butter Knife初始化
        ButterKnife.bind(this);
    }


    @Override
    public void setContentView(View view,ViewGroup.LayoutParams params){
        super.setContentView(view,params);
        //Butter Knife初始化
        ButterKnife.bind(this);
    }


    public abstract int getContentViewResId();

    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
