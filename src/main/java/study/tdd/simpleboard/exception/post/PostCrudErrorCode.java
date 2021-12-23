package study.tdd.simpleboard.exception.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.tdd.simpleboard.exception.common.ErrorCode;

@Getter
public enum PostCrudErrorCode implements ErrorCode {
    POST_TITLE_IS_NULL(HttpStatus.BAD_REQUEST, -1, "{post.title.not_null}"),
    POST_TITLE_IS_EMPTY(HttpStatus.BAD_REQUEST, -2, "{post.title.not_empty}"),
    POST_CONTENT_IS_NULL(HttpStatus.BAD_REQUEST, -3, "{post.title.max_length_exceeded}"),
    POST_CONTENT_IS_EMPTY(HttpStatus.BAD_REQUEST, -4, "{post.content.not_null}"),
    POST_TITLE_IS_EXCEEDED(HttpStatus.BAD_REQUEST, -5, "{post.content.not_empty}"),
    POST_CONTENT_IS_EXCEEDED(HttpStatus.BAD_REQUEST, -6, "{post.content.max_length_exceeded}");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    PostCrudErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
        this.httpStatus = httpStatus;
        this.bizCode = bizCode;
        this.msg = msg;
    }

}
