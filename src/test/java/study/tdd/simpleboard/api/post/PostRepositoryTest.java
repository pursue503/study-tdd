package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import study.tdd.simpleboard.api.member.entity.Member;
import study.tdd.simpleboard.api.member.signup.MemberRepository;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * h2는 임베디드 DB를 사용하므로, inmemory 설정을 사용할 수 있도록
 * # @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
 * 를 설정합니다.
 *
 * @author Informix
 * @create 2021-12-24
 * @since 2.6.1 spring boot
 * @since 0.0.1
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    Member member = mock(Member.class);
    MemberRepository memberRepository = mock(MemberRepository.class);

    @Test
    @DisplayName("게시물 텍스트 저장")
    void saveText() {
        // 준비
        Post wantToSave = Post.builder()
                .postTitle("제목")
                .postContent("내용")
                .member(member)
                .build();

        // 실행
        Post saved = postRepository.save(wantToSave);

        // 검증
        assertThat(saved).isEqualTo(wantToSave);
    }

    @Test
    @DisplayName("게시물 이미지 저장")
    void saveImage() {
        // 준비
        Post wantToSave = Post.builder()
                .postTitle("제목")
                .postContent("내용")
                .member(member)
                .image("/image.png")
                .build();

        // 실행
        Post saved = postRepository.save(wantToSave);

        // 검증
        assertThat(saved.getImage()).isNotNull();
        assertThat(saved.getImage()).isEqualTo(wantToSave.getImage());
    }

    @Test
    @DisplayName("게시물 1개 조회")
    void findOnePost() {
        // 준비
        Post wantToFind = Post.builder()
                .postTitle("30번 제목")
                .postContent("30번 내용")
                .member(member)
                .image("/image.png")
                .build();

        // 실행
        Post saved = postRepository.save(wantToFind);
        Post founded = postRepository.findById(saved.getPostId())
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        // 검증
        assertThat(wantToFind)
                .isEqualTo(saved)
                .isEqualTo(founded);
    }

    @Test
    @DisplayName("게시물 여러 개 (제목) 조회 - 페이징")
    void findPostPage() {
        // 준비
        int wantToSeePage = 0; // 내림차순이므로 가장 최신 게시물이 0번 페이지에 들어옵니다.
        int pagingSize = 10;

        // 실행
        Page<Post> postPage = postRepository
                .findAll(PageRequest
                        .of(wantToSeePage, pagingSize,
                                Sort.by(Sort.Direction.DESC, "postId")));

        String lastestPost = postPage.stream()
                .map(Post::getPostTitle)
                .findFirst()
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        Post lastestSavedPost = postRepository.findById(24L)
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        assertThat(lastestPost).isEqualTo(lastestSavedPost.getPostTitle());
    }

    @Test
    @DisplayName("게시물 1개 수정")
    void updateOnePost() {
        // 준비
        Post wantToChange = Post.builder()
                .postTitle("제목 변경")
                .postContent("내용 변경")
                .member(member)
                .image("/image.png")
                .build();

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

        // 검증
    }

    @Test
    @DisplayName("게시물 1개 삭제")
    void deleteOnePost() {

        // TODO: memberRepository가 준비되는대로 재구현 대기중

        // 준비
        Post wantToDelete = Post.builder()
                .postTitle("32번 제목")
                .postContent("32번 내용")
                .member(member)
                .image("/image.png")
                .build();

        // 준비 - 저장 실행
        Post saved = postRepository.save(wantToDelete);

        // 삭제 전 검증
        Post canFindPost = postRepository.findById(saved.getPostId())
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));

        assertThat(canFindPost).isEqualTo(saved);

        // 삭제 실행


        // 삭제 후 검증
        assertThatThrownBy(() -> {
            postRepository.findById(saved.getPostId())
                    .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));
        });
    }
}
