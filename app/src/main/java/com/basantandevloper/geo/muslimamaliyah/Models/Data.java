package com.basantandevloper.geo.muslimamaliyah.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("ashar")
    @Expose
    private String ashar;
    @SerializedName("dhuha")
    @Expose
    private String dhuha;
    @SerializedName("dzuhur")
    @Expose
    private String dzuhur;
    @SerializedName("imsak")
    @Expose
    private String imsak;
    @SerializedName("isya")
    @Expose
    private String isya;
    @SerializedName("maghrib")
    @Expose
    private String maghrib;
    @SerializedName("subuh")
    @Expose
    private String subuh;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("terbit")
    @Expose
    private String terbit;

    public String getAshar() {
        return ashar;
    }

    public void setAshar(String ashar) {
        this.ashar = ashar;
    }

    public String getDhuha() {
        return dhuha;
    }

    public void setDhuha(String dhuha) {
        this.dhuha = dhuha;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public void setDzuhur(String dzuhur) {
        this.dzuhur = dzuhur;
    }

    public String getImsak() {
        return imsak;
    }

    public void setImsak(String imsak) {
        this.imsak = imsak;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getSubuh() {
        return subuh;
    }

    public void setSubuh(String subuh) {
        this.subuh = subuh;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTerbit() {
        return terbit;
    }

    public void setTerbit(String terbit) {
        this.terbit = terbit;
    }
}