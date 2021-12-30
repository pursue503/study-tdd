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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.post.controller.PostController;
import study.tdd.simpleboard.api.post.controller.PostControllerAdvice;
import study.tdd.simpleboard.api.post.domain.*;
import study.tdd.simpleboard.api.post.domain.entity.Post;
import study.tdd.simpleboard.api.post.service.PostService;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("게시물 1개 조회 성공")
    public void findOnePostSuccess() throws Exception {
        // 준비
        PostOneDTO findPost = new PostOneDTO(Post.builder().postTitle("찾으시는 게시물의 제목").member(new Member("", "nickname", "")).build());
        given(postService.findOnePost(1L)).willReturn(findPost);

        // 실행 및 검증
        mockmvc.perform(get("/posts/1")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postTitle").value("찾으시는 게시물의 제목"))
                .andExpect(jsonPath("$.message").value("게시물이 잘 조회되었습니다."));
    }

    @Test
    @DisplayName("게시물 1개 조회 실패 - 없는 게시물 (주의: 정책상 block 처리된 게시물도 없는 게시물 취급) ")
    public void findOnePostFailure() {
        // 준비
        given(postService.findOnePost(-1L)).willThrow(new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        // 실행 및 검증
        assertThatThrownBy(() -> mockmvc.perform(get("/posts/-1")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(status().isBadRequest()))
                .hasMessageContaining("해당 게시물을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 페이지 조회 성공")
    public void findPostPageSuccess() throws Exception {
        // 준비
        PageResponseDTO result = new PageResponseDTO(1,
                new PageImpl<>(List
                        .of(new Post(1L, "1번 페이지 게시물 제목", "1번 페이지 게시물 내용", null, new Member()))));
        given(postService.findPostsPage(1)).willReturn(result);

        // 실행 및 검증
        mockmvc.perform(get("/posts")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.selectedPosts[0].postTitle").value("1번 페이지 게시물 제목"))
                .andExpect(jsonPath("$.data.selectedPosts[0].postContent").value("1번 페이지 게시물 내용"))
                .andExpect(jsonPath("$.message").value("게시물 목록이 잘 조회되었습니다."));

    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 999999 })
    @DisplayName("게시물 페이지 조회 실패 - 찾는 페이지가 음의 정수이거나 페이지를 초과하는 경우")
    public void findPostPageFailure(int page) {
        // 준비
        given(postService.findPostsPage(page)).willThrow(new BizException(PostCrudErrorCode.PAGE_NOT_FOUND));

        // 실행 및 검증
        assertThatThrownBy(() -> mockmvc.perform(get("/posts")
                        .param("page", String.valueOf(page)))
                .andExpect(status().isBadRequest()))
                .hasMessageContaining("해당 페이지를 찾을 수 없습니다.");

        verify(postService, atLeastOnce()).findPostsPage(page);
    }

    @Test
    @DisplayName("게시물 수정 성공")
    public void updatePostSuccess() throws Exception {
        // 준비
        PostPatchRequestDTO wantToPatch = new PostPatchRequestDTO(1L, "게시물의 변경될 제목", "게시물의 변경될 내용", null);
        UpdatedPostDTO updatedPostDTO = new UpdatedPostDTO(Post.builder()
                                                                .postId(wantToPatch.getPostId())
                                                                .postTitle(wantToPatch.getPostTitle())
                                                                .postContent(wantToPatch.getPostContent())
                                                                .build());

        doReturn(updatedPostDTO).when(postService).updateOnePost(wantToPatch);

        Map<String, String> content = new HashMap<>();
        content.put("postId", String.valueOf(wantToPatch.getPostId()));
        content.put("postTitle", wantToPatch.getPostTitle());
        content.put("postContent", wantToPatch.getPostContent());

        // 실행, 검증
        mockmvc.perform(patch("/posts")
                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(mapper.writeValueAsString(content)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시물 수정 실패")
    public void updatePostFailure() {
        // 준비
        doThrow(new BizException(PostCrudErrorCode.POST_NOT_FOUND)).when(postService).deleteOnePost(-1L);
        Map<String, String> content = new HashMap<>();
        content.put("postId", "-1");
        content.put("postTitle", "게시물 제목 수정 시도");
        content.put("postContent", "게시물 내용 수정 시도");

        // 실행, 검증
        assertThatThrownBy(() -> mockmvc.perform(patch("/posts")
                .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(mapper.writeValueAsString(content)))
                .andExpect(status().isBadRequest()));
    }

    @Test
    @DisplayName("게시물 삭제 성공")
    public void deletePostSuccess() throws Exception {
        // 준비
        given(postService.deleteOnePost(1L)).willReturn(LocalDateTime.now());

        // 실행
        mockmvc.perform(delete("/posts/1")
                        .header("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("게시물이 삭제되었습니다."));

        // 검증
        verify(postService, atLeastOnce()).deleteOnePost(1L);
    }

    @Test
    @DisplayName("게시물 삭제 실패")
    public void deletePostFailure() {
        // 준비
        doThrow(new BizException(PostCrudErrorCode.POST_NOT_FOUND)).when(postService).deleteOnePost(-1L);

        // 실행, 검증
        assertThatThrownBy(() -> mockmvc.perform(delete("/posts/-1"))
                .andExpect(status().isBadRequest()));
    }
}
