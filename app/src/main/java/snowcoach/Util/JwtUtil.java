package snowcoach.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    //private static final String SECRET_KEY;
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    private static final String COOKIE_NAME = "jwt";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public Cookie createJwtCookie(String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (EXPIRATION_TIME / 1000)); // Convert milliseconds to seconds
        return cookie;
    }

    public Cookie createLogoutCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    public String getTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token)
                .getBody().getSubject();
    }
}