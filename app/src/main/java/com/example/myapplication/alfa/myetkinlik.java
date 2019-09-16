package com.example.myapplication.alfa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.bireyselkullanicilar;
import com.example.myapplication.etkinlik;
import com.example.myapplication.prefence;
import com.example.myapplication.test.customtekno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myetkinlik extends AppCompatActivity {
    String etkid;
    DatabaseReference databaseReference,mDatabase;
    prefence pr;
    ImageButton etkpaylas;
    String myid;
    Context context;
    EditText edtad,edttarih,edtadres,edtkontenjan,edtAciklama;
    RecyclerView recyclerView;
    ArrayList<bireyselkullanicilar> users=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_myetkinlik);
        context = getApplicationContext();
        Intent intent = getIntent();
        recyclerView = findViewById(R.id.myrcyletk);
        edtad = findViewById(R.id.myetkad);
        edttarih = findViewById(R.id.myetktarih);
        edtadres = findViewById(R.id.myetkadres);
        edtkontenjan = findViewById(R.id.myetkkontenjan);
        edtAciklama=findViewById(R.id.myetkaciklama);
        etkid = intent.getStringExtra("etkinlikid");
        pr = new prefence();
        etkpaylas=findViewById(R.id.etkpaylas);
        myid = pr.getValue(context);
        databaseReference = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid).child("etkinlikler").child(etkid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                edtad.setText(dataSnapshot.getValue(etkinlik.class).getAd());
                edttarih.setText(dataSnapshot.getValue(etkinlik.class).getTarih());
                edtadres.setText(dataSnapshot.getValue(etkinlik.class).getKonum());
                edtkontenjan.setText(dataSnapshot.getValue(etkinlik.class).getKontenjan());
                edtAciklama.setText(dataSnapshot.getValue(etkinlik.class).getDetay());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid).child("etkinlikler").child(etkid).child("katilimcilar");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                users.clear();
                ArrayList<bireyselkullanicilar> yeni=new ArrayList<>();
                final ArrayList<String> stringArrayList=new ArrayList<>();
                for(DataSnapshot gelen:dataSnapshot.getChildren()){
                  stringArrayList.add(gelen.getValue(String.class));



                }

                DatabaseReference  fire= FirebaseDatabase.getInstance().getReference("bireyselusers");

                fire.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dt:dataSnapshot.getChildren()){
                            bireyselkullanicilar br=    dt.getValue(com.example.myapplication.bireyselkullanicilar.class);

                            if(!stringArrayList.isEmpty()){

                            for (int i=0; i<stringArrayList.size();i++){
                                if (br.getId().equals(stringArrayList.get(i))){
                                    users.add(br);

                                }
                            }


                        }

                        }
                        customtekno customtekno=new customtekno(users,context);
                        recyclerView.setAdapter(customtekno);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });









            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        etkpaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(context,com.example.myapplication.etkinlikpaylas.etkinlikkisiler.class);
                yeni.putExtra("etkid",etkid);

                startActivity(yeni);


            }
        });


    }
}
