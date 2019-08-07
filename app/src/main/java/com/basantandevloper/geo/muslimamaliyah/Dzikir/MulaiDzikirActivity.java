package com.basantandevloper.geo.muslimamaliyah.Dzikir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MulaiDzikirActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTick)
    TextView tvTick;
    @BindView(R.id.tvUlangi)
    TextView tvUlangi;
    @BindView(R.id.circular_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.img_reset)
    ImageView imageReset;
    @BindView(R.id.img_getar)
    ImageView imageGetar;
    @BindView(R.id.img_non_getar)
    ImageView imageGetarNon;
    @BindView(R.id.tx_title)
    TextView tx_title;
    @BindView(R.id.tx_subtitle)
    TextView tx_sub;

    Integer reward = 1;
//    @BindView(R.id.tx_title)
//    TextView txtTitle;
//    @BindView(R.id.tx_subtitle)
//    TextView txtSubTitle;

    String nama,arti,bacaan,hitungan,id;
    int progress_isi =0;
    int total = 0;
    int ulang = 0;

    SharedPreferences sharedPreferences;
    String myDzikirku="myDzikir";
    static final String KeyTick = "tickKey";
     String prefTick;
    static final String KeyUlang = "ulangKey";
     String prefUlang;
    static final String KeyReward = "rewardKey";
    String prefReward;
    static final String myPrefImage="myPrefImage";
    static final String KeyImage = "imageKey";
    private String prefImage;
    private Boolean is_image = true;

    boolean canTick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = MulaiDzikirActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MulaiDzikirActivity.this,R.color.deeppurple));
        setContentView(R.layout.activity_mulai_dzikir);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null){
            id = (String)bd.get("id_bacaan");
            nama = (String)bd.get("nama");
            arti = (String)bd.get("arti");
            bacaan = (String)bd.get("bacaan");
            hitungan = (String)bd.get("hitungan");
            total = Integer.parseInt(hitungan);
            getSupportActionBar().setTitle(nama);
            tx_title.setText(nama);
          //  txtTitle.setText(nama);
           // txtSubTitle.setText(bacaan+" : "+hitungan+" kali");
            toolbar.setSubtitle(hitungan+" kali");
            tx_sub.setText(hitungan+" kali");
           // progress();
            loadDzikirku(id);
            progress();
        }

        SharedPreferences share = getSharedPreferences(myPrefImage,Context.MODE_PRIVATE);
        prefImage = share.getString(KeyImage,"");
        if (prefImage.equals("0")){
            imageGetarNon.setVisibility(View.VISIBLE);
            imageGetar.setVisibility(View.GONE);
            is_image = false;
        }else{
            imageGetarNon.setVisibility(View.GONE);
            imageGetar.setVisibility(View.VISIBLE);
            is_image = true;
        }


        imageReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        imageGetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageGetarNon.setVisibility(View.VISIBLE);
                imageGetar.setVisibility(View.GONE);

                SharedPreferences setting=  getSharedPreferences(myPrefImage,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString(KeyImage,"0");
                editor.apply();
                editor.commit();

                is_image=false;
            }
        });

        imageGetarNon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageGetarNon.setVisibility(View.GONE);
                imageGetar.setVisibility(View.VISIBLE);

                SharedPreferences setting=  getSharedPreferences(myPrefImage,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString(KeyImage,"1");
                editor.apply();
                editor.commit();
                is_image=true;
            }
        });

        MobileAds.initialize(this, "ca-app-pub-5914624441048150~1438549841");


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

    }

    void progress(){
        progressBar.setMax(total);
        progressBar.setProgress(progress_isi);
        tvTick.setText(String.valueOf(progress_isi));
        tvUlangi.setText(String.valueOf(ulang)+"x");
    }

    public void tick(View view) {
        if (ulang == reward && progress_isi == 0){
            showAlert();
        }

        if (canTick){
            progress_isi += 1;
            tvTick.setText(String.valueOf(progress_isi));

            if (is_image){
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    vibrator.vibrate(200);
                }
            }

            if (progress_isi == total){
                ulang += 1;
                progress_isi=0;
            }

            progress();
            simpanDzikirku();

        }
    }
    void simpanDzikirku (){
        myDzikirku = "myDzikir_"+id;
        SharedPreferences setting=  this.getSharedPreferences(myDzikirku,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyTick, String.valueOf(progress_isi));
        editor.putString(KeyUlang, String.valueOf(ulang));
        editor.putString(KeyReward, String.valueOf(reward));

        editor.apply();
        editor.commit();
    }
    void loadDzikirku(String id_baca) {
        myDzikirku = "myDzikir_" + id_baca;
        sharedPreferences = getSharedPreferences(myDzikirku,Context.MODE_PRIVATE);
        prefTick= sharedPreferences.getString(KeyTick,"0");
        prefUlang = sharedPreferences.getString(KeyUlang,"0");
        prefReward = sharedPreferences.getString(KeyReward,"1");

        ulang = Integer.parseInt(prefUlang);
        progress_isi = Integer.parseInt(prefTick);
        tvTick.setText(prefTick);
        tvUlangi.setText(prefUlang);
        reward = Integer.parseInt(prefReward);

    }
    void reset(){
        myDzikirku = "myDzikir_"+id;
        SharedPreferences setting=  this.getSharedPreferences(myDzikirku,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyTick, String.valueOf(0));
        editor.putString(KeyUlang, String.valueOf(0));
        editor.putString(KeyReward, String.valueOf(1));

        editor.apply();
        editor.commit();
        tvTick.setText("0");
        tvUlangi.setText("0");
        reward = 3;
        progress_isi=0;
        ulang=0;
        progress();
      //  loadDzikirku(id);
     //   progress();

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(this, "Ditambahkan 1 Kali Lagi ", Toast.LENGTH_SHORT).show();
        reward = reward+1;
        canTick = true;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-5914624441048150/6907333098", new AdRequest.Builder().build());
    }

    private void startVideo(){
        if(mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.show();
        }
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Tidak Bisa Melanjutkan Bertasbih")
                .setMessage("Ingin Bisa Melanjutkan Bertasbih Pada Dzikir Ini ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                       startVideo();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        canTick = false;
                    }
                });
        dialog.show();
    }
}
