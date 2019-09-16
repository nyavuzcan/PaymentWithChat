package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class sohbetKurumsal extends AppCompatActivity {

    ImageButton mesajKurumsal;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sohbetsayfakurumsal);

        mesajKurumsal = (ImageButton) findViewById(R.id.mesajKurumsal);

        mesajKurumsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), musteriler.class);
                startActivity(intent);



            }
        });





    }
}
