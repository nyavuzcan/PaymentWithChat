package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bireyselkayit extends Activity {
    private final static int REQUEST_CODE = 999;
    Button onay;

    EditText edtisim,edtsoyisim,edtmail;
    DatabaseReference mDatabase;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bryselkayit);

        onay = (Button) findViewById(R.id.bryslbtn);

        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);
                intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        edtisim=(EditText)findViewById(R.id.bryselisim);
        edtsoyisim=(EditText)findViewById(R.id.bryselsoyisim);
        edtmail=(EditText)findViewById(R.id.bryselemail);

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

                           bireyselkullanicilar bk= new bireyselkullanicilar();
                            bk.setId(account.getId());
                            bk.setIsim(edtisim.getText().toString());
                            bk.setSoyisim(edtsoyisim.getText().toString());
                            bk.setEmail(edtmail.getText().toString());
                            bk.setTelefon(account.getPhoneNumber().toString());
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("bireyselusers").child(account.getId()).setValue(bk);
                            //SHAREDPREFENCE


                            prefence pr=new prefence();
                            pr.save(getApplicationContext(),account.getId());
                           Toast.makeText(getApplicationContext(),pr.getValue(getApplicationContext()),Toast.LENGTH_LONG).show();


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

                else
                    Toast.makeText(getApplicationContext(), "Success ! %s" + result.getAuthorizationCode().substring(0, 10), Toast.LENGTH_LONG).show();

                startActivity(new Intent(this, Bottomnavi.class));
            }

        }
    }

}
