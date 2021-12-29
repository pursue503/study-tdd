package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.*;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.post.domain.PageResponseDTO;
import study.tdd.simpleboard.api.post.domain.PostOneDTO;
import study.tdd.simpleboard.api.post.domain.PostPageDTO;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.api.post.service.PostService;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    Member member = mock(Member.class);
    PostRepository postRepository = mock(PostRepository.class);
    PostService postService = new PostService(postRepository);

    // magic words
    String postTItle = "제목";
    String postContent = "내용";
    String image = "/image.png";

    @Test
    @DisplayName("게시물 텍스트 저장 성공")
    void saveTextSuccess() {
        // 준비
        Post wantToSave = Post.builder()
                .postTitle(postTItle)
                .postContent(postContent)
                .image(null)
                .build();

        given(postRepository.save(wantToSave)).willReturn(wantToSave);
        given(postRepository.findById(wantToSave.getPostId())).willReturn(Optional.of(wantToSave));

        // 실행
        Post saved = postRepository.save(wantToSave);
        Post findPost = postRepository.findById(saved.getPostId())
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        // 검증
        assertThat(saved).isEqualTo(findPost);
    }

    @Test
    @DisplayName("게시물 텍스트 저장 실패")
    void saveTextFailureAsDatabaseProblem() {
        // 준비
        Post wantToSave = Post.builder()
                .postTitle(postTItle)
                .postContent(postContent)
                .image(null)
                .build();

        given(postRepository.save(wantToSave)).willThrow(new BizException(PostCrudErrorCode.POST_CRUD_FAIL));

        // 실행, 검증
        assertThatThrownBy(() -> postRepository.save(wantToSave))
                .isInstanceOf(BizException.class)
                .hasMessageContaining("게시물 처리 요청이 실패했습니다.");
    }

    @Test
    @DisplayName("게시물 이미지 저장 성공")
    void saveImageSuccess() {
        // 준비
        Post wantToSave = Post.builder()
                .postTitle(postTItle)
                .postContent(postContent)
                .image(image)
                .build();

        given(postRepository.save(wantToSave)).willReturn(wantToSave);
        given(postRepository.findById(wantToSave.getPostId())).willReturn(Optional.of(wantToSave));

        // 실행
        Post saved = postRepository.save(wantToSave);
        Post findPost = postRepository.findById(saved.getPostId())
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        // 검증
        assertThat(saved).isEqualTo(findPost);
    }

    @Test
    @DisplayName("게시물 이미지 저장 실패")
    void saveImageFailure() {
        // 준비
        Post wantToSave = Post.builder()
                .postTitle(postTItle)
                .postContent(postContent)
                .image(image)
                .build();

        given(postRepository.save(wantToSave)).willThrow(new BizException(PostCrudErrorCode.POST_CRUD_FAIL));

        // 실행, 검증
        assertThatThrownBy(() -> postRepository.save(wantToSave))
                .isInstanceOf(BizException.class)
                .hasMessageContaining("게시물 처리 요청이 실패했습니다.");
    }

    @Test
    @DisplayName("게시물 1개 조회 성공")
    void findOnePostSuccess() {
        // 준비
        Optional<Post> post = Optional.of(Post.builder()
                .postTitle(postTItle)
                .postContent(postContent)
                .image("/image.png")
                .member(member)
                .build());
        PostOneDTO expectedToGetPost = new PostOneDTO(post.get());

        given(postRepository.findById(1L)).willReturn(post);

        // 실행
        PostOneDTO onePost = postService.findOnePost(1L);

        // 검증
        verify(postRepository, times(1)).findById(1L);
        assertThat(expectedToGetPost.getPostId()).isEqualTo(onePost.getPostId());
        // 추가 검증
        assertThat(expectedToGetPost.getPostTitle()).isNotNull();
        assertThat(expectedToGetPost.getImage()).isEqualTo("/image.png");

    }

    @Test
    @DisplayName("게시물 1개 조회 실패 - 없는 게시글 조회 시도")
    void findOnePostThatNotExistsFailure() {
        // 준비
        Throwable throwable = catchThrowable(() -> {
            throw new BizException(PostCrudErrorCode.POST_NOT_FOUND);
        });

        given(postRepository.findById(-1L)).willThrow(throwable);

        // 실행
        // 검증
        assertThatThrownBy(() -> postService.findOnePost(-1L))
                .isInstanceOf(BizException.class)
                .hasMessageContaining("해당 게시물을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 1개 조회 실패 - BLOCK 처리된 게시물 조회 시도")
    void findOnePostThatBlockedFailure() {
        // 준비
        Long blockedPostId = 10L;

        // 실행
        // 검증
        assertThatThrownBy(() -> postService.findOnePost(blockedPostId))
                .isInstanceOf(BizException.class)
                .hasMessageContaining("해당 게시물을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징 성공")
    void findPostPageSuccess() {
        // 준비
        int page = 1;
        int pagingSize = 10;

        Post savedPost1 = new Post(24L, "게시물 제목 24번", "게시물 내용 24번", null, member);
        Post savedPost2 = new Post(23L, "게시물 제목 23번", "게시물 내용 23번", null, member);

        List<Post> savedPagePosts = List.of(savedPost1, savedPost2);
        PageImpl<Post> posts = new PageImpl<>(savedPagePosts);
        Pageable pageable = PageRequest.of(page - 1, pagingSize, Sort.by(Sort.Direction.DESC, "postId"));

        given(postRepository.findAllUnblockedPosts(any(Pageable.class))).willReturn(posts);

        // 실행
        PageResponseDTO result = postService.findPostsPage(page);

        // 검증
        verify(postRepository, atLeastOnce()).findAllUnblockedPosts(refEq(pageable));
        assertThat(result.getSelectedPosts().get(0)).isEqualTo(savedPost1);
        assertThat(result.getSelectedPosts().get(1)).isEqualTo(savedPost2);
    }

    @ParameterizedTest
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징 실패")
    @ValueSource(ints = {0, -1, -999})
    void findPostPageFailure(int invalidPage) {
        // 실행, 검증
        assertThatThrownBy(() -> postService.findPostsPage(invalidPage))
                .isInstanceOf(BizException.class)
                .describedAs("0쪽 이하의 페이지를 찾는 것은 금지되어 있습니다.")
                .hasMessageContaining("해당 페이지를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("게시물 1개 수정 성공")
    void updateOnePostSuccess() {
        // 실행
        /*
            TODO: SELECT p.post_id
                  FROM member m inner join post p
                  ON m.member_id = p.member_id
                  WHERE EXISTS (
                     SELECT p2.post_id
                     FROM POST p2
                     p2.member_id = :member.getId());

            쿼리를 질의하여 존재하지 않으면:
                throw new BizException(PostCrudErrorCode.PATCH_NOT_PERMIT) 반환
            존재하면
                UPDATE post
                SET post_title= :wantToChange.getPostTitle() ....
                WHERE post_id = :위에서 가져온 p.post_id
         */
    }

    @Test
    @DisplayName("게시물 1개 수정 실패")
    void updateOnePostFailure() {

    }

    @Test
    @DisplayName("게시물 1개 삭제 성공")
    void deleteOnePostSuccess() {
        // 저장한 게시물 삭제 전 검증 - 요청자가 게시물 작성자일 경우 삭제 진행:
        // 이 테스트에서는 요청자가 작성자라고 가정하여 성공하게 한다.
        // 준비
        Long postId = 1L;
        Post wantToDelete = Post.builder().postTitle("삭제될 게시물의 제목").build();
        given(postRepository.findById(postId))
                .willReturn(Optional.of(wantToDelete));
        doNothing().when(postRepository).delete(wantToDelete);

        // 실행
        postService.deleteOnePost(postId);

        // 검증
        verify(postRepository, atLeastOnce()).findById(postId);
        verify(postRepository, atLeastOnce()).delete(wantToDelete);
    }

    @Test
    @DisplayName("게시물 1개 삭제 실패 - 없는 게시물 번호")
    void deleteOnePostFailure() {
        // 준비
        Long postId = -1L;
        Post wantToDelete = Post.builder().postTitle("삭제될 게시물의 제목").build();

        // 검증, 실행
        assertThatThrownBy(() -> postRepository.findById(postId)
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND)))
                .isInstanceOf(BizException.class)
                .hasMessageContaining("해당 게시물을 찾을 수 없습니다.");

        verify(postRepository, atLeastOnce()).findById(postId);
        verify(postRepository, never()).delete(wantToDelete);
    }
}
