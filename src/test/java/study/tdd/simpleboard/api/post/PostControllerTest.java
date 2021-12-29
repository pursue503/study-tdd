package study.tdd.simpleboard.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import study.tdd.simpleboard.api.post.controller.PostController;
import study.tdd.simpleboard.api.post.controller.PostControllerAdvice;
import study.tdd.simpleboard.api.post.domain.PostSaveRequestDTO;
import study.tdd.simpleboard.api.post.service.PostService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest(properties = "spring.profiles.activetest")
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // @since SpringBoot 2.2
@WebMvcTest(PostController.class)
public class PostControllerTest {

    private MockMvc mockmvc;

    @MockBean
    private PostService postService;

    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        PostController postController = new PostController(postService);
        mockmvc = MockMvcBuilders.standaloneSetup(postController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new PostControllerAdvice())
                .build();
    }

    @Nested
    @DisplayName("게시물 저장 테스트: 제목 매개변수 검증")
    class PostTitleTest {

        @Test
        @DisplayName("게시물 제목이 null로 전달되어 올 경우 400 에러 반환")
        void whenPostTitleisNull() throws Exception {
            // 실행
            ResultActions perform = mockmvc.perform(post("/posts"));

            // 검증
            perform.andDo(print())
                    .andExpect(status().isBadRequest());
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

            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", "제목");
            content.put("postContent", "내용");

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("게시물이 잘 저장되었습니다."));
        }
    }

    @Nested
    @DisplayName("게시물 저장 테스트: 내용 및 이미지 매개변수 검증")
    class PostContentTest {

        // magic words
        String postTitle = "제목";
        String postContent = "내용";

        @ParameterizedTest
        @ValueSource(strings = {"", "    ", " ", "\n", "\t"})
        @DisplayName("게시물 내용이 공백 또는 내용이 비어있는 상태로 전달되었을 경우 400 에러 반환")
        void whenPostContentisEmpty2(String emptyContent) throws Exception {
            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", postTitle);
            content.put("postContent", emptyContent.trim());

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("게시물 내용이 2000글자를 초과하는 경우 400 에러 반환")
        void whenPostContentExceeds2000Letters() throws Exception {
            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", postTitle);
            content.put("postContent", postContent.repeat(2001));

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("게시물 내용이 1글자 이상 2000 글자 이하일 경우 통과")
        void isPostContentBetween1and2000Letters() throws Exception {
            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", postTitle);
            content.put("postContent", postContent);

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("게시물이 잘 저장되었습니다."));
        }


        @Test
        @DisplayName("게시물에 첨부 이미지가 없을 경우에도 저장 기능이 정상 작동")
        void successAlwaysDoesNotMatterWhetherImageExistsOrNot() throws Exception {
            // 준비
            Map<String, String> content = new HashMap<>();
            content.put("postTitle", postTitle);
            content.put("postContent", postContent);
            PostSaveRequestDTO requestedPostSave = new PostSaveRequestDTO(postTitle, postContent, null);
            Long newSavedPostId = 30L;

            doReturn(newSavedPostId).when(postService).savePost(requestedPostSave);

            // 실행
            ResultActions perform = mockmvc.perform(post("/posts")
                    .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                    .content(mapper.writeValueAsString(content)));

            // 검증
            perform.andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("게시물이 잘 저장되었습니다."));

            verify(postService, atLeastOnce()).savePost(refEq(requestedPostSave));
        }
    }

    @Test
    @DisplayName("게시물 수정 성공")
    public void updatePostSuccess() {

    }

    @Test
    @DisplayName("게시물 수정 실패")
    public void updatePostFailure() {

    }

    @Test
    @DisplayName("게시물 삭제 성공")
    public void deletePostSuccess() {

    }

    @Test
    @DisplayName("게시물 삭제 실패")
    public void deletePostFailure() {

    }
}
