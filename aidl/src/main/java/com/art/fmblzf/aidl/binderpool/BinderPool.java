package com.art.fmblzf.aidl.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.art.fmblzf.aidl.binderpool.impl.IComputeImpl;
import com.art.fmblzf.aidl.binderpool.impl.IEnCodingImpl;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/1/20.
 */
public class BinderPool{

    //第一个类的标识
    public static final int BINDER_ENCODING = 0;
    //第二个类的标识
    public static final int BINDER_COMPUTE = 1;


    //上下文内容
    private Context mContent;
    //线程池统一管理类
    private IBinderPool iBinderPool;

    //保证操作的原子性，异步操作或者回调需回调才知道成功的操作，并且要求保证操作成功时，添加该类就可以通过改变状态达到预期效果
    private CountDownLatch latch;


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
        connectService();
    }

    /**
     * 连接后台服务
     * 保证同步，而且保证服务连接成功之后才可以执行其他操作，所以就加入了CountDownLatch
     */
    private synchronized void connectService(){
        latch = new CountDownLatch(1);
        Intent intent = new Intent(mContent,BinderService.class);
        mContent.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        @Override
        public IBinder queryBinderByClass(String className) throws RemoteException {
            Class eClass = null;
            try {
                eClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (eClass != null){
                try {
                    return (IBinder) eClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * 根据编码标识查询对应的Binder实现类
     * @param binderCode
     * @return
     * @throws RemoteException
     */
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        if (iBinderPool != null){
            binder = iBinderPool.queryBinder(binderCode);
        }
        return binder;
    }

    /**
     * 根据类名查询对应的Binder实现类
     * @param className
     * @return
     * @throws RemoteException
     */
    public IBinder queryBinderByClass(String className) throws RemoteException {
        IBinder binder = null;
        if (iBinderPool != null){
            binder = iBinderPool.queryBinderByClass(className);
        }
        return binder;
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
            try {
                iBinderPool.asBinder().linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //断开连接触发事件，首先解除绑定，然后置空连接池，准备重新连接
            iBinderPool.asBinder().unlinkToDeath(deathRecipient,0);
            iBinderPool = null;
            latch = null;
            connectService();
        }
    };

}
