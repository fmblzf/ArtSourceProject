package com.fmblzf.animate.attr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fmblzf.animate.R;

/**
 * Created by Administrator on 2017/3/22.
 */
public class AttrAnimateActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBackColorAttrButton;
    private Button mXMLAttrAnimateButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attr_animate_layout);

        mBackColorAttrButton = (Button) this.findViewById(R.id.value_color_button);
        mBackColorAttrButton.setOnClickListener(this);

        mXMLAttrAnimateButton = (Button) this.findViewById(R.id.value_color_button1);
        mXMLAttrAnimateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.value_color_button:
                Intent intent=new Intent(AttrAnimateActivity.this,BackColorAnimateActivity.class);
                startActivity(intent);
                break;
            case R.id.value_color_button1:
                Intent intent1=new Intent(AttrAnimateActivity.this,XMLAttrAnimateActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
