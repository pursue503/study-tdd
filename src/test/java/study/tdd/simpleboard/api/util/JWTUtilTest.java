package study.tdd.simpleboard.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JWT 토큰 생성 및 검증 Util 클래스 테스트")
public class JWTUtilTest {

    JwtUtil jwtUtil = new JwtUtil();

    @Test
    public void createTokenTest() {
        assertThat(jwtUtil.createToken()).isNotNull();
    }

    @Test
    public void createRefreshTokenTest() {
        assertThat(jwtUtil.refeshToken()).isNotNull();
    }

    @Test
    public void decodeTokenTest() {
        assertThat(jwtUtil.decodeToken()).isNotNull();
    }


}
