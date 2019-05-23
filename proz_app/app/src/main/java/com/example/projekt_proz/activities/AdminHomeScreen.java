package com.example.projekt_proz.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.projekt_proz.R;

public class AdminHomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
    }
    public void testCreationTransition(View view){}

    public void testViewTransition(View view){ Toast.makeText(this, "td", Toast.LENGTH_SHORT).show();}
    public void resultViewTransition(View view){ Toast.makeText(this, "td", Toast.LENGTH_SHORT).show();}
    public void statsViewTransition(View view){ Toast.makeText(this, "td", Toast.LENGTH_SHORT).show();}
    public void backToMain(View view){ Toast.makeText(this, "td", Toast.LENGTH_SHORT).show();}


}
