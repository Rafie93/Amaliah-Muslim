package com.basantandevloper.geo.muslimamaliyah.Database;

import android.provider.BaseColumns;

public class AyatContract {
    public static final class AyatEntry implements BaseColumns {
        public static final String TABLE_NAME = "ayat";
        public static final String COLUMN_NOMOR = "nomor";
        public static final String COLUMN_ARAB = "arab";
        public static final String COLUMN_IDN = "idn";
        public static final String COLUMN_TR = "tr";
        public static final String COLUMN_SURAH = "surah";
        public static final String COLUMN_TYPE = "type";

    }
}
