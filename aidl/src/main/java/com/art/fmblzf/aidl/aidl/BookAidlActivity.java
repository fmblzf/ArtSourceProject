package com.art.fmblzf.aidl.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.art.fmblzf.aidl.R;
import com.art.fmblzf.aidl.module.Book;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/1/16.
 */
public class BookAidlActivity extends AppCompatActivity {

    private static final String TAG = "BookAidlActivity";

    private IBookManager iBookManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 2000) {
                Object newbook = msg.obj;
                Toast.makeText(getBaseContext(), newbook.toString(), Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                iBookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                List<Book> result = iBookManager.getBookList();
                Log.d(TAG,"服务端返回的List的具体实现类型:"+result.getClass().getCanonicalName());//由此可以证明CopyOnWriteArrayList在IBinder中生成了一个新的ArrayList返回给了客户端
                Toast.makeText(BookAidlActivity.this,result.toString(),Toast.LENGTH_LONG).show();
                //添加一本书
                Book book = new Book(3,"测试");
                iBookManager.addBook(book);

                //再次请求所有的数据集合
                List<Book> newList = iBookManager.getBookList();
                Toast.makeText(BookAidlActivity.this,newList.toString(),Toast.LENGTH_LONG).show();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBookManager = null;
        }
    };

    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewArrived(Book newbook) throws RemoteException {
            mHandler.obtainMessage(2000,newbook).sendToTarget();
        }
    };

    public void onListClick(View button){
            Log.i(TAG,"onClick");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Book> list = iBookManager.getBookList();
                        Log.i(TAG,"list=="+list.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService(new Intent(this,BookServiceAigl.class),serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (iBookManager != null && iBookManager.asBinder().isBinderAlive()){
            try {
                iBookManager.unregisterListner(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
