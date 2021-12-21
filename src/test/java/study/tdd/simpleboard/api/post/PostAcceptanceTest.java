package study.tdd.simpleboard.api.post;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

/**
 * 실제 환경과 유사한 환경에서의 게시물 인수 테스트
 *
 * @author : Informix
 * @since 2.6.1 boot
 * @since 0.0.1 dev
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostAcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("게시물 작성 실패")
    void failToWritePost() {

    }

    @Test
    @DisplayName("게시물 작성 성공")
    void successToWritePost() {
        // 준비
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)

                // 실행
                .when()
                .get("/posts/1")

                // 검증
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat();
        //.body("id", equalTo(1));
        //.body("title", equalTo("게시물 제목"))
        //.body("content", equalTo("게시물 내용"))
        //.body("createDate", equalTo(LocalDateTime.now()));
    }

    @Test
    @DisplayName("게시물 조회 실패")
    void failToFindPost() {

    }

    @Test
    @DisplayName("게시물 조회 성공")
    void successToFindPost() {

    }
}
