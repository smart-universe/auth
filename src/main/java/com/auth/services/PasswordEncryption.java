package com.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.models.User;

@Service
public class PasswordEncryption {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SCryptPasswordEncoder sCryptPasswordEncoder;

	public String encryptPassword(User user) {
		switch (user.getAlgotithm()) {
		case BCRYPT:
			return bCryptPasswordEncoder.encode(user.getPassword());

		case SCRYPT:
			return sCryptPasswordEncoder.encode(user.getPassword());

		}
		return null;

	}

}
