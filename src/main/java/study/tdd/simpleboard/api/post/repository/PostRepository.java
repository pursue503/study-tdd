package study.tdd.simpleboard.api.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.tdd.simpleboard.api.post.domain.entity.Post;

import java.util.Optional;

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
    @Query(value = "SELECT p " +
                    "FROM Post p JOIN FETCH p.member m " +
                    "WHERE p.blocked = false ",
            countQuery = "SELECT count(p) FROM Post p WHERE p.blocked = false ")
    Page<Post> findAllUnblockedPosts(Pageable pageable);

    @Query(value = "SELECT p " +
                    "FROM Post p join fetch p.member m " +
                    "WHERE p.blocked = false " +
                    "AND p.postId = :postId")
    Optional<Post> findById(@Param("postId") Long postId);
}
