package com.example.aplikasitravel.Tampilan_Awal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasitravel.API.ApiConfig;
import com.example.aplikasitravel.Activity.BerandaActivity;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Login.ResponseLogin;
import com.example.aplikasitravel.DataLogin.UserPreference;
import com.example.aplikasitravel.R;
import com.example.aplikasitravel.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        isLoging();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(binding.inputUser.getText());
                String password = String.valueOf(binding.inputPassword.getText());

                if (username.isEmpty()) {
                    binding.inputUser.setError("ISI DULU DONG MANIZZ");
                } else if (password.isEmpty()) {
                    binding.inputPassword.setError("INI JUGA ISI DONG");
                } else {
                    processLogin(username, password);
                }
            }
        });
    }

    private void isLoging() {
        //inisialisasi object
        UserPreference userPreference = new UserPreference(this);
    }

    private void processLogin(String email, String password) {
        //hidupkan progressbar
        showLoading(true);
        ApiConfig apiConfig = new ApiConfig();
        Call<ResponseLogin> client = apiConfig.getApiService().loginuser(email, password);

        client.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                //kalau berhasil
                //matikan progress bar
                showLoading(false);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        //mengambil nilai status (berhasil LOGIN)
                        if (response.body().isStatus()) {
                            // Simpan email ke SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_email", email);
                            editor.apply();

                            Intent homeIntent = new Intent(LoginActivity.this, BerandaActivity.class);
                            startActivity(homeIntent);
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                showLoading(false);
            }
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}
