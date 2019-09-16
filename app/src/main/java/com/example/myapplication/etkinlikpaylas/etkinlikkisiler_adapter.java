package com.example.myapplication.etkinlikpaylas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bireyselkullanicilar;

import java.util.ArrayList;

public class etkinlikkisiler_adapter extends RecyclerView.Adapter<etkinlikkisiler_adapter.ViewHolder> {
    ArrayList<bireyselkullanicilar> bireyselArrayList=new ArrayList<>();
    //her bir satır için temsil edilecek arayüz seçilir
    LayoutInflater layoutInflater;
    Context context;

    public ArrayList<String> getPaylasilanlar() {
        return paylasilanlar;
    }

    public void setPaylasilanlar(ArrayList<String> paylasilanlar) {
        this.paylasilanlar = paylasilanlar;
    }

    ArrayList<String> paylasilanlar=new ArrayList<>();

    public etkinlikkisiler_adapter(ArrayList<bireyselkullanicilar> bireyselArrayList, Context context) {
        this.bireyselArrayList = bireyselArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public etkinlikkisiler_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(context);
        View satirim=    layoutInflater.inflate(R.layout.row_list,viewGroup,false);
        etkinlikkisiler_adapter.ViewHolder vh=new etkinlikkisiler_adapter.ViewHolder(satirim);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final etkinlikkisiler_adapter.ViewHolder viewHolder, final int i) {
        viewHolder.txtisimsoyisim.setText(bireyselArrayList.get(i).getIsim()+" "+bireyselArrayList.get(i).getSoyisim());
        viewHolder.txttelefon.setText(bireyselArrayList.get(i).getTelefon());
        viewHolder.txtid.setText(bireyselArrayList.get(i).getId());
        viewHolder.imageView.setImageResource(R.drawable.prfoilfoto);

        //!!!! SATIRLARA HER TIKLANDIĞINDAİ İŞ YAPTIRMAK İÇİN

        viewHolder.linear.setTag(viewHolder);

        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                etkinlikkisiler_adapter.ViewHolder holder= (etkinlikkisiler_adapter.ViewHolder)v.getTag();
                int position= holder.getAdapterPosition();

                //Intent ile İD'yi çekip yolluyoruz.Sohbet Sayfasına
                if (paylasilanlar.contains(bireyselArrayList.get(position).getId())){
                    paylasilanlar.remove(bireyselArrayList.get(position).getId());
                    viewHolder.linear.setBackgroundColor(Color.WHITE);
                }else {
                    viewHolder.linear.setBackgroundColor(R.color.colorPrimary);
                    paylasilanlar.add(bireyselArrayList.get(position).getId());
                }
                setPaylasilanlar(paylasilanlar);
             //   Toast.makeText(context,paylasilanlar.toString(),Toast.LENGTH_LONG).show();
/*

                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("receiveid",bireyselArrayList.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
*/



            }
        });






    }

    @Override
    public int getItemCount() {
        return bireyselArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtisimsoyisim,txtid,txttelefon;
        ImageView imageView;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtisimsoyisim=itemView.findViewById(R.id.isimsoyisim);
            txtid=itemView.findViewById(R.id.getid);
            txttelefon=itemView.findViewById(R.id.telefonno);
            linear=itemView.findViewById(R.id.linear);
            imageView=itemView.findViewById(R.id.imagev);



        }
    }
}
