package com.basantandevloper.geo.muslimamaliyah.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.basantandevloper.geo.muslimamaliyah.Doa.DoaModel;
import com.basantandevloper.geo.muslimamaliyah.Dzikir.DzikirModel;
import com.basantandevloper.geo.muslimamaliyah.Qosidah.IsiQasidah;
import com.basantandevloper.geo.muslimamaliyah.Qosidah.QosidahModel;
import com.basantandevloper.geo.muslimamaliyah.Quran.AyatModel;
import com.basantandevloper.geo.muslimamaliyah.Quran.SurahModel;
import com.basantandevloper.geo.muslimamaliyah.Doa.ListDoa;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    // Database Version
    private static final int DATABASE_VERSION = 16;
    // Database Name
    private static final String DATABASE_NAME = "MuslimManager.db";
    private String DROP_DZIKIR_TABLE = "DROP TABLE IF EXISTS " + DzikirContract.DzikirEntry.TABLE_NAME;
    private String DROP_FAVORIT_QOSIDAH_TABLE = "DROP TABLE IF EXISTS " + FavoritQosidahContract.FavoritQosidahEntry.TABLE_NAME;
    private String DROP_FAVORIT_QURAN_TABLE = "DROP TABLE IF EXISTS " + FavoritQuranContract.FavoritQuranEntry.TABLE_NAME;
    private String DROP_QOSIDAH_TABLE = "DROP TABLE IF EXISTS " + QosidahContract.QosidahEntry.TABLE_NAME;
    private String DROP_DOA_TABLE = "DROP TABLE IF EXISTS " + DoaContract.DoaEntry.TABLE_NAME;
    private String DROP_SURAH_TABLE = "DROP TABLE IF EXISTS " + SurahContract.SurahEntry.TABLE_NAME;
    private String DROP_FAVORIT_DOA = "DROP TABLE IF EXISTS " + FavoritDoaContract.FavoritDoaEntry.TABLE_NAME;
    private String DROP_DAFTAR_QOSIDAH = "DROP TABLE IF EXISTS " + DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME;
    private String DROP_AYAT_QURAN = "DROP TABLE IF EXISTS " + AyatContract.AyatEntry.TABLE_NAME;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // this.DBHelper = DBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_DZIKIR_TABLE = "CREATE TABLE " + DzikirContract.DzikirEntry.TABLE_NAME + " (" +
                DzikirContract.DzikirEntry._ID + " INTEGER PRIMARY KEY   AUTOINCREMENT," +
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA + " TEXT NOT NULL, " +
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI + " TEXT NOT NULL, " +
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN + " TEXT NOT NULL, " +
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN + " INTEGER NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_DZIKIR_TABLE);

        //
        final String SQL_CREATE_AYAT_TABLE = "CREATE TABLE " + AyatContract.AyatEntry.TABLE_NAME + " (" +
                AyatContract.AyatEntry._ID + " INTEGER PRIMARY KEY   AUTOINCREMENT," +
                AyatContract.AyatEntry.COLUMN_SURAH + " INTEGER NOT NULL, " +
                AyatContract.AyatEntry.COLUMN_NOMOR + " INTEGER NOT NULL, " +
                AyatContract.AyatEntry.COLUMN_ARAB + " TEXT NOT NULL, " +
                AyatContract.AyatEntry.COLUMN_IDN + " TEXT NOT NULL, " +
                AyatContract.AyatEntry.COLUMN_TR + " TEXT NOT NULL, " +
                AyatContract.AyatEntry.COLUMN_TYPE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_AYAT_TABLE);

        //
        final String SQL_CREATE_FAVORIT_QOSIDAH_TABLE = "CREATE TABLE " + FavoritQosidahContract.FavoritQosidahEntry.TABLE_NAME + " (" +
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH + " TEXT NOT NULL, " +
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_NAMA + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORIT_QOSIDAH_TABLE);

        //
        //
        final String SQL_CREATE_DAFTAR_QOSIDAH_TABLE = "CREATE TABLE " + DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME + " (" +
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_DAFTAR_QOSIDAH_TABLE);

        //
        //
        final String SQL_CREATE_FAVORIT_DOA_TABLE = "CREATE TABLE " + FavoritDoaContract.FavoritDoaEntry.TABLE_NAME + " (" +
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_ID + " TEXT NOT NULL, " +
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_NAMA + " TEXT NOT NULL, " +
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arab + " TEXT NOT NULL, " +
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arti + " TEXT NOT NULL, " +
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Latin + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORIT_DOA_TABLE);

        //
        final String SQL_CREATE_FAVORIT_QURAN_TABLE = "CREATE TABLE " + FavoritQuranContract.FavoritQuranEntry.TABLE_NAME + " (" +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAMA + " TEXT NOT NULL," +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ASMA+ " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAME + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_START + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_AYAT + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_TYPE + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_URUT + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_RUKUK + " TEXT NOT NULL, " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ARTI + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORIT_QURAN_TABLE);

        final String SQL_CREATE_QOSIDAH_TABLE = "CREATE TABLE " + QosidahContract.QosidahEntry.TABLE_NAME + " (" +
                QosidahContract.QosidahEntry.COLUMN_ID + " INTEGER NOT NULL," +
                QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH + " INTEGER NOT NULL, " +
                QosidahContract.QosidahEntry.COLUMN_JUDUL + " TEXT NOT NULL, " +
                QosidahContract.QosidahEntry.COLUMN_ARAB + " TEXT NOT NULL, " +
                QosidahContract.QosidahEntry.COLUMN_ARTI + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_QOSIDAH_TABLE);

        final String SQL_CREATE_DOA_TABLE = "CREATE TABLE " + DoaContract.DoaEntry.TABLE_NAME + " (" +
                DoaContract.DoaEntry.COLUMN_ID + " TEXT NOT NULL," +
                DoaContract.DoaEntry.COLUMN_JUDUL + " TEXT NOT NULL, " +
                DoaContract.DoaEntry.COLUMN_ARAB + " TEXT NOT NULL, " +
                DoaContract.DoaEntry.COLUMN_LATHIN + " TEXT NOT NULL, " +
                DoaContract.DoaEntry.COLUMN_ARTI + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_DOA_TABLE);


        final String SQL_INSERT_DZIKIR = " INSERT INTO "+ DzikirContract.DzikirEntry.TABLE_NAME + "("+
                DzikirContract.DzikirEntry._ID+","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN  +
                ") VALUES ('1','Subhanallah','Mahasuci Allah','Subhanllah','33');";

        sqLiteDatabase.execSQL(SQL_INSERT_DZIKIR);

        final String SQL_INSERT_DZIKIR2 = " INSERT INTO "+ DzikirContract.DzikirEntry.TABLE_NAME + "("+
                DzikirContract.DzikirEntry._ID+","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN  +
                ") VALUES ('2','Alhamdulillah','Segala Puji Bagi Allah','Alhamdulillah','33');";

        sqLiteDatabase.execSQL(SQL_INSERT_DZIKIR2);

        final String SQL_INSERT_DZIKIR3 = " INSERT INTO "+ DzikirContract.DzikirEntry.TABLE_NAME + "("+
                DzikirContract.DzikirEntry._ID+","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN  +
                ") VALUES ('3','Allahuakbar','Allah Maha Besar','Allahuakbar','33');";

        sqLiteDatabase.execSQL(SQL_INSERT_DZIKIR3);

        final String SQL_INSERT_DZIKIR4 = " INSERT INTO "+ DzikirContract.DzikirEntry.TABLE_NAME + "("+
                DzikirContract.DzikirEntry._ID+","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN  +
                ") VALUES ('4','LA ILAHA ILLALLAH','Tiada Tuhan Selain Allah', 'LA ILAHA ILLALLAH','99');";

        sqLiteDatabase.execSQL(SQL_INSERT_DZIKIR4);

        final String SQL_INSERT_DZIKIR5 = " INSERT INTO "+ DzikirContract.DzikirEntry.TABLE_NAME + "("+
                DzikirContract.DzikirEntry._ID+","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN +","+
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN  +
                ") VALUES ('5','SHALLALLAHU ALA MUHAMMAD','SHALLALLAHU ALA MUHAMMAD', 'SHALLALLAHU ALA MUHAMMAD','1000');";

        sqLiteDatabase.execSQL(SQL_INSERT_DZIKIR5);

        //
        final String SQL_CREATE_SURAH_TABLE = "CREATE TABLE " + SurahContract.SurahEntry.TABLE_NAME + " (" +
                SurahContract.SurahEntry.COLUMN_NOMOR + " INTEGER NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_NAMA + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_ASMA + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_START + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_AYAT+ " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_URUT + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_RUKUK + " TEXT NOT NULL, " +
                SurahContract.SurahEntry.COLUMN_ARTI + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_SURAH_TABLE);

        //

        final String SQL_INSERT_FAV_SURAH = " INSERT INTO "+ FavoritQuranContract.FavoritQuranEntry.TABLE_NAME + "("+
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN + "," +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAMA + "," +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ASMA+ " , " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAME + ", " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_START + ", " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_AYAT + ", " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_TYPE + ", " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_URUT + ", " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_RUKUK + ", " +
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ARTI +
                ") VALUES ('36','Yaa Siin','يس', 'Yaseen','3705','83','mekah','41','5','YaSin');";

        sqLiteDatabase.execSQL(SQL_INSERT_FAV_SURAH);

        //
        final String SQL_INSERT_FAV_QOSIDAH = " INSERT INTO "+ FavoritQosidahContract.FavoritQosidahEntry.TABLE_NAME + "("+
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH + "," +
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_NAMA +
                ") VALUES ('9','Ahmad Ya Habibi');";

        sqLiteDatabase.execSQL(SQL_INSERT_FAV_QOSIDAH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_DZIKIR_TABLE);
        db.execSQL(DROP_FAVORIT_QOSIDAH_TABLE);
        db.execSQL(DROP_FAVORIT_QURAN_TABLE);
        db.execSQL(DROP_QOSIDAH_TABLE);
        db.execSQL(DROP_DOA_TABLE);
        db.execSQL(DROP_SURAH_TABLE);
        db.execSQL(DROP_FAVORIT_DOA);
        db.execSQL(DROP_DAFTAR_QOSIDAH);
        db.execSQL(DROP_AYAT_QURAN);

        // Create tables again
        onCreate(db);
    }

    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    //Method to create beneficiary records

    public void addDzikir(DzikirModel dzikir) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA, dzikir.getNama());
        values.put(DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI, dzikir.getArti());
        values.put(DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN, dzikir.getBacaan());
        values.put(DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN, dzikir.getHitungan());

        db.insert(DzikirContract.DzikirEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addAyat(AyatModel ayat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AyatContract.AyatEntry.COLUMN_SURAH, Integer.valueOf(ayat.getSurah()));
        values.put(AyatContract.AyatEntry.COLUMN_NOMOR, Integer.valueOf(ayat.getNomor()));
        values.put(AyatContract.AyatEntry.COLUMN_ARAB, ayat.getAr());
        values.put(AyatContract.AyatEntry.COLUMN_IDN, ayat.getId());
        values.put(AyatContract.AyatEntry.COLUMN_TR, ayat.getTr());
        values.put(AyatContract.AyatEntry.COLUMN_TYPE, ayat.getType());
        db.insert(AyatContract.AyatEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void addFavQosidah(QosidahModel qosidahModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH, qosidahModel.getId());
        values.put(FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_NAMA, qosidahModel.getJudul());

        db.insert(FavoritQosidahContract.FavoritQosidahEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addDafQosidah(QosidahModel qosidahModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID,Integer.valueOf( qosidahModel.getId()));
        values.put(DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL, qosidahModel.getJudul());

        db.insert(DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addFavDoa(ListDoa doaModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_ID, doaModel.getId());
        values.put(FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_NAMA, doaModel.getJudul());
        values.put(FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arab, doaModel.getIsiArab());
        values.put(FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arti, doaModel.getArti());
        values.put(FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Latin, doaModel.getLatin());


        db.insert(FavoritDoaContract.FavoritDoaEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addFavQuran(SurahModel surahModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN, surahModel.getNomor());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAMA, surahModel.getNama());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ASMA, surahModel.getAsma());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAME, surahModel.getName());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_START, surahModel.getStart());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_AYAT, surahModel.getAyat());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_TYPE, surahModel.getType());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_URUT, surahModel.getUrut());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_RUKUK, surahModel.getRukuk());
        values.put(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ARTI, surahModel.getArti());

        db.insert(FavoritQuranContract.FavoritQuranEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addQosidah(IsiQasidah qasidah) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QosidahContract.QosidahEntry.COLUMN_ID, Integer.valueOf(qasidah.getId()));
        values.put(QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH, Integer.valueOf(qasidah.getIdQasidah()));
        values.put(QosidahContract.QosidahEntry.COLUMN_JUDUL, qasidah.getJudul());
        values.put(QosidahContract.QosidahEntry.COLUMN_ARAB, qasidah.getArab());
        values.put(QosidahContract.QosidahEntry.COLUMN_ARTI, qasidah.getArti());

        db.insert(QosidahContract.QosidahEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addDoa(ListDoa doa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DoaContract.DoaEntry.COLUMN_ID, doa.getId());
        values.put(DoaContract.DoaEntry.COLUMN_JUDUL, doa.getJudul());
        values.put(DoaContract.DoaEntry.COLUMN_ARAB, doa.getIsiArab());
        values.put(DoaContract.DoaEntry.COLUMN_LATHIN, doa.getLatin());
        values.put(DoaContract.DoaEntry.COLUMN_ARTI, doa.getArti());

        db.insert(DoaContract.DoaEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void addSurah(SurahModel surah) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SurahContract.SurahEntry.COLUMN_NOMOR, Integer.valueOf(surah.getNomor()));
        values.put(SurahContract.SurahEntry.COLUMN_NAMA, surah.getNama());
        values.put(SurahContract.SurahEntry.COLUMN_ASMA, surah.getAsma());
        values.put(SurahContract.SurahEntry.COLUMN_NAME, surah.getName());
        values.put(SurahContract.SurahEntry.COLUMN_START, surah.getStart());
        values.put(SurahContract.SurahEntry.COLUMN_AYAT, surah.getAyat());
        values.put(SurahContract.SurahEntry.COLUMN_TYPE, surah.getType());
        values.put(SurahContract.SurahEntry.COLUMN_RUKUK, surah.getRukuk());
        values.put(SurahContract.SurahEntry.COLUMN_URUT, surah.getUrut());
        values.put(SurahContract.SurahEntry.COLUMN_ARTI, surah.getArti());
        db.insert(SurahContract.SurahEntry.TABLE_NAME, null, values);
        db.close();
    }

    public List<AyatModel> getAyat(String surah,String type, Integer limit) {
        List<AyatModel> ayatModels = new ArrayList<AyatModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + AyatContract.AyatEntry.TABLE_NAME +
                        " WHERE "+AyatContract.AyatEntry.COLUMN_SURAH+" = '"+surah+"'"+" AND "+
                        AyatContract.AyatEntry.COLUMN_TYPE+"='"+type+"' LIMIT "+String.valueOf(limit),
                new String[]{});

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AyatModel model = new AyatModel(
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_SURAH)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_ARAB)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_IDN)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_NOMOR)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_TR))

                );
                // Adding user record to list
                ayatModels.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return ayatModels;
    }

    public List<AyatModel>getAyatJuz(String type, String mulaiSurah, String mulaiAyat, String endSurah, String endAyat, int limit){
        List<AyatModel> ayatModels = new ArrayList<AyatModel>();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor= db.rawQuery("SELECT * FROM (" +
                        " SELECT * FROM " + AyatContract.AyatEntry.TABLE_NAME +
                        " WHERE "+AyatContract.AyatEntry.COLUMN_TYPE+" = '"+type+"'"+" AND "+
                        AyatContract.AyatEntry.COLUMN_SURAH+"="+Integer.valueOf(mulaiSurah)+" AND "+
                        AyatContract.AyatEntry.COLUMN_NOMOR+">="+Integer.valueOf(mulaiAyat)+
                        " UNION ALL SELECT * FROM "+ AyatContract.AyatEntry.TABLE_NAME +
                        " WHERE "+AyatContract.AyatEntry.COLUMN_TYPE+" = '"+type+"'"+" AND "+
                        AyatContract.AyatEntry.COLUMN_SURAH+">"+Integer.valueOf(mulaiSurah)+" AND "+
                        AyatContract.AyatEntry.COLUMN_SURAH+"<"+Integer.valueOf(endSurah)+
                        " UNION ALL SELECT * FROM "+ AyatContract.AyatEntry.TABLE_NAME +
                        " WHERE "+AyatContract.AyatEntry.COLUMN_TYPE+" = '"+type+"'"+" AND "+
                        AyatContract.AyatEntry.COLUMN_SURAH+"="+Integer.valueOf(endSurah)+" AND "+
                        AyatContract.AyatEntry.COLUMN_NOMOR+"<="+Integer.valueOf(endAyat)+
                        ") LIMIT "+limit,
                new String[]{});

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AyatModel model = new AyatModel(
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_SURAH)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_ARAB)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_IDN)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_NOMOR)),
                        cursor.getString(cursor.getColumnIndex(AyatContract.AyatEntry.COLUMN_TR))

                );
                // Adding user record to list
                ayatModels.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return ayatModels;
    }
    public List<DzikirModel> getAllDzikir() {
        // array of columns to fetch
        String[] columns = {
                DzikirContract.DzikirEntry._ID,
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA,
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI,
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN,
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN
        };
        // sorting orders
        String sortOrder =
                DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA + " ASC";
        List<DzikirModel> dzikirModelList = new ArrayList<DzikirModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(DzikirContract.DzikirEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DzikirModel dzikir = new DzikirModel();
                dzikir.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DzikirContract.DzikirEntry._ID))));
                dzikir.setNama(cursor.getString(cursor.getColumnIndex(DzikirContract.DzikirEntry.COLUMN_DZIKIR_NAMA)));
                dzikir.setArti(cursor.getString(cursor.getColumnIndex(DzikirContract.DzikirEntry.COLUMN_DZIKIR_ARTI)));
                dzikir.setBacaan(cursor.getString(cursor.getColumnIndex(DzikirContract.DzikirEntry.COLUMN_DZIKIR_BACAAN)));
                dzikir.setHitungan(cursor.getString(cursor.getColumnIndex(DzikirContract.DzikirEntry.COLUMN_DZIKIR_HITUNGAN)));
                // Adding user record to list
                dzikirModelList.add(dzikir);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return dzikirModelList;
    }

    public List<QosidahModel> getAllFavQosidah() {
        // array of columns to fetch
        String[] columns = {
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH,
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_NAMA,
        };
        // sorting orders
        String sortOrder =FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH + " ASC";
        List<QosidahModel> listqosidahModels = new ArrayList<QosidahModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(FavoritQosidahContract.FavoritQosidahEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QosidahModel qosidahModel = new QosidahModel(
                        cursor.getString(cursor.getColumnIndex(FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH)),
                        cursor.getString(cursor.getColumnIndex(FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_NAMA))
                );
                // Adding user record to list
                listqosidahModels.add(qosidahModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listqosidahModels;
    }
    //
    public int getJumlahDataQosidah(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM " + DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME,
                new String[]{});

        int count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
    public int getJumlahAyatJuz(String type, String mulaiSurah, String mulaiAyat, String endSurah, String endAyat){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM " + AyatContract.AyatEntry.TABLE_NAME +
                        " WHERE "+AyatContract.AyatEntry.COLUMN_TYPE+" = '"+type+"'"+" AND ("+
                        AyatContract.AyatEntry.COLUMN_SURAH+">="+Integer.valueOf(mulaiSurah)+" AND "+
                        AyatContract.AyatEntry.COLUMN_NOMOR+">="+Integer.valueOf(mulaiAyat)+") AND ("+
                        AyatContract.AyatEntry.COLUMN_SURAH+"<="+Integer.valueOf(endSurah)+" AND "+
                        AyatContract.AyatEntry.COLUMN_NOMOR+"<="+Integer.valueOf(endAyat)+")",
                new String[]{});


        int count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();
        db.close();
        return count;
    }
    public int getJumlahDataDoa(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM " + DoaContract.DoaEntry.TABLE_NAME,
                new String[]{});

        int count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();
        db.close();
        return count;
    }

    public int getJumlahDataAyat(String typ){

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM " + AyatContract.AyatEntry.TABLE_NAME+" WHERE "
                        +AyatContract.AyatEntry.COLUMN_TYPE +" = '"+ typ +"'",
                new String[]{});

        int count = 0;
        if(null != cursor)
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();
        db.close();
        return count;
    }

    public List<QosidahModel> getGroupIsiQosidah() {
        List<QosidahModel> listqosidahModels = new ArrayList<QosidahModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH+ " AS "+
                        DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID +","+ QosidahContract.QosidahEntry.COLUMN_JUDUL +
                        " AS " + DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL+
                        " FROM ( SELECT "+ QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH+","+
                        QosidahContract.QosidahEntry.COLUMN_JUDUL +" FROM "+QosidahContract.QosidahEntry.TABLE_NAME+
                        " GROUP BY "+QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH+","+
                        QosidahContract.QosidahEntry.COLUMN_JUDUL +" ORDER BY " +QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH +"  )",
                new String[]{});

        Log.d("JUMHGROUP", "getGroupIsiQosidah: "+cursor.getCount());
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QosidahModel qosidahModel = new QosidahModel(
                        cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL))
                );
                // Adding user record to list
                listqosidahModels.add(qosidahModel);
                qosidahModel.setId(cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID)));
                qosidahModel.setJudul(cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL)));
                addDafQosidah(qosidahModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listqosidahModels;
    }

    public List<QosidahModel> getAllDaftarQosidah(Integer limit) {
        // array of columns to fetch
        String[] columns = {
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID,
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL,
        };
        // sorting orders
        String sortOrder = DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID + " ASC";
        List<QosidahModel> listqosidahModels = new ArrayList<QosidahModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query( DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null,
                String.valueOf(limit)); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QosidahModel qosidahModel = new QosidahModel(
                        cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL))
                );
                // Adding user record to list
                listqosidahModels.add(qosidahModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listqosidahModels;
    }

    public List<QosidahModel> getCariDaftarQosidah(String cari) {
        // array of columns to fetch
        String[] columns = {
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID,
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL,
        };
        // sorting orders
        String sortOrder = DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID + " ASC";
        List<QosidahModel> listqosidahModels = new ArrayList<QosidahModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query( DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL +" LIKE '%"+cari+"%'",        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QosidahModel qosidahModel = new QosidahModel(
                        cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex( DaftarQosidahContract.DaftarQosidahEntry.COLUMN_JUDUL))
                );
                // Adding user record to list
                listqosidahModels.add(qosidahModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listqosidahModels;
    }

    //
    public List<ListDoa> getFavDoa() {
        // array of columns to fetch
        String[] columns = {
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_ID,
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_NAMA,
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arab,
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arti,
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Latin
        };
        // sorting orders
        String sortOrder = FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_ID + " ASC";
        List<ListDoa> listDoas = new ArrayList<ListDoa>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query( FavoritDoaContract.FavoritDoaEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListDoa listDoa = new ListDoa(
                        cursor.getInt(cursor.getColumnIndex( FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_ID)),
                        cursor.getString(cursor.getColumnIndex( FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_NAMA)),
                        cursor.getString(cursor.getColumnIndex( FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arab)),
                        cursor.getString(cursor.getColumnIndex( FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Arti)),
                        cursor.getString(cursor.getColumnIndex( FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_Latin))
                );
                // Adding user record to list
                listDoas.add(listDoa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listDoas;
    }

    public List<SurahModel> getAllFavQuran() {
        // array of columns to fetch

        String[] columns = {
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAMA,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ASMA,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAME,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_START,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_AYAT,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_TYPE,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_URUT,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_RUKUK,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ARTI
        };
        // sorting orders
        String sortOrder =FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN + " ASC";
        List<SurahModel> listsurahModel = new ArrayList<SurahModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoritQuranContract.FavoritQuranEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SurahModel surahModel = new SurahModel(
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAMA)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ASMA)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_NAME)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_START)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_AYAT)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_TYPE)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_URUT)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_RUKUK)),
                        cursor.getString(cursor.getColumnIndex(FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ARTI))
                );
                // Adding user record to list
                listsurahModel.add(surahModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listsurahModel;
    }

    public List<IsiQasidah> getIsiQasidah(String id_qosidah) {
        // array of columns to fetch
        String[] columns = {
                QosidahContract.QosidahEntry.COLUMN_ID,
                QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH,
                QosidahContract.QosidahEntry.COLUMN_ARAB,
                QosidahContract.QosidahEntry.COLUMN_ARTI,
                QosidahContract.QosidahEntry.COLUMN_JUDUL
        };
        // sorting orders
        String sortOrder =
                QosidahContract.QosidahEntry.COLUMN_ID + " ASC";
        List<IsiQasidah> isiQasidahs = new ArrayList<IsiQasidah>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(QosidahContract.QosidahEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH+"="+id_qosidah,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                IsiQasidah isiQasidah = new IsiQasidah(
                    cursor.getString(cursor.getColumnIndex(QosidahContract.QosidahEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH)),
                    cursor.getString(cursor.getColumnIndex(QosidahContract.QosidahEntry.COLUMN_ARAB)),
                    cursor.getString(cursor.getColumnIndex(QosidahContract.QosidahEntry.COLUMN_ARTI)),
                    cursor.getString(cursor.getColumnIndex(QosidahContract.QosidahEntry.COLUMN_JUDUL))
                );
                // Adding user record to list
                isiQasidahs.add(isiQasidah);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return isiQasidahs;
    }

    public List<ListDoa> getAllDoa(Integer limit) {
        // array of columns to fetch
        String[] columns = {
                DoaContract.DoaEntry.COLUMN_ID,
                DoaContract.DoaEntry.COLUMN_JUDUL,
                DoaContract.DoaEntry.COLUMN_ARAB,
                DoaContract.DoaEntry.COLUMN_ARTI,
                DoaContract.DoaEntry.COLUMN_LATHIN
        };
        // sorting orders
        String sortOrder =
                DoaContract.DoaEntry.COLUMN_JUDUL + " ASC";
        List<ListDoa> listDoas = new ArrayList<ListDoa>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(DoaContract.DoaEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null,
                String.valueOf(limit)); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListDoa doa = new ListDoa(
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_ID))),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_JUDUL)),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_ARAB)),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_ARTI)),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_LATHIN))
                );
                // Adding user record to list
                listDoas.add(doa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listDoas;
    }
    public List<ListDoa> getCariDoa(String cari) {
        // array of columns to fetch
        String[] columns = {
                DoaContract.DoaEntry.COLUMN_ID,
                DoaContract.DoaEntry.COLUMN_JUDUL,
                DoaContract.DoaEntry.COLUMN_ARAB,
                DoaContract.DoaEntry.COLUMN_ARTI,
                DoaContract.DoaEntry.COLUMN_LATHIN
        };
        // sorting orders
        String sortOrder =
                DoaContract.DoaEntry.COLUMN_JUDUL + " ASC";
        List<ListDoa> listDoas = new ArrayList<ListDoa>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(DoaContract.DoaEntry.TABLE_NAME, //Table to query
                columns,    //columns to returnaddsURAH
                DoaContract.DoaEntry.COLUMN_JUDUL +" LIKE '%"+cari+"%'",    //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListDoa doa = new ListDoa(
                        Integer.valueOf(cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_ID))),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_JUDUL)),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_ARAB)),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_ARTI)),
                        cursor.getString(cursor.getColumnIndex(DoaContract.DoaEntry.COLUMN_LATHIN))
                );
                // Adding user record to list
                listDoas.add(doa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return listDoas;
    }
    public List<SurahModel> getAllSurah(Integer limit) {
        // array of columns to fetch
        String[] columns = {
                SurahContract.SurahEntry.COLUMN_NOMOR ,
                SurahContract.SurahEntry.COLUMN_NAMA ,
                SurahContract.SurahEntry.COLUMN_NAME,
                SurahContract.SurahEntry.COLUMN_ASMA ,
                SurahContract.SurahEntry.COLUMN_START ,
                SurahContract.SurahEntry.COLUMN_AYAT,
                SurahContract.SurahEntry.COLUMN_TYPE ,
                SurahContract.SurahEntry.COLUMN_URUT ,
                SurahContract.SurahEntry.COLUMN_RUKUK ,
                SurahContract.SurahEntry.COLUMN_ARTI
        };
        // sorting orders
//        String sortOrder =
//                DoaContract.DoaEntry.COLUMN_JUDUL + " ASC";
        List<SurahModel> surahModels = new ArrayList<SurahModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(SurahContract.SurahEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null,
                String.valueOf(limit)); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SurahModel surah = new SurahModel(
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NOMOR)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NAMA)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_ASMA)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_START)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_AYAT)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_RUKUK)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_ARTI))
                );
                // Adding user record to list
                surahModels.add(surah);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return surahModels;
    }
    public List<SurahModel> getCariSurah(String cari) {
        // array of columns to fetch
        String[] columns = {
                SurahContract.SurahEntry.COLUMN_NOMOR ,
                SurahContract.SurahEntry.COLUMN_NAMA ,
                SurahContract.SurahEntry.COLUMN_NAME,
                SurahContract.SurahEntry.COLUMN_ASMA ,
                SurahContract.SurahEntry.COLUMN_START ,
                SurahContract.SurahEntry.COLUMN_AYAT,
                SurahContract.SurahEntry.COLUMN_TYPE ,
                SurahContract.SurahEntry.COLUMN_URUT ,
                SurahContract.SurahEntry.COLUMN_RUKUK ,
                SurahContract.SurahEntry.COLUMN_ARTI
        };
        // sorting orders
//        String sortOrder =
//                DoaContract.DoaEntry.COLUMN_JUDUL + " ASC";
        List<SurahModel> surahModels = new ArrayList<SurahModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(SurahContract.SurahEntry.TABLE_NAME, //Table to query
                columns,    //columns to returnaddsURAH
                SurahContract.SurahEntry.COLUMN_NAMA+" LIKE '%"+cari+"%' OR "+
                        SurahContract.SurahEntry.COLUMN_NAME+" LIKE '%"+cari+"%'",     //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SurahModel surah = new SurahModel(
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NOMOR)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NAMA)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_ASMA)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_START)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_AYAT)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_RUKUK)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_ARTI))
                );
                // Adding user record to list
                surahModels.add(surah);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return surahModels;
    }

    public List<SurahModel> getCariSurahLast(String cari) {
        // array of columns to fetch
        String[] columns = {
                SurahContract.SurahEntry.COLUMN_NOMOR ,
                SurahContract.SurahEntry.COLUMN_NAMA ,
                SurahContract.SurahEntry.COLUMN_NAME,
                SurahContract.SurahEntry.COLUMN_ASMA ,
                SurahContract.SurahEntry.COLUMN_START ,
                SurahContract.SurahEntry.COLUMN_AYAT,
                SurahContract.SurahEntry.COLUMN_TYPE ,
                SurahContract.SurahEntry.COLUMN_URUT ,
                SurahContract.SurahEntry.COLUMN_RUKUK ,
                SurahContract.SurahEntry.COLUMN_ARTI
        };
        // sorting orders
//        String sortOrder =
//                DoaContract.DoaEntry.COLUMN_JUDUL + " ASC";
        List<SurahModel> surahModels = new ArrayList<SurahModel>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(SurahContract.SurahEntry.TABLE_NAME, //Table to query
                columns,    //columns to returnaddsURAH
                SurahContract.SurahEntry.COLUMN_NAMA+" = '"+cari+"'",     //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SurahModel surah = new SurahModel(
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NOMOR)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NAMA)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_ASMA)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_START)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_AYAT)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_URUT)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_RUKUK)),
                        cursor.getString(cursor.getColumnIndex(SurahContract.SurahEntry.COLUMN_ARTI))
                );
                // Adding user record to list
                surahModels.add(surah);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return surahModels;
    }

    public void deleteDzikir (String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DzikirContract.DzikirEntry.TABLE_NAME,
                DzikirContract.DzikirEntry._ID+"="+id, null);

    }
    public void deleteFavQosidah (String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoritQosidahContract.FavoritQosidahEntry.TABLE_NAME,
                FavoritQosidahContract.FavoritQosidahEntry.COLUMN_FAV_ID_QOSIDAH+ "=" + id, null);

    }
    public void deleteAllDafQosidah () {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DaftarQosidahContract.DaftarQosidahEntry.TABLE_NAME,null, null);

    }

    public void deleteFavDoa (String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoritDoaContract.FavoritDoaEntry.TABLE_NAME,
                FavoritDoaContract.FavoritDoaEntry.COLUMN_FAV_ID+"="+id, null);

    }
    public void deleteFavQuran (String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoritQuranContract.FavoritQuranEntry.TABLE_NAME,
                FavoritQuranContract.FavoritQuranEntry.COLUMN_FAV_ID_QURAN+"="+id,null);
    }
    public void deleteQosidah (String id_qosidah) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QosidahContract.QosidahEntry.TABLE_NAME,
                QosidahContract.QosidahEntry.COLUMN_ID_QOSIDAH+"="+id_qosidah,null);
    }
    public void deleteAllQosidah () {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QosidahContract.QosidahEntry.TABLE_NAME,null,null);
    }
    public void deleteDoa () {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DoaContract.DoaEntry.TABLE_NAME,null,null);
    }
    public void deleteSurah () {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SurahContract.SurahEntry.TABLE_NAME,null,null);
    }

    public void deleteAyat(String typ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AyatContract.AyatEntry.TABLE_NAME,AyatContract.AyatEntry.COLUMN_TYPE+"='"+typ+"'",null);
    }
    public void deleteAyatSurah(String typ,String nomor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AyatContract.AyatEntry.TABLE_NAME,AyatContract.AyatEntry.COLUMN_TYPE+"='"+typ+"' AND "+
                AyatContract.AyatEntry.COLUMN_SURAH+"="+Integer.valueOf(nomor),null);

    }
}
