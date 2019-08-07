package com.basantandevloper.geo.muslimamaliyah.Models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Kota {
    @SerializedName("kota")
    public List<Listkota> listkotas;

    String status;

    public Kota(List<Listkota> listkotas) {
        this.listkotas = listkotas;
    }

    public List<Listkota> getListkotas() {
        return listkotas;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
