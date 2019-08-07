package com.basantandevloper.geo.muslimamaliyah.Quran;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurahModel {

    @SerializedName("nomor")
    @Expose
    private String nomor;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("asma")
    @Expose
    private String asma;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("ayat")
    @Expose
    private String ayat;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("urut")
    @Expose
    private String urut;
    @SerializedName("rukuk")
    @Expose
    private String rukuk;
    @SerializedName("arti")
    @Expose
    private String arti;

    public SurahModel(String nomor, String nama, String asma, String name, String start, String ayat, String type, String urut, String rukuk, String arti) {
        this.nomor = nomor;
        this.nama = nama;
        this.asma = asma;
        this.name = name;
        this.start = start;
        this.ayat = ayat;
        this.type = type;
        this.urut = urut;
        this.rukuk = rukuk;
        this.arti = arti;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAsma() {
        return asma;
    }

    public void setAsma(String asma) {
        this.asma = asma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getAyat() {
        return ayat;
    }

    public void setAyat(String ayat) {
        this.ayat = ayat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }

    public String getRukuk() {
        return rukuk;
    }

    public void setRukuk(String rukuk) {
        this.rukuk = rukuk;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

}
