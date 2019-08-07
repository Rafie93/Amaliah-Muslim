package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QosidahModel implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("judul")
    private String judul;

    public QosidahModel(String id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    public String getId() {
        return id;
    }


    public String getJudul() {
        return judul;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
