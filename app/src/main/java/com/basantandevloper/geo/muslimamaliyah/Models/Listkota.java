package com.basantandevloper.geo.muslimamaliyah.Models;
import com.google.gson.annotations.SerializedName;

public class Listkota {
    @SerializedName("id")
    public String id;
    @SerializedName("nama")
    public String nama;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }
}
