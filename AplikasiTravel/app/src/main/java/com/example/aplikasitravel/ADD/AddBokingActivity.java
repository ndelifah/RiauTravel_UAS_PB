package com.example.aplikasitravel.ADD;

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

import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.ListBokingItem;
import com.example.aplikasitravel.API.ApiConfig;
import com.example.aplikasitravel.Activity.BokingActivity;
import com.example.aplikasitravel.Activity.ProfileActivity;
import com.example.aplikasitravel.R;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.TravelResponse;
import com.example.aplikasitravel.Util.FileUtils;
import com.example.aplikasitravel.databinding.ActivityAddBokingBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBokingActivity extends AppCompatActivity {

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddBokingBinding binding = ActivityAddBokingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiConfig apiCore = new ApiConfig();

        ActivityResultLauncher<String> pickVisualMediaLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageUri = uri;
                        binding.productImage.setImageURI(imageUri);  // Menampilkan gambar yang dipilih
                    }
                });

        binding.productImage.setOnClickListener(v -> pickVisualMediaLauncher.launch("image/*"));

        binding.btnTambah.setOnClickListener(v -> {
            String nama = binding.Name.getText().toString();
            String noTelpon = binding.NoHP.getText().toString();
            String rute = binding.Rute.getText().toString();
            String jumlahTiket = binding.JumlahTiket.getText().toString();
            String harga = binding.Harga.getText().toString();

            if (nama.isEmpty()) {
                pesanToast("Nama belum diisi");
            } else if (noTelpon.isEmpty()) {
                pesanToast("Nomor Telpon belum diisi");
            } else if (rute.isEmpty()) {
                pesanToast("Rute / Destinasi belum diisi");
            } else if (jumlahTiket.isEmpty()) {
                pesanToast("Jumlah Tiket belum diisi");
            } else if (imageUri == null) {
                pesanToast("Gambar belum dipilih");
            } else {
                File file = new File(FileUtils.getPath(this, imageUri));
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
                MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("gambar", file.getName(), requestFile);

                RequestBody bodyNama = RequestBody.create(MediaType.parse("text/plain"), nama);
                RequestBody bodyNoTelpon = RequestBody.create(MediaType.parse("text/plain"), noTelpon);
                RequestBody bodyRute = RequestBody.create(MediaType.parse("text/plain"), rute);
                RequestBody bodyJumlahTiket = RequestBody.create(MediaType.parse("text/plain"), jumlahTiket);
                RequestBody bodyHarga = RequestBody.create(MediaType.parse("text/plain"), harga);

                Call<ListBokingItem> travelResponseCall = apiCore.getApiService().AddTravel(bodyNama, bodyNoTelpon, bodyRute, bodyJumlahTiket, bodyHarga, bodyFile);
                travelResponseCall.enqueue(new Callback<ListBokingItem>() {
                    @Override
                    public void onResponse(@NonNull Call<ListBokingItem> call, @NonNull Response<ListBokingItem> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Integer id = response.body().getId();
                            pesanToast("Berhasil menambahkan data");

                            Intent intent = new Intent(AddBokingActivity.this, ProfileActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("nama", nama);
                            intent.putExtra("no_telpon", noTelpon);
                            intent.putExtra("destinasi", rute);
                            intent.putExtra("jumlah_tiket", jumlahTiket);
                            intent.putExtra("harga", harga);
                            intent.putExtra("gambar", imageUri.toString());
                            startActivity(intent);
                            finish();
                        } else {
                            pesanToast("Gagal: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ListBokingItem> call, @NonNull Throwable t) {
                        pesanToast("Kesalahan jaringan: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(AddBokingActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}
