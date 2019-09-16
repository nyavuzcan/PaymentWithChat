package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.Object;
public class ProfilKurumsal extends AppCompatActivity {

    ImageButton btnKurumsalOnay;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilkurumsal);

        btnKurumsalOnay = (ImageButton) findViewById(R.id.btnKurumsalOnay);

        btnKurumsalOnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), sohbetKurumsal.class);
                startActivity(intent);

            }
        });




    }

}
