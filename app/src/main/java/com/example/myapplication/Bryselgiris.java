package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Bryselgiris extends AppCompatActivity {
    Button smsbtn,btnemail;
    DatabaseReference mDatabase;
    FirebaseDatabase database;
    private final static int REQUEST_CODE=999;
    ImageButton btngiris;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bryselgiris);
        printKeyHash();
        btngiris=(ImageButton)findViewById(R.id.girisbtn);
        btngiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),kisiler.class);
                startActivity(intent);
            }
        });

        smsbtn=(Button)findViewById(R.id.smskayit);
        btnemail=(Button)findViewById(R.id.emailkayit);

      btnemail.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startLoginPage(LoginType.EMAIL);

          }
      });
        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.PHONE);

            }
        });


    }

    private void startLoginPage(LoginType loginType) {
       if(loginType==LoginType.EMAIL){
           Intent intent=new Intent(getApplicationContext(), AccountKitActivity.class);
           AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder=
                   new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,AccountKitActivity.ResponseType.TOKEN);
           intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
           startActivityForResult(intent,REQUEST_CODE);
       }
       else if(loginType==LoginType.PHONE){
           Intent intent=new Intent(this, AccountKitActivity.class);
           AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder=
                   new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,AccountKitActivity.ResponseType.TOKEN);
           intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
           startActivityForResult(intent,REQUEST_CODE);
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            AccountKitLoginResult result=data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if(result.getError()!=null){
                Toast.makeText(this,""+result.getError().getErrorType().getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
            else if(result.wasCancelled()){
                Toast.makeText(this,"Cancel",Toast.LENGTH_LONG).show();
                return;
            }
            else{

                if(result.getAccessToken()!=null)
                    Toast.makeText(getApplicationContext(),"Success ! %s"+result.getAccessToken().getAccountId(),Toast.LENGTH_LONG).show();
            else
                    Toast.makeText(getApplicationContext(),"Success ! %s"+result.getAuthorizationCode().substring(0,10),Toast.LENGTH_LONG).show();

                startActivity(new Intent(this,Success.class));
            }

        }
    }







    private void printKeyHash(){
        try{
            PackageInfo info=getPackageManager().getPackageInfo("com.example.myapplication", PackageManager.GET_SIGNATURES);
           for(Signature signature : info.signatures){
               MessageDigest md= MessageDigest.getInstance("SHA");
               md.update(signature.toByteArray());
               Log.d("KEYHASH",Base64.encodeToString(md.digest(),Base64.DEFAULT));
           }
        } catch (PackageManager.NameNotFoundException  e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }



}
