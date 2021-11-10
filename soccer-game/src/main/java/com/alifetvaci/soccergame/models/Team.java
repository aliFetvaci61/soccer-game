package com.alifetvaci.soccergame.models;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "teams")
public class Team {

	@Id
	@JsonIgnore
	private String id;

	@NotBlank
	private String name;

	@NotBlank
	private Countries country;

	private int numOfPlayers;

	private int money;

	private String userId;

	public Team() {
		super();

	}

	public Team(String id, @NotBlank String name, @NotBlank Countries country, int numOfPlayers, int money,
			String userId) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.numOfPlayers = numOfPlayers;
		this.money = money;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
