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

public class sohbet_custom_adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<sohbet_foto> fotolar=new ArrayList<>();
    List<kisi_listesi> kisilistesison=new ArrayList<>();
    List<sohbet_kisa> sohbet_kisa=new ArrayList<>();
    @Override
    public int getCount() {
        return kisilistesison.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public sohbet_custom_adapter(Context context, List<sohbet_foto> fotolar, List<kisi_listesi> kisilistesison, List<com.example.myapplication.sohbet_kisa> sohbet_kisa) {
        this.context = context;
        this.fotolar = fotolar;
        this.kisilistesison = kisilistesison;
        this.sohbet_kisa = sohbet_kisa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater=LayoutInflater.from(context);
        View satir=inflater.inflate(R.layout.sohbetler_satir,null);
        TextView txt1=satir.findViewById(R.id.adsoyadid);
        TextView txt2=satir.findViewById(R.id.sohbetkisa);
        ImageView imgv=satir.findViewById(R.id.sohbetprofilfoto);

        sohbet_foto shb=fotolar.get(position);
        sohbet_kisa kisa=sohbet_kisa.get(position);
        kisi_listesi kisilerim=kisilistesison.get(position);

        txt1.setText(kisilerim.getIsimsoyisim());
        txt2.setText(kisa.getKisamesajlar());
        imgv.setImageResource(shb.getImgsrc());



        return satir;
    }
}
