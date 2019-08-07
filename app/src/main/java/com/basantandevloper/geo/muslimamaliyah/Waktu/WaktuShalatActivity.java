package com.basantandevloper.geo.muslimamaliyah.Waktu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.notification.MyNotification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WaktuShalatActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static CountDownTimer countDownTimer;
    private String kdkota;
    Integer  jamSubuh,menitSubuh,jamZuhur,menitZuhur,jamAsar,menitAsar,jamMagrib,menitMagrib,jamIsya,menitIsya;
    @BindView(R.id.img_subuh)
    ImageView imgShubuhSuara;
    @BindView(R.id.img_shubuh_notif_on)
    ImageView imgShubuhNotifOn;
    @BindView(R.id.img_shubuh_notif_of)
    ImageView imgShubuhNotifOf;
    @BindView(R.id.img_zuhur)
    ImageView imgDzuhurSuara;
    @BindView(R.id.img_zuhur_notif_on)
    ImageView imgZuhurNotifOn;
    @BindView(R.id.img_zuhur_notif_of)
    ImageView imgZuhurNotifOf;
    @BindView(R.id.img_ashar)
    ImageView imgAsharSuara;
    @BindView(R.id.img_ashar_notif_on)
    ImageView imgAsharNotifOn;
    @BindView(R.id.img_ashar_notif_of)
    ImageView imgAsharNotifOf;
    @BindView(R.id.img_magrhib)
    ImageView imgMagribSuara;
    @BindView(R.id.img_magrib_notif_on)
    ImageView imgMagribNotifOn;
    @BindView(R.id.img_magrib_notif_of)
    ImageView imgMagribNotifOf;
    @BindView(R.id.img_isya)
    ImageView imgIsyaSuara;
    @BindView(R.id.img_isya_notif_on)
    ImageView imgIsyaNotifOn;
    @BindView(R.id.img_isya_notif_of)
    ImageView imgIsyaNotifOf;
    @BindView(R.id.img_dhuha_notif_on)
    ImageView imgDhuhaNotifOn;
    @BindView(R.id.img_dhuha_notif_of)
    ImageView imgDhuhaNotifOf;

    @BindView(R.id.tv_tanngal)
    TextView tv_tanngal;
    @BindView(R.id.tv_nama_daerah)
    TextView tv_nama_daerah;
    @BindView(R.id.txImsak)
    TextView txtImsak;
    @BindView(R.id.txtSubuh)
    TextView txtSubuh;
    @BindView(R.id.txtDzuhur)
    TextView txtDzuhur;
    @BindView(R.id.txDhuha)
    TextView txtDhuha;
    @BindView(R.id.txtAshar)
    TextView txtAshar;
    @BindView(R.id.txtMaghrib)
    TextView txtMagrib;
    @BindView(R.id.txtIsya)
    TextView txtIsya;
    @BindView(R.id.waktu)
    TextView waktu;
    @BindView(R.id.countdownText)
    TextView countdownTimerText;
    @BindView(R.id.layout)
    LinearLayout imageView;

    SharedPreferences sharedPreferences;
    static final String myPrefSubuh="myPrefSubuh";
    static final String myPrefZuhur="myPrefZuhur";
    static final String myPrefAshar="myPrefAshar";
    static final String myPrefMagrib="myPrefMagrib";
    static final String myPrefIsya="myPrefIsya";
    static final String myPrefDhuha="myPrefDhuha";
    static final String KeyImsak = "imsakKey";
    static final String KeySubuh = "subuhKey";
    static final String KeyDhuha = "dhuhaKey";
    static final String KeyZuhur = "zuhurKey";
    static final String KeyAshar = "asharKey";
    static final String KeyMagrib = "magribKey";
    static final String KeyIsya = "isyaKey";
    private String prefImsak;
    private String prefSubuh;
    private String prefDhuha;
    private String prefZuhur;
    private String prefAshar;
    private String prefMagrib;
    private String prefIsya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = WaktuShalatActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(WaktuShalatActivity.this,R.color.yello));

        setContentView(R.layout.activity_waktu_shalat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd !=null){
//            String tglhijriah = (String)bd.get("tglhijriah");
            String tglmasehi = (String)bd.get("tglmasehi");
            String kota = (String)bd.get("kota");
            String stats = (String)bd.get("stats");
            String shubuh = (String)bd.get("shubuh");
            String dzuhur = (String)bd.get("dzuhur");
            String ashar = (String)bd.get("ashar");
            String maghrib = (String)bd.get("maghrib");
            String isya = (String)bd.get("isya");
            String imsak = (String)bd.get("imsak");
            String dhuha = (String)bd.get("dhuha");
            String area = (String)bd.get("area");

            getSupportActionBar().setTitle("Jadwal Sholat");
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setSubtitle(kota+", "+stats);
            toolbar.setSubtitleTextColor(Color.WHITE);

            tv_nama_daerah.setText(kota);
            tv_tanngal.setText(tglmasehi);
            txtSubuh.setText(shubuh);
            txtDzuhur.setText(dzuhur);
            txtAshar.setText(ashar);
            txtMagrib.setText(maghrib);
            txtIsya.setText(isya);
            txtImsak.setText(imsak);
            txtDhuha.setText(dhuha);

            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
            String jamSekarang = displayFormat.format(new Date());


            String waktuKe="";
            Integer menitAwal = 0;
            Integer jamAwal =0;

            String[] sekarangPecah=jamSekarang.split(":");
            Integer jamS = Integer.valueOf(sekarangPecah[0]);
            Integer menitS = Integer.valueOf(sekarangPecah[1]);
            Integer detikS = Integer.valueOf(sekarangPecah[2]);
            Integer jamNowToMenit = jamS * 60;
            Integer menitJadinya = jamNowToMenit + menitS;


            String[] sekarangIsya=isya.split(":");
             jamIsya = Integer.valueOf(sekarangIsya[0]);
             menitIsya = Integer.valueOf(sekarangIsya[1]);
            Integer jamIsyaToMenit = jamIsya * 60;
            Integer menitIsyaJadinya = jamIsyaToMenit + menitIsya;

            String[] sekarangImsak=imsak.split(":");
            Integer jamImsak = Integer.valueOf(sekarangImsak[0]);
            Integer menitImsak = Integer.valueOf(sekarangImsak[1]);
            Integer jamImsakToMenit = jamImsak * 60;
            Integer menitImsakJadinya = jamImsakToMenit + menitImsak;

            String[] sekarangShubuh=shubuh.split(":");
             jamSubuh = Integer.valueOf(sekarangShubuh[0]);
             menitSubuh = Integer.valueOf(sekarangShubuh[1]);
            Integer jamSubuhToMenit = jamSubuh * 60;
            Integer menitSubuhJadinya = jamSubuhToMenit + menitSubuh;

            String[] sekarangDhuha=dhuha.split(":");
            Integer jamDhuha = Integer.valueOf(sekarangDhuha[0]);
            Integer menitDhuha = Integer.valueOf(sekarangDhuha[1]);
            Integer jamDhuhaToMenit = jamDhuha * 60;
            Integer menitDhuhaJadinya = jamDhuhaToMenit + menitDhuha;

            String[] sekarangDzuhur=dzuhur.split(":");
             jamZuhur = Integer.valueOf(sekarangDzuhur[0]);
             menitZuhur = Integer.valueOf(sekarangDzuhur[1]);
            Integer jamZuhurToMenit = jamZuhur * 60;
            Integer menitZuhurJadinya = jamZuhurToMenit + menitZuhur;

            String[] sekarangAsar=ashar.split(":");
             jamAsar = Integer.valueOf(sekarangAsar[0]);
             menitAsar = Integer.valueOf(sekarangAsar[1]);
            Integer jamAsarToMenit = jamAsar * 60;
            Integer menitAsarJadinya = jamAsarToMenit + menitAsar;

            String[] sekarangMagrib=maghrib.split(":");
             jamMagrib = Integer.valueOf(sekarangMagrib[0]);
             menitMagrib = Integer.valueOf(sekarangMagrib[1]);
            Integer jamMagribToMenit = jamMagrib * 60;
            Integer menitMagribJadinya = jamMagribToMenit + menitMagrib;

            if(jamS>jamIsya){
                waktuKe = "Tengah Malam";
                jamAwal = 23;
                menitAwal = 59;
                imageView.setBackground(getResources().getDrawable(R.drawable.mesjidmalam));

            }

            else if (menitJadinya > 1 && menitJadinya <= menitImsakJadinya){
                waktuKe = "Imsak";
                jamAwal = jamImsak;
                menitAwal = menitImsak;
                imageView.setBackground(getResources().getDrawable(R.drawable.mesjidmalam));

            }else if (menitJadinya>menitImsakJadinya && menitJadinya<=menitSubuhJadinya){
                waktuKe = "Shubuh";
                jamAwal = jamSubuh;
                menitAwal = menitSubuh;
                imageView.setBackground(getResources().getDrawable(R.drawable.mesjidmalam));

            }else if (menitJadinya>menitSubuh && menitJadinya<=menitDhuhaJadinya){
                waktuKe = "Dhuha";
                jamAwal = jamDhuha;
                menitAwal = menitDhuha;
                imageView.setBackground(getResources().getDrawable(R.drawable.mesjidsiang));
            }
            else
            if(menitJadinya>menitSubuhJadinya && menitJadinya <=menitZuhurJadinya){
                waktuKe = "Dzuhur";
                jamAwal = jamZuhur;
                menitAwal = menitZuhur;
                imageView.setBackground(getResources().getDrawable(R.drawable.mesjidsiang));
            }else
            if(menitJadinya>menitZuhurJadinya && menitJadinya <=menitAsarJadinya){
                waktuKe = "Ashar";
                jamAwal = jamAsar;
                menitAwal = menitAsar;
                imageView.setBackground(getResources().getDrawable(R.drawable.mesjidsiang));
            }else
            if(menitJadinya>menitAsarJadinya && menitJadinya <=menitMagribJadinya){
                waktuKe = "Maghrib";
                jamAwal = jamMagrib;
                menitAwal = menitMagrib;
                imageView.setBackground(getResources().getDrawable(R.drawable.islam));

            }else
            if(menitJadinya>menitMagribJadinya && menitJadinya <=menitIsyaJadinya){
                waktuKe = "Isya";
                jamAwal = jamIsya;
                menitAwal = menitIsya;
                imageView.setBackground(getResources().getDrawable(R.drawable.islam));

            }
            waktu.setText(waktuKe);

            Integer jamToMenit = jamAwal * 60;
            Integer menitFuture = jamToMenit + menitAwal;

            int milNow = ((menitJadinya * 60)+detikS) * 1000;
            int milFuture = menitFuture * 60 * 1000;

            Integer sisaMil = milFuture-milNow;
            Log.d("SISA", String.valueOf(sisaMil));

//        int noOfMinutes = sisaMenit * 60 * 1000;//Convert minutes into milliseconds
            startTimer(sisaMil);

            //RETRIVE
//            sharedPreferences = getSharedPreferences(myPrefSubuh,Context.MODE_PRIVATE);
//            prefImsak= sharedPreferences.getString(KeyImsak,"");
            SharedPreferences shareSubuh = getSharedPreferences(myPrefSubuh,Context.MODE_PRIVATE);
            prefSubuh = shareSubuh.getString(KeySubuh,"");

            SharedPreferences shareDhuha = getSharedPreferences(myPrefDhuha,Context.MODE_PRIVATE);
            prefDhuha= shareDhuha.getString(KeyDhuha,"");

            Log.d("SHUBUH: ",prefSubuh);
            if (prefSubuh.equals("1")){
                imgShubuhNotifOf.setVisibility(View.GONE);
                imgShubuhNotifOn.setVisibility(View.VISIBLE);
                imgShubuhSuara.setVisibility(View.GONE);
            }else if (prefSubuh.equals("2")){
                imgShubuhNotifOf.setVisibility(View.GONE);
                imgShubuhNotifOn.setVisibility(View.GONE);
                imgShubuhSuara.setVisibility(View.VISIBLE);
            }else{
                imgShubuhNotifOf.setVisibility(View.VISIBLE);
                imgShubuhNotifOn.setVisibility(View.GONE);
                imgShubuhSuara.setVisibility(View.GONE);
            }
            //
            SharedPreferences shareZuhur = getSharedPreferences(myPrefZuhur,Context.MODE_PRIVATE);
            prefZuhur = shareZuhur.getString(KeyZuhur,"");
            Log.d("ZUHUR: ",prefZuhur);
            if (prefZuhur.equals("1")){
                imgZuhurNotifOf.setVisibility(View.GONE);
                imgZuhurNotifOn.setVisibility(View.VISIBLE);
                imgDzuhurSuara.setVisibility(View.GONE);
            }else if (prefZuhur.equals("2")){
                imgZuhurNotifOf.setVisibility(View.GONE);
                imgZuhurNotifOn.setVisibility(View.GONE);
                imgDzuhurSuara.setVisibility(View.VISIBLE);
            }else{
                imgZuhurNotifOf.setVisibility(View.VISIBLE);
                imgZuhurNotifOn.setVisibility(View.GONE);
                imgDzuhurSuara.setVisibility(View.GONE);
            }

            SharedPreferences shareAshar = getSharedPreferences(myPrefAshar,Context.MODE_PRIVATE);
            prefAshar = shareAshar.getString(KeyAshar,"");
            Log.d("ASHAR: ",prefAshar);
            //
            if (prefAshar.equals("1")){
                imgAsharNotifOf.setVisibility(View.GONE);
                imgAsharNotifOn.setVisibility(View.VISIBLE);
                imgAsharSuara.setVisibility(View.GONE);
            }else if (prefAshar.equals("2")){
                imgAsharNotifOf.setVisibility(View.GONE);
                imgAsharNotifOn.setVisibility(View.GONE);
                imgAsharSuara.setVisibility(View.VISIBLE);
            }else{
                imgAsharNotifOf.setVisibility(View.VISIBLE);
                imgAsharNotifOn.setVisibility(View.GONE);
                imgAsharSuara.setVisibility(View.GONE);
            }
            //
            SharedPreferences shareMagrib = getSharedPreferences(myPrefMagrib,Context.MODE_PRIVATE);
            prefMagrib = shareMagrib.getString(KeyMagrib,"");
            Log.d("MAGRIB: ",prefMagrib);
            if (prefMagrib.equals("1")){
                imgMagribNotifOf.setVisibility(View.GONE);
                imgMagribNotifOn.setVisibility(View.VISIBLE);
                imgMagribSuara.setVisibility(View.GONE);
            }else if (prefMagrib.contains("2")){
                imgMagribNotifOf.setVisibility(View.GONE);
                imgMagribNotifOn.setVisibility(View.GONE);
                imgMagribSuara.setVisibility(View.VISIBLE);
            }else{
                imgMagribNotifOf.setVisibility(View.VISIBLE);
                imgMagribNotifOn.setVisibility(View.GONE);
                imgMagribSuara.setVisibility(View.GONE);
            }
            //
            SharedPreferences shareIsya = getSharedPreferences(myPrefIsya,Context.MODE_PRIVATE);
            prefIsya = shareIsya.getString(KeyIsya,"");
            Log.d("ISYA: ",prefIsya);
            if (prefIsya.equals("1")){
                imgIsyaNotifOf.setVisibility(View.GONE);
                imgIsyaNotifOn.setVisibility(View.VISIBLE);
                imgIsyaSuara.setVisibility(View.GONE);
            }else if (prefIsya.equals("2")){
                imgIsyaNotifOf.setVisibility(View.GONE);
                imgIsyaNotifOn.setVisibility(View.GONE);
                imgIsyaSuara.setVisibility(View.VISIBLE);
            }else{
                imgIsyaNotifOf.setVisibility(View.VISIBLE);
                imgIsyaNotifOn.setVisibility(View.GONE);
                imgIsyaSuara.setVisibility(View.GONE);
            }

        }

    }
    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {

                countdownTimerText.setText("SHOLAT-SHOLAT!!"); //On finish change timer text
       //         countDownTimer = null;//set CountDownTimer to null
//                startTimer.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void imgSuaraSubuh(View view) {
        imgShubuhNotifOf.setVisibility(View.VISIBLE);
        imgShubuhNotifOn.setVisibility(View.GONE);
        imgShubuhSuara.setVisibility(View.GONE);

       MyNotification.cancelAlarm(this.getApplicationContext(),"1002");
       MyNotification.disableBootReceiver(this.getApplicationContext(),"1002");

        SharedPreferences setting=  this.getSharedPreferences(myPrefSubuh,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeySubuh,"0");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOnSubuh(View view) {
        imgShubuhNotifOf.setVisibility(View.GONE);
        imgShubuhNotifOn.setVisibility(View.GONE);
        imgShubuhSuara.setVisibility(View.VISIBLE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1002,String.valueOf(jamSubuh),String.valueOf(menitSubuh),
                "Shubuh","Pukul "+String.valueOf(jamSubuh)+":"+String.valueOf(menitSubuh)+" waktunya shalat Shubuh",true);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefSubuh,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeySubuh,"2");
        editor.apply();
        editor.commit();

    }

    public void imgNotifOfSubuh(View view) {
        imgShubuhNotifOf.setVisibility(View.GONE);
        imgShubuhNotifOn.setVisibility(View.VISIBLE);
        imgShubuhSuara.setVisibility(View.GONE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1002,String.valueOf(jamSubuh),String.valueOf(menitSubuh),
                "Shubuh","Pukul "+String.valueOf(jamSubuh)+":"+String.valueOf(menitSubuh)+" waktunya shalat Shubuh",false);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefSubuh,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeySubuh,"1");
        editor.apply();
        editor.commit();

    }

    public void imgNotifOnDzuhur(View view) {
        imgZuhurNotifOf.setVisibility(View.GONE);
        imgZuhurNotifOn.setVisibility(View.GONE);
        imgDzuhurSuara.setVisibility(View.VISIBLE);
        MyNotification.cancelAlarm(this.getApplicationContext(),"1003");
        MyNotification.disableBootReceiver(this.getApplicationContext(),"1003");

        SharedPreferences setting=  this.getSharedPreferences(myPrefSubuh,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeySubuh,"2");
        editor.apply();
        editor.commit();
    }

    public void imgSuaraDzuhur(View view) {
        imgZuhurNotifOf.setVisibility(View.VISIBLE);
        imgZuhurNotifOn.setVisibility(View.GONE);
        imgDzuhurSuara.setVisibility(View.GONE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1003,String.valueOf(jamZuhur),String.valueOf(menitZuhur),
                "Dzuhur","Pukul "+String.valueOf(jamZuhur)+":"+String.valueOf(menitZuhur)+" waktu shalat Dzuhur",true);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefZuhur,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyZuhur,"0");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOfDzuhur(View view) {
        imgZuhurNotifOf.setVisibility(View.GONE);
        imgZuhurNotifOn.setVisibility(View.VISIBLE);
        imgDzuhurSuara.setVisibility(View.GONE);


        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1003,String.valueOf(jamZuhur),String.valueOf(menitZuhur),
                "Dzuhur","Pukul "+String.valueOf(jamZuhur)+":"+String.valueOf(menitZuhur)+" waktu shalat Dzuhur",false);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting= this.getSharedPreferences(myPrefZuhur,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyZuhur,"1");
        editor.apply();
        editor.commit();

    }

    public void imgSuaraAshar(View view) {
        imgAsharNotifOf.setVisibility(View.VISIBLE);
        imgAsharNotifOn.setVisibility(View.GONE);
        imgAsharSuara.setVisibility(View.GONE);

        MyNotification.cancelAlarm(this.getApplicationContext(),"1004");
        MyNotification.disableBootReceiver(this.getApplicationContext(),"1004");

        SharedPreferences settingA=  this.getSharedPreferences(myPrefAshar,Context.MODE_PRIVATE);
        SharedPreferences.Editor editorA = settingA.edit();
        editorA.putString(KeyAshar,"0");
        editorA.apply();
        editorA.commit();
    }

    public void imgNotifOnAshar(View view) {
        imgAsharNotifOf.setVisibility(View.GONE);
        imgAsharNotifOn.setVisibility(View.GONE);
        imgAsharSuara.setVisibility(View.VISIBLE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1004,String.valueOf(jamAsar),String.valueOf(menitAsar),
                "Ashar","Pukul "+String.valueOf(jamAsar)+":"+String.valueOf(menitAsar)+" waktu shalat Ashar",true);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences settingA=  this.getSharedPreferences(myPrefAshar,Context.MODE_PRIVATE);
        SharedPreferences.Editor editorA = settingA.edit();
        editorA.putString(KeyAshar,"2");
        editorA.apply();
        editorA.commit();
    }

    public void imgNotifOfAshar(View view) {
        imgAsharNotifOf.setVisibility(View.GONE);
        imgAsharNotifOn.setVisibility(View.VISIBLE);
        imgAsharSuara.setVisibility(View.GONE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1004,String.valueOf(jamAsar),String.valueOf(menitAsar),
                "Ashar","Pukul "+String.valueOf(jamAsar)+":"+String.valueOf(menitAsar)+" waktu shalat Ashar",false);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences settingA=  this.getSharedPreferences(myPrefAshar,Context.MODE_PRIVATE);
        SharedPreferences.Editor editorA = settingA.edit();
        editorA.putString(KeyAshar,"1");
        editorA.apply();
        editorA.commit();

    }

    public void imgSuaraMagrib(View view) {
        imgMagribNotifOf.setVisibility(View.VISIBLE);
        imgMagribNotifOn.setVisibility(View.GONE);
        imgMagribSuara.setVisibility(View.GONE);

        MyNotification.cancelAlarm(this.getApplicationContext(),"1005");
        MyNotification.disableBootReceiver(this.getApplicationContext(),"1005");

        SharedPreferences setting=  this.getSharedPreferences(myPrefMagrib,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyMagrib,"0");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOnMagrib(View view) {
        imgMagribNotifOf.setVisibility(View.GONE);
        imgMagribNotifOn.setVisibility(View.GONE);
        imgMagribSuara.setVisibility(View.VISIBLE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1005,String.valueOf(jamMagrib),String.valueOf(menitMagrib),
                "Maghrib","Pukul "+String.valueOf(jamMagrib)+":"+String.valueOf(menitMagrib)+" waktu shalat Maghrib",true);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefMagrib,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyMagrib,"2");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOfMagrib(View view) {
        imgMagribNotifOf.setVisibility(View.GONE);
        imgMagribNotifOn.setVisibility(View.VISIBLE);
        imgMagribSuara.setVisibility(View.GONE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1005,String.valueOf(jamMagrib),String.valueOf(menitMagrib),
                "Maghrib","Pukul "+String.valueOf(jamMagrib)+":"+String.valueOf(menitMagrib)+" waktu shalat Maghrib",false);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefMagrib,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyMagrib,"1");
        editor.apply();
        editor.commit();
    }

    public void imgSuaraIsya(View view) {
        imgIsyaNotifOf.setVisibility(View.VISIBLE);
        imgIsyaNotifOn.setVisibility(View.GONE);
        imgIsyaSuara.setVisibility(View.GONE);

        MyNotification.cancelAlarm(this.getApplicationContext(),"1006");
        MyNotification.disableBootReceiver(this.getApplicationContext(),"1006");

        SharedPreferences setting=  this.getSharedPreferences(myPrefIsya,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyIsya,"0");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOnIsya(View view) {
        imgIsyaNotifOf.setVisibility(View.GONE);
        imgIsyaNotifOn.setVisibility(View.GONE);
        imgIsyaSuara.setVisibility(View.VISIBLE);

        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1006,String.valueOf(jamIsya),String.valueOf(menitIsya),
                "Isya","Pukul "+String.valueOf(jamIsya)+":"+String.valueOf(menitIsya)+" waktu shalat Isya",true);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefIsya,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyIsya,"2");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOfIsya(View view) {
        imgIsyaNotifOf.setVisibility(View.GONE);
        imgIsyaNotifOn.setVisibility(View.VISIBLE);
        imgIsyaSuara.setVisibility(View.GONE);
        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1006,String.valueOf(jamIsya),String.valueOf(menitIsya),
                "Isya","Pukul "+String.valueOf(jamIsya)+":"+String.valueOf(menitIsya)+" waktu shalat Isya",false);
        MyNotification.enableBootReceiver(this.getApplicationContext());

        SharedPreferences setting=  this.getSharedPreferences(myPrefIsya,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyIsya,"1");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOnDhuha(View view) {
        imgDhuhaNotifOf.setVisibility(View.VISIBLE);
        imgDhuhaNotifOn.setVisibility(View.GONE);

        SharedPreferences setting=  this.getSharedPreferences(myPrefDhuha,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyDhuha,"0");
        editor.apply();
        editor.commit();
    }

    public void imgNotifOfDhuha(View view) {
        imgDhuhaNotifOf.setVisibility(View.GONE);
        imgDhuhaNotifOn.setVisibility(View.VISIBLE);

        SharedPreferences setting=  this.getSharedPreferences(myPrefDhuha,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyDhuha,"1");
        editor.apply();
        editor.commit();
    }

//    public void test(View view) {
//        MyNotification.scheduleRepeatingRTCNotification(this.getApplicationContext(),1111,
//                String.valueOf("14"),String.valueOf("1"),
//                "Test","Pukul "+String.valueOf("14")+":"+String.valueOf("1")+" sadang",true);
//        MyNotification.enableBootReceiver(this.getApplicationContext());
//    }
}
