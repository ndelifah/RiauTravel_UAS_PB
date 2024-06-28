package com.example.aplikasitravel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.ListBokingItem;
import com.example.aplikasitravel.API.ApiConfig;
import com.example.aplikasitravel.Activity.DetailActivity;
import com.example.aplikasitravel.R;
import com.example.aplikasitravel.UPDATE.UpdateBokingActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private Context context;
    private List<ListBokingItem> travels;
    private static final String TAG = "TravelAdapter";

    public TravelAdapter(Context context, List<ListBokingItem> travels) {
        this.context = context;
        this.travels = travels;
    }

    // tampilan baru tiap kita input data
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_travel, parent, false);
        return new ViewHolder(view);
    }

    //memanggil setiap item yang ditampilkan
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListBokingItem travel = travels.get(position);
        holder.name.setText(travel.getNama());
        holder.tujuan.setText(travel.getDestinasi());

        String baseUrl = "http://10.20.34.110/db_travel/uploads/";
        String imageUrl = travel.getGambar() != null ? baseUrl + travel.getGambar() : null;
        Log.d(TAG, "Image URL: " + imageUrl);

        holder.deleteProduct.setOnClickListener(v -> deleteTravel(travel, position));

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.logo_apk)
                    .error(R.drawable.logo_apk)
                    .into(holder.avatar);
        } else {
            Log.e(TAG, "Image URL is empty or null");
            holder.avatar.setImageResource(R.drawable._avanza_gray_metallic_removebg_preview_1);
        }

        holder.itemView.setOnClickListener(click -> {
            Intent intent = new Intent(click.getContext(), DetailActivity.class);
            intent.putExtra("id", travel.getId());
            intent.putExtra("nama", travel.getNama());
            intent.putExtra("no_telpon", travel.getNoTelpon());
            intent.putExtra("destinasi", travel.getDestinasi());
            intent.putExtra("jumlah_tiket", travel.getJumlahTiket());
            intent.putExtra("harga", travel.getHarga());
            intent.putExtra("tanggal", travel.getTanggal());
            intent.putExtra("gambar", imageUrl);

            Log.d(TAG, "Navigating to DetailActivity with image URL: " + imageUrl); // Debug URL
            click.getContext().startActivity(intent);
        });
    }


        //memanggil API untuk menghapus item berdasarkan id
        private void deleteTravel(ListBokingItem travel, int position) {
        ApiConfig apiConfig = new ApiConfig();
        Log.d(TAG, "Deleting travel with ID: " + travel.getId()); // Log ID sebelum dikirim

        Call<Void> call = apiConfig.getApiService().deleteProduct(travel.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Delete successful for ID: " + travel.getId());
                    removeItem(position);
                    Toast.makeText(context, "Delete Travel berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Delete failed for ID: " + travel.getId() + ". Response: " + response.message());
                    Toast.makeText(context, "Delete Travel gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "Delete failed for ID: " + travel.getId() + ". Error: " + t.getMessage());
                Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeItem(int position) {
        travels.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, travels.size());
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    //pegang referensi ke elemen ui
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView tujuan;
        Button deleteProduct;
        Button updateProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.Gambar);
            name = itemView.findViewById(R.id.TeksNama);
            tujuan = itemView.findViewById(R.id.TeksLokasi);
            deleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
            updateProduct = itemView.findViewById(R.id.btnUpdateProduct);


            //listener untuk klik tombol update dengan data item yang sesuai
            updateProduct.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ListBokingItem travel = travels.get(position);
                    Intent intent = new Intent(v.getContext(), UpdateBokingActivity.class);
                    intent.putExtra("id", travel.getId());
                    intent.putExtra("nama", travel.getNama());
                    intent.putExtra("no_telpon", travel.getNoTelpon());
                    intent.putExtra("destinasi", travel.getDestinasi());
                    intent.putExtra("jumlah_tiket", travel.getJumlahTiket());
                    intent.putExtra("harga", travel.getHarga());
                    intent.putExtra("tanggal", travel.getTanggal());
                    intent.putExtra("gambar", travel.getGambar());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
