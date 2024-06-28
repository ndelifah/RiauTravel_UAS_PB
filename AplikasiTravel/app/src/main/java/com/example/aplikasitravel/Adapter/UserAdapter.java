package com.example.aplikasitravel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasitravel.ALL_POJO_JSON_Response.Login.Data;
import com.example.aplikasitravel.DataLogin.User;
import com.example.aplikasitravel.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    //panggil list data di responregis
    private List<Data> userList;

    public UserAdapter(List<Data> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Data user = userList.get(position);
        holder.fullname.setText(user.getFullname());
        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView fullname, email;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.fullname);
            email = itemView.findViewById(R.id.inputEmail);
        }
    }
}
