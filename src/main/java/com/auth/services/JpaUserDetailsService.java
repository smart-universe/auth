package com.auth.services;

import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.models.CustomUserDetails;
import com.auth.models.User;
import com.auth.repositories.UserRepository;

/*
 * Implementation of user service by extending USerDetailsService
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepo;

	private final PasswordEncryption passwordEncryption;

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
		boolean ifExists = userExists(user.getUsername());
		if (!ifExists) {

			user.setPassword(passwordEncryption.encryptPassword(user));

			userRepo.save(user);
			return true;
		} else {
			return false;
		}

	}

}
