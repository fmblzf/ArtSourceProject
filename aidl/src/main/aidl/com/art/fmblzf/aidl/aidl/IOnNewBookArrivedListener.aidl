// IOnNewBookArrivedListener.aidl
package com.art.fmblzf.aidl.aidl;

// Declare any non-default types here with import statements

import com.art.fmblzf.aidl.module.Book;

interface IOnNewBookArrivedListener {
    void onNewArrived(in Book newbook);
}
