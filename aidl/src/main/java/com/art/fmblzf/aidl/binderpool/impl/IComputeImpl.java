package com.art.fmblzf.aidl.binderpool.impl;

import android.os.RemoteException;

import com.art.fmblzf.aidl.binderpool.ICompute;

/**
 * Created by Administrator on 2017/1/20.
 */
public class IComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
