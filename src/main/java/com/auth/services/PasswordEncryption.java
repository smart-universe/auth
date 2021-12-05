package com.auth.services;

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
import com.auth.models.User;

@Service
public class PasswordEncryption implements IPasswordEncryptionService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final SCryptPasswordEncoder sCryptPasswordEncoder;

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

		if (encoder != null && encoder.matches(rawPassword, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
					user.getAuthorities());
		} else {
			throw new BadCredentialsException("Bad credentials");
		}
	}

	private PasswordEncoder getPasswordEncoder(User user) {
		if (user.getAlgotithm().toString().equals("BCRYPT")) {
			return bCryptPasswordEncoder;
		} else {
			return sCryptPasswordEncoder;
		}

	}

}
