package study.tdd.simpleboard.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.tdd.simpleboard.api.post.domain.PostSaveRequestDTO;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

/**
 * 게시물 CRUD 요청 처리 서비스
 *
 * @author Informix
 * @create 2021-12-24
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post findOnePost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));
    }

    public Long savePost(PostSaveRequestDTO dto) {
        return postRepository.save(dto.toEntity())
                             .getPostId();
    }
}
