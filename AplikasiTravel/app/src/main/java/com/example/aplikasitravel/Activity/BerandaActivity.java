package com.example.aplikasitravel.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasitravel.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class BerandaActivity extends AppCompatActivity {

    private ChipNavigationBar bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda);

        bottomNavigation = findViewById(R.id.chipNavigationBar);

        bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                if (id == R.id.home) {
                    startActivity(new Intent(BerandaActivity.this, BerandaActivity.class));
                } else if (id == R.id.list_booking) {
                    startActivity(new Intent(BerandaActivity.this, BokingActivity.class));
                } else if (id == R.id.cart) {
                    startActivity(new Intent(BerandaActivity.this, BerandaActivity.class));
                } else if (id == R.id.profile) {
                    startActivity(new Intent(BerandaActivity.this, ProfileActivity.class));
                }
            }
        });

    }
}