package com.basantandevloper.geo.muslimamaliyah.Database;

import android.provider.BaseColumns;

public class FavoritQosidahContract {
    public static final class FavoritQosidahEntry implements BaseColumns {
        public static final String TABLE_NAME = "favQosidah";
        public static final String COLUMN_FAV_ID_QOSIDAH = "fav_id";
        public static final String COLUMN_FAV_NAMA = "fav_nama";
    }
}
