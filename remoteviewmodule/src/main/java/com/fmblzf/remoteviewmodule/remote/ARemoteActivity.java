package com.fmblzf.remoteviewmodule.remote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.fmblzf.remoteviewmodule.MainActivity;
import com.fmblzf.remoteviewmodule.R;
import com.fmblzf.remoteviewmodule.utils.MyConstant;

/**
 * Created by Administrator on 2017/3/10.
 */
public class ARemoteActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mLinearLayout;
    private Button mButton1;
    private Button mButton2;

    /**
     * 定义的广播
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == MyConstant.BROADCAST_ACTION){
                updateUI(intent);
            }
        }
    };

    /**
     *  更新UI显示
     */
    private void updateUI(Intent intent) {
        RemoteViews remoteViews = intent.getParcelableExtra(MyConstant.INTENT_TAG);
        View view = remoteViews.apply(this,mLinearLayout);
        mLinearLayout.addView(view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_remote_activity_layout);
        initView();
    }

    private void initView() {

        //注册广播
        IntentFilter intentFilter = new IntentFilter(MyConstant.BROADCAST_ACTION);
        registerReceiver(mBroadcastReceiver,intentFilter);

        mLinearLayout = (LinearLayout) findViewById(R.id.ll_cc);
        mButton1 = (Button) findViewById(R.id.button_notification);
        mButton1.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button_remoteview);
        mButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_notification:
                startActivity(new Intent(ARemoteActivity.this, MainActivity.class));
                break;
            case R.id.button_remoteview:
                startActivity(new Intent(ARemoteActivity.this,BRemoteActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
