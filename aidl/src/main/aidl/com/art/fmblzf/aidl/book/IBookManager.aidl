// IBookManager.aidl
package com.art.fmblzf.aidl.book;

// Declare any non-default types here with import statements

import com.art.fmblzf.aidl.book.Book;

interface IBookManager {

    /**
    * 获取图书列表页数据
    */
    List<Book> getBookList();
    /**
    * 添加新图书到集合中去
    */
    void addBook(in Book book);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
