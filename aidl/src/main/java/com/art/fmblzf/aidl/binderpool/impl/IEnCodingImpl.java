package com.art.fmblzf.aidl.binderpool.impl;

import android.os.RemoteException;

import com.art.fmblzf.aidl.binderpool.IEnCoding;

/**
 * Created by Administrator on 2017/1/20.
 */
public class IEnCodingImpl extends IEnCoding.Stub {
    @Override
    public String encrypt(String content) throws RemoteException {
        return "encrypt";
    }

    @Override
    public String decrypt(String content) throws RemoteException {
        return "decrypt";
    }
}
