package com.auth.IService;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.auth.models.CustomUserDetails;

import io.jsonwebtoken.Claims;

public interface IJwtService {
	// retrieve username from jwt token
	public String extractUsername(String token);

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token);

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

	public Claims extractAllClaims(String token);

	// check if the token has expired
	public Boolean isTokenExpired(String token);

	// generate token for user
	public String generateToken(CustomUserDetails userDetails);

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	public String createToken(Map<String, Object> claims, String subject);

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails);
}
