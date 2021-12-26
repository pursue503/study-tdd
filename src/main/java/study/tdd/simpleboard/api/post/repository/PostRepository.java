package study.tdd.simpleboard.api.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.tdd.simpleboard.api.post.domain.PostPageDTO;
import study.tdd.simpleboard.api.post.entity.Post;

import java.util.List;
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
                   "FROM Post p inner join Member m " +
                   "ON p.member.memberId = m.memberId " +
                   "WHERE p.blocked = false ")
    Page<Post> findAllUnblockedPosts(Pageable pageable);

    /**
     * @Query(value = "SELECT p.postId, p.postTitle, m.nickname " +
     *                     "FROM Post p join fetch Member m " +
     *                     "ON p.member.memberId = m.memberId " +
     *                     "WHERE p.blocked = false ")
     */

    @Query(value = "SELECT p " +
            "FROM Post p join fetch p.member m " +
            "WHERE p.blocked = false " +
            "AND p.postId = :postId")
    Optional<Post> findById(@Param("postId") Long postId);
}
