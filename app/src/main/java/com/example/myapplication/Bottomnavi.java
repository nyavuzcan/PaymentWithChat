package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Bottomnavi extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainframe;
    public Sohbetler_fragment sohbetler;
    private Kisiler_fragment kisiler;
    private Etkinlikler etklinlikler;
    public Profil profil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomnavigationmenu);
        profil = new Profil();
        etklinlikler = new Etkinlikler();
        kisiler = new Kisiler_fragment();
        sohbetler = new Sohbetler_fragment();

        //Default FRAGMENT
        setFragment(sohbetler);

        mMainframe = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {




            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.sohbetlerid:
                            setFragment(sohbetler);
                        return true;
                    case R.id.kisilerid:
                        setFragment(kisiler);

                        return true;
                    case R.id.etkinlikid:
                setFragment(etklinlikler);
                        return true;
                    case R.id.profilid:
             setFragment(profil);
                    return true;




                }
                return false;   }
        });
    } //oncreta


    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame ,fragment);
     fragmentTransaction.commit();


    }
    public void switchmenu() {
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mMainNav.setSelectedItemId(R.id.kisilerid);
    }

}
