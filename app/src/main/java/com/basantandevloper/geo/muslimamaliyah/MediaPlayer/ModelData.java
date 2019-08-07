package com.basantandevloper.geo.muslimamaliyah.MediaPlayer;

public class ModelData {

    public String artist;
    public String name;
    public String url;
    public String vol;
    public String image;

    public ModelData(){}

    public ModelData(String artist, String name, String url, String vol,String image) {
        this.artist = artist;
        this.name = name;
        this.url = url;
        this.vol = vol;
    }

    public String getImage() {
        return image;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}
