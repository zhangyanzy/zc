package cn.zhaocaiapp.zc_app_android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.jph.takephoto.app.TakePhotoFragment;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.LoadingDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * fragment懒加载
 */
public abstract class BaseFragment extends TakePhotoFragment implements EasyPermissions.PermissionCallbacks {
    private View rootView;
    private Unbinder unbinder;

    protected boolean isInit = false;//视图是否已经初初始化
    protected boolean isLoad = false;//是否已加载数据

    private List<String> perms = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = setContentView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        isInit = true;
        init();
        isCanLoadData();

        return rootView;
    }

    /**
     * 绑定rootView
     */
    public abstract View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化工程
     */
    public abstract void init();

    /**
     * 加载数据
     */
    public abstract void loadData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint() && !isLoad) {
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

    /**
     * 验证手机号是否符合规则
     */
    protected boolean judgePhone(Context context, String phone) {
        if (GeneralUtils.isNullOrZeroLenght(phone)) {
            ToastUtil.makeText(context, getString(R.string.phone_not_empty));
            return false;
        }
        if (!GeneralUtils.isTel(phone)) {
            ToastUtil.makeText(context, getString(R.string.isNot_phone));
            return false;
        }
        return true;
    }

    /**
     * 验证密码是否符合规则
     */
    protected boolean judgePass(Context context, String pass) {
        if (GeneralUtils.isNullOrZeroLenght(pass)) {
            ToastUtil.makeText(context, getString(R.string.pass_not_empty));
            return false;
        }
        if (!GeneralUtils.IsPassword(pass)) {
            ToastUtil.makeText(context, getString(R.string.pass_length));
            return false;
        }
        return true;
    }

    /**
     * 管理软键盘的显示
     */
    public void manageKeyBord(View view, Activity mActivity) {
        if (KeyBoardUtils.isKeyBordVisiable(mActivity))
            KeyBoardUtils.closeKeybord(view, mActivity);
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

    /**
     * 启动Activity
     */
    public void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 启动Activity
     */
    public void openActivityForResult(Class<?> mClass, int requestCode) {
        Intent intent = new Intent(getActivity(), mClass);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 启动Activity
     */
    public void openActivityForResult(Class<?> mClass,Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), mClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    //判断是否获取到指定的权限
    public boolean isGrantPerm(String perm) {
        return (perms.contains(perm));
    }

    /**
     * 动态申请权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        this.perms = perms;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    /**
     * 监听触摸空白处，隐藏软键盘
     */
    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                    manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            return false;
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        stopLoad();
    }
}
