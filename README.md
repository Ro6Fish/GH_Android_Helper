# GH_Android_Helper

封装了常用的工具类方便使用

###RLog
在自定义的Application中初始化：

RLog.isDebug(BuildConfig.APPLICATION_ID, true);

第一个参数可根据自己的需求配置相应的字符串，这里配置的是包名
第二个参数true：打印日志，false：不打印日志，默认是false，可以配置成BuildConfig.isDebug方便测试调试，发布后不显示日志

在其他地方调用：

RLog.e("这是TAG","这是日志");

RLog.e(MainActivity.class.getSimpleName(),"这是日志");

RLog.e("这是日志");
