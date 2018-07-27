package cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.slide;

import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;

import cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.BaseAnimatorSet;


public class SlideRightExit extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(view, "translationX", 0, 250 * dm.density),
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}
