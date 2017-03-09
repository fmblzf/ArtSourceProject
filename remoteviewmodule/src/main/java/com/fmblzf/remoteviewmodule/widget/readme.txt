创建界面小部件的过程：
1、在res/layout/下创建一个XML文件，命名widget.xml，名称和内容可以自定义。
2、定义小部件配置信息，在res/xml/下新建appwidget_provider_info.xml，名称可以自定义:
    initialLayout就是指小部件所使用的初始化布局，
    minHeight和minWidth定义小部件的最小的尺寸，
    updatePeriodMillis定义小部件的自动更新周期，毫秒为单位，每隔一个周期，小部件的自动更新会被触发。
3、定义小部件的实现类，这个类需要继承 AppWidgetProvider，该类实际上是一个广播类。
4、在AndroidManifest.xml中声明小部件，因为桌面小部件本质是一个广播组件，因此必须要注册，通过静态注册广播的方式。

总结：完成上面的要求之后，在Android手机的屏幕的空白处长按就弹出“小部件”，然后点击就会在桌面上创建类似于快捷方式的（布局样式，主要取决于widget.xml的布局）
启动程序会自动生成小部件。