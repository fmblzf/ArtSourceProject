package com.example.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

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
    /**
     * 是否循环滑动
     */
    private boolean isloop = false;
    /**
     * 子类的个数
     */
    private int mChildSize = 0;
    /**
     * 子类的平均宽度（这一块的宽度认为是一样宽的）
     */
    private int mChildWidth = 0;
    /**
     * 当前显示的子类的索引
     */
    private int mChildIndex = 0;

    /**
     * 弹性滑动
     */
    private Scroller mScroller;
    /**
     * 速度跟踪器
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 记录上次滑动的坐标(onInterceptTouchEvent)
     */
    private int mLastInterceptedX = 0;
    private int mLastInterceptedY = 0;

    /**
     *  记录上次的滑动坐标
     */
    private int mLastX = 0;
    private int mLastY = 0;

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
        //设置当前的View是否执行onDraw()事件
        setWillNotDraw(false);
        if (attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalScrollViewEx);
            //获取自定义的属性


        }
        //初始化全局变量
        if (mScroller == null){
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }


    }

    /**
     * 阻拦事件,解决滑动冲突的事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptedX;
                int deltaY = y - mLastInterceptedY;
                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    intercepted = true;
                }else{
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastInterceptedX = x;
        mLastInterceptedY = y;
        mLastX = x;
        mLastY = y;
        //为true表示阻拦事件向下执行，为false不阻拦执行默认系统执行
        return intercepted;
    }

    /**
     * 当前控件的子类控件在左右滑动的时候实现平滑效果
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        //获取当前的触点的坐标
        int x = (int)event.getX();
        int y = (int)event.getY();
        Toast.makeText(getContext(), "x="+x+",y="+y, Toast.LENGTH_SHORT).show();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //如果上次的动画还没有完成，再次触发该事件时，先取消之前的动画
                if (!mScroller.isFinished()){
                    //取消动画
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int detalX = x-mLastX;
                int detalY = y-mLastY;
                scrollBy(-detalX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = mVelocityTracker.getXVelocity();
                if (Math.abs(velocityX)>=50){
                    mChildIndex = velocityX>0?mChildIndex-1:mChildIndex+1;
                }else{
                    mChildIndex = (scrollX + mChildWidth/2)/mChildWidth;
                }

                //循环滑动，有待优化
                if(isloop) {
                    if (mChildIndex < 0) {
                        mChildIndex = mChildSize - 1;
                    } else if (mChildIndex > mChildSize - 1) {
                        mChildIndex = 0;
                    }
                }
                //防止索引越界
                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildSize-1));
                int dx = mChildIndex*mChildWidth-scrollX;
                Log.e(TAG,"mChildIndex="+mChildIndex+";dx="+dx+";velocityX="+velocityX+"Math.abs(velocityX)>=50="+(Math.abs(velocityX)>=50));
                smoothScrollBy(dx,0);
                mVelocityTracker.clear();
                break;
            default:
                break;
        }
        mLastY = y;
        mLastX = x;
        return true;
    }

    /**
     * 平滑滑动
     * @param dx
     * @param dy
     */
    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(),0,dx,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
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
        mChildSize = childCount;
        //计算所有子类的宽/高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //当前控件的计算格式和剩余的空间大小
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        //当前控件的padding
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPadding = getPaddingTop();
        int bPadding = getPaddingBottom();

        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else if(widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST){
            //获取子类的最大高度和所有子类的宽度之和
            setMeasuredDimension(measureSumWidthChild()+lPadding+rPadding,measureMaxHeightChild()+tPadding+bPadding);
        }else if (widthMeasureMode == MeasureSpec.AT_MOST){
            //获取子类的宽度之和
            setMeasuredDimension(measureSumWidthChild()+lPadding+rPadding,heightMeasureSize);
        }else if (heightMeasureMode == MeasureSpec.AT_MOST){
            //获取子类的最大高度
            setMeasuredDimension(widthMeasureSize,measureMaxHeightChild()+tPadding+bPadding);
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
            if (chidView.getVisibility() != View.GONE) {
                mChildWidth = chidView.getMeasuredWidth();
                ViewGroup.MarginLayoutParams childParams = (ViewGroup.MarginLayoutParams) chidView.getLayoutParams();
                leftWidth += childParams.leftMargin;
                chidView.layout(leftWidth, tPadding + childParams.topMargin, leftWidth + chidView.getMeasuredWidth(), tPadding + childParams.topMargin + chidView.getMeasuredHeight());
                leftWidth += chidView.getMeasuredWidth();
            }
        }
    }

    /**
     * 当前View被移出或者对应的Activity退出时，执行该方法
     */
    @Override
    protected void onDetachedFromWindow() {
         mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Toast.makeText(getContext(),"onDraw",Toast.LENGTH_SHORT).show();
    }
}
