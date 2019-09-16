package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EtkinlikSayfasi extends AppCompatActivity {
    ImageButton etkinliksilbtn, yenietkinlik, etkinlikarabtn;
    EditText etkinlikaraedt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etkinliklistesi);

        etkinliksilbtn = (ImageButton) findViewById(R.id.etkinliksilbtn);

        yenietkinlik = (ImageButton) findViewById(R.id.yenietkinlik);

        etkinlikarabtn = (ImageButton) findViewById(R.id.etkinlikarabtn);

        etkinlikaraedt=(EditText)findViewById(R.id.etkinlikaraedt);
        yenietkinlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),kisiler.class);
                startActivity(intent);
            }
        });

        etkinlikarabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etkinlikaraedt.setVisibility(View.VISIBLE);
                etkinlikaraedt.requestFocus();

            }
        });

    }

}