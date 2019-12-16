package org.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class JwtTokenGenerate {
	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@GetMapping(value = "/getToken")
	public String generateToken(@RequestParam(name = "userName") String userName) {

		return Jwts.builder().setId(userName).setIssuedAt(new Date(800000000)).setSubject("for personal use")
				.setIssuer(userName).signWith(SignatureAlgorithm.HS384, jwtSecret).compact();
	}
}
