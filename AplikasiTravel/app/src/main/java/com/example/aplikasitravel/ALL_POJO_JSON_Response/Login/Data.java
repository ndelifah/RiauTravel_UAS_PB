package com.example.aplikasitravel.ALL_POJO_JSON_Response.Login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("user_id")
	private Integer userId;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("email")
	private String email;

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}