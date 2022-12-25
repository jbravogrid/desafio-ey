package cl.ey.desafio.api.user.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	public static String createJwt(String user, String secret,Long expirationSeconds) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		String jwt = Jwts.builder().setSubject(user).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusSeconds(expirationSeconds))).signWith(key).compact();
		return jwt;
	}
}
