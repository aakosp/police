package com.psb.xg;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import com.psb.R;
import com.psb.core.AppContext;
import com.psb.ui.activity.ActivityMain;

@SuppressLint("NewApi")
public class NotificationUtil {

	private static ActivityManager activityManager = null;
	private static KeyguardManager keyguardManager = null;
	private static String packageName = null;
	public static String MSGNOTIFICATION = "newmsg";

	public NotificationUtil() {
		super();
	}


	public static void showNotification(Context context, String title,
			Bundle bundle_msg, Bundle attrs, int flag) {

		String msg = "";

//		if (isAppOnForeground()) {
//			AudioUtil.recvMsg(AudioUtil.MSG_SOUND, true);
//			return;
//		}

		if (null == context) {
			context = AppContext.getInstance();
		}
		if (title != null) {
            title = "一村一警";
		}
//
//
//		if (bundle_msg != null) {
//			if (flag == 0) {
//				title = get_user_name(bundle_msg);
//				msg = return_txt(bundle_msg);
//			} else if (flag == 3) {
//				title = bundle_msg.getString(DatabaseUtil.MSG_KEY_LINKER);
//				msg = bundle_msg.getString(DatabaseUtil.BUSINESS_KEY_TITLE);
//			}
//		} else {
//			if (flag == 1)
//				msg = "你有一条广播被评论。";
//			else if (flag == 2)
//				msg = "你有一条回复信息。";
//			else if (flag == 3)
//				msg = "你有一条新的消息。";
//		}

		Intent notificationIntent = new Intent(); // 点击该通知后要跳转的Activity
		notificationIntent.setClass(context, ActivityMain.class);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		Bundle b = new Bundle();
		b.putInt(MSGNOTIFICATION, 1);
		notificationIntent.putExtras(b);

		PendingIntent contentItent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = null;
		if (Build.VERSION.SDK_INT >= 11) {
			notification = new Notification.Builder(context)
					.setContentTitle(title)
					.setContentText(msg)
					.setContentIntent(contentItent)
					.setSmallIcon(R.drawable.icon)
					.setLargeIcon(
							BitmapFactory.decodeResource(
									context.getResources(), R.drawable.icon))
					.setWhen(System.currentTimeMillis()).setAutoCancel(true)
					.getNotification();
		} else {
			notification = new Notification(R.drawable.icon, title,
					System.currentTimeMillis());
			notification.setLatestEventInfo(context, title, msg, contentItent);
		}

		notification.flags |= Notification.FLAG_AUTO_CANCEL;// 该通知能被状态栏的清除按钮给清除掉
		// FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
		// FLAG_ONGOING_EVENT 通知放置在正在运行
		// FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
		// notification.flags |= Notification.FLAG_ONGOING_EVENT; //
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中
		// notification.flags |= Notification.FLAG_NO_CLEAR; //
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
		// notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		// DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等
		// DEFAULT_LIGHTS 使用默认闪光提示
		// DEFAULT_SOUNDS 使用默认提示声音
		// DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission
		// android:name="android.permission.VIBRATE" />权限
		notification.defaults = Notification.DEFAULT_LIGHTS;
		// 叠加效果常量
		// notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
		notification.ledARGB = R.color.black;
		notification.ledOnMS = 5000; // 闪光时间，毫秒
		notificationManager.notify(50, notification);// 显示通知 break; }
//		AudioUtil.recvMsg(AudioUtil.PUSH_SOUND, true);
	}

	public static void showNotificationLogin(Context context, String title,
			String msg) {

		if (null == context) {
			context = AppContext.getInstance();
		}

		Intent notificationIntent = new Intent(); // 点击该通知后要跳转的Activity
		notificationIntent.setClass(context, ActivityMain.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

		PendingIntent contentItent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = null;
		if (Build.VERSION.SDK_INT >= 11) {
			notification = new Notification.Builder(context)
					.setContentTitle(title)
					.setContentText(msg)
					.setContentIntent(contentItent)
					.setSmallIcon(R.drawable.icon)
					.setLargeIcon(
							BitmapFactory.decodeResource(
									context.getResources(), R.drawable.icon))
					.setWhen(System.currentTimeMillis()).setAutoCancel(true)
					.getNotification();
		} else {
			notification = new Notification(R.drawable.icon, title,
					System.currentTimeMillis());
			notification.setLatestEventInfo(context, title, msg, contentItent);
		}
		notification.flags |= Notification.FLAG_AUTO_CANCEL;// 该通知能被状态栏的清除按钮给清除掉
		// FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
		// FLAG_ONGOING_EVENT 通知放置在正在运行
		// FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
		// notification.flags |= Notification.FLAG_ONGOING_EVENT; //
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中
		// notification.flags |= Notification.FLAG_NO_CLEAR; //
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		// DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等
		// DEFAULT_LIGHTS 使用默认闪光提示
		// DEFAULT_SOUNDS 使用默认提示声音
		// DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission
		// android:name="android.permission.VIBRATE" />权限
		notification.defaults = Notification.DEFAULT_LIGHTS;
		// 叠加效果常量
		// notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
		notification.ledARGB = R.color.shiming;
		notification.ledOnMS = 5000; // 闪光时间，毫秒
		notificationManager.notify(50, notification);// 显示通知 break; }
//		AudioUtil.recvMsg(AudioUtil.PUSH_SOUND, false);
	}

	// 删除通知
	public static void clearNotification() {
		// 启动后删除之前我们定义的通知
		NotificationManager notificationManager = (NotificationManager) AppContext
				.getInstance().getSystemService(
						Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(50);
	}

	private static boolean isAppOnForeground() {
		boolean isSpecialSystem = false;
		if (null == packageName) {
			packageName = AppContext.getInstance().getPackageName();
		}
		if (null == activityManager) {
			activityManager = (ActivityManager) AppContext.getInstance()
					.getSystemService(Context.ACTIVITY_SERVICE);
		}
		if (null == keyguardManager) {
			keyguardManager = (KeyguardManager) AppContext.getInstance()
					.getSystemService(Context.KEYGUARD_SERVICE);
		}
		if (!isSpecialSystem) {
			boolean isspecial = true;
			List<RunningAppProcessInfo> appProcesses = activityManager
					.getRunningAppProcesses();
			if (appProcesses == null)
				return false;
			for (RunningAppProcessInfo appProcess : appProcesses) {
				if (appProcess.processName.equals(packageName)) {
					if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
							|| appProcess.importance == RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
						return true;
					}
					if (keyguardManager.inKeyguardRestrictedInputMode())
						return true;
				}
				if (isspecial) {
					if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
						isspecial = false;
					}
				}
			}
			if (isspecial) {
				isSpecialSystem = true;
				return !isApplicationBroughtToBackgroundByTask();
			}
			return false;
		} else {
			return !isApplicationBroughtToBackgroundByTask();
		}
	}

	/**
	 * 判断当前应用程序是否处于后台，通过getRunningTasks的方式
	 * 
	 * @return true 在后台; false 在前台
	 */
	public static boolean isApplicationBroughtToBackgroundByTask() {
		List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(packageName)) {
				return true;
			}
		}
		return false;
	}

}
