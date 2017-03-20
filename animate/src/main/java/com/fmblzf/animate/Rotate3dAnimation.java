package com.fmblzf.animate;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2017/3/20.
 */
public class Rotate3dAnimation extends Animation {

    //旋转开始的角度
    private float mFromDegrees;
    //旋转结束的角度
    private float mToDegrees;
    //旋转的轴点x坐标
    private float mCenterX;
    //旋转的轴点y坐标
    private float mCenterY;
    //旋转轴点的z坐标
    private float mDepthZ;
    //是否反转
    private boolean mReverse;
    //照相机
    private Camera mCamera;


    public Rotate3dAnimation(float fromDegrees,float toDegreees,float centerX,float centerY,float depthZ,boolean reverse) {
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegreees;
        this.mCenterX = centerX;
        this.mCenterY = centerY;
        this.mDepthZ = depthZ;
        this.mReverse = reverse;
    }

    /**
     * 初始化操作，自定义动画，需要实现的方法
     * @param width
     * @param height
     * @param parentWidth
     * @param parentHeight
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    /**
     * 动画操作，主要实现通过矩阵变化来实现，自定义动画，需要实现的方法
     * @param interpolatedTime
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees-fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;

        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        //保存状态，和camera.restore成对使用
        camera.save();

        //将当前的View在z轴上移动根据不同情况移动
        if (mReverse){
            camera.translate(0.0f,0.0f,mDepthZ*interpolatedTime);
        }else{
            camera.translate(0.0f,0.0f,mDepthZ*(1.0f-interpolatedTime));
        }
        //rotateX:围绕x轴方向旋转，rotateY:围绕y轴方向旋转
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        //如果有状态变化的，恢复保存的状态
        camera.restore();

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
    }
}
