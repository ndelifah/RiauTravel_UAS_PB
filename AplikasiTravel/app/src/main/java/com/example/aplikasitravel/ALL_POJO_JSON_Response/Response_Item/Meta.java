package com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item;

import com.google.gson.annotations.SerializedName;

public class Meta{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private Boolean status;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public Boolean isStatus(){
		return status;
	}
}