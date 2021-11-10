package com.alifetvaci.soccergame.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alifetvaci.soccergame.models.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
