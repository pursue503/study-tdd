package study.tdd.simpleboard.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.tdd.simpleboard.util.JWTTokenProvider;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JWT 토큰 생성 및 검증 Util 클래스 테스트")
public class JWTTokenProviderTest {

    JWTTokenProvider jwtTokenProvider = new JWTTokenProvider(2000, 2000, "시크릿키는256비트보커야합니다그러므로256비트를넘겨야합니다");

    @Test
    public void createTokenTest() {
        assertThat(jwtTokenProvider.createToken(1L)).isNotNull();
    }

    @Test
    public void createRefreshTokenTest() {
        assertThat(jwtTokenProvider.createRefreshToken()).isNotNull();
    }

    @Test
    public void decodeTokenTest() {
        assertThat(jwtTokenProvider.decodeToken(jwtTokenProvider.createToken(1L))).isEqualTo("1");
    }

}
