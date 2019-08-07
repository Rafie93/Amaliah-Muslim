package com.basantandevloper.geo.muslimamaliyah.Quran;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.utils.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AyatAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public ArrayList<AyatModel> ayatModels;
    private Context context;
    private String surah;
    private String noSurah;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    //POP UP
    private String popCmdArr[] = {"Berbagi", "Play Audio"};
    private int popCmdImgArr[] = { R.mipmap.ic_share, R.mipmap.ic_play};
    // SimpleAdapter list item key.
    final private String LIST_ITEM_KEY_IMAGE = "image";
    final private String LIST_ITEM_KEY_TEXT = "text";
    //


    public AyatAdapter(ArrayList<AyatModel> ayatModels, Context context, String surah, String noSurah) {
        this.ayatModels = ayatModels;
        this.context = context;
        this.surah = surah;
        this.noSurah = noSurah;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_ayat, parent, false);
            return new ItemViewHolder(view);
        }else{
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    private void populateItemRows(final ItemViewHolder holder, final int position) {
        //Load Setting Arabik
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String prefSizeArab = preference.getString(context.getString(R.string.key_list_arabic_size),"0");
        Integer huSize = Integer.valueOf(prefSizeArab);
        Integer ukuruanArab = 22;
        if (huSize == 6){ ukuruanArab = 48;
        }else if  (huSize == 5){ ukuruanArab = 36;
        }else if (huSize == 4){ ukuruanArab = 28;
        }else if  (huSize == 3) { ukuruanArab = 22;
        }else if  (huSize == 2){ ukuruanArab =18;
        }else if  (huSize == 1){ ukuruanArab = 14;
        }else{ ukuruanArab = 22; }

        final String arab =ayatModels.get(position).getAr();
        holder.textArabic.setTextSize(ukuruanArab);
        holder.textArabic.setText(arab);
        String html_txt = ayatModels.get(position).getTr();

        //LATIN
        SharedPreferences preferenceL = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String prefSizeL = preferenceL.getString(context.getString(R.string.key_list_latin_size),"1");
        //
        SharedPreferences preferenceLA = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
       Boolean prefSizeLA = preferenceLA.getBoolean(context.getString(R.string.key_latin),true);
       // String prefSizeLA="false";

        Integer huSizeL = Integer.valueOf(prefSizeL);
        Integer ukuruanLatin = 22;
        if (huSizeL == 6){ ukuruanLatin = 48;
        }else if  (huSizeL == 5){ ukuruanLatin = 36;
        }else if (huSizeL == 4){ ukuruanLatin = 28;
        }else if  (huSizeL == 3) { ukuruanLatin = 22;
        }else if  (huSizeL == 2){ ukuruanLatin =18;
        }else if  (huSizeL == 1){ ukuruanLatin = 14;
        }else{ ukuruanLatin = 14; }

        if (!prefSizeLA){
            holder.txtLatin.setVisibility(View.GONE);
        }else {
            holder.txtLatin.setVisibility(View.VISIBLE);
        }

        holder.txtLatin.setTextSize(ukuruanLatin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txtLatin.setText(Html.fromHtml(html_txt, Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.txtLatin.setText(Html.fromHtml(html_txt).toString());
        }

        //TERJEMAH
        SharedPreferences preferenceT = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String prefSizeT = preferenceT.getString(context.getString(R.string.key_list_terjemahan_size),"1");
        //
        SharedPreferences preferenceTA = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Boolean prefSizeTA = preferenceTA.getBoolean(context.getString(R.string.key_terjemahan),true);

        Integer huSizeT = Integer.valueOf(prefSizeT);
        Integer ukuruanTerjemah = 14;
        if (huSizeT == 6){ ukuruanTerjemah = 48;
        }else if  (huSizeT == 5){ ukuruanTerjemah = 36;
        }else if (huSizeT == 4){ ukuruanTerjemah = 28;
        }else if  (huSizeT == 3) { ukuruanTerjemah = 22;
        }else if  (huSizeT == 2){ ukuruanTerjemah =18;
        }else if  (huSizeT == 1){ ukuruanTerjemah = 14;
        }else{ ukuruanTerjemah = 14; }
        if (!prefSizeTA){
            holder.txtTranslate.setVisibility(View.GONE);
        }else {
            holder.txtTranslate.setVisibility(View.VISIBLE);
        }
        final String arti = ayatModels.get(position).getId();
        final String ayat = ayatModels.get(position).getNomor();

        holder.txtTranslate.setTextSize(ukuruanTerjemah);
        holder.txtTranslate.setText(arti);
        holder.txtNoAyat.setText(ayat);

        if (Integer.valueOf(noSurah) == 0 ){
            holder.imgMore.setVisibility(View.GONE);
        }

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize short message list item data.
                List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
                int itemLen = popCmdArr.length;
                for(int i=0;i<itemLen;i++)
                {
                    Map<String,Object> itemMap = new HashMap<String, Object>();
                    itemMap.put(LIST_ITEM_KEY_IMAGE, popCmdImgArr[i]);
                    itemMap.put(LIST_ITEM_KEY_TEXT, popCmdArr[i]);

                    itemList.add(itemMap);
                }

                // Create SimpleAdapter that will be used by short message list view.
                SimpleAdapter simpleAdapter = new SimpleAdapter(context, itemList, R.layout.popup_left,
                        new String[]{LIST_ITEM_KEY_IMAGE, LIST_ITEM_KEY_TEXT},
                        new int[]{R.id.listItemImage, R.id.listItemText});
                // Get short message popup view.
                LayoutInflater mInflater = LayoutInflater.from(context);
                View popupView = mInflater.inflate(R.layout.popup_left, null);
              //  View popupView = context.g .getLayoutInflater().inflate(R.layout.popup_left, null);
                ListView listView = (ListView) popupView.findViewById(R.id.popupWindowList);

                // Create popup window.
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                // Set popup window animation style.
                popupWindow.setAnimationStyle(R.style.popup_window_animation_left);

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                popupWindow.setFocusable(true);

                popupWindow.setOutsideTouchable(true);

                popupWindow.update();

                // Show popup window offset 1,1 to smsBtton.
                popupWindow.showAsDropDown(holder.imgMore, 1, 1);


                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                        String link = Constant.LINK_APP;
                        final String pesan = "Kutipan dari Al-Qur'An: "+surah+" Ayat "+ayat+ "\n\n"+arab+"\n\n"
                                +arti+"\n\n"+"Download Aplikasi Muslim Amaliyah "+link;
                       if (itemIndex == 0){
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_TEXT,pesan);
                            shareIntent.setType("text/plain");
                            view.getContext().startActivity(shareIntent);
                        }else if (itemIndex == 1 ){
                            playMusik(holder,Integer.valueOf(ayat),position);
                            popupWindow.dismiss();
                        }
                    }
                });

            }
        });

    }

    private void playMusik(final ItemViewHolder holder, final Integer ayat, final int position) {
        final RelativeLayout layPlay = (RelativeLayout)((Activity)context).findViewById(R.id.relMusik);
        final ImageView imgStop = (ImageView) ((Activity)context).findViewById(R.id.img_Stop);
        final ImageView imgBack = (ImageView) ((Activity)context).findViewById(R.id.ic_back);
        layPlay.setVisibility(View.VISIBLE);
        holder.cvAyat.setBackgroundColor(Color.parseColor("#a9a8a8"));

        final RecyclerView recyclerView = (RecyclerView)((Activity)context).findViewById(R.id.my_recycler_view);

        String folderUrl = Constant.BASE_URL_STORAGE+noSurah;
        Integer iAyat = ayat;
        String namaFile = "";
        if (iAyat < 10){  namaFile = "00"+iAyat+".mp3";}
        else if (iAyat < 100){  namaFile = "0"+iAyat+".mp3";}
        else {namaFile = iAyat+".mp3";}
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(folderUrl).child(namaFile);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("URL+", "uri: " + uri.toString());
           //     holder.cvAyat.setBackgroundColor(Color.parseColor("#a9a8a8"));

                final MediaPlayer player = new MediaPlayer();
                //   player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                imgStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       holder.cvAyat.setBackgroundColor(Color.parseColor("#ffffff"));
                        layPlay.setVisibility(View.GONE);
                        player.stop();
                    }
                });

                if (player.isPlaying()){
                    imgBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layPlay.setVisibility(View.GONE);
                            player.stop();
                            ((Activity) context).onBackPressed();
                        }
                    });
                }



                try {
                    player.setDataSource(uri.toString());
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {

                            mediaPlayer.start();
                        }
                    });

                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                       //    holder.cvAyat.setBackgroundColor(Color.parseColor("#ffffff"));
                            layPlay.setVisibility(View.GONE);
                            mediaPlayer.stop();
                            if (ayat < ayatModels.size()){
                                 ayatModels.get(position+1);
                              //   notifyItemMoved(position,position+1);
                                notifyItemChanged(position+1);
                                recyclerView.scrollToPosition(position+1);
                                AyatAdapter ayatAdapter = new AyatAdapter(ayatModels,context,surah,String.valueOf(Integer.valueOf(noSurah+1)));
                                recyclerView.setAdapter(ayatAdapter);

                              //  notifyDataSetChanged();
                                holder.cvAyat.setBackgroundColor(Color.parseColor("#a9a8a8"));

                                playMusik(holder,ayat+1,position+1);


                            }
                        }
                    });

                    player.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("EMUSIK", "onSuccess: error");
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return (ayatModels != null) ? ayatModels.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return ayatModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textArabic,txtNoAyat,txtTranslate,txtLatin;
        private CardView cvAyat;
        private ImageView imgMore;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textArabic = itemView.findViewById(R.id.rowArabic);
            txtLatin = itemView.findViewById(R.id.rowLatin);
            txtNoAyat = itemView.findViewById(R.id.rowNomorAyat);
            txtTranslate = itemView.findViewById(R.id.rowTerjemahan);
            cvAyat = itemView.findViewById(R.id.cvAyat);
            imgMore = itemView.findViewById(R.id.imgMore);
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(final View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
}
