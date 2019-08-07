package com.basantandevloper.geo.muslimamaliyah.Quran;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JuzModel {
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("start_surah")
    @Expose
    private String startSurah;
    @SerializedName("start_ayat")
    @Expose
    private String startAyat;
    @SerializedName("end_surah")
    @Expose
    private String endSurah;
    @SerializedName("end_ayat")
    @Expose
    private String endAyat;
    @SerializedName("start_name")
    @Expose
    private String startName;
    @SerializedName("end_name")
    @Expose
    private String endName;

    public JuzModel(String index, String startSurah, String startAyat, String endSurah, String endAyat, String startName, String endName) {
        this.index = index;
        this.startSurah = startSurah;
        this.startAyat = startAyat;
        this.endSurah = endSurah;
        this.endAyat = endAyat;
        this.startName = startName;
        this.endName = endName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStartSurah() {
        return startSurah;
    }

    public void setStartSurah(String startSurah) {
        this.startSurah = startSurah;
    }

    public String getStartAyat() {
        return startAyat;
    }

    public void setStartAyat(String startAyat) {
        this.startAyat = startAyat;
    }

    public String getEndSurah() {
        return endSurah;
    }

    public void setEndSurah(String endSurah) {
        this.endSurah = endSurah;
    }

    public String getEndAyat() {
        return endAyat;
    }

    public void setEndAyat(String endAyat) {
        this.endAyat = endAyat;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }
}
