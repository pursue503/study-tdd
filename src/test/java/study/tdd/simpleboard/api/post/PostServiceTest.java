package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.api.post.service.PostService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    Member member = mock(Member.class);
    PostRepository postRepository = mock(PostRepository.class);
    PostService postService = new PostService(postRepository);

    @Test
    @DisplayName("게시물 텍스트 저장 성공")
    void saveTextSuccess() {

    }

    @Test
    @DisplayName("게시물 텍스트 저장 실패")
    void saveTextFailure() {

    }

    @Test
    @DisplayName("게시물 이미지 저장 성공")
    void saveImageSuccess() {

    }

    @Test
    @DisplayName("게시물 이미지 저장 실패")
    void saveImageFailure() {

    }

    @Test
    @DisplayName("게시물 1개 조회 성공")
    void findOnePostSuccess() {

        // 준비
        Optional<Post> expectedToGetPost = Optional.of(Post.builder()
                .postTitle("제목")
                .postContent("내용")
                .member(member)
                .build());

        given(postRepository.findById(1L)).willReturn(expectedToGetPost);

        // 실행
        Post onePost = postService.findOnePost(1L);

        // 검증
        verify(postRepository, times(1)).findById(1L);
        assertThat(expectedToGetPost.get()).isEqualTo(onePost);
    }

    @Test
    @DisplayName("게시물 1개 조회 실패")
    void findOnePostFailure() {
        // 준비
        Optional<Post> expectedToGetPost = Optional.of(Post.builder()
                .postTitle("제목")
                .postContent("내용")
                .member(member)
                .build());

        given(postRepository.findById(1L)).willReturn(expectedToGetPost);

        // 실행
        Post onePost = postService.findOnePost(1L);

        // 검증
        verify(postRepository, times(1)).findById(1L);
        assertThat(expectedToGetPost.get()).isEqualTo(onePost);
    }

    @Test
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징 성공")
    void findPostPageSuccess() {

    }

    @Test
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징 실패")
    void findPostPageFailure() {

    }

    @Test
    @DisplayName("게시물 1개 수정 성공")
    void updateOnePostSuccess() {

    }


    @Test
    @DisplayName("게시물 1개 수정 실패")
    void updateOnePostFailure() {

    }

    @Test
    @DisplayName("게시물 1개 삭제 성공")
    void deleteOnePostSuccess() {

    }

    @Test
    @DisplayName("게시물 1개 삭제 실패")
    void deleteOnePostFailure() {

    }
}
