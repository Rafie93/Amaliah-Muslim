package com.basantandevloper.geo.muslimamaliyah.Doa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListDoa {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("isi_arab")
    @Expose
    private String isiArab;
    @SerializedName("arti")
    @Expose
    private String arti;
    @SerializedName("latin")
    @Expose
    private String latin;

    public ListDoa(Integer id, String judul, String isiArab, String arti, String latin) {
        this.id = id;
        this.judul = judul;
        this.isiArab = isiArab;
        this.arti = arti;
        this.latin = latin;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsiArab() {
        return isiArab;
    }

    public void setIsiArab(String isiArab) {
        this.isiArab = isiArab;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

}
