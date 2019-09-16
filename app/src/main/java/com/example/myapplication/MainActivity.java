package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

public class MainActivity extends AppCompatActivity {
    Button btnbireysel;
    Button btnhspvar;
    prefence pr;
    private final static int REQUEST_CODE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pr=new prefence();

        if(pr.getValue(getApplicationContext())!=null){

         //   Intent intent=new Intent(getApplicationContext(),Bottomnavi.class);
            Intent intent=new Intent(getApplicationContext(),Bottomnavi.class);

            intent.putExtra("userid",pr.getValue(getApplicationContext()));
            startActivity(intent);
            finish();
        }

        btnhspvar=findViewById(R.id.btnhspvar);

        btnhspvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);
                intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
                startActivityForResult(intent, REQUEST_CODE);

            }
        });




        btnbireysel=(Button)findViewById(R.id.bireysel);

        btnbireysel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),bireyselkayit.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null) {
                Toast.makeText(this, "" + result.getError().getErrorType().getMessage(), Toast.LENGTH_LONG).show();
                return;
            } else if (result.wasCancelled()) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
                return;
            } else {

                if (result.getAccessToken() != null) {
                    // Toast.makeText(getApplicationContext(), "Success ! %s" + result.getAccessToken().getAccountId(), Toast.LENGTH_LONG).show();
                /*    AlertDialog alertDialog=new SpotsDialog.Builder().setContext(getApplicationContext()).build();
                    alertDialog.show();
                    alertDialog.setMessage("Lütfen bekleyin...");
*/
                    //Get user phone and check exist on server

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {


                            //SHAREDPREFENCE


                            prefence pr=new prefence();
                            pr.save(getApplicationContext(),account.getId());

                            Intent intent=new Intent(getApplicationContext(),Bottomnavi.class);

                            intent.putExtra("userid",pr.getValue(getApplicationContext()));
                            startActivity(intent);
                            finish();

                        }

                        private SharedPreferences getPreferences(int modePrivate) {
                            return null;
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                        }
                    });

//alertDialog.show();/dismiss();

                }


            }

        }
    }

    public void gecis(View v){

        if(v.getId()==R.id.kurumsal){

            Intent x=new Intent(getApplicationContext(),krmsalkayit.class);
            startActivity(x);

        }

    }


}
