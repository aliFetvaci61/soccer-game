package com.alifetvaci.soccergame.security.services;

import org.springframework.security.core.Authentication;

public interface UserService {

	String getAuthanticationUserId(Authentication authentication);

	UserDetailsImpl getAuthanticatedUser(Authentication authentication);

	boolean checkFraud(Authentication authentication, String userId);

	boolean isAdmin(UserDetailsImpl userDetailsImpl);

}
