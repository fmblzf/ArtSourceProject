package com.fmblzf.animate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/3/22.
 *
 * 注：overridePendingTransition方式来实现Activity切换，模拟器上可以实现，但是在真机上不是所有的都能实现；
 *   所以慎用
 * 可以通过Theme样式来实现，设置对应的主题即可
 * 还可以通过Dragger第三方开源的库
 *
 *
 *
 *
 */
public class SwitchAnimateActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_layout);

        mButton = (Button) this.findViewById(R.id.switch_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SwitchAnimateActivity.this,SwitchAnimateActivity1.class);
        startActivity(intent);
        overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
