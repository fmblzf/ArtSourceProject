package com.fmblzf.drawablemodule;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/3/20.
 * 自定义Drawable
 */
public class CustomDrawable extends Drawable {

    private Paint mPaint;

    public CustomDrawable(int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterY();
        float radius = Math.min(cx,cy)/2;
        canvas.drawCircle(cx,cy,radius,mPaint);
    }

    /**
     *
     * getIntrinsicHeight和getIntrinsicWidth这两个方法
     * 当自定义的Drawable有固定大小时最好重写这两个方法，因为它会影响到View的wrap_content布局，
     * 比如自定义Drawable是绘制一张图片，那么这个Drawable的内部大小就可以选用图片的大小。
     *
     */

    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
