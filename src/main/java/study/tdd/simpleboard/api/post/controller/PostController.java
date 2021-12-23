package study.tdd.simpleboard.api.post.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.tdd.simpleboard.api.common.ResponseDTO;
import study.tdd.simpleboard.api.post.domain.PostSaveRequestDTO;
import study.tdd.simpleboard.exception.post.InvalidPostParameterException;

import javax.validation.Valid;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public ResponseDTO<PostSaveRequestDTO> savePost(@Valid @RequestBody PostSaveRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) throw new InvalidPostParameterException(result);
        // TODO: postService.save(dto)
        return new ResponseDTO<>(null, "게시물이 잘 저장되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseDTO<PostResponseDTO> findOnePost(@PathVariable Long id) {
        return new ResponseDTO<>(new PostResponseDTO(), "게시물이 잘 조회되었습니다.", HttpStatus.OK);
    }
}
