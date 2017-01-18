package com.art.fmblzf.aidl.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.art.fmblzf.aidl.R;
import com.art.fmblzf.aidl.module.Book;
import com.art.fmblzf.aidl.module.User;

/**
 * Created by Administrator on 2017/1/18.
 */
public class BookProviderActivity extends AppCompatActivity {

    private static final String TAG = "BookProviderActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //"com.art.fmblzf.aidl.provider.book.provider" 是对应Provider的android:authorities属性值
        Uri uri = Uri.parse("content://com.art.fmblzf.aidl.provider.book.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name","程序设计");
        getContentResolver().insert(uri,values);
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
}
