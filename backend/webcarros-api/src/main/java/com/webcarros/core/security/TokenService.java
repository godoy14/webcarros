package com.webcarros.core.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.webcarros.domain.model.Usuario;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String secret;

	
	public String generateToken(Usuario usuario) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api-web-carros")
					.withSubject(usuario.getEmail())
					.withExpiresAt(generateExpirationDate())
					.sign(algoritmo);
			
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro na geração do Token JWT");
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
			return JWT.require(algoritmo)
					.withIssuer("auth-api-web-carros")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
