package apartment.manager.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

/**
 * Documentation: <a href="https://github.com/jwtk/jjwt?tab=readme-ov-file#what-is-a-json-web-token">...</a>
 */
@Component
public class JwtProvider {
    private final int tokenValidityInDays = 30;
    @Value("${JWT_ENCODED_SECRET_KEY}")
    private String secretKey;
    private SecretKey key;
    private Claims claims;

    public JwtProvider() {
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(String email) {
        Date now = new Date();
        // A signed JWT is called a 'JWS'
        String jws = Jwts.builder().subject(email).claims(new HashMap<>()).issuedAt(now)
                .expiration(new Date(now.getTime() + 1000 * 60 * 60 * 24 * tokenValidityInDays)).signWith(key).compact();
        return jws;
    }

    public Claims validateToken(String token) {
        this.claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims;
    }

    public Claims getClaims(String token) {
        if (claims == null) {
            validateToken(token);
        }
        return claims;
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }
}
