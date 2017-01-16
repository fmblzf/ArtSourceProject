package com.art.fmblzf.aidl.user;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public interface IUserManager extends IInterface {

    //接口唯一标识
    public static final String DECRIPTOR = "com.art.fmblzf.aidl.user.IUserManager";

    public static final int TRANSACTION_saveUser = IBinder.FIRST_CALL_TRANSACTION + 0;

    public static final int TRANSACTION_deleteUser = IBinder.FIRST_CALL_TRANSACTION + 1;

    public static final int TRANSACTION_getUserList = IBinder.FIRST_CALL_TRANSACTION + 2;

    public void saveUser(User user) throws RemoteException;

    public void deleteUser(User user)throws RemoteException;

    public List<User> getUserList() throws RemoteException;

}
