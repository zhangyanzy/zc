package cn.zhaocaiapp.zc_app_android.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.zhaocaiapp.zc_app_android.R;

/**
 * activity基础类，所有activity继承
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public abstract class BaseActivity<T> extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    //返回按钮
    private LinearLayout back;
    //标题
    protected TextView title;
    //header右边字符
    protected TextView right;

    //loading
    protected ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //加载页面
        setContentView(R.layout.activity_main);

        //注册事件总线
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //取消注册事件总线
        EventBus.getDefault().unregister(this);
    }

    /**
     * 发送事件
     *
     * @param obj
     */
    protected void postEvent(T obj){
        EventBus.getDefault().post(obj);
    }

    /**
     * 发送黏性事件
     *
     * @param obj
     */
    protected void postStickyEvent(T obj){
        EventBus.getDefault().postSticky(obj);
    }

    /**
     * 处理事件
     *
     * @param obj
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected abstract void doEvent(T obj);
}
