package study.tdd.simpleboard.api.member.signup.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 *
 * 회원가입 인수 테스트
 *
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberSignUpAcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("회원가입 성공")
    public void MemberSignUpOk() {

        // 준비
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
        // 실행
                .when()
                .post("/members")
        // 검증
                .then()
                .statusCode(HttpStatus.OK.value());
//                .assertThat()
//                .body("id", equalTo(1));

    }
}
