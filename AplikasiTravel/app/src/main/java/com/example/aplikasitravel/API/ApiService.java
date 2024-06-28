package com.example.aplikasitravel.API;


import com.example.aplikasitravel.ALL_POJO_JSON_Response.Login.ResponseLogin;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Register.ResponseRegister;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.ListBokingItem;
import com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item.TravelResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //x-www-from
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseRegister> registerUser(
            @Field("email") String email,
            @Field("fullname") String fullname,
            @Field("password") String password
    );

    //x-www-from
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginuser(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("get_travel.php")
    Call<TravelResponse> gettravel();

    //form-data
    @Multipart
    @POST("create_travel.php")
    Call<ListBokingItem> AddTravel(
            @Part("nama") RequestBody nama,
            @Part("no_telpon") RequestBody no_telpon,
            @Part("destinasi") RequestBody destinasi,
            @Part("jumlah_tiket") RequestBody jumlah_tiket,
            @Part("harga") RequestBody harga,
            @Part MultipartBody.Part picture
    );

    //masukkan id contoh http://127.0.0.1/db_travel/api/delete_travel.php?id=27
    @DELETE("delete_travel.php")
    Call<Void> deleteProduct(@Query("id") int id);


    //form-data
    @Multipart
    @POST("update_travel.php")
    Call<TravelResponse> updateTravel(
            @Part("id") RequestBody id,
            @Part("nama") RequestBody nama,
            @Part("no_telpon") RequestBody noTelpon,
            @Part("destinasi") RequestBody destinasi,
            @Part("jumlah_tiket") RequestBody jumlahTiket,
            @Part("harga") RequestBody harga,
            @Part("_method") RequestBody method,
            @Part MultipartBody.Part gambar
    );

}
