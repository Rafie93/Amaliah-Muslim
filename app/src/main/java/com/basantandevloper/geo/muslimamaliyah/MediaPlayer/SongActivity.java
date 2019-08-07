package com.basantandevloper.geo.muslimamaliyah.MediaPlayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.notification.NotificationSoundGenerator;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    ListView L1;
//    MediaPlayer mediaPlayer;
    List<ModelData> list;
    int backButtonCount;
    public static  String link;
    int mPosisi;
    RelativeLayout layout;
    ImageView imagePlayStop,imgNext,imgPrev;
    boolean startMp = false;
    private SeekBar seekBar;
    TextView txtDurasi,txtCurrent,txtJudul,txtMemuat;
    long totalDurasi;
    long currentDurasi;
    private Handler mHandler = new Handler();
    int totalList = 0;
    long minutes;
    long seconds;
    private Runnable runnable;
    MediaPlayer mediaPlayer=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        L1 = (ListView) findViewById(R.id.AllSongs);
        layout = (RelativeLayout)findViewById(R.id.relMusik);
        imagePlayStop = (ImageView)findViewById(R.id.img_Stop);
        imgNext = (ImageView)findViewById(R.id.img_Next);
        imgPrev = (ImageView)findViewById(R.id.img_Prev);

        seekBar = (SeekBar)findViewById(R.id.musicSeekBar) ;
        txtDurasi = (TextView)findViewById(R.id.musicDuration) ;
        txtCurrent = (TextView)findViewById(R.id.musicCurrentLoc);
        txtJudul = (TextView)findViewById(R.id.txtJudul);
        txtMemuat = (TextView)findViewById(R.id.txtMemuat);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pemutar Qosidah Mp3");

        list = new ArrayList<>();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("song");
        databaseReference.keepSynced(true);

   //     Log.d("REF", "onCreate: "+databaseReference.toString());
        imagePlayStop.setImageResource(R.mipmap.ic_play);

        imgPrev.setEnabled(false);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ModelData Mod = dataSnapshot1.getValue(ModelData.class);
                    list.add(Mod);
                 //   Log.d("SNAPSHOT", "onDataChange: "+Mod.getUrl());
                }
                totalList = list.size();

                final SongAdapter adapterClass = new SongAdapter(SongActivity.this , list);
                L1.setAdapter(adapterClass);
                link = null;
                L1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (isNetworkAvailable()){
                            if (mediaPlayer != null){
                                if (mediaPlayer.isPlaying()){
                                    mediaPlayer.stop();
                                    mediaPlayer.reset();
                                }
                            }
                            txtMemuat.setVisibility(View.VISIBLE);
                            layout.setVisibility(View.VISIBLE);
                            ModelData modelData = list.get(i);
                            link = modelData.getUrl();
                            mPosisi = i;
                            imagePlayStop.setImageResource(R.mipmap.ic_stop);
                            txtJudul.setText(modelData.name);
                            playMusik(link, mPosisi);
                        }else{
                            Toast.makeText(SongActivity.this,"Aktifkan Koneksi Internet Anda Untuk Memutar",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void playMusik(String url, final Integer currenPosisi) {
        if (currenPosisi != 0 ){ imgPrev.setEnabled(true);
        }else{ imgPrev.setEnabled(false); }

        if (currenPosisi == totalList){ imgNext.setEnabled(false);
        }else{ imgNext.setEnabled(true); }

        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReferenceFromUrl(url);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Log.d("URI"," : " +uri.toString());
                    mediaPlayer = new MediaPlayer();

                    mediaPlayer.setDataSource(uri.toString());
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            txtMemuat.setVisibility(View.GONE);
                            if (mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }
                             mediaPlayer.start();
                             totalDurasi = mediaPlayer.getDuration();
                             seekBar.setMax(mediaPlayer.getDuration());
                             currentDurasi = mediaPlayer.getCurrentPosition();
                             changeSeekbar();

                         //   int minutes = (int) ((totalDuration / (1000*60)) % 60);
                          //   minutes = TimeUnit.MILLISECONDS.toMinutes(totalDurasi);
                          //   seconds = TimeUnit.MILLISECONDS.toSeconds(totalDurasi);
                             minutes = totalDurasi / 1000 / 60;
                             seconds = totalDurasi / 1000 % 60;
                            // Displaying Total Duration time
                            txtDurasi.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));

                            ModelData modelData = list.get(currenPosisi);
                         //   NotificationSoundGenerator.customBigNotification(getApplicationContext(),modelData.name,currenPosisi,modelData.url);
                        }
                    });
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            if (b){
                                mediaPlayer.seekTo(i);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    link = null;
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            if (currenPosisi == totalList){
                                layout.setVisibility(View.GONE);
                                mediaPlayer.stop();
                            }else {
                                Integer nextPosisi = currenPosisi+1;
                                ModelData modelData = list.get(nextPosisi);
                                final SongAdapter adapterClass = new SongAdapter(SongActivity.this , list);
                                L1.setAdapter(adapterClass);
                                adapterClass.notifyDataSetChanged();
                                L1.setSelection(nextPosisi);
                                link = modelData.getUrl();
                                txtJudul.setText(modelData.name);
                                playMusik(link,nextPosisi);
                            }
                        }
                    });

                    mediaPlayer.prepare();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeSeekbar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        currentDurasi = mediaPlayer.getCurrentPosition();
        //   int minutes = (int) ((totalDuration / (1000*60)) % 60);
        long c_minutes = currentDurasi / 1000 / 60;
        long c_seconds = currentDurasi / 1000 % 60;
        txtCurrent.setText(c_minutes+":"+c_seconds);

        if (mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            mHandler.postDelayed(runnable,1000);
        }
    }
    private void changeSeleksiList(){

        Handler handler = new Handler();
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                changeSeleksiList();
            }
        };
        handler.postDelayed(runnable2,1000);
    }


    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
  //          intent.addCategory(Intent.CATEGORY_HOME);
    //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      //      startActivity(intent);
            //Songs.this.finish();
            SongActivity.this.onRestart();
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(this, "Tekan Lagi Untuk Kembali ", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    public void mediaStop(View view) {
        mediaPlayer.stop();
        layout.setVisibility(View.GONE);
    }

//    public void stop(){
//        mediaPlayer.stop();
//        layout.setVisibility(View.GONE);
//    }

    public void mediaNext(View view) {
        Integer nextPosisi = mPosisi+1;
        ModelData modelData = list.get(nextPosisi);
        final SongAdapter adapterClass = new SongAdapter(SongActivity.this , list);
        L1.setAdapter(adapterClass);
        L1.setSelection(nextPosisi);
        link = modelData.getUrl();
        playMusik(link,nextPosisi);
    }

    public void mediaPrev(View view) {
        Integer prevPosisi = mPosisi-1;
        ModelData modelData = list.get(prevPosisi);
        final SongAdapter adapterClass = new SongAdapter(SongActivity.this , list);
        L1.setAdapter(adapterClass);
        L1.setSelection(prevPosisi);
        link = modelData.getUrl();
        playMusik(link,prevPosisi);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
