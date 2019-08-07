package com.basantandevloper.geo.muslimamaliyah.Qosidah;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mqasidah {
    @SerializedName("isi_qasidah")
    @Expose
    private List<IsiQasidah> isiQasidah = null;

    public List<IsiQasidah> getIsiQasidah() {
        return isiQasidah;
    }

    public void setIsiQasidah(List<IsiQasidah> isiQasidah) {
        this.isiQasidah = isiQasidah;
    }


}
