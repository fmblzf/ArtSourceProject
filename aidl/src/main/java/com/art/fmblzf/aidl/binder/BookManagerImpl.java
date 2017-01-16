package com.art.fmblzf.aidl.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.art.fmblzf.aidl.book.Book;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class BookManagerImpl extends Binder implements IBookManager {
    /**
     * 构造器中实现当前类连接到指定的接口中
     */
    public BookManagerImpl() {
        this.attachInterface(this,DESCRIPTOR);
    }

    /**
     * 将一个Ibinder对象转化成IBookManager接口类型，
     * 如果是跨进程交互的时，需要创建一个Proxy代理类，
     *
     * @param obj
     * @return
     */
    public static IBookManager asInterface(IBinder obj){
        if (obj == null){
            return null;
        }
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if ((iin != null) && (iin instanceof IBookManager)){
            return (IBookManager)iin;
        }
        return new BookManagerImpl.Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_getBookList:
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSACTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book book;
                if (0 != data.readInt()){
                    book = Book.CREATOR.createFromParcel(data);
                }else{
                    book = null;
                }
                this.addBook(book);
                reply.writeNoException();
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {

    }

    /**
     *
     * 如果是跨进程操作时，
     * 该类作用于客户端，
     * 通过Binder获得服务端的数据和服务，进而得到数据
     * 由此可以看出Binder是作用于不同进程或者同一进程客户端和服务端的有效桥梁
     *
     */
    private static class Proxy implements IBookManager{

        private IBinder mRemote;

        public Proxy(IBinder remote) {
            this.mRemote = remote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        /**
         * 获取接口的唯一标示
         * @return
         */
        public String getInterfaceDescriptor(){
            return DESCRIPTOR;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Book> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                reply.readException();
                result = reply.createTypedArrayList(Book.CREATOR);
            }finally {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try{
                data.writeInterfaceToken(DESCRIPTOR);
                if ((book != null)){
                    data.writeInt(1);
                    book.writeToParcel(data,0);
                }else{
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook,data,reply,0);
                reply.readException();
            }finally {
                reply.recycle();
                data.recycle();
            }
        }
    }
}
