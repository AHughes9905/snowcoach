package snowcoach.utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtility {
    
        private static final String SECRET_KEY = "your_secret_key"; // Replace with your actual secret key
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

        public static String getUsernameFromToken(String token) {
           return Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token)
            .getBody().getSubject(); 
        }


}
