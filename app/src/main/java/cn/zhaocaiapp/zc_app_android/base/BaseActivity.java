package cn.zhaocaiapp.zc_app_android.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;


/**
 * activity基础类，所有activity继承
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private boolean isPassUsable;//密码是否可用

    //@Nullable
    //@BindView(R.id.toolbar)
    //protected Toolbar header;   //header

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        mUnbinder = ButterKnife.bind(this);

        //设置header转成actionBar
        //setSupportActionBar(header);

        //初始化工程
        init(savedInstanceState);
    }

    public abstract int getContentViewResId();

    public abstract void init(Bundle savedInstanceState);


    //验证手机号和密码是否符合规则
    protected boolean judgPhoneAndPass(String phone, String pass){
        if (GeneralUtils.isNullOrZeroLenght(phone)){
            ToastUtil.makeText(this, getString(R.string.phone_not_empty));
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(pass)){
            ToastUtil.makeText(this, getString(R.string.pass_not_empty));
            return false;
        }
        if (!GeneralUtils.isTel(phone)){
            ToastUtil.makeText(this, getString(R.string.isNot_phone));
            return false;
        }
        if (!GeneralUtils.IsPassword(pass)){
            ToastUtil.makeText(this, getString(R.string.isNot_pass));
            return false;
        }
        return true;
    }

    protected void monitorEditChange(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!GeneralUtils.IsPassword(s.toString())) {
                    editText.setTextColor(Color.RED);
                    isPassUsable = false;
                }
                else {
                    editText.setTextColor(getResources().getColor(R.color.text_black));
                    isPassUsable = true;
                }
            }
        });
    }

    protected boolean getIsPassUsable(){
        return isPassUsable;
    }
}
