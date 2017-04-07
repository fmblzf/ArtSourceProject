package com.fmblzf.autotest;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

/**
 * Created by Administrator on 2017/4/7.
 */
public class FirstUiautomatorTest extends UiAutomatorTestCase {

    public void testDemo() throws UiObjectNotFoundException {

        UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
        //利用框架设置启动
        uiDevice.pressHome();
        UiObject myApp = uiDevice.findObject(new UiSelector().text("AutoTest"));
//        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
//        appViews.setAsHorizontalList();
//        UiSelector selector = new UiSelector().className("android.widget.TextView");
//        UiObject myApp = appViews.getChildByText(selector,"AutoTest");
        //点击启动app
//        assertTrue(myApp.exists());
        myApp.click();
        uiDevice.wait(Until.hasObject(By.pkg("com.fmblzf.autotest")),5000L);
//        myApp.clickAndWaitForNewWindow();
        //操作对应的界面ui
        UiObject et1 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/et1"));
        UiObject et2 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/et2"));
        UiObject btn1 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/btn1"));
        UiObject tv1 = new UiObject(new UiSelector().resourceId("com.fmblzf.autotest:id/tv1"));

        et1.setText("12");
        et2.setText("21");
        btn1.click();
        assertEquals(33,Integer.parseInt(tv1.getText().toString()));
    }
}
