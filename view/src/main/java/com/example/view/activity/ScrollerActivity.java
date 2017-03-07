package com.example.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.view.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/6.
 */
public class ScrollerActivity extends AppCompatActivity {

    private LinearLayout linearLayout ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_scroller_view_layout);
        linearLayout = (LinearLayout) this.findViewById(R.id.ll_1);
        createList(linearLayout);

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
