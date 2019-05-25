package com.example.projekt_proz.activities.admin.a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.projekt_proz.R;
import com.example.projekt_proz.activities.MainActivity;
import com.example.projekt_proz.models.ResultsWrapper;
import com.example.projekt_proz.models.prozResults;

import java.util.ArrayList;

public class AdminHomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_admin_home_screen);
    }
    public void testCreationTransition(View view){Intent intent = new Intent(AdminHomeScreen.this, TestCreation.class);
        startActivity(intent);}

    public void testViewTransition(View view){Intent intent = new Intent(AdminHomeScreen.this, TestView.class);
        startActivity(intent);
    }
    public void resultViewTransition(View view){
   /*TODO*/

    }


    public void statsViewTransition(View view){  /*TODO*/}


}
