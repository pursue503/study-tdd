package study.tdd.simpleboard.api.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.tdd.simpleboard.api.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}
