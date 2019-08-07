package com.basantandevloper.geo.muslimamaliyah.Quran;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AyatModel {
    @SerializedName("surah")
    @Expose
    private String surah;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ar")
    @Expose
    private String ar;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nomor")
    @Expose
    private String nomor;
    @SerializedName("tr")
    @Expose
    private String tr;

    public AyatModel(String surah, String type, String ar, String id, String nomor, String tr) {
        this.surah = surah;
        this.type = type;
        this.ar = ar;
        this.id = id;
        this.nomor = nomor;
        this.tr = tr;
    }

    public String getSurah() {
        return surah;
    }

    public void setSurah(String surah) {
        this.surah = surah;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }
}
