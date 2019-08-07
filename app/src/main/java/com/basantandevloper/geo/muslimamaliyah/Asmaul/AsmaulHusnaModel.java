package com.basantandevloper.geo.muslimamaliyah.Asmaul;

public class AsmaulHusnaModel {
    private String id;
    private String nama;
    private String arti;
    private String arab;

    public AsmaulHusnaModel(String id, String nama, String arti, String arab) {
        this.id = id;
        this.nama = nama;
        this.arti = arti;
        this.arab = arab;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getArti() {
        return arti;
    }

    public String getArab() {
        return arab;
    }
}
