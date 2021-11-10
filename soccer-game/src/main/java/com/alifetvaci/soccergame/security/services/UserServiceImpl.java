package com.alifetvaci.soccergame.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.alifetvaci.soccergame.models.Roles;


@Service
public class UserServiceImpl implements UserService{
	
	@Override
	public UserDetailsImpl getAuthanticatedUser(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) (authentication.getPrincipal());

		return userDetails;
	}
	
	@Override
	public String getAuthanticationUserId(Authentication authentication) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) (authentication.getPrincipal());
		return userDetailsImpl.getId();
	}
	
	
	@Override
	public boolean checkFraud(Authentication authentication,String userId) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) (authentication.getPrincipal());
		if (!userId.equals(userDetailsImpl.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isAdmin(UserDetailsImpl userDetailsImpl) {
		// TODO Auto-generated method stub
		return userDetailsImpl.getAuthorities().toString().contains(Roles.ROLE_ADMIN.toString());
	}

}
