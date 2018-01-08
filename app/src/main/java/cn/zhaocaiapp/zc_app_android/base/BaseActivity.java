package cn.zhaocaiapp.zc_app_android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

    @Nullable
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

    public abstract int getContentViewResId();

    public abstract void init(Bundle savedInstanceState);
}
