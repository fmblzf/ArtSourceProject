// IBookManager.aidl
package com.art.fmblzf.aidl.aidl;

// Declare any non-default types here with import statements

import com.art.fmblzf.aidl.module.Book;
import com.art.fmblzf.aidl.aidl.IOnNewBookArrivedListener;

interface IBookManager {

    /**
     *
     *  获取图书列表
     *
    */
    List<Book> getBookList();
    /**
    *
     * 添加图书
    *
    */
    void addBook(in Book book);
    /**
    *
    * 注册监听事件
    */
    void registerListener(IOnNewBookArrivedListener listener);
    /**
        *
        * 取消监听事件
        */
    void unregisterListner(IOnNewBookArrivedListener listener);


    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
