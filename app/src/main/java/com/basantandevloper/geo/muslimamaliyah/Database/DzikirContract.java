package com.basantandevloper.geo.muslimamaliyah.Database;

import android.provider.BaseColumns;

public class DzikirContract {
    public static final class DzikirEntry implements BaseColumns{
        public static final String TABLE_NAME = "dzikir";
        public static final String COLUMN_DZIKIR_NAMA = "dzikir_nama";
        public static final String COLUMN_DZIKIR_ARTI = "dzikir_arti";
        public static final String COLUMN_DZIKIR_BACAAN = "dzikir_bacaan";
        public static final String COLUMN_DZIKIR_HITUNGAN = "dzikir_hitungan";

    }
}
