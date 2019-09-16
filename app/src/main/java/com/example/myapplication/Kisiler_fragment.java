package com.example.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.test.customtekno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Kisiler_fragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    ArrayList<bireyselkullanicilar> users=new ArrayList<>();
    DatabaseReference mDatabase;
    String myid;
    prefence pr;
    public Kisiler_fragment() {
        // Required empty public constructor
    }
    private void readData(final FirebaseCallback firebaseCallback){

    }

    private interface FirebaseCallback{
        void onCallback(ArrayList<bireyselkullanicilar> user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recly, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
            context=getActivity().getApplicationContext();
        recyclerView=view.findViewById(R.id.rexview);
        pr=new prefence();
       myid=pr.getValue(context);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("bireyselusers");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                users.clear();
                ArrayList<bireyselkullanicilar> yeni=new ArrayList<>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot gelen:dataSnapshot.getChildren()){
                        bireyselkullanicilar dtx=(gelen.getValue(bireyselkullanicilar.class));
                        if(!dtx.getId().equals(myid)) {
                            yeni.add(dtx);
                        }

                    }

                    customtekno customtekno=new customtekno(yeni,context);
                    recyclerView.setAdapter(customtekno);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    readData(new FirebaseCallback() {
        @Override
        public void onCallback(final ArrayList<bireyselkullanicilar> user) {

            customtekno customtekno=new customtekno(user,context);
            recyclerView.setAdapter(customtekno);
            Log.d("Hata","basidi");

        }
    });



        /*

        Liste=(ListView) getActivity().findViewById(R.id.kisilerlistesi);
        kisi_listesi.add(new kisi_listesi("NEVZAT YAVUZCAN"));
        kisi_listesi.add(new kisi_listesi("SÜLEYMAN EMRE GÜN"));
        kisi_listesi.add(new kisi_listesi("MEHMET NURULLAH SAĞLAM"));
        kisi_listesi.add(new kisi_listesi("KEMAL EMRE"));
        kisi_listesi.add(new kisi_listesi("VEYSEL SALİH"));
        kisi_listesi.add(new kisi_listesi("AHMET KAYA"));
        kisi_listesi.add(new kisi_listesi("CENGİZ ÜLKER"));

        final CustomAdapter adapter=new CustomAdapter(getActivity().getApplicationContext(),kisi_listesi);

        Liste.setAdapter(adapter);



        imgbttn=(ImageButton)getView(). findViewById(R.id.imgbttn);
        edttext=(EditText)getView(). findViewById(R.id.editsearch);
        lst=(ListView)getView().findViewById(R.id.kisilerlistesi);
        imgbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttext.setVisibility(View.VISIBLE);
                edttext.requestFocus();
            }
        });
     //   final ArrayAdapter<String> veriler=new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.satir,R.id.lkr,ulkeler);

     //   lst.setAdapter(veriler);

        edttext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //  veriler.getFilter().filter(s);

             //   ArrayList<String> temps=new ArrayList<>();
                templistesi.clear();
                s=s.toString().toUpperCase();

                for (int i=0; i<kisi_listesi.size();i++){
                    com.example.myapplication.kisi_listesi tempkisi=kisi_listesi.get(i);
                    if(tempkisi.getIsimsoyisim().toUpperCase().contains(s)){
                        templistesi.add(tempkisi);
                    }
                }
                if(templistesi!=null){
                CustomAdapter cs=new CustomAdapter(getActivity().getApplicationContext(),templistesi);
                lst.setAdapter(cs);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


*/
    }


}
