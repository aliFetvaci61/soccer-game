package com.alifetvaci.soccergame.security.services;

import com.alifetvaci.soccergame.models.Team;
import com.alifetvaci.soccergame.models.User;

public interface TeamService {
	Team createTeam(User user);
}
