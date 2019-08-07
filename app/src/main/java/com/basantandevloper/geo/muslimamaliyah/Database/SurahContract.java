package com.basantandevloper.geo.muslimamaliyah.Database;

import android.provider.BaseColumns;

public class SurahContract {
    public static final class SurahEntry implements BaseColumns {
        public static final String TABLE_NAME = "surah";
        public static final String COLUMN_NOMOR = "nomor";
        public static final String COLUMN_NAMA = "nama";
        public static final String COLUMN_ASMA = "asma";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_START = "start";
        public static final String COLUMN_AYAT = "ayat";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_URUT = "urut";
        public static final String COLUMN_RUKUK = "rukuk";
        public static final String COLUMN_ARTI = "arti";
    }
}
