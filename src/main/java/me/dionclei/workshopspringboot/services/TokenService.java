package me.dionclei.workshopspringboot.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.management.RuntimeMBeanException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.services.exceptions.TokenException;

@Service
public class TokenService {
	@Value("${API.SECURITY.TOKEN}")
	private String key;
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			String token = JWT.create().withIssuer("workshop-api").withSubject(user.getEmail()).withExpiresAt(generateExpirationDate()).sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new TokenException(e.getMessage());
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			return JWT.require(algorithm)
					.withIssuer("workshop-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new TokenException(e.getMessage());
		}
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusDays(5).toInstant(ZoneOffset.UTC);
	}
}
