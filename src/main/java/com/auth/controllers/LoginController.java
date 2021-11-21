package com.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.models.AuthenticateResponse;
import com.auth.models.AuthenticationRequest;
import com.auth.services.MyUserDetailsService;
import com.auth.utils.JwtUtils;

@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticateRequest) throws Exception{
		try{authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getUserName(),authenticateRequest.getPassword()));
	}catch(BadCredentialsException e) {
		//wrong passsword
		
	}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getUserName());
		
		final String jwt = jwtUtils.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticateResponse(jwt));
		
	}
	

}