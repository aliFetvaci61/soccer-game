package com.alifetvaci.soccergame.payload.request;

import javax.validation.constraints.NotBlank;

import com.alifetvaci.soccergame.models.Countries;

public class TeamRequest {
	
	@NotBlank
	private String name;

	@NotBlank
	private Countries country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Countries getCountry() {
		return country;
	}

	public void setCountry(Countries country) {
		this.country = country;
	}
	
	

}
