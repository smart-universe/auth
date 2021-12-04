package com.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.models.AuthenticateResponse;
import com.auth.models.AuthenticationRequest;
import com.auth.models.CustomUserDetails;
import com.auth.models.EncryptionAlgorithm;
import com.auth.models.User;
import com.auth.services.AuthenticationProviderService;
import com.auth.services.JpaUserDetailsService;
import com.auth.utils.JwtUtils;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationProviderService authenticationProvider;

	@Autowired
	private JpaUserDetailsService jpaUserDetailsService;
	@Autowired
	private JwtUtils jwtUtils;

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if(user != null) {
			user.setAlgotithm(EncryptionAlgorithm.randomAlgorithm());
			boolean isSuccess = jpaUserDetailsService.createUser(user);
			if(isSuccess) {
				return ResponseEntity.ok("user successfully created");
			}else {
				return ResponseEntity.ok("user already exists");
			}
			
		}
		return (ResponseEntity<?>) ResponseEntity.badRequest();
		
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticateRequest)
			throws Exception {

		try {
			authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
					authenticateRequest.getUsername(), authenticateRequest.getPassword()));
		} catch (Exception e) {
				/*
				 * We might get UsernameNotFoundException or BadCredentialsException needs to be handeled 
				 */
			return ResponseEntity.ok("Username or password not found");

		}
		final CustomUserDetails userDetails = jpaUserDetailsService
				.loadUserByUsername(authenticateRequest.getUsername());

		final String jwt = jwtUtils.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticateResponse(jwt));

	}

}