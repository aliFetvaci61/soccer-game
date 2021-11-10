package com.alifetvaci.soccergame.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.soccergame.exception.BadRequestException;
import com.alifetvaci.soccergame.models.Team;
import com.alifetvaci.soccergame.payload.request.TeamRequest;
import com.alifetvaci.soccergame.repository.TeamRepository;
import com.alifetvaci.soccergame.security.services.UserService;

@RestController
@RequestMapping("/api")
public class TeamController {
	
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private UserService userService;

	@PutMapping("/user/team/{teamId}")
	@PreAuthorize("hasRole('ROLE_USER')or hasRole('ADMIN')")
	public Team updateTeam(@PathVariable String teamId, @Valid @RequestBody TeamRequest teamRequet,
			Authentication authentication) {

		return teamRepository.findById(teamId).map(team1 -> {
			
			userService.checkFraud(authentication, team1.getId());

			team1.setCountry(teamRequet.getCountry());
			team1.setName(teamRequet.getName());
			return teamRepository.save(team1);
		}).orElseThrow(() -> {
			logger.info("teamId " + teamId + " not found");
			return new BadRequestException("teamId " + teamId + " not found");
		});
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/team")
	public Page<Team> getAllTeam(Pageable pageable) {
		logger.info("findAll User ");
		return teamRepository.findAll(pageable);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/team/{teamId}")
	public Team updateTeam(@PathVariable String teamId, @Valid @RequestBody TeamRequest teamRequest) {

		Team team = teamRepository.findById(teamId).orElseThrow();
		team.setCountry(teamRequest.getCountry());
		team.setName(team.getName());

		return teamRepository.save(team);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/team/{teamId}")
	public ResponseEntity<?> deleteTeam(@PathVariable String teamId) {
		teamRepository.deleteById(teamId);
		return ResponseEntity.ok().build();
	}

}
