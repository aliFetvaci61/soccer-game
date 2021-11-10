package com.alifetvaci.soccergame.payload.response;

import java.util.List;

import com.alifetvaci.soccergame.models.Player;
import com.alifetvaci.soccergame.models.Team;

public class LoginResponse {
	
	private JwtResponse jwtResponse;
	
	private Team team;
	
	private List<Player> players;
	
	public LoginResponse() {
		super();
	}
	
	public LoginResponse(JwtResponse jwtResponse, Team team, List<Player> players) {
		super();
		this.jwtResponse = jwtResponse;
		this.team = team;
		this.players = players;
	}



	public JwtResponse getJwtResponse() {
		return jwtResponse;
	}

	public void setJwtResponse(JwtResponse jwtResponse) {
		this.jwtResponse = jwtResponse;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "LoginResponse [jwtResponse=" + jwtResponse + ", team=" + team + ", players=" + players + "]";
	}
	
	

}
