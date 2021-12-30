package study.tdd.simpleboard.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.tdd.simpleboard.api.post.domain.enums.PostMessage;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
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
            body.put("postTitle", "게시물 제목 저장");
            body.put("postContent", "게시물 내용 저장");
            body.put("image", "/image.png");

            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all();

            // 실행
            Response when = given.when()
                    .post("/posts");

            // 검증
            when.then()
                    .statusCode(HttpStatus.OK.value())
                    .assertThat()
                    .body("message", equalTo(PostMessage.SAVE_POST_SUCCESS.getSuccessMsg())).log().all();

        }

        @ParameterizedTest
        @CsvSource(value = {":게시물 내용:", "   :게시물 내용:/image.png",
                "게시물 제목 길이가 30글자보다 깁니다. 게시물 제목 길이는 30글자를 초과할 수 없습니다.:게시물 내용:/image.png"},
                delimiterString = ":")
        @DisplayName("게시물 제목이 조건에 맞지 않을 경우 400 BAD_REQUEST 반환")
        void savePostFailureWhenPostTitleIsInvalid(String postTitle, String postContent, String image) {
            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("postTitle", postTitle);
            body.put("postContent", postContent);
            body.put("image", image);

            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all();

            // 실행
            Response when = given.when()
                    .post("/posts");

            // 검증
            when.then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .assertThat()
                    .body("message", containsString("매개변수가 충분히 전달되지 못했거나 올바르지 않은 매개변수 값이 전달되었습니다."));
        }

        @Test
        @DisplayName("게시물 내용이 조건에 맞지 않을경우 400 BAD_REQUEST 반환")
        public void savePostFailureWhenPostContentIsInvalid() {
            // 준비
            String postTitle = "게시물 내용";
            String postContent = "게시물 내용".repeat(2000);
            String image = "게시물 이미지 주소";

            Map<String, Object> body = new HashMap<>();
            body.put("postTitle", postTitle);
            body.put("postContent", postContent);
            body.put("image", image);

            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all();

            // 실행
            Response when = given.when()
                    .post("/posts");

            //검증
            when.then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .assertThat()
                    .body("message", containsString("매개변수가 충분히 전달되지 못했거나 올바르지 않은 매개변수 값이 전달되었습니다."));
        }

        @ParameterizedTest
        @CsvSource(value = {":게시물 내용:", "게시물 제목::/image.png", "::/image.png"}, delimiterString = ":")
        @DisplayName("게시물 내용, 제목 중 하나라도 비어 있다면 400 BAD_REQUEST 반환")
        public void savePostFailureWhenBlankOrNullParam(String postTitle, String postContent, String image) {
            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("postTitle", postTitle);
            body.put("postContent", postContent);
            body.put("image", image);

            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all();

            // 실행
            Response when = given.when()
                    .post("/posts");

            //검증
            when.then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .assertThat();
        }
    }

    @Nested
    @DisplayName("게시물 조회 인수 테스트")
    class FindPostTest {

        @Nested
        @DisplayName("게시물 단건 조회 테스트")
        @Order(Ordered.HIGHEST_PRECEDENCE)
        class FindPostOneTest {

            @Test
            @DisplayName("게시물 단건 조회 성공")
            void findOnePostSuccess() {
                // 준비
                RequestSpecification given = given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(ContentType.JSON)
                        .log().all();

                // 실행
                Response when = given.when()
                        .get("/posts/5");

                //검증
                when.then()
                        .statusCode(HttpStatus.OK.value())
                        .assertThat();
            }

            @ParameterizedTest
            @ValueSource(longs = {-1, 10, 99999})
            @DisplayName("게시물 단건 조회 실패 - 음수값을 가진 게시물 아이디 또는 BLOCK 처리된 게시물, 삭제된 게시물 등")
            void findOnePostFailure(Long postId) {
                // 준비
                RequestSpecification given = given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(ContentType.JSON)
                        .log().all();

                // 실행
                Response when = given.when()
                        .get("/posts" + postId);

                //검증
                when.then()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .assertThat();
            }

        }

        @Nested
        @DisplayName("게시물 페이징 테스트")
        class FindPostPageTest {

            @Test
            @DisplayName("게시물 페이징 조회 성공")
            void findOnePostSuccess() {
                // 준비
                RequestSpecification given = given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(ContentType.JSON)
                        .param("page", 1).log().all();

                // 실행
                Response when = given.when()
                        .get("/posts");

                //검증
                when.then()
                        .statusCode(HttpStatus.OK.value())
                        .assertThat();
            }

            @ParameterizedTest
            @ValueSource(ints = {-1, 9999999})
            @DisplayName("게시물 페이징 조회 실패 - 음수값을 가진 페이지 또는 페이지 수 초과")
            void findOnePostFailure(int requestedPageNumber) {
                // 준비
                RequestSpecification given = given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(ContentType.JSON)
                        .param("page", requestedPageNumber).log().all();

                // 실행
                Response when = given.when()
                        .get("/posts");

                //검증
                when.then()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .assertThat();
            }
        }
    }

    @Nested
    @DisplayName("게시물 수정 테스트")
    class UpdatePostTest {

        @Test
        @DisplayName("게시물 수정 성공")
        void updateOnePostSuccess() {
            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("postId", 3);
            body.put("postTitle", "수정된 3번 게시물 제목입니다.");
            body.put("postContent", "수정된 3번 게시물의 내용입니다.");
            body.put("image", "/newImage.png");

            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all();

            // 실행
            Response when = given.when()
                    .patch("/posts");

            //검증
            when.then()
                    .statusCode(HttpStatus.OK.value())
                    .assertThat();
        }

        @ParameterizedTest
        @ValueSource(longs = {-1, 10})
        @DisplayName("게시물 수정 실패 - 없는 게시물 번호, 또는 BLOCK 된 게시물")
        void updateOnePostFailure(Long requestPostId) {
            // 준비
            Map<String, Object> body = new HashMap<>();
            body.put("postId", requestPostId);
            body.put("postTitle", "수정된 N번 게시물 제목입니다.");
            body.put("postContent", "수정된 N번 게시물의 내용입니다.");
            body.put("image", "/newImage.png");

            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .body(body).log().all();

            // 실행
            Response when = given.when()
                    .patch("/posts");

            //검증
            when.then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .assertThat();
        }

    }

    @Nested
    @DisplayName("게시물 삭제 테스트")
    class DeletePostTest {

        @Test
        @DisplayName("게시물 삭제 성공")
        void deleteOnePostSuccess() {
            // 준비
            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .log().all();

            // 실행
            Response when = given.when()
                    .delete("/posts/1");

            //검증
            when.then()
                    .statusCode(HttpStatus.OK.value())
                    .assertThat();
        }

        @ParameterizedTest
        @ValueSource(longs = {-1, 10})
        @DisplayName("게시물 삭제 실패 - 없는 게시물 번호, 또는 BLOCK 된 게시물")
        void deleteOnePostFailure(Long postId) {
            // 준비
            RequestSpecification given = given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(ContentType.JSON)
                    .log().all();

            // 실행
            Response when = given.when()
                    .delete("/posts" + postId);

            //검증
            when.then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .assertThat();
        }

    }
}
