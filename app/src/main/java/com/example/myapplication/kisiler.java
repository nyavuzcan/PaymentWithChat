package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class kisiler extends AppCompatActivity {
    public String[] ulkeler =
            {"Türkiye", "Almanya", "Avusturya", "Amerika","İngiltere",
                    "Macaristan", "Yunanistan", "Rusya", "Suriye", "İran", "Irak",
                    "Şili", "Brezilya", "Japonya", "Portekiz", "İspanya",
                    "Makedonya", "Ukrayna", "İsviçre"};
    ListView lst;
    ImageButton imgbttn;
    EditText edttext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kisiler);

    imgbttn=(ImageButton) findViewById(R.id.imgbttn);
    edttext=(EditText) findViewById(R.id.editsearch);
    lst=(ListView)findViewById(R.id.kisilerlistesi);
    imgbttn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edttext.setVisibility(View.VISIBLE);
            edttext.requestFocus();
        }
    });
            final ArrayAdapter<String> veriler=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,ulkeler);

            lst.setAdapter(veriler);

edttext.addTextChangedListener(new TextWatcher() {
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
