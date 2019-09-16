package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.test.mesajlar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {

    Button btn_send;
    EditText  text_send;


    int i=0;
    Intent intent;

    DatabaseReference mDatabase,mhedef,firemesaj,fireodeme;
    FirebaseDatabase database;
    String myid=null;
    String receiverid=null;
    TextView msg_name,mesajlartxt;
    prefence pr;
    ArrayList<mesajlar> mesajlarArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    ArrayList<String> etkinlikgonderilenler;
    String etkid;
    Boolean etkmi=false;
    String type;
    Button btnfazla;
    ArrayList<etkinlik> etkinlikArrayList=new ArrayList<>();
    public MessageActivity() {
    }

    public MessageActivity(ArrayList<String> etkinlikgonderilenler, String etkid, Boolean etkmi) {
        this.etkinlikgonderilenler = etkinlikgonderilenler;
        this.etkid = etkid;
        this.etkmi = etkmi;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==999){
            final String result=data.getStringExtra("result");

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bireyselkullanicilar k = dataSnapshot.getValue(bireyselkullanicilar.class);
                    sendPayment(myid,k.getId(),result);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!etkmi){
            setContentView(R.layout.activity_message);
            btnfazla=findViewById(R.id.btnfazla);
            btn_send = findViewById(R.id.btn_send);
            text_send = findViewById(R.id.text_send);
            msg_name = findViewById(R.id.msg_name);
            recyclerView = findViewById(R.id.reclyer_mesaj);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);

            intent = getIntent();
            receiverid = intent.getStringExtra("receiveid");
            pr = new prefence();
            myid = pr.getValue(this);

            mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers").child(receiverid);
            mhedef = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid);
            firemesaj = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid).child("mesajlar");


            firemesaj.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mesajlarArrayList.clear();

                    for (final DataSnapshot gelen : dataSnapshot.getChildren()) {
                        if (  (((gelen.getValue(mesajlar.class).getSender().equals(myid)) && (gelen.getValue(mesajlar.class).getReceiver().equals(receiverid))  )
                                || ((gelen.getValue(mesajlar.class).getSender().equals(receiverid)) && (gelen.getValue(mesajlar.class).getReceiver().equals(myid))  ) )) {
                            mesajlarArrayList.add(gelen.getValue(mesajlar.class));


                        }


                    }
                    sohbet_adapter sohbet_adapter = new sohbet_adapter(mesajlarArrayList, getApplicationContext());
                     recyclerView.setAdapter(sohbet_adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bireyselkullanicilar k = dataSnapshot.getValue(bireyselkullanicilar.class);
                    msg_name.setText(k.getIsim() + " " + k.getSoyisim());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            btnfazla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),com.example.myapplication.odeme.odemesayfasi.class);
                   startActivityForResult(i,999);



                }
            });




            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String msg = text_send.getText().toString();

                    if (!msg.equals("")) {

                        sendMessage(myid, receiverid, msg);
                    } else {
                        ValueEventListener dinle = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                        mDatabase.addValueEventListener(dinle);

                        Toast.makeText(MessageActivity.this, "Boş mesaj gönderemezsiniz!", Toast.LENGTH_SHORT).show();

                    }

                    text_send.setText("");

                }
            });


        }

    }

    private void sendPayment (String sender, String receiver, String message) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        type = String.valueOf(5);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);


        reference.child("bireyselusers").child(myid).child("mesajlar").push().setValue(hashMap);
        type=String.valueOf(6);


        HashMap<String, Object> hashMapgonder = new HashMap<>();
        hashMapgonder.put("type",type);
        hashMapgonder.put("sender", sender);
        hashMapgonder.put("receiver", receiver);
        hashMapgonder.put("message", message);


        reference.child("bireyselusers").child(receiverid).child("mesajlar").push().setValue(hashMapgonder);
    }

    private void sendMessage (String sender, String receiver, String message){


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        type=String.valueOf(1);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type",type);
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);



        reference.child("bireyselusers").child(myid).child("mesajlar").push().setValue(hashMap);


        type=String.valueOf(0);


        HashMap<String, Object> hashMapgonder = new HashMap<>();
        hashMapgonder.put("type",type);
        hashMapgonder.put("sender", sender);
        hashMapgonder.put("receiver", receiver);
        hashMapgonder.put("message", message);


        reference.child("bireyselusers").child(receiverid).child("mesajlar").push().setValue(hashMapgonder);

    }


}

