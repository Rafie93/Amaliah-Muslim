package com.basantandevloper.geo.muslimamaliyah.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.MainActivity;
import com.basantandevloper.geo.muslimamaliyah.R;

public class AlarmBroadcastRecevier extends BroadcastReceiver {
    String title,description;
    String TAG = "AlarmBroadcastRecevier";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
     String NOTIFICATION_CHANNEL_ID = "10001";
     Boolean is_sound = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bd = intent.getExtras();
        if (bd !=null) {
//            String tglhijriah = (String)bd.get("tglhijriah");
            title = (String) bd.get("Title");
            description = (String) bd.get("Desc");
            is_sound = (Boolean)bd.get("Sound");
             NOTIFICATION_CHANNEL_ID = (String)bd.get("Notif");
        }else{
            title = "Bangun dan Ingat";
            description = "";
        }

        Toast.makeText(context,"WAKTUNYA SHALAT",Toast.LENGTH_LONG).show();
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        intentToRepeat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //set flag to restart/relaunch the app
       // intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(NOTIFICATION_CHANNEL_ID)
                 /* Request code */, intentToRepeat,
                PendingIntent.FLAG_UPDATE_CURRENT);

    //    PendingIntent pendingIntent = PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);
        //Build notification

        if (is_sound){
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.mipmap.ic_my_launcher);
            mBuilder.setContentTitle(title)
                    .setContentText(description)
                    .setAutoCancel(false)
                    .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.adzan_mekkah))
                    .setContentIntent(pendingIntent);
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }else {
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.mipmap.ic_my_launcher);
            mBuilder.setContentTitle(title)
                    .setContentText(description)
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
//        Notification repeatedNotification = buildLocalNotification(context, pendingIntent).build();
  //      NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);
        assert mNotificationManager != null;
        mNotificationManager.notify(Integer.parseInt(NOTIFICATION_CHANNEL_ID) /* Request Code */, mBuilder.build());

        //Send local notification
    }

    public NotificationCompat.Builder buildLocalNotification(Context context, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.drawable.arrow_up_float)
                        .setContentTitle(title)
                        .setContentText(description)
                        .setAutoCancel(true);

        return builder;
    }

    public static void showNotification(Context context,Class<?> cls,String title,String content) {
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle(title)
                .setContentText(content).setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);

//    }
    }
}
