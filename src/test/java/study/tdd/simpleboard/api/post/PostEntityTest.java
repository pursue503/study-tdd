package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import study.tdd.simpleboard.api.post.domain.entity.Post;

import static org.assertj.core.api.Assertions.assertThat;

public class PostEntityTest {

    @Test
    @DisplayName("게시물 엔티티가 존재할 경우")
    public void createPostEntity() {
        Post post = new Post();
        assertThat(post).isNotNull();
    }

    @Nested
    @DisplayName("게시물 제목 속성 테스트")
    class PostTitlePropertyTest {

        @Test
        @DisplayName("post 엔티티에 게시물 제목 속성이 있고 정상 동작할 경우")
        public void createPostTitleProperty() {
            Post post = Post.builder().postTitle("게시물 제목1").build();
            assertThat(post.getPostTitle()).isNotNull().isEqualTo("게시물 제목1");
        }
    }

    @Nested
    @DisplayName("게시물 내용 속성 테스트")
    class PostContentPropertyTest {

        @Test
        @DisplayName("post 엔티티에 게시물 내용 속성이 있을 경우")
        public void createPostContentProperty() {

        }

    }

    @Nested
    @DataJpaTest
    @DisplayName("Member와 Post 연관관계 테스트")
    class _1Member_NPostRelationTest {

        @Test
        @DisplayName("존재하지 않는 회원은 게시물을 작성할 수 없음")
        public void cannotPostExistByUnExistedMember() {

        }

        @Test
        @DisplayName("존재하는 회원은 게시물 작성 가능")
        public void canPostByExistedMember() {

        }

    }

}
