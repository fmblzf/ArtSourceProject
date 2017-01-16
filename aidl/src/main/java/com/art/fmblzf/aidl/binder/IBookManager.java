package com.art.fmblzf.aidl.binder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.art.fmblzf.aidl.book.Book;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public interface IBookManager extends IInterface {
    //定义当前接口的唯一标示
    static final String DESCRIPTOR = IBookManager.class.getName();
    /**
     * 当前自定义接口getBookList的唯一标示
     */
    static final int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    /**
     * 当前自定义接口addBook的唯一标示
     */
    static final int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    /**
     * 接口获取图书集合
     * @return
     * @throws RemoteException
     */
    public List<Book> getBookList() throws RemoteException;

    /**
     * 添加新图书到集合中去
     * @param book
     * @throws RemoteException
     */
    public void addBook(Book book) throws RemoteException;
}
