package study.tdd.simpleboard.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tdd.simpleboard.api.post.domain.*;
import study.tdd.simpleboard.api.post.domain.entity.Post;
import study.tdd.simpleboard.api.post.repository.PostRepository;
import study.tdd.simpleboard.exception.common.BizException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import java.time.LocalDateTime;

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
@Transactional(rollbackFor = RuntimeException.class)
public class PostService {

    private final PostRepository postRepository;
    int pagingSize = 10;

    public PostOneDTO findOnePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));
        return new PostOneDTO(post);
    }

    public Long savePost(PostSaveRequestDTO dto) {
        return postRepository.save(dto.toEntity())
                .getPostId();
    }

    public PageResponseDTO findPostsPage(int page) {
        if ((page = page - 1) < 0) {
            throw new BizException(PostCrudErrorCode.PAGE_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, pagingSize, Sort.by(Sort.Direction.DESC, "postId"));
        PageResponseDTO pageResponseDTO = new PageResponseDTO(page, postRepository.findAllUnblockedPosts(pageable));

        if (pageResponseDTO.getTotalPages() < (page + 1)) {
            throw new BizException(PostCrudErrorCode.PAGE_NOT_FOUND);
        }

        return pageResponseDTO;
    }

    public UpdatedPostDTO updateOnePost(PostPatchRequestDTO dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));
        return new UpdatedPostDTO(post.updatePost(dto.toEntity()));
    }

    public LocalDateTime deleteOnePost(Long postId) {
        Post canFindPost = postRepository.findById(postId)
                .orElseThrow(() -> new BizException(PostCrudErrorCode.POST_NOT_FOUND));
        postRepository.delete(canFindPost);
        return LocalDateTime.now();
    }
}
