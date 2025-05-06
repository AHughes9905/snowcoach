package snowcoach.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "KJH823jkGhf!@#lkj9852Kfa23nKJLD8hfa==szegsvdevszdewosnvooinvinvdsins7ONVDSiewpn"; // Replace with your actual secret key
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }

    public static String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token)
                .getBody().getSubject();
    }


}
