package br.com.ael.infosolo.pagoo.security.service;

import br.com.ael.infosolo.pagoo.model.Usuario;

public enum UserRole {
	
	USER, ADMIN;
	public UserAuthority asAuthorityFor(final Usuario user) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setUser(user);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_USER":
			return USER;
		case "ROLE_ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}
