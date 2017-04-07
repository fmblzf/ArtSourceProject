package com.fmblzf.autotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;

    private Button mButton1;

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initWidget();
    }

    private void initWidget() {
        mEditText1 = (EditText) this.findViewById(R.id.et1);
        mEditText2 = (EditText) this.findViewById(R.id.et2);
        mButton1 = (Button) this.findViewById(R.id.btn1);
        mTextView1 = (TextView) this.findViewById(R.id.tv1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int et1 = Integer.parseInt(mEditText1.getText().toString());
                int et2 = Integer.parseInt(mEditText2.getText().toString());
                mTextView1.setText(String.valueOf(et1+et2));
            }
        });
    }
}
