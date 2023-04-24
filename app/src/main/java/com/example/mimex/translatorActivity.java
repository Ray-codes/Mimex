package com.example.mimex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class translatorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        toolbar = findViewById(R.id.toolbarTranslator);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutTranslator);
        navigationView = findViewById(R.id.navigationViewTranslator);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationOpen, R.string.navigationClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);

        // navigation menu
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.signs_menu:
                startActivity(new Intent(translatorActivity.this,signActivity.class));
            case R.id.translator_menu:
                break;
            case R.id.guide_menu:
                startActivity(new Intent(translatorActivity.this,guideActivity.class));
            case R.id.about_menu:
                startActivity(new Intent(translatorActivity.this,aboutActivity.class));
        }
        return true;
    }
}