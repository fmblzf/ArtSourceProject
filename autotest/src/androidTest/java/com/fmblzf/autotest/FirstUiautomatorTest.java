package com.fmblzf.autotest;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/7.
 */
public class FirstUiautomatorTest extends UiAutomatorTestCase {


    /**
     *
     * 执行操作的手机必须是root过的
     *
     */



    public void testDemo() throws UiObjectNotFoundException, IOException {

//        Runtime.getRuntime().exec("am start com.fmblzf.autotest/com.fmblzf.autotest.MainActivity");

        UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
        //利用框架设置启动
//        uiDevice.pressHome();
//        UiObject myApp = uiDevice.findObject(new UiSelector().description("AutoTest"));
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();
        UiSelector selector = new UiSelector().className("android.widget.TextView");
        UiObject myApp = appViews.getChildByText(selector,"AutoTest");
        //点击启动app
        assertTrue(myApp.exists());
        myApp.click();
        uiDevice.wait(Until.hasObject(By.pkg("com.fmblzf.autotest")),5000L);
//        myApp.clickAndWaitForNewWindow();
//        //操作对应的界面ui
        UiObject et1 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/et1"));//
        UiObject et2 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/et2"));
        UiObject btn1 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/btn1"));
        UiObject tv1 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/tv1"));

        et1.setText("12");
        et2.setText("21");
        btn1.click();
        assertEquals(33,Integer.parseInt(tv1.getText().toString()));
    }
    //断言失败报告例子
    public void testDemo1() throws UiObjectNotFoundException{
        UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
        //点击短信查看是否有无会话产生
        //当前界面获取短信按钮
        UiObject msm=new UiObject(new UiSelector().text("短信"));
        UiObject None=new UiObject(new UiSelector().text("短信"));
        //点击短信按钮
        msm.clickAndWaitForNewWindow();
        assertTrue(!None.exists());
    }
}
