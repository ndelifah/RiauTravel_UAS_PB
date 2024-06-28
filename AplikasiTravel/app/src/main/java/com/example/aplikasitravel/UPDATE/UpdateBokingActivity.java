package com.example.aplikasitravel.UPDATE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasitravel.ADD.AddBokingActivity;
import com.example.aplikasitravel.API.ApiConfig;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.TravelResponse;
import com.example.aplikasitravel.Activity.BokingActivity;
import com.example.aplikasitravel.Activity.ProfileActivity;
import com.example.aplikasitravel.R;
import com.example.aplikasitravel.Util.FileUtils;
import com.example.aplikasitravel.databinding.ActivityUpdateBokingBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBokingActivity extends AppCompatActivity {

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUpdateBokingBinding binding = ActivityUpdateBokingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengatur padding berdasarkan system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiConfig apiCore = new ApiConfig();

        // ActivityResultLauncher untuk memilih gambar dari galeri
        ActivityResultLauncher<String> pickVisualMediaLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageUri = uri;
                        binding.productImage.setImageURI(imageUri);  // Menampilkan gambar yang dipilih
                    }
                });

        // Menggunakan image picker saat gambar diklik
        binding.productImage.setOnClickListener(v -> pickVisualMediaLauncher.launch("image/*"));

        // Listener untuk tombol update
        binding.btnUpdate.setOnClickListener(v -> {
            String id = binding.Id.getText().toString();
            String nama = binding.Name.getText().toString();
            String noTelpon = binding.NoHP.getText().toString();
            String rute = binding.Rute.getText().toString();
            String jumlahTiket = binding.JumlahTiket.getText().toString();
            String harga = binding.Harga.getText().toString();

            // Validasi input
            if (nama.isEmpty() || noTelpon.isEmpty() || rute.isEmpty() || jumlahTiket.isEmpty() || harga.isEmpty()) {
                pesanToast("Harap lengkapi semua data");
                return;
            }

            // Persiapkan bagian file jika gambar dipilih
            MultipartBody.Part bodyFile = null;
            if (imageUri != null) {
                String filePath = FileUtils.getPath(this, imageUri);
                if (filePath != null) {
                    File file = new File(filePath);
                    RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
                    bodyFile = MultipartBody.Part.createFormData("gambar", file.getName(), requestFile);
                } else {
                    pesanToast("Gagal mendapatkan path gambar");
                    return;
                }
            }

            // Persiapkan bagian lainnya
            RequestBody bodyId = RequestBody.create(MediaType.parse("text/plain"), id);
            RequestBody bodyNama = RequestBody.create(MediaType.parse("text/plain"), nama);
            RequestBody bodyNoTelpon = RequestBody.create(MediaType.parse("text/plain"), noTelpon);
            RequestBody bodyRute = RequestBody.create(MediaType.parse("text/plain"), rute);
            RequestBody bodyJumlahTiket = RequestBody.create(MediaType.parse("text/plain"), jumlahTiket);
            RequestBody bodyHarga = RequestBody.create(MediaType.parse("text/plain"), harga);
            RequestBody method = RequestBody.create(MediaType.parse("text/plain"), "PATCH");


            // Panggil API untuk mengupdate data travel
            Call<TravelResponse> travelResponseCall = apiCore.getApiService().updateTravel(bodyId, bodyNama, bodyNoTelpon, bodyRute, bodyJumlahTiket, bodyHarga, method, bodyFile);
            travelResponseCall.enqueue(new Callback<TravelResponse>() {
                @Override
                public void onResponse(@NonNull Call<TravelResponse> call, @NonNull Response<TravelResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        pesanToast("Berhasil mengupdate data");
                        Intent intent = new Intent(UpdateBokingActivity.this, BokingActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pesanToast("Gagal: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TravelResponse> call, @NonNull Throwable t) {
                    pesanToast("Kesalahan jaringan: " + t.getMessage());
                }
            });
        });
    }

    // Metode untuk menampilkan pesan toast
    private void pesanToast(String pesan) {
        Toast.makeText(UpdateBokingActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}
