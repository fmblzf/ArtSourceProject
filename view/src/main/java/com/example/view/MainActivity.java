package com.example.view;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.view.custom.HorizontalScrollViewEx;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private HorizontalScrollViewEx mHorizontalScrollViewEx;
    private LayoutInflater mLayoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {
        mHorizontalScrollViewEx = (HorizontalScrollViewEx) this.findViewById(R.id.horizon_scroll_id);
        mLayoutInflater = LayoutInflater.from(this);
        DisplayMetrics outerMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(outerMetrics);
        int sreenH = outerMetrics.heightPixels;
        int sreenW = outerMetrics.widthPixels;
        for (int i = 0;i<3;i++) {
            ViewGroup layout = (ViewGroup) mLayoutInflater.inflate(R.layout.content_layout, mHorizontalScrollViewEx.getContainer(), false);
            layout.getLayoutParams().width = sreenW;
            layout.getLayoutParams().height = sreenH;
            layout.setBackgroundColor(Color.argb(1,225/(i+1),225/(i+1),225/(i+1)));
            TextView textView = (TextView) layout.findViewById(R.id.content_title);
            textView.setText("page:"+(i+1));
            createList(layout);
            mHorizontalScrollViewEx.addSubView(layout);
        }
    }

    /**
     * 创建ListView
     * @param layout
     */
    private void createList(ViewGroup layout) {
        ListView listView = new ListView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0;i<50;i++){
            arrayList.add("name"+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_text,R.id.text,arrayList);
        listView.setAdapter(adapter);
        layout.addView(listView);
    }
}
