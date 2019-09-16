package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profilduzenle extends AppCompatActivity {

    ImageButton profilduzenleisim, profilduzenlesoyisim, profilduzenlemail;
    ImageButton profilonay;

    TextView profilduzenleid;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Context context;

    prefence pr;

    String myid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilduzenle);

        pr = new prefence();
        myid = pr.getValue(context);

        profilonay=(ImageButton)findViewById(R.id.profilonay);
        profilduzenleisim=(ImageButton)findViewById(R.id.profilduzenleisim);
        profilduzenlesoyisim=(ImageButton)findViewById(R.id.profilduzenlesoyisim);
        profilduzenlemail=(ImageButton)findViewById(R.id.profilduzenlemail);
        profilduzenleid=(TextView) findViewById(R.id.profilduzenleid);



        myRef = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                bireyselkullanicilar bk = dataSnapshot.getValue(bireyselkullanicilar.class);
                profilduzenleid.setText(bk.getId());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });







        profilonay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Sohbetler.class);
                startActivity(intent);
            }
        });
    }
}