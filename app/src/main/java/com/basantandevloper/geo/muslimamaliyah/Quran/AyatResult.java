package com.basantandevloper.geo.muslimamaliyah.Quran;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AyatResult {
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

    public AyatResult(String ar, String id, String nomor, String tr) {
        this.ar = ar;
        this.id = id;
        this.nomor = nomor;
        this.tr = tr;
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
