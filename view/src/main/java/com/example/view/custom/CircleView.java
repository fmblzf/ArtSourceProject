package com.example.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.view.R;


/**
 * Created by zhaofeng on 2017/3/1.
 */
public class CircleView extends View {

    private int mWidth = 200;
    private int mHeight = 200;

    private Context mContext ;

    private int mColor = Color.RED;
    private Paint mPaint = new Paint();

    public CircleView(Context context) {
        super(context);
        this.mContext = context;
        initWidget(null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initWidget(attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initWidget(attrs);
    }

    /**
     *  初始化组件
     */
    private void initWidget(AttributeSet attrs) {
        if (attrs != null) {
            //读取自定义属性
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleView);
            mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        }
        //设置颜色
        mPaint.setColor(mColor);
        //ANTI_ALIAS_FLAG 绘制时，抗锯齿
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //设置当前的View支持wrap_content
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,mHeight);
        }else if(widthSpecMode == MeasureSpec.AT_MOST ){
            setMeasuredDimension(mWidth,heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置当前的View支持padding
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPassing = getPaddingTop();
        int bPadding = getPaddingBottom();

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width-lPadding-rPadding,height-tPassing-bPadding)/2;
        if (radius<0){
            return;
        }
        canvas.drawCircle(width/2,height/2,radius,mPaint);
    }

    /**
     * 当包含此View的Activity启动时，该方法会被调用
     * 可以做些逻辑处理，比如启动线程或者动画
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 当包含该View的Activity退出，或者该View被remove时，也会触发该方法
     * 可以关闭线程或者动画
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
