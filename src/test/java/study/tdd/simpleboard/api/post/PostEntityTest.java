package study.tdd.simpleboard.api.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostEntityTest {

    @Test
    @DisplayName("게시물 엔티티가 존재할 경우")
    public void createPostEntity() {
        Post post = new Post();
        assertThat(post).isNotNull();
    }

    @Nested
    @DisplayName("게시물 제목 테스트")
    class PostTitleTest {

        @Test
        @DisplayName("post 엔티티에 게시판 제목 속성이 있을 경우")
        public void createPostTitleProperty() {

        }

        @Test
        @DisplayName("게시물 제목이 비어 있는 경우 400 에러 반환")
        public void whenPostTitleisEmpty() {

        }

        @Test
        @DisplayName("게시물 제목이 30글자를 초과하는 경우 400 에러 반환")
        public void whenPostTitleExceeds30Letters() {

        }

        @Test
        @DisplayName("게시물 제목이 1글자 이상 30 글자 이하일 경우")
        public void isPostTitleBetween1and30Letters() {

        }

    }

    @Nested
    @DisplayName("게시물 내용 테스트")
    class PostContentTest {

        @Test
        @DisplayName("post 엔티티에 게시물 내용 속성이 있을 경우")
        public void createPostContentProperty() {

        }

        @Test
        @DisplayName("게시물 내용이 비어 있는 경우 400 에러 반환")
        public void whenPostContentisEmpty() {

        }

        @Test
        @DisplayName("게시물 내용이 2000글자를 초과하는 경우 400 에러 반환")
        public void whenPostContentExceeds2000Letters() {

        }

        @Test
        @DisplayName("게시물 내용이 1글자 이상 2000 글자 이하일 경우")
        public void isPostContentBetween1and2000Letters() {

        }
    }

}
