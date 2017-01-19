package com.art.fmblzf.aidl.aidl;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.art.fmblzf.aidl.module.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Administrator on 2017/1/16.
 */
public class BookServiceAigl extends Service {

    private static final String TAG = "BookServiceAigl";

    /**
     * 能实现自动化同步的操作，支持多并发的集合
     */
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    /**
     * 注册的监听的集合,但是使用CopyOnWriteArrayList在进行注册和解注册是，由于IBinder机制使得在IInterface在传递过程中都重新创建了新的对象，所以在解注册时，
     * 就不能实现。所以用RemoteCallbackList代替CopyOnWriteArrayList
     */
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> listeners = new CopyOnWriteArrayList<>();//不适用
    /**
     * RemoteCallbackList内部自动同步，所以支持并发处理，不需要手动去编写同步操作；
     * RemoteCallbackList内部有ArrayMap<IBinder,Callback>缓存，虽然IInterface在Binder机制中传递时，都创建了新的对象，但是对应的Binder(asBinder)没有变化；
     * 所以在注册时直接放在ArrayMap即可，而在解注册时，通过方法asBinder获取当前IInterface的Binder,通过比较Binder的key值，达到找到对应的接口进而可以准确的删除；
     * 而且RemoteCallbackList内部封装有当前服务和客户端是否断开的判断，如果断开就会自动删除已经注册的接口；
     * 以上的操作都已经封装好了，只需要进行注册register和解注册unregister即可。
     */
    private RemoteCallbackList<IOnNewBookArrivedListener> mListeners = new RemoteCallbackList<>();
    /**
     *
     * 原子布尔值，变量值变化过程，保证操作的原子性
     *
     */
    private AtomicBoolean mAtomicBoolean = new AtomicBoolean(false);


    /**
     * 和服务器端交互的桥梁
     */
    private IBinder mBinder = new IBookManager.Stub(){

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //可以加上权限控制
            //根据调用者的包名进行权限调用
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length>0){
                packageName = packages[0];
            }
            Log.i(TAG,"packageName="+packageName+";Uid="+getCallingUid()+";Pid="+getCallingPid());
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            //IBinder会按照List接口的规范去访问CopyOnWriteArrayList，最终生成一个新的ArrayList传给客户端
            //所以在跨进程访问中可以使用CopyOnWriteArrayList，同理还有ConCurrentHashMap最终生成HashMap传给客户端
            //AIDL跨进程支持传递的数据类型：
            //1.基本数据类型，2.List(ArrayList),3.map(HashMap),4实现Paracelable接口的，5.AIDL接口 6.String 和 CharSequence
            //SystemClock.sleep(5000);
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            Boolean issuccess = mListeners.register(listener);
            if (issuccess){
                Log.i(TAG,"listener register success!");
            }else{
                Log.i(TAG,"listener had exist!");
            }
            Log.i(TAG," listeners.size=="+mListeners.getRegisteredCallbackCount());
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregisterListner(IOnNewBookArrivedListener listener) throws RemoteException {
            Boolean issuccess = mListeners.unregister(listener);
            if (issuccess){
                Log.i(TAG,"unregister is success!");
            }else{
                Log.i(TAG,"listener not register!");
            }
            Log.i(TAG," listeners.size=="+mListeners.getRegisteredCallbackCount());
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "android"));
        bookList.add(new Book(2, "IOS"));
        new Thread(new WorkService()).start();
    }

    @Override
    public void onDestroy() {
        mAtomicBoolean.set(true);
        super.onDestroy();
    }

    /**
     *  后台服务处理类
     */
    private class WorkService implements Runnable{

        @Override
        public void run() {
            while (!mAtomicBoolean.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = bookList.size()+1;
                Book book = new Book(bookId,"测试"+bookId);
                try {
                    onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通知处理事件
     * @param book
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void onNewBookArrived(Book book) throws RemoteException {
        bookList.add(book);
        int N = mListeners.beginBroadcast();
        for (int i = 0;i<N;i++){
            IOnNewBookArrivedListener listener = mListeners.getBroadcastItem(i);
            if (listener!=null) {
                listener.onNewArrived(book);
            }
        }
        mListeners.finishBroadcast();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (!checkPermission()){
            Log.e(TAG,"uses-permission not find , onBind fail!");
            return null;
        }
        return mBinder;
    }

    private boolean checkPermission(){
//        int checkIndex = checkCallingOrSelfPermission("com.art.fmblzf.aidl.permission.ACCESS_BOOK_SERVICE");
//        if (PackageManager.PERMISSION_DENIED == checkIndex){
//            return false;
//        }

        return true;
    }

}
