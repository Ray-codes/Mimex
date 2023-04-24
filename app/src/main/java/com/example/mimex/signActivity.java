package com.example.mimex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class signActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CardView whatUrNameCard,whereLiveCard,howRUCard,howOldCard,howCanIHelpCard,thankYouCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        toolbar = findViewById(R.id.toolbarSigns);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutSigns);
        navigationView = findViewById(R.id.navigationViewSigns);

        whatUrNameCard = findViewById(R.id.WhatsUrName);
        whereLiveCard = findViewById(R.id.WhereULive);
        howRUCard = findViewById(R.id.HowRU);
        howOldCard = findViewById(R.id.HowOldRU);
        howCanIHelpCard = findViewById(R.id.HowCanIHelpU);
        thankYouCard = findViewById(R.id.ThankingYou);

        //tool bar modification
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationOpen, R.string.navigationClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);

        // navigation menu
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        whatUrNameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=7_IfOa0NZoA&ab_channel=ASLLOVE");
            }
        });

        whereLiveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=PuMaXsWhR-E&ab_channel=eHowEducation");
            }
        });


        howRUCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=14L639SkuXE&ab_channel=MUSLSClub");
            }
        });

        howOldCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=O2rk8olIBT4&ab_channel=eHowEducation");
            }
        });

        howCanIHelpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=_tQWsgX-ljU&ab_channel=SignTimeWithEmilie");
            }
        });

        thankYouCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/watch?v=EPlhDhll9mw&ab_channel=GrabOfficial");
            }
        });
    }

    private void gotoUrl(String s) {

        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.signs_menu:
                 break;
            case R.id.translator_menu:
                startActivity(new Intent(signActivity.this,translatorActivity.class));
            case R.id.guide_menu:
                startActivity(new Intent(signActivity.this,guideActivity.class));
            case R.id.about_menu:
                startActivity(new Intent(signActivity.this,aboutActivity.class));
        }
        return true;
    }

}