package com.example.myapplication.etkinlikpaylas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.bireyselkullanicilar;
import com.example.myapplication.prefence;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class etkinlikkisiler extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    ArrayList<bireyselkullanicilar> users=new ArrayList<>();
    DatabaseReference mDatabase;
    String myid;
    String etkid;
    prefence pr;
    Button imgbtn;
    String type;
    ArrayList<String> gonderileceklerlist=new ArrayList<>();
    etkinlikkisiler_adapter etkinlikkisiler_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlikkisiler);
        context=getApplicationContext();
        recyclerView=findViewById(R.id.rcykullanicilar);
        pr=new prefence();
        myid=pr.getValue(context);
        imgbtn=findViewById(R.id.btnpaylas);
        Intent intent=getIntent();
        etkid=intent.getStringExtra("etkid");
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                ArrayList<bireyselkullanicilar> yeni=new ArrayList<>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot gelen:dataSnapshot.getChildren()){
                        bireyselkullanicilar dtx=(gelen.getValue(bireyselkullanicilar.class));
                        if(!dtx.getId().equals(myid)) {
                            yeni.add(dtx);
                        }

                    }
                    etkinlikkisiler_adapter= new etkinlikkisiler_adapter(yeni,context);
                    recyclerView.setAdapter(etkinlikkisiler_adapter);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    imgbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gonderileceklerlist=  etkinlikkisiler_adapter.getPaylasilanlar();

            for (String alici:gonderileceklerlist){
                sendMessage(myid,alici,etkid);

            }


        }
    });
    }


    private void sendMessage (String sender, String receiver, String message){


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        type=String.valueOf(3);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type",type);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);



        reference.child("bireyselusers").child(myid).child("mesajlar").push().setValue(hashMap);


        type=String.valueOf(4);


        HashMap<String, Object> hashMapgonder = new HashMap<>();
        hashMapgonder.put("type",type);
        hashMapgonder.put("sender", sender);
        hashMapgonder.put("receiver", receiver);
        hashMapgonder.put("message", message);


        reference.child("bireyselusers").child(receiver).child("mesajlar").push().setValue(hashMapgonder);
        finish();
    }
}
