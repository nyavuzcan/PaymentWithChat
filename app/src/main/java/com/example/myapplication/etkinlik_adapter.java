package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class  etkinlik_adapter extends RecyclerView.Adapter< etkinlik_adapter.ViewHolder> {
    ArrayList<etkinlik> etkinlikArrayList=new ArrayList<>();
    //her bir satır için temsil edilecek arayüz seçilir
    LayoutInflater layoutInflater;
    Context context;

    public etkinlik_adapter(ArrayList<etkinlik> etkinlikArrayList, Context context) {
        this.etkinlikArrayList = etkinlikArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public etkinlik_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(context);


            if (i==3){
                View satirim=    layoutInflater.inflate(R.layout.etkinlik_item,viewGroup,false);
                etkinlik_adapter.ViewHolder vh=new  etkinlik_adapter.ViewHolder(satirim);
                return vh;
            }

            else if(i==4){
                View satirim=    layoutInflater.inflate(R.layout.etkinlikitemkatilim,viewGroup,false);
                etkinlik_adapter.ViewHolder vh=new  etkinlik_adapter.ViewHolder(satirim);
                return vh;

            }
            else {

                View satirim=    layoutInflater.inflate(R.layout.etkinlik_item,viewGroup,false);
                etkinlik_adapter.ViewHolder vh=new  etkinlik_adapter.ViewHolder(satirim);
                return vh;
            }


    }

    @Override
    public void onBindViewHolder(@NonNull final  etkinlik_adapter.ViewHolder viewHolder, int i) {

        switch (viewHolder.getItemViewType()) {

            case 3:
                viewHolder.txtetkinlikad.setText(etkinlikArrayList.get(i).getAd());
                viewHolder.txtetkinlikyer.setText(etkinlikArrayList.get(i).getKonum());
                viewHolder.txtetkinliktarih.setText(etkinlikArrayList.get(i).getTarih());
                //!!!! SATIRLARA HER TIKLANDIĞINDAİ İŞ YAPTIRMAK İÇİN
                viewHolder.etklinear.setTag(viewHolder);

                viewHolder.etklinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etkinlik_adapter.ViewHolder holder= (ViewHolder) v.getTag();
                        int position= holder.getAdapterPosition();

                        //Intent ile İD'yi çekip yolluyoruz.Sohbet Sayfasına


                        Intent intent=new Intent(context, com.example.myapplication.alfa.myetkinlik.class);
                        intent.putExtra("etkinlikid",etkinlikArrayList.get(position).getId());
                        //  Toast.makeText(context,etkinlikArrayList.get(position).getId(),Toast.LENGTH_LONG).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });



                break;

            case 4:
                viewHolder.txtetkinlikad.setText(etkinlikArrayList.get(i).getAd());
                viewHolder.txtetkinlikyer.setText(etkinlikArrayList.get(i).getKonum());
                viewHolder.txtetkinliktarih.setText(etkinlikArrayList.get(i).getTarih());
                viewHolder.etklinear.setTag(viewHolder);

                viewHolder.etklinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etkinlik_adapter.ViewHolder holder= (ViewHolder) v.getTag();
                        int position= holder.getAdapterPosition();

                        //Intent ile İD'yi çekip yolluyoruz.Sohbet Sayfasına


                        Intent intent=new Intent(context, com.example.myapplication.myetkinlikkatilim.class);
                        intent.putExtra("etkinlikid",etkinlikArrayList.get(position).getSonraid());
                        intent.putExtra("etkinlikasilid",etkinlikArrayList.get(position).getId());
                        //  Toast.makeText(context,etkinlikArrayList.get(position).getId(),Toast.LENGTH_LONG).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });
                break;
        }







    }

    @Override
    public int getItemCount() {
        return etkinlikArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (etkinlikArrayList.get(position).getType().equals("3")){
            return 3;
        }

        else if (etkinlikArrayList.get(position).getType().equals("4")){
            return 4;
        }

        else
        {
            return 3;
        }




    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtetkinlikad,txtetkinliktarih,txtetkinlikyer;

        LinearLayout etklinear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtetkinlikad=itemView.findViewById(R.id.txtetkinlikad);
            txtetkinliktarih=itemView.findViewById(R.id.txtetkinliktarih);
            txtetkinlikyer=itemView.findViewById(R.id.txtetkinlikyer);
            etklinear=itemView.findViewById(R.id.etklinear);


        }
    }
}