package cn.zhaocaiapp.zc_app_android.views.my;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;

/**
 * Created by ASUS on 2017/11/8.
 */

public class ApplyCashActivity extends Activity {
    @BindView(R.id.layout_top_title)RelativeLayout layout_top_title;
    @BindView(R.id.iv_top_back)ImageView iv_top_back;
    @BindView(R.id.iv_top_menu) ImageView iv_top_menu;
    @BindView(R.id.tv_top_title)TextView tv_top_title;
    @BindView(R.id.tv_apply_cash)TextView tv_apply_cash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_apply_cash);
        ButterKnife.bind(this);

        initView();
    }

    private void initView(){
//        layout_top_title.setBackgroundColor(getResources().getColor(R.color.white));
        iv_top_menu.setVisibility(View.GONE);
//        tv_top_title.setText(R.string.apply_cash);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_apply_cash.getWindowToken(), 0);//从控件所在的窗口中隐藏
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_top_back:
                finish();
                break;
        }
    }
}
