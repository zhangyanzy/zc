package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/5/15.
 */

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.et_feedback)
    EditText et_feedback;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private String content;
    private static String TAG = "反馈意见";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_feedbck_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText(getString(R.string.feedback));
        iv_top_menu.setVisibility(View.GONE);

//        addEditListener();
    }

    @OnClick({R.id.iv_top_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_submit:
                content = et_feedback.getText().toString().trim();
                if (GeneralUtils.isNotNullOrZeroLenght(content))
                    doSubmit();
                else ToastUtil.makeText(FeedbackActivity.this, getString(R.string.feedback_empty));
                break;
        }
    }

    private void doSubmit() {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        HttpUtil.get(Constants.URL.FEEDBACK, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.i(TAG, response.getCode()+"");
                ToastUtil.makeText(FeedbackActivity.this, response.getDesc());
            }
        });
    }

    private void addEditListener() {
        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_submit, this);
        return super.onTouchEvent(event);
    }
}
