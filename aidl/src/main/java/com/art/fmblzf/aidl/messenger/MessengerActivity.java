package com.art.fmblzf.aidl.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.art.fmblzf.aidl.R;

/**
 * Created by Administrator on 2017/1/13.
 */
public class MessengerActivity extends AppCompatActivity {

    //客户端接受消息处理的Messenger
    private Messenger clientAcceptMessenger;
    //客户端发送消息处理的Messenger
    private Messenger clientSendMessenger;

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if(what == 1001){
                Bundle data = msg.getData();
                String message = data.getString("message");
                Log.e(MessengerActivity.class.getName(),message);
                Toast.makeText(MessengerActivity.this,message,Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            clientAcceptMessenger = new Messenger(myHandler);
            clientSendMessenger = new Messenger(service);
            Message message = Message.obtain();
            //将客户端的接受服务端的消息的处理Messenger传递到服务端
            message.replyTo = clientAcceptMessenger;
            message.what = 1000;
            Bundle data = new Bundle();
            data.putString("message","Hello! test client request!");
            message.setData(data);
            try {
                clientSendMessenger.send(message);
            }catch (RemoteException e){
                Log.e(MessengerActivity.class.getName(),e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,MyMessengerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
