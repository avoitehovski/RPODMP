package com.example.rpodmp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rpodmp.LR3.ProductsActivity;
import com.example.rpodmp.LR4.RecepiesActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        getSupportActionBar().hide();
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.LR1)
                    startActivity(new Intent(instance, LR1Activity.class));
                else if (itemId == R.id.LR2)
                    startActivity(new Intent(instance, LR2Activity.class));
                else if (itemId == R.id.LR3)
                    startActivity(new Intent(instance, ProductsActivity.class));
                else if (itemId == R.id.LR5)
                    startActivity(new Intent(instance, RecepiesActivity.class));
                return true;
            }
        });
    }
}