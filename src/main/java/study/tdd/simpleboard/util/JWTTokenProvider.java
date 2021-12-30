package study.tdd.simpleboard.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final int accessTokenExpirationTime;
    private final int refreshTokenExpirationTime;
    private final Key secretKey;

    public JWTTokenProvider(
            @Value("service.study.jwt.access-token-expiration-time")
            int tokenExpirationTime,
            @Value("service.study.jwt.refresh-token-expiration-time")
            int refreshTokenExpirationTime,
            @Value("service.study.jwt.secret-key")
            String secretKey
    ) {
        this.accessTokenExpirationTime = tokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long memberId) {

        long now = (new Date()).getTime();

        Date accessTokenExpiresDate = new Date(now + accessTokenExpirationTime);
        return Jwts.builder()
                .setSubject(memberId.toString())
                .claim("member-id", memberId)
                .setExpiration(accessTokenExpiresDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {

        long now = (new Date()).getTime();

        Date refreshTokenExpiresDate = new Date(now + refreshTokenExpirationTime);

        return Jwts.builder()
                .setExpiration(refreshTokenExpiresDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String decodeToken(String token) {

        Claims claims = parseClaims(token);

        return claims.get("member-id").toString();
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

}
