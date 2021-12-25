package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.api.post.service.PostService;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import java.util.List;
import java.util.Optional;

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
        Optional<Post> expectedToGetPost = Optional.of(Post.builder()
                .postTitle(postTItle)
                .postContent(postContent)
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
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징 성공")
    void findPostPageSuccess() {
        // 준비
        int wantToSeePage = 0;
        int pagingSize = 10;

        List<Post> findPostsInSelectPage = List.of(new Post("22번 제목", "22번 내용", image, member),
                                                   new Post("23번 제목", "23번 내용", image, member)
                                           );
        Page<Post> pagingPosts = new PageImpl<>(findPostsInSelectPage, new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 1;
            }

            @Override
            public long getOffset() {
                return 2;
            }

            @Override
            public Sort getSort() {
                return Sort.by(Sort.Direction.DESC, "postId");
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        }, 2);

        given(postRepository
                .findAll(PageRequest
                        .of(wantToSeePage, 10,
                                Sort.by(Sort.Direction.DESC, "postId"))))
                .willReturn(pagingPosts);

        // 실행
        Page<Post> result = postService.findPostsPage(wantToSeePage);

        // 검증
        assertThat(result).containsExactlyInAnyOrder(findPostsInSelectPage.get(0), findPostsInSelectPage.get(1));

    }

    @Test
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징 실패")
    void findPostPageFailure() {
        // 준비
        int wantToSeePage = -1;

        // 실행, 검증
        assertThatIllegalArgumentException()
                .isThrownBy(() -> postService.findPostsPage(wantToSeePage))
                .describedAs("0쪽 이하의 페이지를 찾는 것은 금지되어 있습니다.")
                .withMessageContaining("Page index must not be less than zero!");

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
                SET post_title=:wantToChange.getPostTitle() ....
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
    }

    @Test
    @DisplayName("게시물 1개 삭제 실패")
    void deleteOnePostFailure() {

    }
}
