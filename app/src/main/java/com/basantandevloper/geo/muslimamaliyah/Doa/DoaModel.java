package com.basantandevloper.geo.muslimamaliyah.Doa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class DoaModel {

    @SerializedName("list_doa")
    @Expose
    private List<ListDoa> listDoa = null;

    public List<ListDoa> getListDoa() {
        return listDoa;
    }

    public void setListDoa(List<ListDoa> listDoa) {
        this.listDoa = listDoa;
    }
}
