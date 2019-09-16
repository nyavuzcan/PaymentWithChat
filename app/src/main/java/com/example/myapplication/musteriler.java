package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class musteriler extends AppCompatActivity {
    public String[] ulkeler =
            {"Türkiye", "Almanya", "Avusturya", "Amerika","İngiltere",
                    "Macaristan", "Yunanistan", "Rusya", "Suriye", "İran", "Irak",
                    "Şili", "Brezilya", "Japonya", "Portekiz", "İspanya",
                    "Makedonya", "Ukrayna", "İsviçre"};
    ListView musterilistesi;
    ImageButton mustrbtn;
    EditText musteriara;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musteriler);

        mustrbtn=(ImageButton) findViewById(R.id.mustrbtn);
        musteriara=(EditText) findViewById(R.id.musteriara);
        musterilistesi=(ListView)findViewById(R.id.musterilistesi);
        mustrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musteriara.setVisibility(View.VISIBLE);
                musteriara.requestFocus();
            }
        });
        final ArrayAdapter<String> veriler=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,ulkeler);

        musterilistesi.setAdapter(veriler);

        musteriara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                veriler.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
