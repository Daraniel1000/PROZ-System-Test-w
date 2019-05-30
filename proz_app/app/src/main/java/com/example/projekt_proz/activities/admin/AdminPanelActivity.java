package com.example.projekt_proz.activities.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.projekt_proz.R;

public class AdminPanelActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        login = getIntent().getStringExtra("login");
        password = getIntent().getStringExtra("password");

        if (savedInstanceState == null)
        {
            // Start with the drawer open
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class<? extends Fragment> fragmentClass = null;
        if (id == R.id.nav_results) {
            fragmentClass = FragmentResultsList.class;
        } else if (id == R.id.nav_tests) {
            fragmentClass = FragmentTestList.class;
        } else if (id == R.id.nav_questions) {
            fragmentClass = FragmentQuestionList.class;
        } else if (id == R.id.nav_logout) {
            supportFinishAfterTransition();
        }
        if(fragmentClass != null) {
            changeView(fragmentClass);
            setTitle(item.getTitle());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeView(Class<? extends Fragment> fragmentClass)
    {
        try {
            Fragment fragment = fragmentClass.newInstance();
            Bundle args = new Bundle();
            args.putString("login", login);
            args.putString("password", password);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
