package com.xxx.property.animation.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by xxx001 on 2017/12/15.
 */

public class AnimationView {

    private static final String TAG = AnimationView.class.getSimpleName();

    private View mView;
    private int mViewHeight;
    private float mTranslationY;
    private float mTranslationSpeed;
    private long mDuration = 4000;
    private boolean isTranslation;
    private ValueAnimator mAnim;
    private MainActivity mActivity;

    public AnimationView(MainActivity activity, View view) {
        mActivity = activity;
        mView = view;
        initViewHeight(view);
        initAnim();
    }

    /**
     * 初始化View高度
     * @param view
     */
    private void initViewHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        mViewHeight = view.getMeasuredHeight();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mAnim = ValueAnimator
                .ofFloat(mDuration, 0.0f) // 以整个动画时间(mDuration)为起始值,便于onAnimationUpdate计算
                .setDuration(mDuration);
        mAnim.addListener(mAnimatorListenerAdapter);
        mAnim.addUpdateListener(mAnimatorUpdateListener);
    }

    private AnimatorListenerAdapter mAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            mActivity.getParentLayout().removeView(mView);
            mActivity.getListAnimationView().remove(AnimationView.this);
        }
    };

    private AnimatorUpdateListener mAnimatorUpdateListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float cVal = (Float) animation.getAnimatedValue();
            // 最后一秒时开始进行透明渐变设置
            if (cVal <= 1000.0f) {
                // 透明值渐变以(1.0 -> 0.0)变化
                mView.setAlpha(cVal / 1000.f);
            }
            if (isTranslation) {
                // 防止整个动画过程中，上移部分结束后重复调用
                if (mTranslationY <= 0.0f) {
                    mTranslationY = 0.0f;
                    isTranslation = false;
                }
                mView.setTranslationY(mTranslationY);
                // 控制上移速度
                // float直接相减，精度丢失，使用BigDecimal进行相减
                BigDecimal translationY = new BigDecimal(Float.toString(mTranslationY));
                BigDecimal translationSpeed = new BigDecimal(Float.toString(mTranslationSpeed));
                mTranslationY = translationY.subtract(translationSpeed).floatValue();
            }
        }
    };

    /**
     * 开始动画
     */
    public void start() {
        isTranslation = true;
        mTranslationY = mViewHeight + 20.0f;// 包含margin高度20.0f
        mTranslationSpeed = mTranslationY / 10;
        if (!mAnim.isStarted()) {
            mAnim.start();
        }
    }

}
