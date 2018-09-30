package cn.zhaocaiapp.zc_app_android.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.IEditTextChangeListener;

/**
 * Created by admin on 2018/9/10.
 */

public class WorkSizeCheckUtil {

    static IEditTextChangeListener mChangerListener;

    public static void setChangeListener(IEditTextChangeListener listener) {
        mChangerListener = listener;
    }


    public static class textChangeListener {
        private TextView mBtn;
        private EditText[] editTexts;


        public textChangeListener(TextView mBtn) {
            this.mBtn = mBtn;
        }

        public textChangeListener addAllEditText(EditText... editTexts) {
            this.editTexts = editTexts;
            initEditListener();
            return this;
        }


        //遍历EditText个数
        private void initEditListener() {
            for (EditText editText : editTexts) {
                editText.addTextChangedListener(new editTextState());
            }
        }

        //监听EditText状态变化

        private class editTextState implements TextWatcher {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入文本之前的状态
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如数文本中的状态
            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入文本后状态
                if (checkAllEdit()) {
                    mChangerListener.textChange(true);
                } else {
                    mChangerListener.textChange(false);
                }
            }


            private boolean checkAllEdit() {
                for (EditText editText : editTexts) {
                    if (!TextUtils.isEmpty(editText.getText().toString())) {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
    }

}
