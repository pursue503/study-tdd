package study.tdd.simpleboard.api.post.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

}
