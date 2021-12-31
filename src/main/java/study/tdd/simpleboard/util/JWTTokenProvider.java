package study.tdd.simpleboard.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.jwt.JWTErrorCode;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final int accessTokenExpirationTime;
    private final int refreshTokenExpirationTime;
    private final Key secretKey;
    private final String claimKey;
    private final String issuer;
    private final String subject;

    public JWTTokenProvider(
            @Value("service.study.jwt.access-token-expiration-time")
                    int accessTokenExpirationTime,
            @Value("service.study.jwt.refresh-token-expiration-time")
                    int refreshTokenExpirationTime,
            @Value("service.study.jwt.secret-key")
                    String secretKey,
            @Value("service.study.jwt.claim-key")
                    String claimKey,
            @Value("service.study.jwt.issuer")
                    String issuer,
            @Value("service.study.jwt.subject")
                    String subject
    ) {
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.claimKey = claimKey;
        this.issuer = issuer;
        this.subject = subject;
    }

    public String createAccessToken(Long memberId) {

        long now = (new Date()).getTime();
        Date accessTokenExpiresDate = new Date(now + accessTokenExpirationTime);

        return Jwts.builder()
                .claim(claimKey, memberId)
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(accessTokenExpiresDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {

        long now = (new Date()).getTime();

        Date refreshTokenExpiresDate = new Date(now + refreshTokenExpirationTime);

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(refreshTokenExpiresDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String verifyAccessToken(String accessToken) {
        return decodeAccessToken(accessToken).get(claimKey)
                .toString();
    }

    public void verifyRefreshToken(String refreshToken) {
        decodeRefreshToken(refreshToken);
    }

    private Claims decodeAccessToken(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .requireIssuer(issuer)
                    .requireSubject(subject)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BizException(JWTErrorCode.JWT_ACCESS_TOKEN_TIME_EXPIRED); // 시간
        } catch (SignatureException e) {
            throw new BizException(JWTErrorCode.JWT_VALID_NOT_SIGNATURE); // 서명값 변조
        } catch (IncorrectClaimException e) {
            throw new BizException(JWTErrorCode.JWT_VALID_NOT_INCORRECT_CLAIM); // 필수 클레임
        }
    }

    private void decodeRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .requireIssuer(issuer)
                    .requireSubject(subject)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BizException(JWTErrorCode.JWT_REFRESH_TOKEN_TIME_EXPIRED); // 시간
        } catch (SignatureException e) {
            throw new BizException(JWTErrorCode.JWT_VALID_NOT_SIGNATURE); // 서명값 변조
        } catch (IncorrectClaimException e) {
            throw new BizException(JWTErrorCode.JWT_VALID_NOT_INCORRECT_CLAIM); // 필수 클레임
        }
    }


}
