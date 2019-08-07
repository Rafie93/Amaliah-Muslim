package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsiQasidah {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_qasidah")
    @Expose
    private String idQasidah;
    @SerializedName("arab")
    @Expose
    private String arab;
    @SerializedName("arti")
    @Expose
    private String arti;
    @SerializedName("judul")
    @Expose
    private String judul;

    public IsiQasidah(String id, String idQasidah, String arab, String arti,String judul) {
        this.id = id;
        this.idQasidah = idQasidah;
        this.arab = arab;
        this.arti = arti;
        this.judul = judul;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdQasidah() {
        return idQasidah;
    }

    public void setIdQasidah(String idQasidah) {
        this.idQasidah = idQasidah;
    }

    public String getArab() {
        return arab;
    }

    public void setArab(String arab) {
        this.arab = arab;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
