package com.fmblzf.remoteviewmodule.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.fmblzf.remoteviewmodule.R;

/**
 * Created by Administrator on 2017/3/8.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String TAG = "MyAppWidgetProvider";
    public static final String CLICK_ACTIONA = "com.fmblzf.remoteviewmodule.widget.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        //系统处理部分
        super.onReceive(context, intent);

        Log.i(TAG,"onReceive : action = "+intent.getAction());
        ////自定义处理部分
        if (intent.getAction().equals(CLICK_ACTIONA)){
            Toast.makeText(context,"click it",Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.timg);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int i=0;i<37;i++){
                        float degree = (i * 10)%360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.imageview1,rotateBitmap(context,bitmap,degree));
                        Intent intentClick = new Intent();
                        intentClick.setAction(CLICK_ACTIONA);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
                        remoteViews.setOnClickPendingIntent(R.id.imageview1,pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.i(TAG,"onUpdate");
        final int counter = appWidgetIds.length;
        Log.i(TAG,"counter== "+counter);
        for (int i = 0;i<counter;i++){
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context,appWidgetManager,appWidgetId);
        }
    }

    /**
     * 桌面小部件更新
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG,"appWidgetId = "+appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);

        //“桌面小部件”单击事件大宋的Intent广播
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTIONA);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
        remoteViews.setOnClickPendingIntent(R.id.imageview1,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }

    private Bitmap rotateBitmap(Context context,Bitmap bitmap,float degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return bitmap1;
    }
}
