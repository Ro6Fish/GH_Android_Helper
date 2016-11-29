# GH_Android_Helper

封装了常用的工具类方便使用

###RLog
在自定义的Application中初始化：

RLog.isDebug(BuildConfig.APPLICATION_ID, true);

BuildConfig.APPLICATION_ID这个配置的是包名可根据自己的需求配置相应的字符串

在其他地方调用：

RLog.e("这是TAG","这是日志");

RLog.e(MainActivity.class.getSimpleName(),"这是日志");

RLog.e("这是日志");
