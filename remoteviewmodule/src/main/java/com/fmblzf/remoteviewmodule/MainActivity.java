package com.fmblzf.remoteviewmodule;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * 通知消息的唯一主键
     */
    private final static int NOTIFICATION_NOTIFY_ID = 10;
    private final static int NOTIFICATION_NOTIFY_ID_1 = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建并显示通知消息
        createNotification();
        createNotificationByRemoteView();
    }

    /**
     * 创建通知栏
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void createNotification(){
        //创建Builder
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("hello world!")
            .setContentTitle("study")
            .setTicker("Hi")
            .setDefaults(Notification.DEFAULT_ALL);
        //创建PendingIntent
        Bundle bundle = new Bundle();
        bundle.putString("STRING","测试789");
        Intent intent = new Intent(this,NotificationActivity.class);
//        intent.putExtras(bundle);
        intent.putExtra("NOTIFICATION",bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,10000,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //关联builder和pendingIntent
        builder.setContentIntent(pendingIntent);
        //创建Manger
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建Notification
        Notification notification = null;
        //根据版本获取对应的Notification
        if (Build.VERSION.SDK_INT>15) {
            notification = builder.build();
        }else{
            notification = builder.getNotification();
        }
        //设置点击状态栏后自动清除通知栏信息
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        //发送通知
        manager.notify(NOTIFICATION_NOTIFY_ID, notification);
    }

    /**
     * 创建自定义通知栏
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void createNotificationByRemoteView(){
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher).setTicker("更新提示！").setDefaults(Notification.DEFAULT_ALL);
        //创建PendingIntent
        Bundle bundle = new Bundle();
        bundle.putString("STRING","测试123");
        Intent intent = new Intent(this,NotificationActivity.class);
        intent.putExtra("NOTIFICATION",bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,10001,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //关联Builder和PendingIntent
//        builder.setContentIntent(pendingIntent);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.remoteview_style_layout);
        remoteViews.setTextViewText(R.id.remote_view_tv_title,"有新版本需要更新");
        remoteViews.setTextViewText(R.id.remote_view_tv_content,"更新内容更新内容更新内容更新内容更新内容");
//        remoteViews.setOnClickPendingIntent(R.id.rl,pendingIntent);
//        builder.setCustomContentView(remoteViews);

        //创建Notification
        Notification notification = null;
        //根据版本获取对应的Notification
        if (Build.VERSION.SDK_INT>15) {
            notification = builder.build();
        }else{
            notification = builder.getNotification();
        }
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        notification.when = System.currentTimeMillis()+1000;

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_NOTIFY_ID_1,notification);
        
    }

}

