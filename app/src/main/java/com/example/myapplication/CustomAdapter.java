package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
Context context;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<com.example.myapplication.kisi_listesi> kisi_listesi) {
        this.kisi_listesi = kisi_listesi;
        this.context=context;
    }

    List<kisi_listesi> kisi_listesi=new ArrayList<>();
    @Override
    public int getCount() { //eleman sayısı
        return kisi_listesi.size();
    }

    @Override
    public Object getItem(int position) {  //sırası gelen eleman
        return position;
    }

    @Override
    public long getItemId(int position) { //varsa id
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //position ile gelen viewei döndüroyruz
        inflater= LayoutInflater.from(context);
        View satir=inflater.inflate(R.layout.satir_custom_list,null);
        ImageView img=satir.findViewById(R.id.profilfotoid);
        TextView txt=satir.findViewById(R.id.isimsoyisimid);
       kisi_listesi kisiler= kisi_listesi.get(position);
       txt.setText(kisiler.getIsimsoyisim());

       //isme göre fotolar
        if(kisiler.getIsimsoyisim().equals("NEVZAT YAVUZCAN")){
            img.setImageResource(R.drawable.prfoilfoto);
        }
        else if(kisiler.getIsimsoyisim().equals("SÜLEYMAN EMRE GÜN")){
            img.setImageResource(R.drawable.profilkurumsal);
        }
        else{
            img.setImageResource(R.drawable.baseline_add_circle_outline_black_48dp);
        }

            //Artık xmli döndürüyoruz foto ve isimsoyisimi yani.
        return satir;
    }
}
