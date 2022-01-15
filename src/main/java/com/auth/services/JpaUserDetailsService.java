package com.auth.services;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.models.CustomUserDetails;
import com.auth.models.Entities.User;
import com.auth.repositories.UserRepository;

/*
 * Implementation of user service by extending USerDetailsService
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepo;

	private final PasswordEncryption passwordEncryption;
	
	Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

	public JpaUserDetailsService(UserRepository userRepo, PasswordEncryption passwordEncryption) {
		this.passwordEncryption = passwordEncryption;
		this.userRepo = userRepo;
	}

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Probelm during authentication");
		User user = userRepo.findUserByUsername(username).orElseThrow(s);
		return new CustomUserDetails(user);
	}

	public boolean userExists(String username) {
		User user = userRepo.findUserByUsername(username).orElse(null);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean createUser(User user) {
		logger.info("Checking if the user exists for the given username : "+ user.getUsername());
		boolean ifExists = userExists(user.getUsername());
		if (!ifExists) {
			logger.info("The user doesnt exist for the given username : " + user.getUsername());
			user.setPassword(passwordEncryption.encryptPassword(user));
			userRepo.save(user);
			return true;
		} else {
			logger.info("A user exists with the given user name. ");
			return false;
		}

	}

}
