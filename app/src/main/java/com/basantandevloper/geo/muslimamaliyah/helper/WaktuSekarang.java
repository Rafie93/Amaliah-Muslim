package com.basantandevloper.geo.muslimamaliyah.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WaktuSekarang {
    public String jamSekarang;
    public String tglSekarang;
    public String tglParse;
    public String getJamSekarang(){
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
//        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        jamSekarang = displayFormat.format(todayDate);
        return jamSekarang;
    }
    public String getTglSekarang(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat  = new SimpleDateFormat("yyyy-MM-dd");

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        tglSekarang = mdformat.format(calendar.getTime());
        return tglSekarang;
    }

    public String getTglParse(String tanggal) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("YYYY-MM-DD");
        SimpleDateFormat parseFormat = new SimpleDateFormat("DD-MM-YYYY");
        try {
            Date date = parseFormat.parse(tanggal);
           tglParse = String.valueOf(displayFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tglParse;
    }
}
