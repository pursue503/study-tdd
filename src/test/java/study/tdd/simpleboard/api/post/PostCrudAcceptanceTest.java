package study.tdd.simpleboard.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.tdd.simpleboard.exception.member.signup.MemberSignUpErrorCode;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * 게시물 CRUD 인수 테스트
 *
 * @author Informix
 * @create 2021-12-29
 * @since 2.6.1 spring boot
 * @since 0.0.2 dev
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostCrudAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("게시물 저장 인수 테스트")
    class SavePostTest {

        @Test
        @DisplayName("게시물 저장 성공")
        void savePostSuccess() {

            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("nickname", "nickname123");
            body.put("password", "abcd1234!A");
            body.put("memberEmail", "pursue503@naver.com");

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all()
                    // 실행
                    .when()
                    .post("/posts")
                    // 검증
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .assertThat()
                    .body("message", equalTo("회원가입에 성공하였습니다.")).log().all();

        }

        @ParameterizedTest
        @CsvSource(value = {"ni:abcd1234!A:pursue503@naver.com", "v1:abcd1234!A:pursue503@naver.com"}, delimiterString = ":")
        @DisplayName("게시물 제목이 조건에 맞지 않을 경우 400 BAD_REQUEST 반환")
        void savePostFailureWhenPostTitleIsInvalid(String nickname, String password, String memberEmail) {

            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("nickname", nickname);
            body.put("password", password);
            body.put("memberEmail", memberEmail);

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all()
                    // 실행
                    .when()
                    .post("/posts")
                    //검증
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .assertThat()
                    .body("message", equalTo(MemberSignUpErrorCode.SIGN_UP_NICKNAME_NOT_VALID.getMsg()));
        }

        @ParameterizedTest
        @CsvSource(value = {"nickname123:abcd12:pursue503@naver.com", "nickname123:abcd1234!:pursue503@naver.com"}, delimiterString = ":")
        @DisplayName("게시물 내이가 조건에 맞지 않을경우 400 BAD_REQUEST 반환")
        public void savePostFailureWhenPostContentIsInvalid(String nickname, String password, String memberEmail) {

            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("nickname", nickname);
            body.put("password", password);
            body.put("memberEmail", memberEmail);

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all()
                    // 실행
                    .when()
                    .post("/posts")

                    //검증
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .assertThat()
                    .body("message", equalTo(MemberSignUpErrorCode.SIGN_UP_PASSWORD_NOT_VALID.getMsg()));
        }

        @ParameterizedTest
        @CsvSource(value = {":abcd12:pursue503@naver.com", "nickname123::pursue503@naver.com", "nickname123:abcd1234!:", "::"}, delimiterString = ":")
        @DisplayName("필요한 파라매터가 하나라도 비어 있다면 400 BAD_REQUEST 반환")
        public void BAD_REQUESTWhenBlankParam(String nickname, String password, String memberEmail) {
            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("nickname", nickname);
            body.put("password", password);
            body.put("memberEmail", memberEmail);

            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all()
                    // 실행
                    .when()
                    .post("/posts")
                    //검증
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .assertThat();
        }
    }


}
