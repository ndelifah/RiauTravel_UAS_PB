package com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item;

import com.google.gson.annotations.SerializedName;

public class ListBokingItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("nama")
	private String nama;

	@SerializedName("harga")
	private String harga;

	@SerializedName("jumlah_tiket")
	private String jumlahTiket;

	@SerializedName("id")
	private int id;

	@SerializedName("destinasi")
	private String destinasi;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("no_telpon")
	private String noTelpon;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("updatedAt")
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setJumlahTiket(String jumlahTiket){
		this.jumlahTiket = jumlahTiket;
	}

	public String getJumlahTiket(){
		return jumlahTiket;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDestinasi(String destinasi){
		this.destinasi = destinasi;
	}

	public String getDestinasi(){
		return destinasi;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setNoTelpon(String noTelpon){
		this.noTelpon = noTelpon;
	}

	public String getNoTelpon(){
		return noTelpon;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}