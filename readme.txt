×完成SplashActivity界面
×完成出界面九宫格
×完成设置中心————设置提示自动升级
×完成自定义控件属性设置，并加入注释，软件必须写注释
×完成手机防盗--设置保护密码，并对保护密码进行MD5,SHA加密
×手机防盗4个activity的开发，跳转控制
×TextView也可以点击
×实现activity的跳转动画
×修改默认显示升级对话框
*代码仓库改为git
×设置向导页手势滑动
*绑定SIM卡，程序日志记录改为LogUtil
*实现选择联系人功能
*完成手机防盗设置页面开发
*对手机防盗设置页面的流程进行优化，设置的结果保存在临时变量中，
	只有进入到最后一步才会保存，而且按返回键中途设置的值不会保存
*播放报警音乐
×发送手机当前地理位置
当前bug：
bug01：手机防盗设置变量保存到实例变量中
bug02：无法拦截短信指令
*修改bug01,手机防盗设置变量保存到临时Temp变量中，设置完成后再真正保存
×关闭bug02，android17平台上可以拦截短信，android19平台上无法拦截短信
*完成远程锁屏以及远程删除数据