package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @Nested
    @DisplayName("게시물 제목 테스트")
    class PostTitleTest {

        @ParameterizedTest
        @NullSource
        @DisplayName("게시물 제목이 null로 전달되어 올 경우 400 에러 반환")
        void whenPostTitleisNull(String postTitleParam) {

        }

        @ParameterizedTest
        @DisplayName("게시물 제목이 비어 있는 상태로 올 경우 400 에러 반환")
        @EmptySource
        @ValueSource(strings = {" ", "     ", "\t", "\n" })
        void whenPostTitleisEmpty(String postTitleParam) {

        }

        @Test
        @DisplayName("게시물 제목이 30글자를 초과하여 올 경우 400 에러 반환")
        void whenPostTitleExceeds30Letters() {

        }

        @Test
        @DisplayName("게시물 제목이 1글자 이상 30 글자 이하일 경우 통과")
        void isPostTitleBetween1and30Letters() throws Exception {
            // 준비

            // 실행
            ResultActions perform = mockmvc.perform(get("/posts/1"));

            // 검증
            perform.andExpect(status().isOk());

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
