package com.example.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.view.R;

/**
 * Created by Administrator on 2017/1/24.
 */
public class HorizontalScrollViewEx extends ViewGroup {
    /**
     *
     * 功能描述：
     * 1.可以使子元素左右滑动的父容器
     * 2.子元素左右滑动时，实现平滑无冲突滑动
     *
     */
    private static final String TAG = "HorizontalScrollViewEx";

    public HorizontalScrollViewEx(Context context) {
        super(context);
        initWidget(null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidget(attrs);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(attrs);
    }

    /**
     * 初始化控件
     * @param attrs
     */
    private void initWidget(AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalScrollViewEx);
            //获取自定义的属性


        }


    }

    /**
     * 如果在布局文件中使用margin属性，那必须实现该方法并且返回MarginLayoutParams对象才可
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //还可以继承 ViewGroup.MarginLayoutParams 实现更多的功能
        return new ViewGroup.MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量父类的宽/高，以及测量子类的宽/高
        final int childCount = getChildCount();
        //计算所有子类的宽/高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //当前控件的计算格式和剩余的空间大小
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else if(widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST){
            //获取子类的最大高度和所有子类的宽度之和
            setMeasuredDimension(measureSumWidthChild(),measureMaxHeightChild());
        }else if (widthMeasureMode == MeasureSpec.AT_MOST){
            //获取子类的宽度之和
            setMeasuredDimension(measureSumWidthChild(),heightMeasureSize);
        }else if (heightMeasureMode == MeasureSpec.AT_MOST){
            //获取子类的最大高度
            setMeasuredDimension(widthMeasureSize,measureMaxHeightChild());
        }
    }

    /**
     * 获取子类中高度以及和margin属性之和最大的
     * @return
     */
    private int measureMaxHeightChild(){
        int maxHeight = 0;
        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            MarginLayoutParams childParams = (MarginLayoutParams) child.getLayoutParams();
            int childHeightSpace = child.getMeasuredHeight()+childParams.topMargin+childParams.bottomMargin;
            if (maxHeight < childHeightSpace){
                maxHeight = childHeightSpace;
            }
        }
        return maxHeight;
    }

    /**
     * 获取所有子类的宽度之和
     * @return
     */
    private int measureSumWidthChild(){
        int sumWidth = 0;
        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            ViewGroup.MarginLayoutParams childParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            int childWidthSpace = child.getMeasuredWidth()+childParams.leftMargin+childParams.rightMargin;
            sumWidth  += childWidthSpace;
        }
        return sumWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //定位子类的位置，需要考虑自身的padding和子类的margin属性的值
        int leftWidth = 0;
        int childCount = getChildCount();

        //当前控件的padding
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPadding = getPaddingTop();
        int bPadding = getPaddingBottom();
        leftWidth = lPadding;

        for (int i = 0;i<childCount;i++){
            View chidView = getChildAt(i);
            ViewGroup.MarginLayoutParams childParams = (ViewGroup.MarginLayoutParams) chidView.getLayoutParams();
            leftWidth += childParams.leftMargin;
            chidView.layout(leftWidth,tPadding+childParams.topMargin,leftWidth+chidView.getMeasuredWidth(),tPadding+childParams.topMargin+chidView.getMeasuredHeight());
            leftWidth += chidView.getMeasuredWidth();
        }
    }
}
