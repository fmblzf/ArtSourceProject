package com.fmblzf.animate;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/3/20.
 */
public class FrameAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private View mView;

    private Button mButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_animation_layout);

        mView = this.findViewById(R.id.frame_view);
        mButton = (Button) this.findViewById(R.id.frame_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.frame_button:
                AnimationDrawable drawable = (AnimationDrawable) mView.getBackground();
                drawable.start();
                break;
            default:
                break;
        }
    }
}
