package com.auth.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth.IService.IJwtService;
import com.auth.models.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements IJwtService {

	@Value("$jwt.secret.validity")
	public static long JWT_TOKEN_VALIDITY;

	@Value("$jwt.secret.key")
	private String JWT_SECRET_KEY;

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public Date getExpirationDateFromToken(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@Override
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
	}

	@Override
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	@Override
	public String generateToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	@Override
	public String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY).compact();
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
