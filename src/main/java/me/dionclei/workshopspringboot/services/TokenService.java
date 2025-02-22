package me.dionclei.workshopspringboot.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.services.exceptions.TokenException;

@Service
public class TokenService {
	
	
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
	
    public TokenService() throws Exception {
        this.privateKey = generatePrivateKey("private_key.pem");
        this.publicKey = generatePublicKey("public_key.pem");
    }
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
			String token = JWT.create().withIssuer("workshop-api").withSubject(user.getEmail()).withExpiresAt(generateExpirationDate()).sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new TokenException(e.getMessage());
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.RSA256(publicKey, null);
			return JWT.require(algorithm)
					.withIssuer("workshop-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new TokenException(e.getMessage());
		}
	}
	
	private RSAPrivateKey generatePrivateKey(String path) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
        String privateKeyPEM = new String(keyBytes).replaceAll("-----.*-----", "").replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
	
	private RSAPublicKey generatePublicKey(String path) throws Exception {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String cut = new String(bytes).replaceAll("-----.*-----", "").replaceAll("\\s", "");
		byte[] decoded = Base64.getDecoder().decode(cut);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
		
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusDays(5).toInstant(ZoneOffset.UTC);
	}
}
