package com.example.aplikasitravel.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasitravel.ADD.AddBokingActivity;
import com.example.aplikasitravel.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ProfileActivity extends AppCompatActivity {

    private ChipNavigationBar bottomNavigation;
    private TextView fullname;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        bottomNavigation = findViewById(R.id.chipNavigationBar);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);

        // Ambil email dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", "Email not found");
        String userFullname = sharedPreferences.getString("user_fullname", "Fullname not found");

        // Set text pada TextView fullname
        fullname.setText(userFullname);
        email.setText(userEmail);

        bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                if (id == R.id.home) {
                    startActivity(new Intent(ProfileActivity.this, BerandaActivity.class));
                } else if (id == R.id.list_booking) {
                    startActivity(new Intent(ProfileActivity.this, BokingActivity.class));
                } else if (id == R.id.cart) {
                    startActivity(new Intent(ProfileActivity.this, BerandaActivity.class));
                } else if (id == R.id.profile) {
                    startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                }
            }
        });

        // Tambahkan OnClickListener untuk ImageButton
        ImageButton tambahProdukButton = findViewById(R.id.addData);
        tambahProdukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, AddBokingActivity.class));
            }
        });

        ImageButton keluarButton = findViewById(R.id.logout);
        keluarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Konfirmasi Keluar")
                        .setMessage("Apakah Anda yakin ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });

        ImageView addressButton = findViewById(R.id.address);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://maps.app.goo.gl/XuUn4XfzHtMfW6m2A"));
                startActivity(intent);
            }
        });
    }
}
