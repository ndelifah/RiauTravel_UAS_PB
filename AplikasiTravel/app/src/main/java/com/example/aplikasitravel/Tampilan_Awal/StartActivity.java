package com.example.aplikasitravel.Tampilan_Awal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasitravel.R;


public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_acitivity);

        // Handler untuk menunda eksekusi selama 2 detik (2000 milidetik)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Berpindah ke aktivitas kedua
                Intent intent = new Intent(StartActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
                // Menutup aktivitas saat ini
                finish();
            }
        }, 1000);
    }
}
