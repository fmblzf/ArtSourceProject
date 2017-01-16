package com.art.fmblzf.aidl.book;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/1/12.
 */
public class Book implements Parcelable {

    public int bookId;
    public String bookName;

    public Book(int bookId,String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }

    private Book(Parcel in) {
        this.bookId = in.readInt();
        this.bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }
}
