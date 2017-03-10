package com.fmblzf.remoteviewmodule.remote;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.fmblzf.remoteviewmodule.NotificationActivity;
import com.fmblzf.remoteviewmodule.R;
import com.fmblzf.remoteviewmodule.utils.MyConstant;

/**
 * Created by Administrator on 2017/3/10.
 */
public class BRemoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_remote_activity_layout);
    }

    public void buttonClick(View v){
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.remoteview_style_layout);
        remoteViews.setTextViewText(R.id.remote_view_tv_title,"模拟消息发送");
        remoteViews.setTextViewText(R.id.remote_view_tv_content,"MSG from process:"+ Process.myPid());
        remoteViews.setImageViewResource(R.id.remote_iamgeview,R.mipmap.icon1);
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent2 = new Intent(this, BRemoteActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.remote_iamgeview,pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.remote_view_tv_title,pendingIntent2);
        Intent actionIntent = new Intent();
        actionIntent.putExtra(MyConstant.INTENT_TAG,remoteViews);
        actionIntent.setAction(MyConstant.BROADCAST_ACTION);
        sendBroadcast(actionIntent);
    }
}
