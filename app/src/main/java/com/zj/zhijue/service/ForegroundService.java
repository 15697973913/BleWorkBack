package com.zj.zhijue.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.common.baselibrary.log.MLog;
import com.zj.zhijue.R;
import com.zj.zhijue.activity.BleMainControlActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";
    private static final int RES_ID = 1001;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MLog.d("ForegroundService onCreate ");
        String channelId = "my_service";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(channelId, "My Background Service");
        }

        Notification notification = new Notification();
        try {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, channelId);
//            Intent intent = new Intent(this, LiteActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            PendingIntent pendingIntent =
//                    PendingIntent.getActivity(this, 0, intent, 0);
            notification = builder.setSmallIcon(R.mipmap.qidongtubiao)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.qidongtubiao))
                    .setTicker(getResources().getString(R.string.app_name))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(getResources().getString(R.string.foreground_service_notification_text))
                    .setContentIntent(getPendingIntent())
                    .build();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        startForeground(RES_ID, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLog.d("ForegroundService onStartCommand ");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 给通知栏添加点击事件，实现具体操作
     *
     * @return
     */
    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, BleMainControlActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
