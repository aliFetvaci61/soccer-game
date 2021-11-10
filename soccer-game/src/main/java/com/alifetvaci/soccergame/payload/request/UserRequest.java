package com.alifetvaci.soccergame.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.alifetvaci.soccergame.models.Roles;

public class UserRequest {

	
	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@NotBlank
	private Roles roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	
	
}
