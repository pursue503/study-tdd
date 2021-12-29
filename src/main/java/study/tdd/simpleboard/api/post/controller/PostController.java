package study.tdd.simpleboard.api.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.tdd.simpleboard.api.common.ResponseDTO;
import study.tdd.simpleboard.api.post.domain.PageResponseDTO;
import study.tdd.simpleboard.api.post.domain.PostOneDTO;
import study.tdd.simpleboard.api.post.domain.PostSaveRequestDTO;
import study.tdd.simpleboard.api.post.entity.Post;
import study.tdd.simpleboard.api.post.service.PostService;
import study.tdd.simpleboard.exception.post.InvalidPostParameterException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import javax.validation.Valid;

/**
 * 게시물과 관련된 작업 요청을 처리하는 컨트롤러
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    /**
     * 게시물 저장 요청을 받아 저장 처리후 반환값으로 저장된 게시물의 postId를 반환합니다.
     *
     * @param dto    게시물 제목, 게시물 내용, 이미지 주소 (선택사항)
     * @param result 제목 및 내용에 대한 규칙 검증 결과
     * @return 성공적으로 저장된 게시물의 고유 아이디
     */
    @PostMapping("/posts")
    public ResponseDTO<Long> savePost(@Valid @RequestBody PostSaveRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) throw new InvalidPostParameterException(result, PostCrudErrorCode.POST_CRUD_FAIL);
        return new ResponseDTO<>(postService.savePost(dto), "게시물이 잘 저장되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseDTO<PostOneDTO> findOnePost(@PathVariable Long postId) {
        return new ResponseDTO<>(postService.findOnePost(postId), "게시물이 잘 조회되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseDTO<PageResponseDTO> pagingPost(@RequestParam int page) {
        return new ResponseDTO<>(postService.findPostsPage(page), "게시물이 잘 조회되었습니다.", HttpStatus.OK);
    }
}
