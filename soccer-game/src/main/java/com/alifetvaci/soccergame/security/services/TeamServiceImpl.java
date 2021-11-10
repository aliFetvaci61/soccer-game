package com.alifetvaci.soccergame.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alifetvaci.soccergame.models.Countries;
import com.alifetvaci.soccergame.models.Team;
import com.alifetvaci.soccergame.models.User;
import com.alifetvaci.soccergame.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	private TeamRepository teamRepository;
	
	@Override
	public Team createTeam(User user) {
		Team team = new Team();
		team.setMoney(5000000);
		team.setNumOfPlayers(20);
		team.setCountry(Countries.TR);
		team.setUserId(user.getId());
		team.setName("Teams of " + user.getUsername());
		
		return teamRepository.insert(team);
	}

}
