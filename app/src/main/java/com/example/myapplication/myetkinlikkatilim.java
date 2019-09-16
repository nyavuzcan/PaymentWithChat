package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myetkinlikkatilim extends AppCompatActivity {
    String etkid,etkinlikasilid;
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


        setContentView(R.layout.activity_myetkinlikkatilim);
        context = getApplicationContext();
        Intent intent = getIntent();
        recyclerView = findViewById(R.id.myrcyletk);
        edtad = findViewById(R.id.myetkad);
        edttarih = findViewById(R.id.myetktarih);
        edtadres = findViewById(R.id.myetkadres);
        edtkontenjan = findViewById(R.id.myetkkontenjan);
        etkid = intent.getStringExtra("etkinlikid");
        etkinlikasilid = intent.getStringExtra("etkinlikasilid");
        edtAciklama=findViewById(R.id.myetkaciklama);
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
