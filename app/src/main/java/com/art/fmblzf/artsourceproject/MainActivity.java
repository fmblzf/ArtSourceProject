package com.art.fmblzf.artsourceproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.art.fmblzf.aidl.aidl.IBookManager;
import com.art.fmblzf.aidl.aidl.IOnNewBookArrivedListener;
import com.art.fmblzf.aidl.module.Book;
import com.art.fmblzf.aidl.module.User;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        interAppProvider();
        //跨以应用绑定service
        Intent intent = new Intent();
        intent.setAction("android.intent.action.ACCESS_BOOK_SERVICE");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }
    /**
     * 跨App共享provider
     */
    public void interAppProvider(){
        Uri uri = Uri.parse("content://com.art.fmblzf.aidl.provider.book.provider/book");
//        ContentValues values = new ContentValues();
//        values.put("_id",6);
//        values.put("name","程序设计");
//        getContentResolver().insert(uri,values);
        Cursor bookCursor = getContentResolver().query(uri,new String[]{"_id","name"},null,null,null);
        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG,"query book:"+book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.art.fmblzf.aidl.provider.book.provider/user");
        Cursor userCursor = getContentResolver().query(userUri,new String[]{"_id","name","sex"},null,null,null);
        while (userCursor.moveToNext()){
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.sex = userCursor.getInt(2);
            Log.d(TAG,"query user: "+user.toString());
        }
        userCursor.close();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    /**
     *
     *  AIDL接口都是运行在Binder线程池中的，所以是不能直接调用UI的，只能通过handler来处理
     *
     *
     */
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){

        @Override
        public void onNewArrived(Book newbook) throws RemoteException {
            Log.i(TAG,"onNewArrived new book : "+newbook.toString());
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected " +name.toString());
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                iBookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
//            try {
//                service.linkToDeath(deathRecipient,0);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            try {
                List<Book> result = iBookManager.getBookList();
                Log.d(TAG,"服务端返回的List的具体实现类型:"+result.getClass().getCanonicalName());//由此可以证明CopyOnWriteArrayList在IBinder中生成了一个新的ArrayList返回给了客户端
                Toast.makeText(MainActivity.this,result.toString(),Toast.LENGTH_LONG).show();
                //添加一本书
                Book book = new Book(3,"测试");
                iBookManager.addBook(book);

                //再次请求所有的数据集合
                List<Book> newList = iBookManager.getBookList();
                Toast.makeText(MainActivity.this,newList.toString(),Toast.LENGTH_LONG).show();

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
