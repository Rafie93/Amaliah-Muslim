package com.basantandevloper.geo.muslimamaliyah;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.API.APIClient;
import com.basantandevloper.geo.muslimamaliyah.API.ApiInterface;
import com.basantandevloper.geo.muslimamaliyah.Asmaul.AsmaulHusnaActivity;
import com.basantandevloper.geo.muslimamaliyah.Doa.DoaActivity;
import com.basantandevloper.geo.muslimamaliyah.Dzikir.DzikirActivity;
import com.basantandevloper.geo.muslimamaliyah.Kompas.KompasActivity;
import com.basantandevloper.geo.muslimamaliyah.Models.Data;
import com.basantandevloper.geo.muslimamaliyah.Models.Jadwal;
import com.basantandevloper.geo.muslimamaliyah.Models.JadwalModel;
import com.basantandevloper.geo.muslimamaliyah.Models.Kota;
import com.basantandevloper.geo.muslimamaliyah.Models.Listkota;
import com.basantandevloper.geo.muslimamaliyah.Models.LokasiModel;
import com.basantandevloper.geo.muslimamaliyah.Qosidah.QosidahActivity;
import com.basantandevloper.geo.muslimamaliyah.Quran.QuranActivity;
import com.basantandevloper.geo.muslimamaliyah.Sholat.SholatActivity;
import com.basantandevloper.geo.muslimamaliyah.Waktu.WaktuShalatActivity;
import com.basantandevloper.geo.muslimamaliyah.helper.WaktuSekarang;
import com.basantandevloper.geo.muslimamaliyah.utils.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.joda.time.DateTime;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import android.location.LocationListener;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Context mContext;
    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialAd2;

    private TextView txtTanggal, txtHijri, waktu, countdownTimerText,jamShalat;
   // private AdView adView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Double longitude, latitude;
    private Toolbar toolbar;
    public String jamShalatBerikutnya, futureJam,futureMenit, kdkota, imsak, dhuha, zuhur, ashar, magrib, isya, subuh, tanggal, kota, area, stats, negara, tgl_hijriah, tgl_masehi;
    private Jadwal jadwal;
    private Data datas;
    List<Listkota> listkota;
    private static CountDownTimer countDownTimer;
    private SwipeRefreshLayout swipe_id;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private boolean isEnabledGps;
    SharedPreferences sharedPreferences;
    static final String myPref="mypref";
    static final String myPrefHarian="myHarian";

    static final String KeyLatti = "lattiKey";
    static final String KeyLongi = "longiKey";
    static final String KeyKota = "kotaKey";
    static final String KeyStats = "statsKey";
    static final String KeyArea = "areaKey";
    static final String KeyNegara = "negaraKey";

    static final String KeyImsak = "imsakKey";
    static final String KeySubuh = "subuhKey";
    static final String KeyDhuha = "dhuhaKey";
    static final String KeyZuhur = "zuhurKey";
    static final String KeyAshar = "asharKey";
    static final String KeyMagrib = "magribKey";
    static final String KeyIsya = "isyaKey";
    static final String KeyTgl = "tglKey";

    private String prefLatti;
    private String prefLongi;
    private String prefKota;
    private String prefStats;
    private String prefArea;
    private String prefNegara;

    private String prefImsak;
    private String prefSubuh;
    private String prefDhuha;
    private String prefZuhur;
    private String prefAshar;
    private String prefMagrib;
    private String prefIsya;
    private String prefTgl;
    private ImageView imageView;

    Boolean is_nothing = false;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    Boolean is_ada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= 23){
            setContentView(R.layout.activity_main_l);
        }else{
            setContentView(R.layout.activity_main);
        }
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        txtTanggal = (TextView) findViewById(R.id.tanggal);
        txtHijri = (TextView) findViewById(R.id.txt_hijri);
        jamShalat = (TextView)findViewById(R.id.waktuShalat);
        waktu = (TextView) findViewById(R.id.waktu);
        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        swipe_id = (SwipeRefreshLayout) findViewById(R.id.swipe_id);
        imageView = (ImageView)findViewById(R.id.gambarHari);
       // adView = (AdView)findViewById(R.id.adView);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.mipmap.ic_my_launcher);

//        MobileAds.initialize(this, "ca-app-pub-5914624441048150~1438549841");
//
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId("ca-app-pub-5914624441048150/7384354557");
//        interstitialAd.loadAd(new AdRequest.Builder().addKeyword("muslim").addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

//        interstitialAd.setAdListener(new AdListener()
//                                        {
//                                            @Override
//                                            public void onAdClosed() {
//                                                Intent intent = new Intent(MainActivity.this,QosidahActivity.class);
//                                                startActivity(intent);
//                                                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
//                                            }
//                                        });
//
//        interstitialAd2 = new InterstitialAd(this);
//        interstitialAd2.setAdUnitId("ca-app-pub-5914624441048150/8863288882");
//        interstitialAd2.loadAd(new AdRequest.Builder().addKeyword("sholat").addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
//
//        interstitialAd2.setAdListener(new AdListener()
//        {
//            @Override
//            public void onAdClosed() {
//                Intent intent = new Intent(MainActivity.this,DoaActivity.class);
//                startActivity(intent);
//                interstitialAd2.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
//            }
//        });

        getTanggal();
        //loadLokasi();
        retriveJadwal();
        if (is_nothing){
            startWaktu();
        }

        //IZIN AKSES LOKASI
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                load_lokasi();

                izin_storage();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                load_lokasi();

                izin_storage();
            }
        }

        izin_storage();

        load_lokasi();

    }
    void izin_storage(){
        //IZIN AKSES STORAGE
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    void load_lokasi(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    private void getTanggal() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txtTanggal.setText(currentDate);

        int tahun = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

     //   Log.d("TANGGAL", String.valueOf(tahun) + " " + String.valueOf(month + 1));

        DateTime dtISO = new DateTime(tahun, month + 1, day + 1, 0, 0, 0, 0);
        DateTime dtIslamic = dtISO.withChronology(IslamicChronology.getInstance());
        String formatIslamic = "dd MMMM yyyy";
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(formatIslamic).withChronology(IslamicChronology.getInstance());
        String islamicDateString = formatter.print(dtIslamic);

        String[] exploded = islamicDateString.split(" ");
        String dayH = exploded[0];
        String monthH = exploded[1];
        String yearH = exploded[2];

        String bulanH = "";
        Integer bulanKu = Integer.parseInt(monthH);
        if (bulanKu == 1) {
            bulanH = "Muharam";
        } else if (bulanKu == 2) {
            bulanH = "Safar";
        } else if (bulanKu == 3) {
            bulanH = "Rabiul Awal";
        } else if (bulanKu == 4) {
            bulanH = "Rabiul Akhir";
        } else if (bulanKu == 5) {
            bulanH = "Jumadil Awal";
        } else if (bulanKu == 6) {
            bulanH = "Jumadil Akhir";
        } else if (bulanKu == 7) {
            bulanH = "Rajab";
        } else if (bulanKu == 8) {
            bulanH = "Sya'ban";
        } else if (bulanKu == 9) {
            bulanH = "Ramadhan";
        } else if (bulanKu == 10) {
            bulanH = "Syawal";
        } else if (bulanKu == 11) {
            bulanH = "Dzulhijah";
        } else if (bulanKu == 12) {
            bulanH = "Dzulqaidah";
        }

        tgl_hijriah = dayH + " " + bulanH + " " + yearH;
        txtHijri.setText(tgl_hijriah);
        tgl_masehi = currentDate;
    }

    void getPosisi(Double latt, Double longi) {
        Geocoder gcd3 = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd3.getFromLocation(latt, longi, 1);
            if (addresses.size() > 0)
            {
                //while(locTextView.getText().toString()=="Location") {
     //           Log.d("Cek lokasi", "1 :" + addresses.get(0).getLocality().toString());
//                                lokasi = addresses.get(0).getLocality().toString();
                //tvNamaDaerah.setText(lokasi);

                kota = addresses.get(0).getLocality().toString();
                negara = addresses.get(0).getCountryName().toString();
                stats = addresses.get(0).getSubAdminArea().toString();
                area = addresses.get(0).getAdminArea().toString();
                Log.d("Lokasiku", kota);
                getSupportActionBar().setTitle(kota);
                toolbar.setTitleTextColor(Color.WHITE);
                toolbar.setSubtitle(stats + ", " + area);
                toolbar.setSubtitleTextColor(Color.WHITE);
                LokasiModel lokasiModel = new LokasiModel();
                lokasiModel.setKota(kota);
                lokasiModel.setNegara(negara);
                lokasiModel.setArea(area);
                lokasiModel.setStats(stats);


                if (kota != null) {
                    getKodeLokasi(kota);
                    saveLokasi();
                } else {
                    Toast.makeText(MainActivity.this, "Swipe Layar Untuk Refresh", Toast.LENGTH_SHORT).show();
                }
         }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d("LOAD", "NULLPOINTER");


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LOAD", "IOERROR");
          //  showAlertInternet();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    private void getKodeLokasi(String lokasi) {
        Log.d("location", "locatin :" + lokasi);

        ApiInterface anInterface = APIClient.getClientKemeneg().create(ApiInterface.class);
        Call<Kota> kotaCall = anInterface.getKota(lokasi);
        kotaCall.enqueue(new Callback<Kota>() {
            @Override
            public void onResponse(Call<Kota> call, Response<Kota> response) {
                listkota = response.body().getListkotas();
                if (!listkota.isEmpty()) {
                    Log.d("TESKODE", listkota.toString());
                    kdkota = listkota.get(0).getId();
                    getWaktuHarian(kdkota, null);

                }else{
                    retriveJadwal();
                    if (is_nothing){
                        startWaktu();
                    }else{
                        if (stats.equals("Kota Jakarta Pusat") || stats.equals("Jakarta Selatan")||stats.equals("Jakarta Barat")||stats.equals("Jakarta Utara"))
                        {
                            stats = "Kota Jakarta";
                        }else if(stats.equals("Kapuas Hilir")){
                            stats = "Kapuas";
                        }else if(stats.equals("Kabupaten Pulang Pisau") || stats.equals("Pulang Pisau")){
                            stats = "PulangPisau";
                        }
                        stats = stats.replace("Kabupaten","");

                        getKodeLokasi(stats);
                    }
                }
            }

            @Override
            public void onFailure(Call<Kota> call, Throwable t) {
                retriveJadwal();
                if (is_nothing){
                    startWaktu();
                }
                Log.d("TESKODEGAGAL","GALAT");
               // Toast.makeText(MainActivity.this, "Internet Anda Sedang OFF, Ini Adalah Data Terakhir Anda", Toast.LENGTH_LONG).show();
            }
        });
//        if (listkota!=null){
//            showAlertInternet();
//        }
    }

    public void getWaktuHarian(String kdkota, String tgl) {
        String tglMauShalat;
        if (tgl != null) {
            tglMauShalat = tgl;
        } else {
            WaktuSekarang sekarang = new WaktuSekarang();
            tglMauShalat = sekarang.getTglSekarang();

        }
        Log.d("WAKTUHARIAN", "getWaktuHarian: "+tglMauShalat);
      //  stopCountdown();
        ApiInterface apiInterface = APIClient.getClientKemeneg().create(ApiInterface.class);
        Call<JadwalModel> jadwalModelCall = apiInterface.getJadwalSalat(kdkota, tglMauShalat);
        jadwalModelCall.enqueue(new Callback<JadwalModel>() {
            @Override
            public void onResponse(Call<JadwalModel> call, Response<JadwalModel> response) {
                //    jadwal = response.body().getJadwal();
                Log.d("RESPONEWAKTU", "onResponse: "+response.body().getStatus());
                jadwal = response.body().getJadwal();
                datas = jadwal.getData();

                zuhur = datas.getDzuhur();
                ashar = datas.getAshar();
                magrib = datas.getMaghrib();
                isya = datas.getIsya();
                subuh = datas.getSubuh();
                imsak = datas.getImsak();
                dhuha = datas.getDhuha();
                tanggal = datas.getTanggal();
                is_nothing = true;
                saveJadwal();
                startWaktu();
            }

            @Override
            public void onFailure(Call<JadwalModel> call, Throwable t) {
                Log.d("FAILUREWAKTU", "onFailure: gagagaaggagal");
            }
        });
    }

    public void jadwalShalat(View view) {
        if (is_nothing){
            Intent intent = new Intent(MainActivity.this, WaktuShalatActivity.class);
            intent.putExtra("tglhijriah", tgl_hijriah);
            intent.putExtra("tglmasehi", tanggal);
            intent.putExtra("kota", kota);
            intent.putExtra("area", area);
            intent.putExtra("stats", stats);
            intent.putExtra("imsak", imsak);
            intent.putExtra("shubuh", subuh);
            intent.putExtra("dhuha", dhuha);
            intent.putExtra("dzuhur", zuhur);
            intent.putExtra("ashar", ashar);
            intent.putExtra("maghrib", magrib);
            intent.putExtra("isya", isya);
            startActivity(intent);

        }else{
            Toast.makeText(MainActivity.this,"Oooops Beri Izin Akses Lokasi dan Temukan Waktu Shalat di Lokasi Anda",Toast.LENGTH_LONG).show();
        }
    }

    void startWaktu() {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
//        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        String jamSekarang = displayFormat.format(new Date());

//       WaktuSekarang sekarang = new WaktuSekarang();
        //      String jamSekarang = sekarang.getJamSekarang();

        String waktuKe = "";
        Integer menitAwal = 0;
        Integer jamAwal = 0;

        String[] sekarangPecah = jamSekarang.split(":");
        Integer jamS = Integer.valueOf(sekarangPecah[0]);
        Integer menitS = Integer.valueOf(sekarangPecah[1]);
        Integer detikS = Integer.valueOf(sekarangPecah[2]);
        Integer jamNowToMenit = jamS * 60;
        Integer menitJadinya = jamNowToMenit + menitS;


        String[] sekarangIsya = isya.split(":");
        Integer jamIsya = Integer.valueOf(sekarangIsya[0]);
        Integer menitIsya = Integer.valueOf(sekarangIsya[1]);
        Integer jamIsyaToMenit = jamIsya * 60;
        Integer menitIsyaJadinya = jamIsyaToMenit + menitIsya;

        String[] sekarangImsak = imsak.split(":");
        Integer jamImsak = Integer.valueOf(sekarangImsak[0]);
        Integer menitImsak = Integer.valueOf(sekarangImsak[1]);
        Integer jamImsakToMenit = jamImsak * 60;
        Integer menitImsakJadinya = jamImsakToMenit + menitImsak;

        String[] sekarangShubuh = subuh.split(":");
        Integer jamSubuh = Integer.valueOf(sekarangShubuh[0]);
        Integer menitSubuh = Integer.valueOf(sekarangShubuh[1]);
        Integer jamSubuhToMenit = jamSubuh * 60;
        Integer menitSubuhJadinya = jamSubuhToMenit + menitSubuh;

        String[] sekarangDhuha = dhuha.split(":");
        Integer jamDhuha = Integer.valueOf(sekarangDhuha[0]);
        Integer menitDhuha = Integer.valueOf(sekarangDhuha[1]);
        Integer jamDhuhaToMenit = jamDhuha * 60;
        Integer menitDhuhaJadinya = jamDhuhaToMenit + menitDhuha;

        String[] sekarangDzuhur = zuhur.split(":");
        Integer jamZuhur = Integer.valueOf(sekarangDzuhur[0]);
        Integer menitZuhur = Integer.valueOf(sekarangDzuhur[1]);
        Integer jamZuhurToMenit = jamZuhur * 60;
        Integer menitZuhurJadinya = jamZuhurToMenit + menitZuhur;

        String[] sekarangAsar = ashar.split(":");
        Integer jamAsar = Integer.valueOf(sekarangAsar[0]);
        Integer menitAsar = Integer.valueOf(sekarangAsar[1]);
        Integer jamAsarToMenit = jamAsar * 60;
        Integer menitAsarJadinya = jamAsarToMenit + menitAsar;

        String[] sekarangMagrib = magrib.split(":");
        Integer jamMagrib = Integer.valueOf(sekarangMagrib[0]);
        Integer menitMagrib = Integer.valueOf(sekarangMagrib[1]);
        Integer jamMagribToMenit = jamMagrib * 60;
        Integer menitMagribJadinya = jamMagribToMenit + menitMagrib;

        if (jamS > jamIsya) {
            waktuKe = "Tengah Malam";
            jamAwal = 23;
            menitAwal = 59;
            imageView.setBackground(getResources().getDrawable(R.drawable.mesjidmalam));

        }

//        else if (jamS<jamImsak && jamS>=0){
//            waktuKe = "Sahur";
//            jamAwal = jamImsak;
//            menitAwal = menitImsak;
//        }
        else if (menitJadinya > 1 && menitJadinya <= menitImsakJadinya) {
            waktuKe = "Imsak";
            jamAwal = jamImsak;
            menitAwal = menitImsak;
            imageView.setBackground(getResources().getDrawable(R.drawable.mesjidmalam));


        } else if (menitJadinya > menitImsakJadinya && menitJadinya <= menitSubuhJadinya) {
            waktuKe = "Shubuh";
            jamAwal = jamSubuh;
            menitAwal = menitSubuh;
            imageView.setBackground(getResources().getDrawable(R.drawable.mesjidmalam));

        } else if (menitJadinya > menitSubuh && menitJadinya <= menitDhuhaJadinya) {
            waktuKe = "Dhuha";
            jamAwal = jamDhuha;
            menitAwal = menitDhuha;
            imageView.setBackground(getResources().getDrawable(R.drawable.mesjidsiang));

        } else if (menitJadinya > menitSubuhJadinya && menitJadinya <= menitZuhurJadinya) {
            waktuKe = "Dzuhur";
            jamAwal = jamZuhur;
            menitAwal = menitZuhur;
            imageView.setBackground(getResources().getDrawable(R.drawable.mesjidsiang));

        } else if (menitJadinya > menitZuhurJadinya && menitJadinya <= menitAsarJadinya) {
            waktuKe = "Ashar";
            jamAwal = jamAsar;
            menitAwal = menitAsar;
            imageView.setBackground(getResources().getDrawable(R.drawable.mesjidsiang));

        } else if (menitJadinya > menitAsarJadinya && menitJadinya <= menitMagribJadinya) {
            waktuKe = "Maghrib";
            jamAwal = jamMagrib;
            menitAwal = menitMagrib;
            imageView.setBackground(getResources().getDrawable(R.drawable.islam));

        } else if (menitJadinya > menitMagribJadinya && menitJadinya <= menitIsyaJadinya) {
            waktuKe = "Isya";
            jamAwal = jamIsya;
            menitAwal = menitIsya;
            imageView.setBackground(getResources().getDrawable(R.drawable.islam));
        }
        waktu.setText(waktuKe);

        futureJam = String.valueOf(jamAwal);
        futureMenit = String.valueOf(menitAwal);

        if (jamAwal<10){
            futureJam = "0"+String.valueOf(jamAwal);
        }
        if (menitAwal<10){
            futureMenit = "0"+String.valueOf(menitAwal);
        }
        jamShalatBerikutnya = futureJam+":"+futureMenit;
        jamShalat.setText(jamShalatBerikutnya);

        Log.d("FUTURETIME",futureJam+":"+futureMenit);

        Integer jamToMenit = jamAwal * 60;
        Integer menitFuture = jamToMenit + menitAwal;

        int milNow = ((menitJadinya * 60) + detikS) * 1000;
        int milFuture = menitFuture * 60 * 1000;

        Integer sisaMil = milFuture - milNow;
        Log.d("SISA", String.valueOf(sisaMil));

//        int noOfMinutes = sisaMenit * 60 * 1000;//Convert minutes into milliseconds
        if (sisaMil <= 0) {
            stopCountdown();
            //   startWaktu();
        } else {
            startTimer(sisaMil);
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
            }
        }.start();

    }

    public void alQuran(View view) {
        Intent intent = new Intent(MainActivity.this, QuranActivity.class);
        startActivity(intent);
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
           LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.disconnect();
        }

    }

    private void handleNewLocation(Location location) {

         currentLatitude = location.getLatitude();
         currentLongitude = location.getLongitude();
         Log.d("HANDLENEWLOCATION",currentLatitude+"-"+currentLongitude);
         getPosisi(currentLatitude,currentLongitude);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Aktifkan Lokasi Terkini")
                .setMessage("GPS Kamu Sedang 'Off'.\nIngin mengaktifkan GPS")
                .setPositiveButton("Pengaturan Lokasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private void showAlertInternet() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Mohon Periksa Internet Anda !!")
                .setMessage("Internet Anda sedang off.\nIngin menggunakan Data Terakhir ")
                .setPositiveButton("Pengaturan Paket data ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        retriveLokasi();

                    }
                })
                .setNegativeButton("Tidak Usah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location locationGoogle = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (locationGoogle == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Log.d("GPSNON:", String.valueOf(currentLatitude));
            retriveLokasi();
            retriveJadwal();

            if (is_nothing){
                startWaktu();
            }

          //  showAlert();
        } else {
            //If everything went fine lets get latitude and longitude
            handleNewLocation(locationGoogle);

           // getPosisi(currentLatitude,currentLongitude);

            Log.d("WORKS", String.valueOf(locationGoogle));

//            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public void saveJadwal(){
        SharedPreferences settingJ =  this.getSharedPreferences(myPrefHarian,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settingJ.edit();
        editor.putString(KeyImsak,imsak);
        editor.putString(KeySubuh,subuh);
        editor.putString(KeyDhuha,dhuha);
        editor.putString(KeyZuhur,zuhur);
        editor.putString(KeyAshar,ashar);
        editor.putString(KeyMagrib,magrib);
        editor.putString(KeyIsya,isya);
        editor.putString(KeyTgl,tanggal);
        editor.putBoolean("is_nothing",true);

        editor.apply();
        editor.commit();

        Log.d("SAVEJADWAL ", String.valueOf(tanggal));

    }

    public void saveLokasi(){
        String lt = String.valueOf(currentLatitude);
        String lg = String.valueOf(currentLongitude);
        String k = kota;
        String s = stats;
        String a = area;
        String n = negara;

        SharedPreferences setting =  this.getSharedPreferences(myPref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(KeyLatti,lt);
        editor.putString(KeyLongi,lg);
        editor.putString(KeyKota,k);
        editor.putString(KeyStats,s);
        editor.putString(KeyArea,a);
        editor.putString(KeyArea,n);

        editor.apply();
        editor.commit();

        Log.d("SAVELOKASI ", String.valueOf(editor));

    }
    public void retriveJadwal(){
        sharedPreferences = getSharedPreferences(myPrefHarian,Context.MODE_PRIVATE);
        prefImsak= sharedPreferences.getString(KeyImsak,"");
        prefSubuh = sharedPreferences.getString(KeySubuh,"");
        prefDhuha= sharedPreferences.getString(KeyDhuha,"");
        prefZuhur = sharedPreferences.getString(KeyZuhur,"");
        prefAshar = sharedPreferences.getString(KeyAshar,"");
        prefMagrib = sharedPreferences.getString(KeyMagrib,"");
        prefIsya = sharedPreferences.getString(KeyIsya,"");
        prefTgl = sharedPreferences.getString(KeyTgl,"");
        is_nothing = sharedPreferences.getBoolean("is_nothing", Boolean.parseBoolean(""));
        imsak = prefImsak;
        subuh = prefSubuh;
        dhuha = prefDhuha;
        zuhur = prefZuhur;
        ashar = prefAshar;
        magrib = prefMagrib;
        isya = prefIsya;
        tanggal = prefTgl;
        Log.d("PREFWAKTU",imsak+","+subuh+","+dhuha+","+zuhur+","+ashar+","+magrib+","+isya);
    }

    public void retriveLokasi(){
        sharedPreferences = getSharedPreferences(myPref,Context.MODE_PRIVATE);
        prefLatti= sharedPreferences.getString(KeyLatti,"");
        prefLongi = sharedPreferences.getString(KeyLongi,"");
        prefKota= sharedPreferences.getString(KeyKota,"");
        prefStats = sharedPreferences.getString(KeyStats,"");
        prefArea = sharedPreferences.getString(KeyArea,"");
        prefNegara = sharedPreferences.getString(KeyNegara,"");

        //Load data lama jadi yang terbaru
        kota = prefKota;
        negara = prefNegara;
        stats = prefStats;
        area = prefArea;

        getSupportActionBar().setTitle(kota);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle(stats + ", " + area);
        toolbar.setSubtitleTextColor(Color.WHITE);
        LokasiModel lokasiModel = new LokasiModel();
        lokasiModel.setKota(kota);
        lokasiModel.setNegara(negara);
        lokasiModel.setArea(area);
        lokasiModel.setStats(stats);

    }

    public void qosidah(View view) {
//        if (interstitialAd.isLoaded()){
//            interstitialAd.show();
//        }else{
            Intent intent = new Intent(MainActivity.this,QosidahActivity.class);
            startActivity(intent);
       //}

    }

    public void Kompas(View view) {
        Intent intent = new Intent(MainActivity.this,KompasActivity.class);
        startActivity(intent);
    }

    public void Doa(View view) {
//       if (interstitialAd2.isLoaded()){
//           interstitialAd2.show();
//       }else {
            Intent intent = new Intent(MainActivity.this, DoaActivity.class);
            startActivity(intent);
       // }
    }

    public void dzikir(View view) {
        Intent intent = new Intent(MainActivity.this,DzikirActivity.class);
        startActivity(intent);
    }

    public void asma(View view) {
        Intent intent = new Intent(MainActivity.this,AsmaulHusnaActivity.class);
        startActivity(intent);

    }

    public void kalender(View view) {
    }



    public void tentang(View view) {
        Intent intent = new Intent(MainActivity.this,Tentang.class);
        startActivity(intent);
    }

    public void sholat(View view) {
        Intent intent = new Intent(MainActivity.this,SholatActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_setting){
            Intent mIntent = new Intent(this, SettingsActivity.class);
            startActivity(mIntent);
            return  true;
        }else if (item.getItemId() == R.id.action_share){
            String pesan =Constant.MSG_APP + " " + Constant.LINK_APP;
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT,pesan);
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        }

        return super.onOptionsItemSelected(item);

    }
}
