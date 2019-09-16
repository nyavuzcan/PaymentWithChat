package com.example.myapplication;


import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sohbetler_fragment extends Fragment {



    ImageButton silbtn, yenisohbetbtn, aramabtn;
    EditText sohbetaramaedt;
    List<sohbet_foto> fotolar=new ArrayList<>();
    List<kisi_listesi> kisiler=new ArrayList<>();
    List<sohbet_kisa> sohbet_kisa=new ArrayList<>();

    List<sohbet_foto> tempfoto=new ArrayList<>();
    List<kisi_listesi> tempkisi=new ArrayList<>();
    List<sohbet_kisa> tempsohbet=new ArrayList<>();
    ListView ls;
    LinearLayout linear;

    public Sohbetler_fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseApp.initializeApp(getActivity().getApplicationContext());

        return inflater.inflate(R.layout.fragment_sohbetler_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ls=(ListView)getActivity().findViewById(R.id.sohbetSayfa) ;



        linear=(LinearLayout)getActivity().findViewById(R.id.linear);
        sohbet_custom_adapter cs=new sohbet_custom_adapter(getActivity().getApplicationContext(),fotolar,kisiler,sohbet_kisa);
        ls.setAdapter(cs);
        if(cs.getCount()==0){


            ImageButton imgbutton = new ImageButton(getActivity().getApplicationContext());

            imgbutton.setBackgroundResource(R.drawable.shbet);

    linear.addView(imgbutton,0);
           imgbutton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Bottomnavi btm=(Bottomnavi)getActivity();
                   btm.setFragment(new Kisiler_fragment());
                   btm.switchmenu();
               }
           });


        }


        silbtn = (ImageButton) getView().findViewById(R.id.silbutonu);

        yenisohbetbtn = (ImageButton)getView(). findViewById(R.id.yenisohbet);

        aramabtn = (ImageButton)getView(). findViewById(R.id.sohbetaramabtn);

        sohbetaramaedt=(EditText)getView().findViewById(R.id.sohbetaramaedt);

        yenisohbetbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

            }
        });



        aramabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sohbetaramaedt.setVisibility(View.VISIBLE);
                sohbetaramaedt.requestFocus();

            }
        });


        sohbetaramaedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tempkisi.clear();
                tempsohbet.clear();
                s=s.toString().toUpperCase();
                for(int i=0; i<fotolar.size(); i++ ){
                    com.example.myapplication.sohbet_kisa sohbettemp=sohbet_kisa.get(i);
                    kisi_listesi kisitemp=kisiler.get(i);
                    if(sohbettemp.getKisamesajlar().toUpperCase().contains(s) ||  kisitemp.getIsimsoyisim().toUpperCase().contains(s) ){

                        tempkisi.add(kisitemp);
                        tempsohbet.add(sohbettemp);



                    }
                }
                if(tempkisi!=null || tempsohbet!=null){

                    sohbet_custom_adapter cs=new sohbet_custom_adapter(getActivity().getApplicationContext(),fotolar,tempkisi,tempsohbet);
                    ls.setAdapter(cs);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }








}
