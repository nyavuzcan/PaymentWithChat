package com.example.myapplication.test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.MessageActivity;
import com.example.myapplication.R;
import com.example.myapplication.bireyselkullanicilar;

import java.util.ArrayList;

public class customtekno extends RecyclerView.Adapter<customtekno.ViewHolder> {
        ArrayList<bireyselkullanicilar> bireyselArrayList=new ArrayList<>();
        //her bir satır için temsil edilecek arayüz seçilir
        LayoutInflater layoutInflater;
        Context context;

    public customtekno(ArrayList<bireyselkullanicilar> bireyselArrayList, Context context) {
        this.bireyselArrayList = bireyselArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(context);
    View satirim=    layoutInflater.inflate(R.layout.row_list,viewGroup,false);
    ViewHolder vh=new ViewHolder(satirim);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txtisimsoyisim.setText(bireyselArrayList.get(i).getIsim()+" "+bireyselArrayList.get(i).getSoyisim());
        viewHolder.txttelefon.setText(bireyselArrayList.get(i).getTelefon());
        viewHolder.txtid.setText(bireyselArrayList.get(i).getId());

        viewHolder.imageView.setImageResource(R.drawable.prfoilfoto);
        //!!!! SATIRLARA HER TIKLANDIĞINDAİ İŞ YAPTIRMAK İÇİN

        viewHolder.linear.setTag(viewHolder);

        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder holder= (ViewHolder)v.getTag();
                int position= holder.getAdapterPosition();

                //Intent ile İD'yi çekip yolluyoruz.Sohbet Sayfasına


                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("receiveid",bireyselArrayList.get(position).getId());
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);

            }
        });

        viewHolder.linear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

         //       Toast.makeText(context,""+v.getId(),Toast.LENGTH_LONG).show();
                return false;
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
