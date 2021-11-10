package com.alifetvaci.soccergame.payload.request;

import javax.validation.constraints.NotBlank;

public class InsertPlayerToTransferListRequest {

	@NotBlank
	private String playerId;

	@NotBlank
	private int price;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
