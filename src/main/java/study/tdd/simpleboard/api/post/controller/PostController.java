package study.tdd.simpleboard.api.post.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.tdd.simpleboard.api.common.ResponseDTO;
import study.tdd.simpleboard.api.post.domain.PostSaveRequestDTO;
import study.tdd.simpleboard.exception.post.InvalidPostParameterException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

import javax.validation.Valid;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public ResponseDTO<PostSaveRequestDTO> savePost(@Valid @RequestBody PostSaveRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) throw new InvalidPostParameterException(result, PostCrudErrorCode.POST_CRUD_FAIL);
        // TODO: postService.save(dto)
        return new ResponseDTO<>(null, "게시물이 잘 저장되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseDTO<PostResponseDTO> findOnePost(@PathVariable Long postId) {
        return new ResponseDTO<>(new PostResponseDTO(), "게시물이 잘 조회되었습니다.", HttpStatus.OK);
    }

    /* TODO: TEST 코드를 먼저 작성한 뒤에 재활성화
    @GetMapping("/posts")
    public ResponseDTO<PostResponseDTO> findPagingPostList(@RequestParam Long page) {
        return new ResponseDTO<>(new PostResponseDTO(), "게시물이 잘 조회되었습니다.", HttpStatus.OK);
    }
    */
}
