package com.basantandevloper.geo.muslimamaliyah.notification;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.basantandevloper.geo.muslimamaliyah.MainActivity;
import com.basantandevloper.geo.muslimamaliyah.MediaPlayer.SongActivity;
import com.basantandevloper.geo.muslimamaliyah.R;

public class NotificationSoundGenerator {
    public static final String NOTIFY_PREVIOUS = "com.basantandevloper.geo.muslimamaliyah.previous";
    public static final String NOTIFY_DELETE = "com.basantandevloper.geo.muslimamaliyah.delete";
    public static final String NOTIFY_PAUSE = "com.basantandevloper.geo.muslimamaliyah.pause";
    public static final String NOTIFY_PLAY = "com.basantandevloper.geo.muslimamaliyah.play";
    public static final String NOTIFY_NEXT = "com.basantandevloper.geo.muslimamaliyah.next";
    public static  Integer mPosisi;
    public static String player;
    private static final int NOTIFICATION_ID_OPEN_ACTIVITY = 9;
    private static final int NOTIFICATION_ID_CUSTOM_BIG = 9;

    public static void openNotification(Context context){
        NotificationCompat.Builder nc = new NotificationCompat.Builder(context);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(context, MainActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nc.setContentIntent(pendingIntent);

        nc.setSmallIcon(R.mipmap.ic_launcher);
        nc.setAutoCancel(true);
        nc.setContentTitle("Notification Demo");
        nc.setContentText("Click please");

        nm.notify(NOTIFICATION_ID_OPEN_ACTIVITY, nc.build());
    }

    @SuppressLint("RestrictedApi")
    public static void customBigNotification(Context context, String nameSong, Integer posisi, String mediaPlayerUrl){

        mPosisi = posisi;
        player = mediaPlayerUrl;

        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.layout_notification_sound);

        NotificationCompat.Builder nc = new NotificationCompat.Builder(context);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(context, SongActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nc.setContentIntent(pendingIntent);
        nc.setSmallIcon(R.drawable.ic_play);
        nc.setAutoCancel(true);
        nc.setCustomBigContentView(expandedView);
        nc.setContentTitle("Music Player");
        nc.setContentText("Control Audio");
        nc.getBigContentView().setTextViewText(R.id.txtSong, nameSong);

        setListeners(expandedView, context);

        nm.notify(NOTIFICATION_ID_CUSTOM_BIG, nc.build());
    }

    private static void setListeners(RemoteViews view, Context context) {
        Intent previous = new Intent(NOTIFY_PREVIOUS);
        previous.putExtra("posisi",mPosisi);

        Intent delete = new Intent(NOTIFY_DELETE);
        delete.putExtra("song",player);

        Intent pause = new Intent(NOTIFY_PAUSE);
        pause.putExtra("song",player);

        Intent next = new Intent(NOTIFY_NEXT);
        next.putExtra("song",player);
        next.putExtra("posisi",mPosisi);

        Intent play = new Intent(NOTIFY_PLAY);
        play.putExtra("song",player);

        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPrev, pPrevious);


        PendingIntent pDelete = PendingIntent.getBroadcast(context, 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnDelete, pDelete);


        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPause, pPause);


        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnNext, pNext);


        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPlay, pPlay);
    }
}
