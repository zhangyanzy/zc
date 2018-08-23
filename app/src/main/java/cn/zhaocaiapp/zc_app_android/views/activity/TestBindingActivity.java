package cn.zhaocaiapp.zc_app_android.views.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityTestBindingBinding;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

public class TestBindingActivity extends BaseCompatActivity {

    private ActivityTestBindingBinding binding;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_binding);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.test_view:
                    ToastUtil.makeText(getApplicationContext(), "test");
                    break;
                default:
                    break;

            }
        }
    }
}
