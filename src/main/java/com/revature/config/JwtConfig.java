package com.revature.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Declare beans required for management of JSON Web Tokens
 * @author cenat
 *
 */
@Configuration
public class JwtConfig {
	private static final String JWT_SECRET_ENV = "JWT_SECRET";
	private static final String JWT_ISSUER = "scrum-dojo";
	
	@Autowired
	Algorithm algorithm;
	
	/**
	 * Algorithm object used to encode/decode JWT
	 * @return
	 */
	@Bean
	public Algorithm getAlgorithm() {
		Algorithm algorithm = Algorithm.HMAC256(System.getenv(JWT_SECRET_ENV));
		return algorithm;
	}
	
	/**
	 * JWTVerifier reference used to verify/decode a JWT.
	 * @return
	 */
	@Bean
	public JWTVerifier getJwtVerifier() {
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer(JWT_ISSUER)
				.build();
		return verifier;
	}
	
	/**
	 * Builder used to create new JWT
	 * @return
	 */
	@Bean
	public Builder getJwtBuilder() {
		Builder builder = JWT.create()
				.withIssuer(JWT_ISSUER);
		return builder;
	}
}
