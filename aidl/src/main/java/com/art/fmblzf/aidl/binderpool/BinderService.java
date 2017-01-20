package com.art.fmblzf.aidl.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/1/20.
 */
public class BinderService extends Service {

    private static final String TAG = "BinderService";

    private IBinder mBinder = new BinderPool.BinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
