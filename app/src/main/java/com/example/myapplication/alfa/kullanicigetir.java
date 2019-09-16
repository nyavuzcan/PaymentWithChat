package com.example.myapplication.alfa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class kullanicigetir extends AppCompatActivity {

    public  DataSnapshot dx;
    DatabaseReference mDatabase;
    TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testgetir);
        kullanicidondur();
    }

    public void setDx(DataSnapshot dx) {
        this.dx = dx;
    }

    public DataSnapshot getDx() {
        return dx;
    }

    public DataSnapshot kullanicidondur(){

        mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot dt) {
              /*  for (DataSnapshot gelen:dataSnapshot.getChildren()){
                    txt.append(gelen.getValue(bireyselkullanicilar.class).getId()+"\n"+gelen.getValue(bireyselkullanicilar.class).getIsim()+"\n");
                }*/


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       setDx(dt);
                    }
                });
                Log.d("hatax1", String.valueOf(getDx()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("hatax2", String.valueOf(getDx()));
        return dx;

    }

}
