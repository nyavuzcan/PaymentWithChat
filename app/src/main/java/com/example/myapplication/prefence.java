package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class prefence {
    final static String PREF_NAME="eXEDRA";
    public void save(Context context,String id){

        SharedPreferences settings=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString("valueid",id);
        editor.commit();



    }

    public  String getValue(Context context){

        SharedPreferences settings=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        String valueid=  settings.getString("valueid",null);

        return valueid;
    }

    public void clear(Context context){
        SharedPreferences settings=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.clear();
        editor.commit();
    }
}
