package com.fmblzf.drawablemodule;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private int levelImageViewClickCount = 0;

    private TextView mTextView ;
    private TextView mScaleTextView;

    private ImageView mClipImageView;

    private TextView mCustomTextView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ScaleDrawable scaleDrawable = (ScaleDrawable) mScaleTextView.getBackground();
            scaleDrawable.setLevel(1);
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) this.findViewById(R.id.level_imageview);
        mImageView.setOnClickListener(this);

        mTextView = (TextView) this.findViewById(R.id.transition_textview);
        mTextView.setOnClickListener(this);

        mScaleTextView = (TextView) this.findViewById(R.id.scale_textview);
//        ScaleDrawable scaleDrawable = (ScaleDrawable) mScaleTextView.getBackground();
//        scaleDrawable.setLevel(1);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1000);
            }
        }.start();

        mClipImageView = (ImageView) this.findViewById(R.id.clip_imageview);
        ClipDrawable clipDrawable = (ClipDrawable) mClipImageView.getDrawable();
        clipDrawable.setLevel(5000);

        mCustomTextView = (TextView) this.findViewById(R.id.custom_textview);
        mCustomTextView.setBackground(new CustomDrawable(Color.BLUE));

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.level_imageview:
                levelImageViewClickCount ++;
                mImageView.setImageLevel(levelImageViewClickCount%2);
                break;
            case R.id.transition_textview:
                TransitionDrawable td = (TransitionDrawable) mTextView.getBackground();
                td.startTransition(1000);
                break;
            default:
                break;
        }
    }
}
