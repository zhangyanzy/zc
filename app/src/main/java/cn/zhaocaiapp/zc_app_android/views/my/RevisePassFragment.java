package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RevisePassFragment extends BaseFragment {
    @BindView(R.id.edit_old_pass)
    EditText edit_old_pass;
    @BindView(R.id.edit_new_pass)
    EditText edit_new_pass;
    @BindView(R.id.edit_confirm_pass)
    EditText edit_confirm_pass;
    @BindView(R.id.tv_submit)
    TextView tv_submit;


    private View rootView;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_revisepass_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getActivity().onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public void loadData() {

    }

}
