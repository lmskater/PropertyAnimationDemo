package com.xxx.property.animation.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout mAnimationViewParent;
    private Button mAddViewBtn;
    private ArrayList<AnimationView> mAnimationViewList;
    private int mMaxCount = 5;
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mAnimationViewParent = (LinearLayout) findViewById(R.id.parent);
        mAnimationViewList = new ArrayList<>();
        mAddViewBtn = (Button) findViewById(R.id.add);
        mAddViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnimationView();
            }
        });
    }

    public LinearLayout getParentLayout() {
        return mAnimationViewParent;
    }

    public ArrayList<AnimationView> getListAnimationView() {
        return mAnimationViewList;
    }

    private void addAnimationView() {
        if (mAnimationViewList.size() >= mMaxCount) {
            return;
        }
        createTextView();
        translationAllView();
    }

    private void createTextView() {
        TextView tx = new TextView(this);
        tx.setText("xxx" + mIndex);
        tx.setBackgroundColor(Color.RED);
        tx.setTextSize(30.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        tx.setLayoutParams(layoutParams);
        mIndex++;
        mAnimationViewParent.addView(tx);
        AnimationView animationView = new AnimationView(this, tx);
        mAnimationViewList.add(animationView);
    }

    /**
     * 开始所有View的动画
     */
    private void translationAllView() {
        int size = mAnimationViewList.size();
        for (int i = 0; i < size; i++) {
            mAnimationViewList.get(i).start();
        }
    }

}
