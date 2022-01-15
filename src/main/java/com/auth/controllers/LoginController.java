package com.auth.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.auth.models.GenericResponse;
import com.auth.models.JwtRequest;
import com.auth.models.JwtResponse;
import com.auth.models.UserCreationRequest;
import com.auth.models.UserPOJO;
import com.auth.models.UsernameValidationRequest;
import com.auth.models.UsernameValidationResponse;
import com.auth.models.Entities.User;
import com.auth.services.AuthenticationProviderService;
import com.auth.services.JpaUserDetailsService;
import com.auth.services.JwtService;

import io.jsonwebtoken.SignatureException;

@RestController
@RequestMapping("/auth")
public class LoginController {

	private AuthenticationProviderService authenticationProvider;

	private JpaUserDetailsService jpaUserDetailsService;

	private JwtService jwtService;

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	public LoginController(AuthenticationProviderService authenticationProvider,
			JpaUserDetailsService jpaUserDetailsService, JwtService jwtService) {
		this.authenticationProvider = authenticationProvider;
		this.jpaUserDetailsService = jpaUserDetailsService;
		this.jwtService = jwtService;
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> createUser(@RequestBody UserCreationRequest request) {
		GenericResponse response = new GenericResponse();
		User user = new User();
		user.setAlgotithm(EncryptionAlgorithm.randomAlgorithm());
		user.setEmailId(request.getEmailId());
		user.setFirstName(request.getFirstName());
		user.setCountry(request.getCountry());
		user.setLastName(request.getLastName());
		user.setPassword(request.getPassword());
		user.setUsername(request.getUsername());
		logger.info("Recieved user creation request for the new user with emailId : " + request.getEmailId() + " "
				+ request.toString());
		try {
			boolean isSuccess = jpaUserDetailsService.createUser(user);

			if (isSuccess) {
				logger.info("User successfully created with username : " + user.getUsername());
				response.setResultCode(1);
				response.setResultMessage("User Successfully Created");
			} else {
				response.setResultCode(-1);
				response.setResultMessage("User already Exists");
			}

		} catch (Exception ex) {
			logger.error(
					"Error has been occured while creating user for the user with email id : " + request.getEmailId());
			response.setResultCode(-500);
			response.setResultMessage(
					"This shouldnt come if it is here please tell bharadwaj that he was wrong somewhere");
		}
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		AuthenticateResponse response = new AuthenticateResponse();
		logger.info("Received authentication request for the username : " + authenticationRequest.getUsername());

		try {
			authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			/*
			 * We might get UsernameNotFoundException or BadCredentialsException needs to be
			 * handeled
			 */
			logger.info("Got exception " + e + "  for the username " + authenticationRequest.getUsername());
			response.setResultCode(-1);
			response.setResultMessage("Username or password not found");
			return ResponseEntity.ok(response);

		}
		final CustomUserDetails userDetails = jpaUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtService.generateToken(userDetails);

		UserPOJO user = new UserPOJO();
		user.setCountry(userDetails.getUser().getCountry());
		user.setEmailId(userDetails.getUser().getEmailId());
		user.setFirstName(userDetails.getUser().getFirstName());
		user.setLastName(userDetails.getUser().getLastName());
		user.setUserID(userDetails.getUser().getUserID());
		user.setUsername(userDetails.getUsername());

		response.setJwt(jwt);
		response.setUser(user);
		response.setResultCode(1);
		response.setResultMessage("User is authenticated");
		logger.info("user successfully authenticated" + user.getUsername());
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
	public ResponseEntity<UsernameValidationResponse> checkUsername(@RequestBody UsernameValidationRequest request) {
		logger.info("Received request to check if the usename exists in the system" + request.getUsername());
		UsernameValidationResponse response = new UsernameValidationResponse();
		try {

			boolean isExists;
			isExists = jpaUserDetailsService.userExists(request.getUsername());
			response.setExists(isExists);
			if (isExists) {
				response.setResultMessage("UserName Exists");
				logger.info("A user with the same username already exists in our system" + request.getUsername());
				response.setResultCode(-1);

			} else {
				logger.info("New User can be created by this username " + request.getUsername());
				response.setResultMessage("UserName Doesnt Exists");
				response.setResultCode(1);
			}

			return ResponseEntity.ok(response);

		} catch (Exception ex) {
			// to do (add Logger)
			logger.error("An exception has been occured at checkUsername Method" + ex);
			response.setResultMessage("Internal server Error");
			response.setResultCode(-500);
			return ResponseEntity.ok(response);
		}

	}

	@RequestMapping(value = "/validateToken", method = RequestMethod.POST)
	public ResponseEntity<?> validateToken(@RequestBody JwtRequest jwtRequest) {
		JwtResponse response = new JwtResponse();
		try {

			String token = jwtRequest.getJwt();
			String username = jwtService.extractUsername(token);
			logger.info("JWT validation request for the username " + username);
			CustomUserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
			boolean isValid = jwtService.validateToken(token, userDetails);
			if (isValid) {
				logger.info("JWT signature is valid for the username " + username);
				response.setResultCode(1);
				response.setResultMessage("Valid JWT Token");
				response.setValidated(true);

			} else {
				logger.warn("JWT signature is not valid for the username " + username);
				response.setResultCode(-1);
				response.setResultMessage("Not Valid JWT Token");
				response.setValidated(false);
			}
			return ResponseEntity.ok(response);
		} catch (SignatureException ex) {
			logger.warn("JWT signature is valid for the username and we are getting a signature Exception"
					+ jwtRequest.toString());
			response.setResultCode(-500);
			response.setResultMessage("Signature Exception");
			response.setValidated(false);
			return ResponseEntity.ok(response);

		}

	}

}