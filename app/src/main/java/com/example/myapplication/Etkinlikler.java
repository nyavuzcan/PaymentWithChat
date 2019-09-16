package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Etkinlikler extends Fragment {
    public static int REQUEST_CODE=999;
    ImageButton etkinliksilbtn, yenietkinlik, etkinlikarabtn;
    EditText     etkinlikaraedt;
    RecyclerView etkrcy;
    Context context;
    RecyclerView recyclerView;
    ArrayList<etkinlik> etkinlikArrayList=new ArrayList<>();
    DatabaseReference mDatabase;
    String myid;
    prefence pr;

    public Etkinlikler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.etkinliklistesi, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etkinliksilbtn = (ImageButton)getView().findViewById(R.id.etkinliksilbtn);

        yenietkinlik = (ImageButton) getView().findViewById(R.id.yenietkinlik);

        etkinlikarabtn = (ImageButton)getView(). findViewById(R.id.etkinlikarabtn);

        etkinlikaraedt=(EditText)getView().findViewById(R.id.etkinlikaraedt);

        etkrcy=getView().findViewById(R.id.etkrcy);

        context=getActivity().getApplicationContext();

        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        etkrcy.setLayoutManager(layoutManager);
        etkrcy.setHasFixedSize(true);
        pr=new prefence();
        myid=pr.getValue(context);
        mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers").child(myid).child("etkinlikler");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                etkinlikArrayList.clear();
                ArrayList<etkinlik> yeni=new ArrayList<>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot gelen:dataSnapshot.getChildren()){
                        etkinlik etkinlikobj=(gelen.getValue(etkinlik.class));

                            yeni.add(etkinlikobj);


                    }
                    etkinlik_adapter etkinlik_adapter=new etkinlik_adapter(yeni,context);
                    etkrcy.setAdapter(etkinlik_adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        yenietkinlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),etkinlikOlusturma.class);
                startActivityForResult(intent , REQUEST_CODE);
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
