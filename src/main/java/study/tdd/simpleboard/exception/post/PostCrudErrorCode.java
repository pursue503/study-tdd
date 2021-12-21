package study.tdd.simpleboard.api.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostCrudErrorCode implements ErrorCode {
    POST_TITLE_EXCEEDED(HttpStatus.BAD_REQUEST,  -1, "게시물 제목 길이가 초과되었습니다.");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    PostCrudErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
        this.httpStatus = httpStatus;
        this.bizCode = bizCode;
        this.msg = msg;
    }

}
