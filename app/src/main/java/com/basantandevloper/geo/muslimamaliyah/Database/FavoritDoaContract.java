package com.basantandevloper.geo.muslimamaliyah.Database;

import android.provider.BaseColumns;

public class FavoritDoaContract {
    public static final class FavoritDoaEntry implements BaseColumns {
        public static final String TABLE_NAME = "favDoa";
        public static final String COLUMN_FAV_ID= "fav_id";
        public static final String COLUMN_FAV_NAMA = "fav_nama";
        public static final String COLUMN_FAV_Arab= "fav_arab";
        public static final String COLUMN_FAV_Arti = "fav_arti";
        public static final String COLUMN_FAV_Latin= "fav_latin";
    }
}
