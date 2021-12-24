package study.tdd.simpleboard.api.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.tdd.simpleboard.api.post.entity.Post;

/**
 * Post Entity를 조작하는 기능을 제공하는 data-jpa 인터페이스
 *
 * @author Informix
 * @create 2021-12-24
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}
