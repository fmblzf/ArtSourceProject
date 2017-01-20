package com.art.fmblzf.aidl.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.art.fmblzf.aidl.binderpool.impl.IComputeImpl;
import com.art.fmblzf.aidl.binderpool.impl.IEnCodingImpl;

/**
 * Created by Administrator on 2017/1/20.
 */
public class BinderPool {

    //第一个类的标识
    public static final int BINDER_ENCODING = 0;
    //第二个类的标识
    public static final int BINDER_COMPUTE = 1;


    //上下文内容
    private Context mContent;
    //线程池统一管理类
    private IBinderPool iBinderPool;


    //单例模式-当前实例
    private static BinderPool instance = null;
    /**
     * 获取当前实例
     * @param context
     * @return
     */
    public static BinderPool getInstance(Context context){
        if (instance == null){
            synchronized (BinderPool.class){
                instance = new BinderPool(context);
            }
        }
        return instance;
    }

    //构造器
    private BinderPool(Context context) {
        mContent = context;
    }

    /**
     *
     * 实现线程池的同一管理
     * 根据不同的binderCode获取不同的Binder
     *
     */
    public static class BinderPoolImpl extends IBinderPool.Stub{

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case BINDER_ENCODING:
                    binder = new IEnCodingImpl();
                    break;
                case BINDER_COMPUTE:
                    binder = new IComputeImpl();
                    break;
                default:break;
            }
            return binder;
        }
    }

    /**
     *
     * 后台服务连接类监听类
     *
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBinderPool = IBinderPool.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
