package com.fmblzf.animate.attr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.fmblzf.animate.R;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Administrator on 2017/3/22.
 */
public class BackColorAnimateActivity extends AppCompatActivity implements View.OnClickListener {

    private View mView;
    private View mTranslateAnimate;

    private TextView mPackageTextView;

    private Button mButton;
    private Button mTranButton;
    //mTranButton该控件是否已经移动过了
    private boolean isTransltion = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backcolor_animate_layout);

        mView = this.findViewById(R.id.attr_view);
        mTranslateAnimate = this.findViewById(R.id.attr_view_translate);
        mPackageTextView = (TextView) this.findViewById(R.id.attr_set_tv);
        mButton = (Button) this.findViewById(R.id.animator_button);
        mButton.setOnClickListener(this);

        mTranButton = (Button) this.findViewById(R.id.animator_tran_button);
        mTranButton.setOnClickListener(this);
        //创建移动属性的变化
        createTranslationAnimate();
        //设置背景色动画效果
        createColorAttrAnimate();
        //创建组合动画效果
        createPackageAttrAnimate();
    }

    /**
     * 创建组合属性的动画效果
     */
    private void createPackageAttrAnimate() {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator value1 = ObjectAnimator.ofFloat(mPackageTextView,"rotationX",0,360);
        ValueAnimator value2 = ObjectAnimator.ofFloat(mPackageTextView,"rotationY",0,180);
        ValueAnimator value3 = ObjectAnimator.ofFloat(mPackageTextView,"rotation",0,-90);
        ValueAnimator value4 = ObjectAnimator.ofFloat(mPackageTextView,"translationX",0,90);
        ValueAnimator value5 = ObjectAnimator.ofFloat(mPackageTextView,"translationY",0,90);
        ValueAnimator value6 = ObjectAnimator.ofFloat(mPackageTextView,"scaleX",1,1.5f);
        ValueAnimator value7 = ObjectAnimator.ofFloat(mPackageTextView,"scaleY",1,0.5f);
        ValueAnimator value8 = ObjectAnimator.ofFloat(mPackageTextView,"alpha",1,0.25f);

        value1.setRepeatCount(ValueAnimator.INFINITE);
        value1.setRepeatMode(ValueAnimator.REVERSE);

        value2.setRepeatCount(ValueAnimator.INFINITE);
        value2.setRepeatMode(ValueAnimator.REVERSE);

        value3.setRepeatCount(ValueAnimator.INFINITE);
        value3.setRepeatMode(ValueAnimator.REVERSE);

        value4.setRepeatCount(ValueAnimator.INFINITE);
        value4.setRepeatMode(ValueAnimator.REVERSE);

        value5.setRepeatCount(ValueAnimator.INFINITE);
        value5.setRepeatMode(ValueAnimator.REVERSE);

        value6.setRepeatCount(ValueAnimator.INFINITE);
        value6.setRepeatMode(ValueAnimator.REVERSE);

        value7.setRepeatCount(ValueAnimator.INFINITE);
        value7.setRepeatMode(ValueAnimator.REVERSE);

        value8.setRepeatCount(ValueAnimator.INFINITE);
        value8.setRepeatMode(ValueAnimator.REVERSE);

        set.playTogether(
                value1
                ,
                value2
                ,
                value3
                ,
                value4
                ,
                value5
                ,
                value6
                ,
                value7
                ,
                value8

        );
        set.setDuration(5*1000).start();
    }

    /**
     * 创建对象移动的动画效果
     */
    private void createTranslationAnimate() {
        //创建动画对象
        ValueAnimator translateValue = ObjectAnimator.ofFloat(mTranslateAnimate,"translationY",mTranslateAnimate.getTranslationY(),mTranslateAnimate.getTranslationY()+100f);
        translateValue.setDuration(500);
        translateValue.setEvaluator(new FloatEvaluator());
        translateValue.setRepeatCount(ValueAnimator.INFINITE);
        translateValue.setRepeatMode(ValueAnimator.REVERSE);
        translateValue.start();
    }

    /**
     * 设置背景色动画效果
     */
    private void createColorAttrAnimate() {
        //创建动画对象
        ValueAnimator colorValue = ObjectAnimator.ofInt(mView,"backgroundColor",0xFFFF8080,0xFF8080FF);
        //设置时间周期
        colorValue.setDuration(3000);
        //设置属性的变化过程类
        colorValue.setEvaluator(new ArgbEvaluator());
        //设置循环次数，ValueAnimator.INFINITE：无限次
        colorValue.setRepeatCount(ValueAnimator.INFINITE);
        //设置循环执行的模式，REVERSE：反转执行(一次结束之后，然后倒着执行)；RESTART：重新开始执行（一次执行完之后，又从头开始）
        colorValue.setRepeatMode(ValueAnimator.REVERSE);
        //开始动画
        colorValue.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.animator_button:

                /* View动画通过ScaleX实现的变大，会使得文本也拉伸，但是属性动画则没有出现文本拉伸的情况 */

                /*  通过View动画实现控件变大（代码）  */
//                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,(1.0f)*(mButton.getWidth()+500)/mButton.getWidth(),0.0f,1.0f);
//                scaleAnimation.setDuration(500);
//                scaleAnimation.setFillAfter(true);
//                mButton.startAnimation(scaleAnimation);
                /*  通过View动画实现控件变大（XML配置）  */
//                AnimationSet animation = (AnimationSet) AnimationUtils.loadAnimation(BackColorAnimateActivity.this,R.anim.scale_animation_resource);
//                mButton.startAnimation(animation);
                /*  通过属性动画实现控件变大（代码）----当view对象属性没有set或者get方法时，或者set方法并没有启动改变UI显示的时候，  */
                //1.  可以通过封装方式来实现
//                WrapperViews wrapperViews = new WrapperViews(mButton);
//                Animator animator = ObjectAnimator.ofInt(wrapperViews,"width",mButton.getWidth(),mButton.getWidth()+500);
//                animator.setDuration(500);
//                animator.start();

                //2.  通过ValueAnimator来实现动画效果，然后在动画监听器下实现对象的属性变化
                final int start = mButton.getWidth();
                final int end = mButton.getWidth()+500;
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
                valueAnimator.setDuration(5000);
                valueAnimator.setTarget(mButton);
                final IntEvaluator intEvaluator = new IntEvaluator();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //获取当前动画的进度值，整型，（0--100）
                        int currentValue = (int)animation.getAnimatedValue();
                        Log.i("",String.valueOf(currentValue));
                        //获取当前进度占整个动画过程的比例，浮点型，0--1
                        float faction = animation.getAnimatedFraction();
                        //根据整型估值器获取当前的属性值
                        mButton.getLayoutParams().width = intEvaluator.evaluate(faction,start,end);
                        //唤起重新布局设置大小
                        mButton.requestLayout();
                    }
                });
                valueAnimator.start();

                break;
            case R.id.animator_tran_button:
                if(isTransltion){
                    //从左移动到右边
                    ViewLeftToRight();
                    isTransltion = false;
                }else{
                    //从右边移动到左边
                    ViewRightToLeft();
                    isTransltion = true;
                }
                break;
            default:
                break;
        }
    }

    /**
     *  从右边移动到左边
     */
    private void ViewRightToLeft() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mTranButton,"translationX",0,450);
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }

    /**
     *  从左边移动到右边
     */
    private void ViewLeftToRight() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mTranButton,"translationX",450,0);
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }
}
