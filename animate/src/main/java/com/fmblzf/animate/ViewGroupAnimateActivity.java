package com.fmblzf.animate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/3/21.
 */
public class ViewGroupAnimateActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewgroup_animation_layout);

        mListView = (ListView) this.findViewById(R.id.listview_animate);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.listview_item_resource,R.id.listview_item_tv);
        for (int i = 0;i<20;i++){
            arrayAdapter.add("测试"+i);
        }
        mListView.setAdapter(arrayAdapter);

        //代码实现给ViewGroup添加动画效果
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        //0.5 表示是 延迟加载时间是 动画周期的0.5赔
        controller.setDelay(0.5f);
        //设置子类加载的顺序
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mListView.setLayoutAnimation(controller);

    }
}
