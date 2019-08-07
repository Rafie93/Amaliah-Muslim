package com.basantandevloper.geo.muslimamaliyah.Quran;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JuzJsonResponse {
    public JuzModel[] parseJSON(String response){
        // GSON and its API.
        Gson gson = new GsonBuilder().create();
        JuzModel[] responseObject = gson.fromJson(response, JuzModel[].class);
        return responseObject;
    }
}
