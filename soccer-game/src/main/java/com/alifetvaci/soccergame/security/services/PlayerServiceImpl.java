package com.alifetvaci.soccergame.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alifetvaci.soccergame.models.Countries;
import com.alifetvaci.soccergame.models.Player;
import com.alifetvaci.soccergame.models.Team;
import com.alifetvaci.soccergame.models.TypeOfPlayers;
import com.alifetvaci.soccergame.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public boolean createPlayerOfTeams(Team team) {

		createPlayers(team, 3, TypeOfPlayers.GOALKEEPERS);
		createPlayers(team, 6, TypeOfPlayers.DEFENDERS);
		createPlayers(team, 6, TypeOfPlayers.MIDFIELDERS);
		createPlayers(team, 5, TypeOfPlayers.ATTACKERS);
		// TODO Auto-generated method stub
		return false;
	}

	private void createPlayers(Team team, int numOfPlayers, TypeOfPlayers typeOfPlayer) {
		for (int i = 1; i <= numOfPlayers; i++) {
			Player player = new Player();
			player.setFirstname("Player " + i + " " + typeOfPlayer.toString());
			player.setLastname("Lastname of " + i + " " + typeOfPlayer.toString());
			player.setCountry(Countries.TR);
			player.setMarketValue(1000000);
			player.setAge(randomAge());
			player.setTeamId(team.getId());
			player.setTypeOfPlayers(typeOfPlayer);
			player.setTransferFlag(false);
			player.setTransferPrice(0);
			playerRepository.insert(player);
		}
	}

	private int randomAge() {
		int min = 18;
		int max = 40;

		// Generate random int value from 50 to 100
		return (int) Math.floor(Math.random() * (max - min + 1) + min);

	}

}
