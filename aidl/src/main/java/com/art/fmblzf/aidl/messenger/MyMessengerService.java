package com.art.fmblzf.aidl.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/13.
 * Messenger步骤：
 * 1.创建服务端Service
 * 2.创建Handler
 * 3.创建Messenger对象
 *
 */
public class MyMessengerService extends Service {

    private Handler myHandler;
    //这个只是接受客户端发来请求的处理Messenger
    private Messenger serviceAcceptMessenger;
    //这个只是通过服务器向客户端发送消息的Messenger
    private Messenger serviceSendMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 1000){
                    Bundle data = msg.getData();
                    Log.e(MyMessengerService.class.getName(),data.getString("message"));

                    String _message = data.getString("message");
                    Log.e(MessengerActivity.class.getName(),_message);
                    Toast.makeText(getBaseContext(),_message,Toast.LENGTH_LONG).show();

                    //回复客户端消息，replyTo是客户端传递过来的信使
                    serviceSendMessenger = msg.replyTo;
                    Message message = Message.obtain();
                    message.what = 1001;
                    Bundle _data = new Bundle();
                    _data.putString("message","Thank you client!");
                    message.setData(_data);
                    try{
                        serviceSendMessenger.send(message);
                    }catch (RemoteException e){
                        Log.e(MyMessengerService.class.getName(),e.getMessage());
                    }
                }
                super.handleMessage(msg);
            }
        };
        serviceAcceptMessenger = new Messenger(myHandler);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (serviceAcceptMessenger != null){
            return serviceAcceptMessenger.getBinder();
        }
        return null;
    }
}
