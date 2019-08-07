package com.basantandevloper.geo.muslimamaliyah.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JadwalModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("jadwal")
    @Expose
    private Jadwal jadwal;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public void setJadwal(Jadwal jadwal) {
        this.jadwal = jadwal;
    }

}