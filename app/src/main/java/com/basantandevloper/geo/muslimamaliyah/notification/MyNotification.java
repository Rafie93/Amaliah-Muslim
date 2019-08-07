package com.basantandevloper.geo.muslimamaliyah.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class MyNotification {
    private static AlarmManager alarmManagerRTC;
    private static PendingIntent alarmIntentRTC;

    public static int ALARM_TYPE_ELAPSED = 101;
    private static AlarmManager alarmManagerElapsed;
    private static PendingIntent alarmIntentElapsed;

    public static void scheduleRepeatingRTCNotification(Context context,Integer NOTIFICATION_ID, String hour, String min,String title,String desc,Boolean sound) {

        Calendar waktuku = Calendar.getInstance();
      //  Date date = new Date();
     //   cal_now.setTime(date);
        waktuku.setTimeInMillis(System.currentTimeMillis());

        waktuku.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        waktuku.set(Calendar.MINUTE, Integer.parseInt(min));
        waktuku.set(Calendar.SECOND, 0);
        long afterWaktu = waktuku.getTimeInMillis();
        long waktuSekarang =  System.currentTimeMillis();
        long esok = 86400 * 1000;
        long hasilWaktu = afterWaktu-waktuSekarang;
        if (hasilWaktu < 0){
            afterWaktu = afterWaktu + esok;
        }

        Intent intent = new Intent(context, AlarmBroadcastRecevier.class);
        intent.putExtra("Notif",String.valueOf(NOTIFICATION_ID));
        intent.putExtra("Title",title);
        intent.putExtra("Desc",desc);
        intent.putExtra("Sound",sound);
//        alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManagerRTC = (AlarmManager)context.getSystemService(ALARM_SERVICE);
//        alarmManagerElapsed.setRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntentRTC);

        PendingIntent alaramJam = PendingIntent.getBroadcast(context, NOTIFICATION_ID,
                intent, PendingIntent.FLAG_ONE_SHOT);

        //getting instance of AlarmManager service
        if (Integer.valueOf(hour)<10){
            hour = "0"+hour;
        }
        if (Integer.valueOf(min)<10){
            hour = "0"+min;
        }

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            AlarmManager alarmManagerJam = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManagerJam.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, afterWaktu,alaramJam);
            Log.d("MILIDETIK:", String.valueOf(afterWaktu)+" OR "+String.valueOf(waktuSekarang));
            Toast.makeText(context, "Notifikasi sudah di atur ", Toast.LENGTH_LONG).show();
        }else {
            AlarmManager alarmManagerJam = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManagerJam.setRepeating(AlarmManager.RTC_WAKEUP, afterWaktu, AlarmManager.INTERVAL_DAY, alaramJam);
            Log.d("MILIDETIK:", String.valueOf(afterWaktu)+" OR "+String.valueOf(waktuSekarang));
            Toast.makeText(context, "Notifikasi sudah di atur ", Toast.LENGTH_LONG).show();
        }
//        alarmManagerRTC.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentRTC);
    }

    public static void scheduleRepeatingElapsedNotification(Context context) {
        //Setting intent to class where notification will be handled
        Intent intent = new Intent(context,AlarmBroadcastRecevier.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),23432,intent,0);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(i*1000),pendingIntent);
//

        PendingIntent alarmIntentElapsed = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //getting instance of AlarmManager service
        AlarmManager alarmManagerElapsed = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        alarmManagerElapsed.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,alarmIntentElapsed);
//        Toast.makeText(context,"Alaram SET IN "+i+" Detik",Toast.LENGTH_LONG).show();
    }
    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    public static void disableBootReceiver(Context context,String NOTIFICATION_ID) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    public static void cancelAlarm(Context context, String NOTIFICATION_ID) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, AlarmBroadcastRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, Integer.parseInt(NOTIFICATION_ID), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
