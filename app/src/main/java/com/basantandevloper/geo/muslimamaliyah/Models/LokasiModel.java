package com.basantandevloper.geo.muslimamaliyah.Models;

public class LokasiModel {
    private String kota;
    private String negara;
    private String stats;
    private String area;

//    public LokasiModel(String kota, String negara, String stats, String area) {
//        this.kota = kota;
//        this.negara = negara;
//        this.stats = stats;
//        this.area = area;
//    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
