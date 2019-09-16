package com.example.myapplication.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.alfa.kullanicigetir;
import com.example.myapplication.bireyselkullanicilar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class testgetir extends AppCompatActivity {
TextView textView;
DataSnapshot dt;

    DatabaseReference mDatabase;
com.example.myapplication.alfa.kullanicigetir kullanicigetir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testgetir);

        textView=(TextView)findViewById(R.id.txtgetir);

   com.example.myapplication.alfa.kullanicigetir k1=new kullanicigetir();

       dt= k1.kullanicidondur();
     /*  for (DataSnapshot gelen:dt.getChildren()){
            textView.append(gelen.getValue(bireyselkullanicilar.class).getId()+"\n"+gelen.getValue(bireyselkullanicilar.class).getIsim()+"\n");
        }*/
        mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot gelen:dataSnapshot.getChildren()){
                    textView.append(gelen.getValue(bireyselkullanicilar.class).getId()+"\n"+gelen.getValue(bireyselkullanicilar.class).getIsim()+"\n");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
