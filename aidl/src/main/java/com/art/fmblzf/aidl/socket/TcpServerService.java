package com.art.fmblzf.aidl.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Administrator on 2017/1/19.
 */
public class TcpServerService extends Service {

    private static final String TAG = "TcpServerService";

    private  String[] DEFAULT_MESSAGE = new String[]{
            "你好啊！哈哈哈",
            "你叫什么名字？",
            "今天天气真好！",
            "你好！有事稍后联系你！"
    };

    //标记当前服务是否已经销毁
    private static boolean mIsDestroy = false;

    private ServerSocket serverSocket = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //启动Tcp服务器
        new Thread(new WorkServer()).start();
    }

    @Override
    public void onDestroy() {
        mIsDestroy = true;
        if (serverSocket!=null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 服务器回复客户端的请求
     * @param socket
     */
    private void responeClient(Socket socket)throws IOException{
        //接受客户端的数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //向客户端发送消息
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        //writer.println("欢迎来到聊天室");
        while (!mIsDestroy){
            //服务尚未断开
            //获取客户端的消息
            String message = reader.readLine();
            if (message == null){
                Log.i(TAG,"客户端断开连接！");
                return ;
            }
            Log.i(TAG,"message from client:"+message);
            //向客户端发送消息
            int index = new Random().nextInt(DEFAULT_MESSAGE.length);
            writer.println(DEFAULT_MESSAGE[index]);
        }
    }

    /**
     * TCP任务
     */
    private class WorkServer implements Runnable{
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,"设置监听失败 port ：8868,"+e.getMessage());
            }
            while (!mIsDestroy){
                try {
                    final Socket socket = serverSocket.accept();
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responeClient(socket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,"accept fail:"+e.getMessage());
                }
            }
        }
    }

}
