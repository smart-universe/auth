package com.auth.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.auth.models.CustomUserDetails;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

	private final JpaUserDetailsService userDetailsService;

	private final PasswordEncryption passwordEncryption;

	public AuthenticationProviderService(JpaUserDetailsService userDetailsService,
			PasswordEncryption passwordEncryption) {
		this.passwordEncryption = passwordEncryption;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		CustomUserDetails user = userDetailsService.loadUserByUsername(username);

		return passwordEncryption.checkPassword(user, password, user.getUser().getAlgotithm());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
