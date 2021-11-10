package com.alifetvaci.soccergame.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alifetvaci.soccergame.models.Team;


@Repository
public interface TeamRepository extends MongoRepository<Team, String>{
	
	Optional<Team> findByUserId(String userId);

}
