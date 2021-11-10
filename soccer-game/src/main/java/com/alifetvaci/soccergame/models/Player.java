package com.alifetvaci.soccergame.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "players")
public class Player {
	@Id
	private String id;
	private String firstname;
	private String lastname;
	private Countries country;
	private int age;
	private int marketValue;
	private TypeOfPlayers typeOfPlayers;
	private String teamId;
	private int transferPrice;
	private boolean transferFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Countries getCountry() {
		return country;
	}

	public void setCountry(Countries country) {
		this.country = country;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(int marketValue) {
		this.marketValue = marketValue;
	}
	
	public TypeOfPlayers getTypeOfPlayers() {
		return typeOfPlayers;
	}

	public void setTypeOfPlayers(TypeOfPlayers typeOfPlayers) {
		this.typeOfPlayers = typeOfPlayers;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public int getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(int transferPrice) {
		this.transferPrice = transferPrice;
	}

	public boolean getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(boolean transferFlag) {
		this.transferFlag = transferFlag;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", country=" + country
				+ ", age=" + age + ", marketValue=" + marketValue + ", typeOfPlayers=" + typeOfPlayers + ", teamId="
				+ teamId + ", transferPrice=" + transferPrice + ", transferFlag=" + transferFlag + "]";
	}

	
}
