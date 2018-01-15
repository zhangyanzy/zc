package cn.zhaocaiapp.zc_app_android.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.widget.LoadingDialog;


/**
 * fragment懒加载
 * */
public abstract class BaseFragment extends Fragment {
    private View rootView;
    private Unbinder unbinder;

    protected boolean isInit = false;//视图是否已经初初始化
    protected boolean isLoad = false;//是否已加载数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = setContentView(inflater, container, savedInstanceState);
            unbinder = ButterKnife.bind(this, rootView);
            isInit = true;
            init();
            isCanLoadData();
        }

        return rootView;
    }

    //绑定rootView
    public abstract View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    //初始化数据
    public abstract void init();

    //加载数据
    public abstract void loadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    //是否可以加载数据
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            loadData();
            isLoad = true;
        } else if (isLoad) {
            stopLoad();
        }
    }

    /**
     * 停止加载数据
     * 当视图不可见并且加载过数据,调用此方法
     */
    public void stopLoad() {

    }

    //管理软键盘的显示
    public void manageKeyBord(View view, Activity mActivity) {
        if (KeyBoardUtils.isKeyBordVisiable(mActivity))
            KeyBoardUtils.closeKeybord(view, mActivity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
            unbinder.unbind();
        }
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启浮动加载进度条Gif
     */
    public void startGifProgressDialog() {
        LoadingDialog.showDialogForLoadingGif(getActivity());
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }


    /**
     * 启动Activity
     */
    public void openActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
