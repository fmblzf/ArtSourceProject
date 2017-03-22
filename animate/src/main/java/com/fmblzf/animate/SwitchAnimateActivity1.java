package com.fmblzf.animate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/3/22.
 */
public class SwitchAnimateActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_layout1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
    }
}
