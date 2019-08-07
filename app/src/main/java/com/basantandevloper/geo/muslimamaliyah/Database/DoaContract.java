package com.basantandevloper.geo.muslimamaliyah.Database;

import android.provider.BaseColumns;

public class DoaContract {
    public static final class DoaEntry implements BaseColumns {
        public static final String TABLE_NAME = "doa";
        public static final String COLUMN_ID = "doa_id";
        public static final String COLUMN_JUDUL = "doa_judul";
        public static final String COLUMN_ARAB = "doa_arab";
        public static final String COLUMN_LATHIN = "doa_lathin";
        public static final String COLUMN_ARTI = "doa_arti";
    }
}
