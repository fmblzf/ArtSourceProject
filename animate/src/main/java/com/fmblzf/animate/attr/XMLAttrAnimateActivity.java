package com.fmblzf.animate.attr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fmblzf.animate.R;
import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorSet;

/**
 * Created by Administrator on 2017/3/22.
 */
public class XMLAttrAnimateActivity extends AppCompatActivity {

    private View mView;
    private View mTranslateAnimate;

    private TextView mPackageTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backcolor_animate_layout);

        mView = this.findViewById(R.id.attr_view);
        mTranslateAnimate = this.findViewById(R.id.attr_view_translate);
        mPackageTextView = (TextView) this.findViewById(R.id.attr_set_tv);

        AnimatorSet valueAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.attr_translation_resource);
        valueAnimator.setTarget(mTranslateAnimate);
        valueAnimator.start();

        AnimatorSet value2 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.attr_backcolor_resource);
        value2.setTarget(mView);
        value2.start();

        AnimatorSet value3 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.attr_set_resource);
        value3.setTarget(mPackageTextView);
        value3.start();


    }
}
