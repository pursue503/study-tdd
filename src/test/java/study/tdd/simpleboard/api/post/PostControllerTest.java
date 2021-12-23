package study.tdd.simpleboard.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import study.tdd.simpleboard.api.post.controller.PostController;
import study.tdd.simpleboard.api.post.controller.PostControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PostControllerTest {

    private MockMvc mockmvc;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mockmvc = MockMvcBuilders.standaloneSetup(new PostController())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new PostControllerAdvice())
                .build();
    }

    @Nested
    @DisplayName("게시물 제목 테스트")
    class PostTitleTest {

        @Test
        @DisplayName("게시물 제목이 null로 전달되어 올 경우 400 에러 반환")
        void whenPostTitleisNull() throws Exception {
            ResultActions perform = mockmvc.perform(post("/posts"));

            // 검증
            perform.andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @DisplayName("게시물 제목이 비어 있는 상태로 올 경우 400 에러 반환")
        @ValueSource(strings = {" ", "     ", "\t", "\n"})
        void whenPostTitleisEmpty(String postTitleParam) throws Exception {
            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", postTitleParam.trim());
            content.put("postContent", "내용");

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("게시물 제목이 30글자를 초과하여 올 경우 400 에러 반환")
        void whenPostTitleExceeds30Letters() throws Exception {

            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", "30글자를 초과하여 작성된 게시물 제목입니다." +
                                     "400 에러를 반환해야 합니다. 400 에러를 정상적으로 받았습니다. 감사합니다~.");
            content.put("postContent", "내용");

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("게시물 제목이 1글자 이상 30 글자 이하일 경우 통과")
        void isPostTitleBetween1and30Letters() throws Exception {
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", "제목");
            content.put("postContent", "내용");

            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("게시물이 잘 저장되었습니다."));
        }
    }

    @Nested
    @DisplayName("게시물 내용 테스트")
    class PostContentTest {

        @Test
        @DisplayName("게시물 내용이 비어 있는 경우 400 에러 반환")
        void whenPostContentisEmpty() {

        }

        @Test
        @DisplayName("게시물 내용이 2000글자를 초과하는 경우 400 에러 반환")
        void whenPostContentExceeds2000Letters() {

        }

        @Test
        @DisplayName("게시물 내용이 1글자 이상 2000 글자 이하일 경우")
        void isPostContentBetween1and2000Letters() {

        }
    }
}
