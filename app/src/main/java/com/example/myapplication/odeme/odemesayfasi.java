package com.example.myapplication.odeme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;


public class odemesayfasi extends AppCompatActivity {
Button odemebtn;
EditText fiyatedt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odemesayfasi);
        odemebtn=findViewById(R.id.button2);
        fiyatedt=(EditText)findViewById(R.id.fiyatedt);
        odemebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();

                String fiyat= fiyatedt.getText().toString();
                returnIntent.putExtra("result",fiyat);

                setResult(Activity.RESULT_OK,returnIntent);
                finish();

        //        PaymentSample py=new PaymentSample();
          //      py.should_create_payment();
            }
        });


    }






}
