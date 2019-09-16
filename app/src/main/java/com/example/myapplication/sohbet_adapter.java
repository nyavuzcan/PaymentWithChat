package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.test.mesajlar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class sohbet_adapter extends RecyclerView.Adapter<sohbet_adapter.ViewHolder> {

    private static HttpURLConnection con;
    public static final int MESSAGE_LEFT_ITEM = 0;
    public static final int MESSAGE_RIGHT_ITEM = 1;

    public static final int MESSAGE_ETKINLIK_RIGHT = 3;
    public static final int MESSAGE_ETKINLIK_LEFT = 4;

    public static final int MESSAGE_ODEME_RIGHT = 5;
    public static final int MESSAGE_ODEME_LEFT = 6;


    ArrayList<mesajlar> mesajlarArrayList = new ArrayList<>();
    //her bir satır için temsil edilecek arayüz seçilir
    LayoutInflater layoutInflater;
    Context context;
    prefence pr;
    String myid;
    DatabaseReference fireetkinlik;
    ArrayList<etkinlik> etkinlikArrayList;


    public sohbet_adapter(ArrayList<mesajlar> mesajlarArrayList, Context context) {
        this.mesajlarArrayList = mesajlarArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        layoutInflater = LayoutInflater.from(context);
        if (i == MESSAGE_LEFT_ITEM) {
            View satirim = layoutInflater.inflate(R.layout.chat_item_left, viewGroup, false);
            sohbet_adapter.ViewHolder vh = new sohbet_adapter.ViewHolder(satirim);
            return vh;
        } else if (i == MESSAGE_RIGHT_ITEM) {
            View satirim = layoutInflater.inflate(R.layout.chat_item_right, viewGroup, false);
            sohbet_adapter.ViewHolder vh = new sohbet_adapter.ViewHolder(satirim);
            return vh;
        } else if (i == MESSAGE_ETKINLIK_RIGHT) {
            View satirim = layoutInflater.inflate(R.layout.etkinlik_satir_right, viewGroup, false);
            sohbet_adapter.ViewHolder vh = new sohbet_adapter.ViewHolder(satirim);
            return vh;
        } else if (i == MESSAGE_ETKINLIK_LEFT) {
            View satirim = layoutInflater.inflate(R.layout.etkinlik_satir, viewGroup, false);
            sohbet_adapter.ViewHolder vh = new sohbet_adapter.ViewHolder(satirim);
            return vh;
        }else if (i == MESSAGE_ODEME_RIGHT) {
            View satirim = layoutInflater.inflate(R.layout.odeme_satir_leftsag, viewGroup, false);
            sohbet_adapter.ViewHolder vh = new sohbet_adapter.ViewHolder(satirim);
            return vh;
        }else if (i == MESSAGE_ODEME_LEFT) {
            View satirim = layoutInflater.inflate(R.layout.odeme_satir_right, viewGroup, false);
            sohbet_adapter.ViewHolder vh = new sohbet_adapter.ViewHolder(satirim);
            return vh;
        } else return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final sohbet_adapter.ViewHolder viewHolder, final int i) {

        switch (viewHolder.getItemViewType()) {

            case 0:
                viewHolder.txtmesaj.setText(mesajlarArrayList.get(i).getMessage());
                viewHolder.profile_image.setImageResource(R.drawable.prfoilfoto);

                break;

            case 1:
                viewHolder.txtmesaj.setText(mesajlarArrayList.get(i).getMessage());
                viewHolder.profile_image.setImageResource(R.drawable.prfoilfoto);

                break;

            case 3:


                fireetkinlik = FirebaseDatabase.getInstance().getReference("bireyselusers").child(mesajlarArrayList.get(i).getSender()).child("etkinlikler").child(mesajlarArrayList.get(i).getMessage());
                fireetkinlik.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        etkinlik etkinlik = dataSnapshot.getValue(com.example.myapplication.etkinlik.class);


                        viewHolder.edtetkinlikad.setText(etkinlik.getAd());
                        viewHolder.edtetkinliktarih.setText(etkinlik.getTarih());
                        viewHolder.edtetkinlikyer.setText(etkinlik.getKonum());
                        viewHolder.edtetkinlikkontenjan.setText(etkinlik.getKontenjan());
                        viewHolder.edtetkinlikdetay.setText(etkinlik.getDetay());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;


            case 4:

                fireetkinlik = FirebaseDatabase.getInstance().getReference("bireyselusers").child(mesajlarArrayList.get(i).getSender()).child("etkinlikler").child(mesajlarArrayList.get(i).getMessage());
                fireetkinlik.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        final etkinlik etkinlik = dataSnapshot.getValue(com.example.myapplication.etkinlik.class);


                        viewHolder.edtetkinlikad.setText(etkinlik.getAd());
                        viewHolder.edtetkinliktarih.setText(etkinlik.getTarih());
                        viewHolder.edtetkinlikyer.setText(etkinlik.getKonum());
                        viewHolder.edtetkinlikkontenjan.setText(etkinlik.getKontenjan());
                        viewHolder.edtetkinlikdetay.setText(etkinlik.getDetay());
                        viewHolder.btnevet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final DatabaseReference fire = FirebaseDatabase.getInstance().getReference("bireyselusers").child(mesajlarArrayList.get(i).getSender())
                                        .child("etkinlikler").child(mesajlarArrayList.get(i).getMessage()).child("katilimcilar");


                                fire.addValueEventListener(new ValueEventListener() {
                                    ArrayList<String> yeniarray = new ArrayList<>();

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                                            yeniarray.add(data.getValue(String.class));


                                        }

                                        if (!yeniarray.contains(mesajlarArrayList.get(i).getReceiver())) {
                                            yeniarray.add(mesajlarArrayList.get(i).getReceiver());

                                            fire.setValue(yeniarray);
                                            viewHolder.btnevet.setEnabled(false);

                                            DatabaseReference etkekle = FirebaseDatabase.getInstance().getReference();
                                            String key = etkekle.child("posts").push().getKey();
                                            etkinlik.setSonraid(key);
                                            etkinlik.setType("4");
                                            etkekle.child("bireyselusers").child(myid).child("etkinlikler").child(key).setValue(etkinlik);


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                        });

                        viewHolder.btnhayir.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final DatabaseReference fire = FirebaseDatabase.getInstance().getReference("bireyselusers").child(mesajlarArrayList.get(i).getSender())
                                        .child("etkinlikler").child(mesajlarArrayList.get(i).getMessage()).child("katilimcilar");


                                fire.addValueEventListener(new ValueEventListener() {
                                    ArrayList<String> yeniarray = new ArrayList<>();

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {


                                            yeniarray.add(data.getValue(String.class));


                                        }


                                        if (yeniarray.contains(mesajlarArrayList.get(i).getReceiver())) {
                                            yeniarray.remove(mesajlarArrayList.get(i).getReceiver());

                                            fire.setValue(yeniarray);
//                                 
                                            //    DatabaseReference etkekle=FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid).child("etkinlikler").child(etkinlik.getSonraid());
                                            //  etkekle.removeValue();
                                            //  Log.d("Sxxx",etkekle.getKey());
                                            viewHolder.btnhayir.setEnabled(false);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                break;



            case 5:


                viewHolder.odemebeklenen.setText(mesajlarArrayList.get(i).getMessage());
                break;

            case 6:


            viewHolder.txtodenecek.setText(mesajlarArrayList.get(i).getMessage());
            viewHolder.btnonayla.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
               final String kartno=     viewHolder.edtkartno.getText().toString();
               final String sonkullanmaay= viewHolder.edtay.getText().toString();
               final String sonkullanmayil=viewHolder.edtyil.getText().toString();
               final String cvc=viewHolder.edtcvc.getText().toString();
               final String fiyat=viewHolder.txtodenecek.getText().toString();

                    try {

                        final DatabaseReference firem = FirebaseDatabase.getInstance().getReference("bireyselusers").child(mesajlarArrayList.get(i).getReceiver());
                        firem.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                          bireyselkullanicilar bireyselkullanici=      dataSnapshot.getValue(bireyselkullanicilar.class);
                                try {
        sendPost(kartno,sonkullanmaay,sonkullanmayil,cvc,bireyselkullanici.getIsim(),bireyselkullanici.getSoyisim(),fiyat,bireyselkullanici.getIsim()+" "+bireyselkullanici.getSoyisim(),bireyselkullanici.getEmail(),viewHolder);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


                break;
        }


    }

    @Override
    public int getItemCount() {
        return mesajlarArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmesaj,txtodenecek,odemebeklenen;
        ImageView profile_image;
        TextView edtetkinlikad, edtetkinliktarih, edtetkinlikyer, edtetkinlikkontenjan, edtetkinlikdetay;
        Button btnevet, btnhayir,btnonayla;
        EditText edtkartno,edtay,edtyil,edtcvc;

        //    LinearLayout linear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            txtmesaj = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            edtetkinlikad = itemView.findViewById(R.id.edtetkinlikad);
            edtetkinliktarih = itemView.findViewById(R.id.edtetkinliktarih);
            edtetkinlikyer = itemView.findViewById(R.id.edtetkinlikyer);
            edtetkinlikkontenjan = itemView.findViewById(R.id.edtetkinlikkontenjan);
            edtetkinlikdetay = itemView.findViewById(R.id.edtetkinlikdetay);
            btnevet = itemView.findViewById(R.id.btnevet);
            btnhayir = itemView.findViewById(R.id.btnhayir);

            txtodenecek=itemView.findViewById(R.id.txtodenecek);

            edtkartno=itemView.findViewById(R.id.edtkartno);
            edtay=itemView.findViewById(R.id.edtay);
            edtyil=itemView.findViewById(R.id.edtyil);
            edtcvc=itemView.findViewById(R.id.edtcvc);
            btnonayla=itemView.findViewById(R.id.btnonayla);
            odemebeklenen=itemView.findViewById(R.id.odemebeklenen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        pr = new prefence();
        myid = pr.getValue(context);
        if (Integer.parseInt(mesajlarArrayList.get(position).getType()) == MESSAGE_RIGHT_ITEM) {

            return MESSAGE_RIGHT_ITEM;
        } else if (Integer.parseInt(mesajlarArrayList.get(position).getType()) == MESSAGE_LEFT_ITEM) {

            return MESSAGE_LEFT_ITEM;
        } else if (Integer.parseInt(mesajlarArrayList.get(position).getType()) == MESSAGE_ETKINLIK_RIGHT) {

            return MESSAGE_ETKINLIK_RIGHT;
        } else if (Integer.parseInt(mesajlarArrayList.get(position).getType()) == MESSAGE_ETKINLIK_LEFT) {

            return MESSAGE_ETKINLIK_LEFT;
        }else if (Integer.parseInt(mesajlarArrayList.get(position).getType()) == MESSAGE_ODEME_LEFT) {

            return MESSAGE_ODEME_LEFT;
        }else if (Integer.parseInt(mesajlarArrayList.get(position).getType()) == MESSAGE_ODEME_RIGHT) {

            return MESSAGE_ODEME_RIGHT;
        } else {

            return 0;
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendPost(  String kartno,String sonkullanmaay, String sonkullanmayil,  String cvc,String isim,String soyisim, String fiyat, String kartsahibi,String email,ViewHolder viewHolder) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //your codes here



        String url = "http://el-ayn.com/chitchat/samples/create_payment_post.php";
        String urlParameters = "kart_sahibi="+isim+soyisim+"&kart_son_kullanim_ay="+sonkullanmaay+"&kart_son_kullanim_yil="+sonkullanmayil+"&kart_cvc="+cvc+"&isim="+isim+"&soyisim="+soyisim+"&email="+email+"&fiyat="+fiyat+"&kart_numarasi="+kartno;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

            String sonuc=content.toString();

            if (sonuc.contains("success")){
                Toast.makeText(context,"Ödeme Başarılı",Toast.LENGTH_LONG).show();
                viewHolder.btnonayla.setEnabled(false);
            }
            else {
                Toast.makeText(context,"Ödeme Gerçekleştirilemedi. Bilgileri Kontrol Edip Tekrar Deneyin",Toast.LENGTH_LONG).show();
            }

        } finally {

            con.disconnect();
        }
    }


}