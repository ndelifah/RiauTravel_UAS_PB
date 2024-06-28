package com.example.aplikasitravel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aplikasitravel.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView idNo = findViewById(R.id.TeksId);
        ImageView gambarView = findViewById(R.id.Gambar);
        TextView namaView = findViewById(R.id.Teksname);
        TextView noTelponView = findViewById(R.id.TeksNohp);
        TextView lokasiView = findViewById(R.id.TeksLokasi);
        TextView jumlahTiketView = findViewById(R.id.TeksJumlahTiket);
        TextView hargaView = findViewById(R.id.TeksHarga);
        TextView tanggalView = findViewById(R.id.TeksTanggal);

        // Mengambil data dari Intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String nama = intent.getStringExtra("nama");
        String noTelpon = intent.getStringExtra("no_telpon");
        String destinasi = intent.getStringExtra("destinasi");
        String jumlahTiket = intent.getStringExtra("jumlah_tiket");
        String harga = intent.getStringExtra("harga");
        String tanggal = intent.getStringExtra("tanggal");
        String gambarUrl = intent.getStringExtra("gambar");

        // Menampilkan data ke komponen UI
        if (id != null) idNo.setText(id);
        if (nama != null) namaView.setText(nama);
        if (noTelpon != null) noTelponView.setText(noTelpon);
        if (destinasi != null) lokasiView.setText(destinasi);
        if (jumlahTiket != null) jumlahTiketView.setText(jumlahTiket);
        if (harga != null) hargaView.setText(harga);
        if (tanggal != null) tanggalView.setText(tanggal);

        // Menggunakan Picasso untuk menampilkan gambar dari URL
        if (gambarUrl != null && !gambarUrl.isEmpty()) {
            Picasso.get()
                    .load(gambarUrl)
                    .placeholder(R.drawable.logo_apk)
                    .error(R.drawable.logo_apk)
                    .into(gambarView);
        } else {
            gambarView.setImageResource(R.drawable.logo_apk);
        }

        // Debugging untuk memastikan data diterima dengan benar
        Log.d(TAG, "Data diterima: ID = " + id + ", Nama = " + nama + ", No Telpon = " + noTelpon + ", Destinasi = " + destinasi +
                ", Jumlah Tiket = " + jumlahTiket + ", Harga = " + harga + ", Tanggal = " + tanggal + ", Gambar URL = " + gambarUrl);
    }
}