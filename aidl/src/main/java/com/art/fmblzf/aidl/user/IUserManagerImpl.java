package com.art.fmblzf.aidl.user;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class IUserManagerImpl extends Binder implements IUserManager {

    public IUserManagerImpl() {
        this.attachInterface(this,DECRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    public static IUserManager asInterface(IBinder obj){
        if (obj == null){
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DECRIPTOR);
        if (iin!=null && iin instanceof IUserManager){
            return (IUserManager)iin;
        }
        //上面的条件都不满足时，就可以说明是跨进程操作，所以需要将客户端的数据参数服务端的返回值封装成Parcle再操作；
        return new IUserManagerImpl.Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DECRIPTOR);
                return true;
            case TRANSACTION_deleteUser:
                data.enforceInterface(DECRIPTOR);
                User user;
                if (data.readInt()!=0){
                    user = User.CREATOR.createFromParcel(data);
                }else{
                    user = null;
                }
                this.deleteUser(user);
                reply.writeNoException();
                return true;
            case TRANSACTION_saveUser:
                data.enforceInterface(DECRIPTOR);
                User saveU;
                if (data.readInt() != 0){
                    saveU = User.CREATOR.createFromParcel(data);
                }else{
                    saveU = null;
                }
                this.saveUser(saveU);
                reply.writeNoException();
                return true;
            case TRANSACTION_getUserList:
                data.enforceInterface(DECRIPTOR);
                List<User> list = this.getUserList();
                reply.writeNoException();
                reply.writeTypedList(list);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    private static class Proxy implements IUserManager{

        //服务端和客户端的桥梁
        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescriptor(){
            return DECRIPTOR;
        }

        @Override
        public void saveUser(User user) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try{
                data.writeInterfaceToken(DECRIPTOR);
                if(user!=null){
                    data.writeInt(1);
                    user.writeToParcel(data,0);
                }else{
                    data.writeInt(0);
                }
                //触发服务端onTransact方法
                mRemote.transact(TRANSACTION_saveUser,data,reply,0);
                reply.readException();
            }finally {
                reply.recycle();
                data.recycle();
            }
        }

        @Override
        public void deleteUser(User user) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try{
                data.writeInterfaceToken(DECRIPTOR);
                if(user!=null){
                    data.writeInt(1);
                    user.writeToParcel(data,0);
                }else{
                    data.writeInt(0);
                }
                //触发服务端onTransact方法
                mRemote.transact(TRANSACTION_deleteUser,data,reply,0);
                reply.readException();
            }finally {
                reply.recycle();
                data.recycle();
            }
        }

        @Override
        public List<User> getUserList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<User> list;
            try {
                data.writeInterfaceToken(DECRIPTOR);
                mRemote.transact(TRANSACTION_getUserList,data,reply,0);
                reply.readException();
                list = reply.createTypedArrayList(User.CREATOR);
            }finally {
                reply.recycle();
                data.recycle();
            }
            return list;
        }

    }

    @Override
    public void saveUser(User user) throws RemoteException {

    }

    @Override
    public void deleteUser(User user) throws RemoteException {

    }

    @Override
    public List<User> getUserList() throws RemoteException {
        return null;
    }
}
