package com.example.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/1/24.
 */
public class HorizontalScrollViewEx extends HorizontalScrollView {

    private static final String TAG = "HorizontalScrollViewEx";


    private LinearLayout mLinearLayout;
    private Context mContext;


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
        super(context, attrs,0);
        Log.e(TAG,"HorizontalScrollViewEx(Context context, AttributeSet attrs)");
        this.mContext = context;
        mLinearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayout.setLayoutParams(params);
        this.addView(mLinearLayout);
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
