package study.tdd.simpleboard.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.jwt.JWTErrorCode;
import study.tdd.simpleboard.util.JWTProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JWT 토큰 생성 및 검증 테스트")
public class JWTProviderTest {

    Integer accessTokenExpirationTime = 2000;
    Integer refreshTokenExpirationTime = 2000;
    String secretKey = "시크릿키는256비트보커야합니다그러므로256비트를넘겨야합니다";
    String claimKey = "claim-key-test";
    String issuer = "issuer";
    String subject = "subject";

    Long memberId = 1L;

    JWTProvider jwtProvider = new JWTProvider(accessTokenExpirationTime, refreshTokenExpirationTime, secretKey, claimKey, issuer, subject) ;

    @Nested
    @DisplayName("AccessToken Test")
    public class AccessTokenTest {
        @Test
        @DisplayName("AccessToken 토큰 생성 테스트")
        public void createTokenTest() {
            assertThat(jwtProvider.createAccessToken(memberId)).isNotNull();
        }

        @Test
        @DisplayName("토큰 검증 테스트 (토큰을 디코딩하고 페이로드를 검증)")
        public void verifyAccessTokenTest() {

            // 준비
            String accessToken = jwtProvider.createAccessToken(memberId);

            // 실행
            String decodeTokenValue = jwtProvider.verifyAccessToken(accessToken);

            // 검증
            assertThat(decodeTokenValue).isEqualTo(memberId.toString());
        }


        @Test
        @DisplayName("JWT AccessToken 만료 시간 경과시 BizException 발생")
        public void accessTokenExpirationErrorTest() throws InterruptedException {

            // 준비
            String accessToken = jwtProvider.createAccessToken(memberId);

            // 시간 초과를 위한 슬립
            Thread.sleep(accessTokenExpirationTime + 1000);

            // 검증
            assertThatThrownBy(() -> jwtProvider.verifyAccessToken(accessToken))
                    .isInstanceOf(BizException.class)
                    .hasMessage(JWTErrorCode.JWT_ACCESS_TOKEN_TIME_EXPIRED.getMsg());
        }

        @Test
        @DisplayName("JWT AccessToken 서명키 위조시 BizException 발생")
        public void accessTokenSignatureException() {

            // 준비
            // 위조 된 서명키를 가진 객체를 생성
            String forgerySecretKey = "위조된서명을가지고있는비밀키를생성해버립니다.";
            JWTProvider forgeryProvider = new JWTProvider(accessTokenExpirationTime, refreshTokenExpirationTime, forgerySecretKey, claimKey, issuer, subject) ;


            // 실행
            // 위조된 시크릿 키 값으로 accessToken 생성
            String forgeryAccessToken = forgeryProvider.createAccessToken(memberId);

            // 검증
            assertThatThrownBy(() -> jwtProvider.verifyAccessToken(forgeryAccessToken))
                    .isInstanceOf(BizException.class)
                    .hasMessage(JWTErrorCode.JWT_VALID_NOT_SIGNATURE.getMsg());
        }

    }

    @Nested
    public class RefreshTokenTest {

        @Test
        @DisplayName("RefreshToken 생성 테스트")
        public void createRefreshTokenTest() {
            assertThat(jwtProvider.createRefreshToken()).isNotNull();
        }

        @Test
        @DisplayName("RefreshToken 검증 테스트")
        public void verifyRefreshTokenTest() {

            // 준비
            String refreshToken = jwtProvider.createRefreshToken();

            jwtProvider.verifyRefreshToken(refreshToken);
        }

        @Test
        @DisplayName("JWT AccessToken 만료 시간 경과시 BizException 발생")
        public void refreshTokenExpirationErrorTest() throws InterruptedException {

            // 준비
            String refreshToken = jwtProvider.createRefreshToken();

            // 실행 시간초과를 위해 슬립
            Thread.sleep(refreshTokenExpirationTime + 1000);

            assertThatThrownBy(() -> jwtProvider.verifyRefreshToken(refreshToken))
                    .isInstanceOf(BizException.class)
                    .hasMessage(JWTErrorCode.JWT_REFRESH_TOKEN_TIME_EXPIRED.getMsg());
        }

        @Test
        @DisplayName("JWT refreshToken 서명키 위조시 BizException 발생")
        public void refreshTokenSignatureException() {

            // 준비
            // 위조 된 서명키를 가진 객체를 생성
            String forgerySecretKey = "위조된서명을가지고있는비밀키를생성해버립니다.";
            JWTProvider forgeryProvider = new JWTProvider(accessTokenExpirationTime, refreshTokenExpirationTime, forgerySecretKey, claimKey, issuer, subject) ;


            // 실행
            // 위조된 시크릿 키 값으로 accessToken 생성
            String forgeryRefreshToken = forgeryProvider.createRefreshToken();

            assertThatThrownBy(() -> jwtProvider.verifyRefreshToken(forgeryRefreshToken))
                    .isInstanceOf(BizException.class)
                    .hasMessage(JWTErrorCode.JWT_VALID_NOT_SIGNATURE.getMsg());
        }
    }





}
