package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class etkinlikOlusturma extends AppCompatActivity {


        Button btnEtkinlik;
        EditText edtad,edttarih,edtkonum,edtdetay,edtkontenjan;
        prefence pr;
        String myid;
        DatabaseReference mDatabase;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etkinlikolusturma);


        edtad=findViewById(R.id.etkinlikad);
        edttarih=findViewById(R.id.etkinlikttarih);
        edtkonum=findViewById(R.id.etkinlikadres);
        edtkontenjan=findViewById(R.id.etkinlikkontenjan);
        edtdetay=findViewById(R.id.etkinlikaciklama);
        btnEtkinlik = (Button) findViewById(R.id.btnEtkinlik);
        pr=new prefence();
        myid=pr.getValue(getApplicationContext());
        btnEtkinlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etkinlik etk=new etkinlik();
                etk.setAd(edtad.getText().toString());
                etk.setOlusturan(myid);
                etk.setKonum(edtkonum.getText().toString());
                etk.setDetay(edtdetay.getText().toString());
                etk.setTarih(edttarih.getText().toString());
                etk.setKontenjan(edtkontenjan.getText().toString());
                etk.setType("3");
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String key = mDatabase.child("posts").push().getKey();
                etk.setId(key);
                mDatabase.child("bireyselusers").child(myid).child("etkinlikler").child(key).setValue(etk);



               finish();

            }
        });



    }
}
