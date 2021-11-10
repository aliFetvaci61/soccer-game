package com.alifetvaci.soccergame.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.soccergame.exception.BadRequestException;
import com.alifetvaci.soccergame.exception.ForbiddenException;
import com.alifetvaci.soccergame.models.Player;
import com.alifetvaci.soccergame.models.Team;
import com.alifetvaci.soccergame.payload.request.InsertPlayerToTransferListRequest;
import com.alifetvaci.soccergame.payload.request.TransferRequest;
import com.alifetvaci.soccergame.repository.PlayerRepository;
import com.alifetvaci.soccergame.repository.TeamRepository;
import com.alifetvaci.soccergame.security.services.UserService;

@RestController
@RequestMapping("/api")
public class PlayerController {

	private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ADMIN')")
	@GetMapping("/player")
	public Page<Player> getAllPlayers(Pageable pageable, Authentication authentication) {
		logger.info("findAll Players ");
		if (userService.isAdmin(userService.getAuthanticatedUser(authentication))) {
			return playerRepository.findAll(pageable);
		} else {
			Team team = teamRepository.findByUserId(userService.getAuthanticationUserId(authentication))
					.orElseThrow(() -> {
						logger.info("team not found");
						return new BadRequestException("team not found");
					});
			return playerRepository.findByTeamId(pageable, team.getId());
		}

	}

	@PostMapping("/transfer/player")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ADMIN')")
	public Player insertPlayerToTransferList(@Valid @RequestBody InsertPlayerToTransferListRequest transferRequest,
			Authentication authentication) {

		return playerRepository.findById(transferRequest.getPlayerId()).map(player1 -> {

			Team team = teamRepository.findById(player1.getTeamId()).orElseThrow(() -> {
				logger.info("team not found");
				return new BadRequestException("team not found");
			});
			if (userService.isAdmin(userService.getAuthanticatedUser(authentication))
					|| userService.checkFraud(authentication, team.getUserId())) {

				player1.setTransferFlag(true);
				player1.setTransferPrice(transferRequest.getPrice());
				return playerRepository.save(player1);
			}
			throw new ForbiddenException();

		}).orElseThrow(() -> {
			logger.info("playerId " + transferRequest.getPlayerId() + " not found");
			return new BadRequestException("playerId " + transferRequest.getPlayerId() + " not found");
		});

	}

	@DeleteMapping("/transfer/player/{playerId}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ADMIN')")
	public Player deletePlayerToTransferList(@PathVariable String playerId,
			@RequestBody TransferRequest transferRequest, Authentication authentication) {

		Player player = playerRepository.findById(playerId).orElseThrow();
		if (userService.isAdmin(userService.getAuthanticatedUser(authentication))) {

			// Buyer
			Team team = teamRepository.findByUserId(transferRequest.getTeamId()).orElseThrow();
			team.setMoney(team.getMoney() - player.getTransferPrice());
			teamRepository.save(team);

			// Seller
			Team team2 = teamRepository.findByUserId(player.getTeamId()).orElseThrow();
			team2.setMoney(team2.getMoney() - player.getTransferPrice());
			teamRepository.save(team2);

			player.setMarketValue(player.getMarketValue() + randomTransferValue());
			player.setTeamId(transferRequest.getTeamId());
			player.setTransferFlag(false);
			player.setTransferPrice(0);

			return playerRepository.save(player);
		} else {

			// Buyer
			Team team = teamRepository.findByUserId(transferRequest.getTeamId()).orElseThrow();
			team.setMoney(team.getMoney() - player.getTransferPrice());
			teamRepository.save(team);

			// Seller
			Team team2 = teamRepository.findByUserId(player.getTeamId()).orElseThrow();
			team2.setMoney(team2.getMoney() - player.getTransferPrice());
			teamRepository.save(team2);

			player.setMarketValue(player.getMarketValue() + randomTransferValue());
			player.setTeamId(team.getId());
			player.setTransferFlag(false);
			player.setTransferPrice(0);
			return playerRepository.save(player);
		}

	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ADMIN')")
	@GetMapping("/player/transfers")
	public List<Player> getAllTransferList(
			@RequestParam(value = "country", required = false, defaultValue = "TR,DE,FR,IT,GB") String country,
			@RequestParam(value = "min-value", required = false, defaultValue = "0") int minValue,
			@RequestParam(value = "max-value", required = false, defaultValue = "50000000") int maxValue,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			Authentication authentication) {
		logger.info("find transfer list");

		if ("asd".contains("")) {
			logger.info("sadasd");
		}

		List<Player> collect = playerRepository.findByTransferFlagTrue().stream()
				.filter(t -> country.contains(t.getCountry().toString())).filter(t -> t.getTransferPrice() > minValue)
				.filter(t -> t.getTransferPrice() < maxValue).filter(t -> t.getFirstname().contains(name))
				.collect(Collectors.toList());

		return collect;

	}

	private int randomTransferValue() {
		int min = 10;
		int max = 100;

		// Generate random int value from 10 to 100
		return (int) Math.floor(Math.random() * (max - min + 1) + min);

	}

}
