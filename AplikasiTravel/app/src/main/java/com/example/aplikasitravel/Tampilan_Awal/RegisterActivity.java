package com.example.aplikasitravel.Tampilan_Awal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasitravel.API.ApiConfig;
import com.example.aplikasitravel.R;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Register.ResponseRegister;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText Email, Fullname, Password;
    private MaterialButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Email = findViewById(R.id.inputEmail);
        Fullname = findViewById(R.id.inputFullname);
        Password = findViewById(R.id.inputPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String email = Email.getText().toString().trim();
            String fullname = Fullname.getText().toString().trim();
            String password = Password.getText().toString().trim();

            if (email.isEmpty() || fullname.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Mohon isi semua field", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(email, fullname, password);
            }
        });
    }

    private void registerUser(String email, String fullname, String password) {
        ApiConfig apiConfig = new ApiConfig();
        Call<ResponseRegister> call = apiConfig.getApiService().registerUser(email, fullname, password);

        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseRegister registerResponse = response.body();
                    if (registerResponse.isStatus()) {
                        // Simpan data pengguna ke SharedPreferences setelah registrasi berhasil
                        saveUserToSharedPreferences(email, fullname);

                        Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal: " + registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToSharedPreferences(String email, String fullname) {
        // Simpan data ke SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.putString("user_fullname", fullname);
        editor.apply();

        // Debug: Tambahkan pesan log atau toast untuk memastikan data tersimpan dengan benar
        Log.d("SharedPreferences", "Data tersimpan: email = " + email + ", fullname = " + fullname);
         }
}
