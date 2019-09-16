package com.example.myapplication.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.etkinlik;
import com.example.myapplication.prefence;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class etktest extends AppCompatActivity {
    String myid;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.etktest);

       etkinlik etk=new etkinlik();

       etk.setAd("Yeni Etkinlik");
       etk.setDetay("Detay");
       etk.setKontenjan("30");
       etk.setKonum("Afyon");
     ArrayList<String> katilimci=  new ArrayList<>();
katilimci.add("132819238123");
katilimci.add("3214324552");
    etk.setKatilimcilar(katilimci);

        prefence pr=new prefence();
    myid=pr.getValue(getApplicationContext());
       etk.setOlusturan(myid);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("bireyselusers").child("948604872009137").child("etkinlikler").setValue(etk);
    }
}
