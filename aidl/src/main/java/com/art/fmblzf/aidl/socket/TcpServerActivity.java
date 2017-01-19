package com.art.fmblzf.aidl.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.art.fmblzf.aidl.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/19.
 */
public class TcpServerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG= "TcpServerActivity";

    //消息传递的标识
    private static final int CONNECT_SUCESS_FLAG = 0;
    private static final int MESSAGE_FROM_SERVER_FLAG = 1;

    //线性容器
    private LinearLayout contaire;
    //发送按钮
    private Button button;
    //文本输入框
    private EditText editText;
    //
    private ScrollView scrollView;
    //消息处理机制
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case MESSAGE_FROM_SERVER_FLAG:
                    contaire.addView(createChildView(null,getTime()));
                    contaire.addView(createChildView(null,msg.obj.toString()));
                    scrollToBottom();
                    break;
                case CONNECT_SUCESS_FLAG:
                    Toast.makeText(TcpServerActivity.this,"连接成功！",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private Socket mSocket;
    private PrintWriter mPrintWriter;


    private void scrollToBottom() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_layout);
        //启动服务，这里是测试，真的项目开发，可以把service启动放在Application中
        Intent intent = new Intent(this,TcpServerService.class);
        startService(intent);
        new Thread(new TcpClientWork()).start();
        button = (Button) this.findViewById(R.id.send_button);
        button.setOnClickListener(this);
        editText = (EditText) this.findViewById(R.id.editText);
        contaire = (LinearLayout) this.findViewById(R.id.contaire);
        scrollView = (ScrollView) this.findViewById(R.id.scrollView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.send_button:
                sendMessage();
                break;
            default:
                break;
        }
    }

    private TextView createChildView(String location,String message){
        TextView tv = new TextView(this);
        tv.setText(message);
        if (location == "right"){
            tv.setGravity(Gravity.RIGHT);
            tv.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        return tv;
    }

    private String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("(HH:mm:ss)");
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    private void sendMessage() {
        String message = editText.getText().toString();
        Log.i(TAG,message);
        if (message.isEmpty()){
            Toast.makeText(TcpServerActivity.this,"请输入发送信息！",Toast.LENGTH_SHORT).show();
            return ;
        }
        mPrintWriter.println(message);
        editText.setText("");
        contaire.addView(createChildView("right",getTime()));
        contaire.addView(createChildView("right",message));
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private class TcpClientWork implements Runnable{

        @Override
        public void run() {
            Socket socket = null;
            while (socket == null) {
                try {
                    socket = new Socket("localhost", 8688);
                    mSocket = socket;

                    mPrintWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                    mPrintWriter.println("连接成功");
                    handler.sendEmptyMessage(CONNECT_SUCESS_FLAG);


                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (!TcpServerActivity.this.isFinishing()){
                        String message = reader.readLine();
                        handler.obtainMessage(MESSAGE_FROM_SERVER_FLAG, message).sendToTarget();
                    }
                    mPrintWriter.close();
                    reader.close();
                    socket.close();
                } catch (IOException e) {
                    Log.e(TAG,"连接失败，等待1秒之后再次自动连接..."+e.getMessage());
                    SystemClock.sleep(1000);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        mPrintWriter.close();
        try {
            mSocket.shutdownInput();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
