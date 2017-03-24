package com.fmblzf.animate.attr;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/24.
 */
public class WrapperViews {

    /**
     *
     * 该类实现封装View，从而通过该类实现属性动画中的要求（属性必须有对应的get和set方法才行）
     * 可以在该类中完善属性的get和set方法，进而实现对应的效果
     *
     *
     */

    private View mTarget;

    public WrapperViews(View view){
        this.mTarget = view;
    }

    public void setWidth(int width){
        ViewGroup.LayoutParams layoutParams = mTarget.getLayoutParams();
        layoutParams.width = width;
        mTarget.requestLayout();
        mTarget.invalidate();
    }
    public int getWidth(){
        return mTarget.getWidth();
    }


}
