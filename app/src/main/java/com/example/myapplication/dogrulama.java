package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class dogrulama extends Activity {
    Context context=this;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogrulama);

        btn=(Button)findViewById(R.id.btngonder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Bryselgiris.class);
                startActivity(intent);
                Toast.makeText(context,"Git",Toast.LENGTH_LONG).show();
            }
        });
    }
}
