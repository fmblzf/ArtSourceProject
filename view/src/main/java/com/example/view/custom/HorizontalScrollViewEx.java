package com.example.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/1/24.
 */
public class HorizontalScrollViewEx extends ViewGroup {

    private static final String TAG = "HorizontalScrollViewEx";


    private LinearLayout mLinearLayout;
    private Context mContext;


    Scroller mScroller = null;

    public HorizontalScrollViewEx(Context context) {
        this(context,null);
        Log.e(TAG,"HorizontalScrollViewEx(Context context)");
    }

    /**
     * XML配置的构造器
     * @param context
     * @param attrs
     */
    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(TAG,"HorizontalScrollViewEx(Context context, AttributeSet attrs)");
        this.mContext = context;
        mLinearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayout.setLayoutParams(params);
        this.addView(mLinearLayout);
        mScroller = new Scroller(mContext);
    }

    /**
     * 缓慢滑动到指定坐标位置
     * @param destX
     * @param destY
     */
    private void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        int scrollY = getScrollY();
        int deltaY = destY - scrollY;
        //1000ms滑动到指定的destX,destY位置
        mScroller.startScroll(scrollX,scrollY,deltaX,deltaY,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * 添加子控件
     */
    public void addSubView(View view){
        mLinearLayout.addView(view);
    }

    /**
     * 获取当前的容器
     * @return
     */
    public ViewGroup getContainer(){
        return mLinearLayout;
    }

}
