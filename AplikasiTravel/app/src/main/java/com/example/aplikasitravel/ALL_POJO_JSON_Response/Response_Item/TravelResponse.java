package com.example.aplikasitravel.ALL_POJO_JSON_Response.Response_Item;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TravelResponse{

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("list_boking")
	private List<ListBokingItem> listBoking;

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setListBoking(List<ListBokingItem> listBoking){
		this.listBoking = listBoking;
	}

	public List<ListBokingItem> getListBoking(){
		return listBoking;
	}
}