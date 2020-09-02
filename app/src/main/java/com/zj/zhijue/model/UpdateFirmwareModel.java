package com.zj.zhijue.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.zj.zhijue.util.AppUtils;
import com.zj.zhijue.MyApplication;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.mine.PersonalInfoActivity;
import com.zj.zhijue.fragment.MineFragment;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class UpdateFirmwareModel {
    private NotificationManager notificationManager;
    public static String updateChannel ="upgrade";
    private final String CHANNEL_GROUP_ONE = "group_first";

    public void showFirmwareUpdateNotification() {
        Context context = MyApplication.getInstance();
        getNotificationManager(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createFirstChannelGroup();
        }
        firmwareUpdate(context);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, importance);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        //NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        chan.setGroup(CHANNEL_GROUP_ONE);
        notificationManager.createNotificationChannel(chan);
    }

    private void getNotificationManager(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createFirstChannelGroup () {
        NotificationChannelGroup ncGroup = new NotificationChannelGroup(CHANNEL_GROUP_ONE, "通知渠道1");
        notificationManager.createNotificationChannelGroup(ncGroup);
    }

    /**
     * 固件更新提示
     * @param context
     */
    private void firmwareUpdate(Context context) {

        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(updateChannel, "固件更新", NotificationManager.IMPORTANCE_HIGH);
            builder = new NotificationCompat.Builder(context,  updateChannel);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setContentTitle(AppUtils.getAppName(context)) //设置通知标题
                .setSmallIcon(R.mipmap.qidongtubiao)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.qidongtubiao)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的优先级：最大
                .setAutoCancel(true)//设置通知被点击一次是否自动取消
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentText(AppUtils.getStringResource(context, R.string.check_new_version_click_download_tip_text))
                .setContentIntent(getPendingIntent(context));

        Notification notification = builder.build();//构建通知对象
        notificationManager.notify(1002 , notification);

    }

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        intent.putExtra(PersonalInfoActivity.FRAGMENT_INDEX_KEY, MineFragment.SYSTEM_SETTINGS_KEY);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
