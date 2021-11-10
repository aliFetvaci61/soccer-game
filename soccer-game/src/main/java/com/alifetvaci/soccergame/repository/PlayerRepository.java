package com.alifetvaci.soccergame.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alifetvaci.soccergame.models.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

	Page<Player> findByTeamId(Pageable pageable, String teamId);
	List<Player> findByTeamId(String teamId);
	List<Player> findByTransferFlagTrue(); 
	List<Player> findByTransferFlagTrueAndCountry(String country); 
}
