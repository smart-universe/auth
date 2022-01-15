package com.auth.IService;

import org.springframework.security.core.Authentication;

import com.auth.models.CustomUserDetails;
import com.auth.models.EncryptionAlgorithm;
import com.auth.models.Entities.User;

public interface IPasswordEncryptionService {

	public String encryptPassword(User user) throws Exception;

	public Authentication checkPassword(CustomUserDetails user, String rawPassword,
			EncryptionAlgorithm encryptionAlgorithm);
}
