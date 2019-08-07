package com.basantandevloper.geo.muslimamaliyah.Sholat;

public class SholatModel {
    private String id;
    private String judul;
    private String isi;

    public SholatModel(String id, String judul, String isi) {
        this.id = id;
        this.judul = judul;
        this.isi = isi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
