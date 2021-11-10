package com.alifetvaci.soccergame.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.soccergame.models.Player;
import com.alifetvaci.soccergame.models.Roles;
import com.alifetvaci.soccergame.models.Team;
import com.alifetvaci.soccergame.models.User;
import com.alifetvaci.soccergame.payload.request.LoginRequest;
import com.alifetvaci.soccergame.payload.response.JwtResponse;
import com.alifetvaci.soccergame.payload.response.LoginResponse;
import com.alifetvaci.soccergame.repository.PlayerRepository;
import com.alifetvaci.soccergame.repository.TeamRepository;
import com.alifetvaci.soccergame.repository.UserRepository;
import com.alifetvaci.soccergame.security.jwt.JwtUtils;
import com.alifetvaci.soccergame.security.services.PlayerService;
import com.alifetvaci.soccergame.security.services.TeamService;
import com.alifetvaci.soccergame.security.services.UserDetailsImpl;
import com.alifetvaci.soccergame.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamService teamService;

	@Autowired
	PlayerService playerService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if (userService.isAdmin(userDetails)) {
			return ResponseEntity
					.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
		} else {
			Team team = teamRepository.findByUserId(userDetails.getId()).orElseThrow();
			List<Player> players = playerRepository.findByTeamId(team.getId());

			return ResponseEntity.ok(new LoginResponse(
					new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()), team,
					players));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			return ResponseEntity.badRequest().build();
		}
		user.setRoles(Roles.ROLE_USER);
		user.setPassword(encoder.encode(user.getPassword()));
		User insertedUser = userRepository.insert(user);
		Team createdTeam = teamService.createTeam(insertedUser);
		playerService.createPlayerOfTeams(createdTeam);
		return ResponseEntity.ok(insertedUser);

	}
}
