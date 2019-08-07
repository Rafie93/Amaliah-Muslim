package com.basantandevloper.geo.muslimamaliyah.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.MediaPlayer.SongActivity;

import java.io.IOException;

public class NotificatioSoundBroadcast extends BroadcastReceiver {
    MediaPlayer mp ;// Here
    Integer posisi = 0;
    String url;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bd = intent.getExtras();
        if (bd !=null) {
            posisi = (Integer) bd.get("posisi");
            url = (String)bd.get("song");
            mp = MediaPlayer.create(context,Uri.parse(url));
           // mp = new MediaPlayer();

            Log.d("BROADCASTSOUND"," " + mp.toString());
        }
        if (intent.getAction().equals(NotificationSoundGenerator.NOTIFY_PLAY)){
       //    mp.start();
            Toast.makeText(context, "NOTIFY_PLAY", Toast.LENGTH_LONG).show();
        } else if (intent.getAction().equals(NotificationSoundGenerator.NOTIFY_PAUSE)){
          //  stopPlayer();
            SongActivity song = new SongActivity();

            Toast.makeText(context, "NOTIFY_PAUSE", Toast.LENGTH_LONG).show();
        }else if (intent.getAction().equals(NotificationSoundGenerator.NOTIFY_NEXT)){
            Integer nextPosisi = Integer.valueOf(posisi)+1;
            Toast.makeText(context, "NOTIFY_NEXT "+nextPosisi, Toast.LENGTH_LONG).show();
        }else if (intent.getAction().equals(NotificationSoundGenerator.NOTIFY_DELETE)){
            stopPlayer();
            SongActivity song = new SongActivity();
            Toast.makeText(context, "NOTIFY_DELETE", Toast.LENGTH_LONG).show();
        }else if (intent.getAction().equals(NotificationSoundGenerator.NOTIFY_PREVIOUS)){
            Integer prevPosisi = Integer.valueOf(posisi);
            if (Integer.valueOf(posisi)!=0){
                 prevPosisi = Integer.valueOf(posisi)-1;
            }
            Toast.makeText(context, "NOTIFY_PREVIOUS "+prevPosisi, Toast.LENGTH_LONG).show();
        }
    }
    public void stopPlayer() {
        try {
            if (mp.isPlaying()) {

                mp.stop();
                mp.release();
                mp = null;

            } else {
                Log.e("null", "empty player");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
