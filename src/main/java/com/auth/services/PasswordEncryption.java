package com.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.IService.IPasswordEncryptionService;
import com.auth.models.CustomUserDetails;
import com.auth.models.EncryptionAlgorithm;
import com.auth.models.Entities.User;

@Service
public class PasswordEncryption implements IPasswordEncryptionService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final SCryptPasswordEncoder sCryptPasswordEncoder;

	Logger logger = LoggerFactory.getLogger(PasswordEncryption.class);
	
	public PasswordEncryption(BCryptPasswordEncoder bCryptPasswordEncoder,
			SCryptPasswordEncoder sCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.sCryptPasswordEncoder = sCryptPasswordEncoder;

	}

	@Override
	public String encryptPassword(User user) {
		PasswordEncoder encoder = getPasswordEncoder(user);

		return encoder.encode(user.getPassword());

	}

	@Override
	public Authentication checkPassword(CustomUserDetails user, String rawPassword,
			EncryptionAlgorithm encryptionAlgorithm) {
		PasswordEncoder encoder = getPasswordEncoder(user.getUser());
		logger.info("Getting User password encoder type from db ");
		if (encoder != null && encoder.matches(rawPassword, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
					user.getAuthorities());
		} else {
			logger.info("User has entered wrong credentials . The User id is : "+ user.getUsername());
			throw new BadCredentialsException("Bad credentials");
			
		}
	}

	private PasswordEncoder getPasswordEncoder(User user) {
		if (user.getAlgotithm().toString().equals("BCRYPT")) {
			logger.info("The password encoder for the user is : BCRYPT");
			return bCryptPasswordEncoder;
		} else {
			logger.info("The password encoder for the user is : SCRYPT");
			return sCryptPasswordEncoder;
		}

	}

}
