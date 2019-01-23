package com.ndg.springdemo.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CurrentUserModel {
	public CurrentUserModel(String displayName,Collection<? extends String> roles) {
		super();
		this.name = displayName;
		this.roles = roles;
	}
	private final String name;
	public String getName() {
		return name;
	}
	private final Collection<? extends String> roles;
	public Collection<? extends String> getRoles() {
		return roles;
	}
	
}
