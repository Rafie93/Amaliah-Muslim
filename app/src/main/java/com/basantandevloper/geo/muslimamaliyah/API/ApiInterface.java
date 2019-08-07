package com.basantandevloper.geo.muslimamaliyah.API;

import com.basantandevloper.geo.muslimamaliyah.Models.Items;
import com.basantandevloper.geo.muslimamaliyah.Models.JadwalModel;
import com.basantandevloper.geo.muslimamaliyah.Models.Kota;
import com.basantandevloper.geo.muslimamaliyah.Qosidah.Mqasidah;
import com.basantandevloper.geo.muslimamaliyah.Doa.DoaModel;
import com.basantandevloper.geo.muslimamaliyah.Quran.AyatModel;
import com.basantandevloper.geo.muslimamaliyah.Quran.AyatResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("{periode}/month.json")
    Call<Items> getJadwalSholat(@Path("periode") String periode);

    @GET("sholat/format/json/kota/nama/{kota}")
    Call<Kota>getKota(@Path("kota")String kota);

    @GET("{nomor}.json")
    Call<List<AyatResult>>getAyat(@Path("nomor")String nomor);

    @GET("sholat/format/json/jadwal/kota/{jadwal}/tanggal/{tgl}")
    Call<JadwalModel>getJadwalSalat(@Path("jadwal") String jadwal,@Path("tgl")String tgl);

    @GET("exec?action=filter&tabelName=isi_qasidah")
    Call<Mqasidah>getIsiQasidah(@Query("qasidah") String qasidah);

    @GET("exec?action=read")
    Call<Mqasidah>getQasidah(@Query("tabelName") String tabelName);


    @GET("exec?action=read")
    Call<DoaModel>getDoa(@Query("tabelName") String tabelName);

}
