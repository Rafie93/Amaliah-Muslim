package com.basantandevloper.geo.muslimamaliyah.Asmaul;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AsmaulJsonResponse {
    public AsmaulHusnaModel[] parseJSON(String response){
        // GSON and its API.
        Gson gson = new GsonBuilder().create();
        AsmaulHusnaModel[] responseObject = gson.fromJson(response, AsmaulHusnaModel[].class);
        return responseObject;
    }
}
