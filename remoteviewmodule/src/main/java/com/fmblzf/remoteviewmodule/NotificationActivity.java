package com.fmblzf.remoteviewmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/7.
 */
public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_jump_layout);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("NOTIFICATION");
        String name = bundle.getString("STRING");
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
    }
}
