package com.basantandevloper.geo.muslimamaliyah.Quran;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SurahJsonResponse {
    public SurahModel[] parseJSON(String response){
        // GSON and its API.
        Gson gson = new GsonBuilder().create();
        SurahModel[] responseObject = gson.fromJson(response, SurahModel[].class);
        return responseObject;
    }
}
