package com.art.fmblzf.aidl.binderpool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.art.fmblzf.aidl.R;
import com.art.fmblzf.aidl.binderpool.impl.IComputeImpl;
import com.art.fmblzf.aidl.binderpool.impl.IEnCodingImpl;

/**
 * Created by Administrator on 2017/1/20.
 */
public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new WorkTask()).start();
    }

    /**
     *  任务类
     */
    private class WorkTask implements Runnable{

        @Override
        public void run() {
            doWork();
        }
    }

    /**
     *  具体的工作
     */
    private void doWork(){
        BinderPool binderPool = BinderPool.getInstance(getApplicationContext());
        try {
            IBinder iBinder = binderPool.queryBinder(BinderPool.BINDER_ENCODING);
            IEnCoding iEnCoding = IEnCodingImpl.asInterface(iBinder);
            String decrypt = iEnCoding.decrypt("");
            Log.i(TAG,"queryBinder:"+decrypt);
            String encrypt = iEnCoding.encrypt("");
            Log.i(TAG,"queryBinder:"+encrypt);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
            ICompute iCompute = IComputeImpl.asInterface(computeBinder);
            int num = iCompute.add(1,2);
            Log.i(TAG,"queryBinder:"+String.valueOf(num));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            IBinder binder_0 = binderPool.queryBinderByClass(IEnCodingImpl.class.getName());
            IEnCoding iEnCoding = IEnCodingImpl.asInterface(binder_0);
            String decrypt = iEnCoding.decrypt("");
            Log.i(TAG,"queryBinderByClass:"+decrypt);
            String encrypt = iEnCoding.encrypt("");
            Log.i(TAG,"queryBinderByClass:"+encrypt);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            IBinder binder_1 = binderPool.queryBinderByClass(IComputeImpl.class.getName());
            ICompute iCompute = IComputeImpl.asInterface(binder_1);
            int num = iCompute.add(1,2);
            Log.i(TAG,"queryBinderByClass:"+String.valueOf(num));
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
