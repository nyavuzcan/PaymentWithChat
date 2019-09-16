package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;

public class Success extends AppCompatActivity {
    Button btnlogout;
    EditText userid,usermail,userphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        btnlogout=(Button)findViewById(R.id.logout);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();
                finish();
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                userid=(EditText)findViewById(R.id.userid);
                usermail=(EditText)findViewById(R.id.useremail);
                userphone=(EditText)findViewById(R.id.userphone);
                userid.setText(String.format("User ID %s",account.getId()));

                userphone.setText(String.format("User ID %s",account.getPhoneNumber()==null ?"":account.getPhoneNumber().toString()));


                usermail.setText(String.format("User ID %s",account.getEmail()));

            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }
}
