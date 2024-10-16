package com.gamified.learning_platform.Common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

//	@Autowired
//	private SecretKeyGenerator _keyGenerator;

	private String secretKey  = "vLIAQ48fLJ/ubFR56kuhBwJ8TqbzhI+Yrdn7P5Wuhf4zCRTpstlGNh/siANtWY83UQjTxxZykzPiI3V78EOKZQ==";

	public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
	
	// Lazily initialize the secret when first needed
//    private String getSecret() {
//        if (secret == null) {
//            secret = _keyGenerator.keyGenerator();  // Generate the key only when needed
//        }
//        return secret;
//    }

//	public String generateToken(String username) {
//		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
//				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//				.signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes())) // Updated
//				.compact();
//	}
//
//	public String extractUsername(String token) {
//		System.out.println(Jwts.parser().setSigningKey(secret) // Use getSecret() method
//				.parseClaimsJws(token).getBody().getSubject());
//		return Jwts.parser().setSigningKey(secret) // Use getSecret() method
//				.parseClaimsJws(token).getBody().getSubject();
//	}
//
//	public boolean isTokenValid(String token, UserDetails userDetails) {
//		String username = extractUsername(token);
//		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//	}
//
//	private boolean isTokenExpired(String token) {
//		return Jwts.parser().setSigningKey(secret) // Use getSecret() method
//				.parseClaimsJws(token).getBody().getExpiration().before(new Date());
//	}
	
}
