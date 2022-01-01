package com.auth.controllers;

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
import com.auth.models.UserNameRequest;
import com.auth.models.UserNameResponse;
import com.auth.services.AuthenticationProviderService;
import com.auth.services.JpaUserDetailsService;
import com.auth.services.JwtService;

@RestController
public class LoginController {

	private AuthenticationProviderService authenticationProvider;

	private JpaUserDetailsService jpaUserDetailsService;

	private JwtService jwtService;

	public LoginController(AuthenticationProviderService authenticationProvider,
			JpaUserDetailsService jpaUserDetailsService, JwtService jwtService) {
		this.authenticationProvider = authenticationProvider;
		this.jpaUserDetailsService = jpaUserDetailsService;
		this.jwtService = jwtService;
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (user != null) {
			user.setAlgotithm(EncryptionAlgorithm.randomAlgorithm());
			boolean isSuccess = jpaUserDetailsService.createUser(user);
			if (isSuccess) {
				return ResponseEntity.ok("user successfully created");
			} else {
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
			 * We might get UsernameNotFoundException or BadCredentialsException needs to be
			 * handeled
			 */
			return ResponseEntity.ok("Username or password not found");

		}
		final CustomUserDetails userDetails = jpaUserDetailsService
				.loadUserByUsername(authenticateRequest.getUsername());

		final String jwt = jwtService.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticateResponse(jwt));

	}

	@RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
	public ResponseEntity<?> checkUsername(@RequestBody UserNameRequest request) {
		UserNameResponse response = new UserNameResponse();
		try {

			boolean isExists;

			isExists = jpaUserDetailsService.userExists(request.getUsername());
			response.setExists(isExists);

			if (isExists) {
				response.setMessage("UserName Exists");

			} else {
				response.setMessage("Sucessfull");
			}

			return ResponseEntity.ok(response);

		} catch (Exception ex) {
			// to do (add Logger)

			response.setMessage("Internal server Error");
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
		}

	}

}