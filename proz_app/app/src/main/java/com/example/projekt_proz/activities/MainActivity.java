package com.example.projekt_proz.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_proz.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AfterLogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void logInService(View view){
         String account =  ((EditText) findViewById(R.id.input1)).getText().toString();
            String password =  ((EditText) findViewById(R.id.input2)).getText().toString();

            if(account.equals("test")&&password.equals("123")){   startActivity(new Intent(MainActivity.this, AfterLogin.class));}
           else{  Toast.makeText(this,"Nie udało się zalogować! ",Toast.LENGTH_LONG).show();}

    }

}
