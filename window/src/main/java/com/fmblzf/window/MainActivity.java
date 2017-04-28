package com.fmblzf.window;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";


    private Button mFloatWindowButton;

    private WindowManager.LayoutParams mLayoutParams;

    private WindowManager mWindowManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWindowFloatButton();
    }

    /**
     *  创建悬浮按钮，给window上添加按钮
     */
    private void createWindowFloatButton(){
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mFloatWindowButton = new Button(this);
        mFloatWindowButton.setText("悬浮按钮");
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,
                0,0, PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        //配置该属性，必须在AndroidManifest.xml配置相应的权限android.permission.SYSTEM_ALERT_WINDOW，否则会报错
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mWindowManager.addView(mFloatWindowButton,mLayoutParams);
        mFloatWindowButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (action){
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG,"ACTION_MOVE");
                        if (rawX>mLayoutParams.x+mFloatWindowButton.getWidth() || rawX<mLayoutParams.x || rawY<mLayoutParams.y || rawY > mLayoutParams.y+mFloatWindowButton.getHeight()){
                            mLayoutParams.x = rawX;
                            mLayoutParams.y = rawY;
                            mWindowManager.updateViewLayout(mFloatWindowButton, mLayoutParams);
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG,"ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG,"ACTION_UP");
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeViewImmediate(mFloatWindowButton);
    }
}
