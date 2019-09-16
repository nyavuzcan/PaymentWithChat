package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class Sohbetler extends AppCompatActivity {
    ImageButton silbtn, yenisohbetbtn, aramabtn;
    EditText sohbetaramaedt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sohbetsayfasi);

        silbtn = (ImageButton) findViewById(R.id.silbutonu);

        yenisohbetbtn = (ImageButton) findViewById(R.id.yenisohbet);

        aramabtn = (ImageButton) findViewById(R.id.sohbetaramabtn);

        sohbetaramaedt=(EditText)findViewById(R.id.sohbetaramaedt);
        yenisohbetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),kisiler.class);
                startActivity(intent);
            }
        });

        aramabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sohbetaramaedt.setVisibility(View.VISIBLE);
                sohbetaramaedt.requestFocus();

            }
        });

    }

}