package study.tdd.simpleboard.api.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public ResponseDTO<PostSaveRequestDTO> savePost(@Valid PostSaveRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) throw new InvalidParameterException(result.getFieldErrors());
        // TODO: postService.save(dto)
        return new ResponseDTO<>(null, "게시물이 잘 저장되었습니다.", HttpStatus.OK);
    }


}
