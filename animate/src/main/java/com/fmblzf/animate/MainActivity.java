package com.fmblzf.animate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.fmblzf.animate.attr.AttrAnimateActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private Button mAlphaButton ;
    private Button mCustomButton;
    private Button mFrameButton;
    private Button mViewGroupAnimateButton;
    private Button mSwitchAnimateButton;
    private Button mAttrAnimateButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) this.findViewById(R.id.button1);
        mButton.setOnClickListener(this);

        mAlphaButton = (Button) this.findViewById(R.id.button2);
        mAlphaButton.setOnClickListener(this);

        mCustomButton = (Button) this.findViewById(R.id.button3);
        mCustomButton.setOnClickListener(this);
        mCustomButton.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0,360,mCustomButton.getWidth()/2,mCustomButton.getHeight()/2,100,true);
//                rotate3dAnimation.setDuration(10000);
//                mCustomButton.startAnimation(rotate3dAnimation);
            }
        },0L);


        mFrameButton = (Button) this.findViewById(R.id.button4);
        mFrameButton.setOnClickListener(this);

        mViewGroupAnimateButton = (Button) this.findViewById(R.id.button5);
        mViewGroupAnimateButton.setOnClickListener(this);

        mSwitchAnimateButton = (Button) this.findViewById(R.id.button6);
        mSwitchAnimateButton.setOnClickListener(this);

        mAttrAnimateButton = (Button) this.findViewById(R.id.button7);
        mAttrAnimateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button1:
                Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_resource_0);
                mButton.startAnimation(animation);
                break;
            case R.id.button2:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                alphaAnimation.setDuration(500);
                mAlphaButton.startAnimation(alphaAnimation);
                break;
            case R.id.button3:
                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0,360,mCustomButton.getWidth()/2,mCustomButton.getHeight()/2,-1000,true);
                rotate3dAnimation.setDuration(5000);
                mCustomButton.startAnimation(rotate3dAnimation);
                break;
            case R.id.button4:
                Intent  intent = new Intent(MainActivity.this,FrameAnimationActivity.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                break;
            case R.id.button5:
                Intent intent1 = new Intent(MainActivity.this,ViewGroupAnimateActivity.class);
                startActivity(intent1);
                break;
            case R.id.button6:
                Intent intent2 = new Intent(MainActivity.this,SwitchAnimateActivity.class);
                startActivity(intent2);
                break;
            case R.id.button7:
                Intent intent3 = new Intent(MainActivity.this, AttrAnimateActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
