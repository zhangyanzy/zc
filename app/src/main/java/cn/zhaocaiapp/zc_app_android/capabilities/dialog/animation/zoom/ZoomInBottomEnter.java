package cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.zoom;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.View.MeasureSpec;

import cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.BaseAnimatorSet;


/**
 * 从底部急速滑入
 * */

public class ZoomInBottomEnter extends BaseAnimatorSet {
	public ZoomInBottomEnter() {
		duration = 300;
	}

	@Override
	public void setAnimation(View view) {
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int h = view.getMeasuredHeight();

		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.475f, 1),
				ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 0.475f, 1),
				ObjectAnimator.ofFloat(view, "translationY", h, -60, 0), ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1));
	}
}
