package com.example.aplikasitravel.ALL_POJO_JSON_Response.Register;

public class ResponseRegister{
	private Data data;
	private String message;
	private Boolean status;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

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
