package com.example.aplikasitravel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitravel.API.ApiConfig;
import com.example.aplikasitravel.Adapter.TravelAdapter;
import com.example.aplikasitravel.R;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.ListBokingItem;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.TravelResponse;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BokingActivity extends AppCompatActivity {
    private ChipNavigationBar bottomNavigation;
    private RecyclerView recyclerView;
    private TravelAdapter adapter;
    private List<ListBokingItem> travels; // Variabel untuk menyimpan data travel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boking);

        recyclerView = findViewById(R.id.Tampilan);

        ApiConfig apiConfig = new ApiConfig();
        Call<TravelResponse> call = apiConfig.getApiService().gettravel();

        call.enqueue(new Callback<TravelResponse>() {
            @Override
            public void onResponse(Call<TravelResponse> call, Response<TravelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    travels = response.body().getListBoking(); // Simpan data travel ke variabel travels
                    setupRecyclerView(); // Panggil fungsi setup RecyclerView
                } else {
                    Toast.makeText(BokingActivity.this, "Failed to get boking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TravelResponse> call, Throwable t) {
                Toast.makeText(BokingActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        bottomNavigation = findViewById(R.id.chipNavigationBar);

        bottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                if (id == R.id.home) {
                    startActivity(new Intent(BokingActivity.this, BerandaActivity.class));
                } else if (id == R.id.list_booking) {
                    startActivity(new Intent(BokingActivity.this, BokingActivity.class));
                } else if (id == R.id.cart) {
                    startActivity(new Intent(BokingActivity.this, BerandaActivity.class));
                } else if (id == R.id.profile) {
                    startActivity(new Intent(BokingActivity.this, ProfileActivity.class));
                }
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new TravelAdapter(BokingActivity.this, travels); // Gunakan variabel travels dari kelas BokingActivity
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BokingActivity.this));
    }
}
