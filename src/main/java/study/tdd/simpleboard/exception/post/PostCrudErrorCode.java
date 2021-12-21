package study.tdd.simpleboard.exception.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.tdd.simpleboard.exception.common.ErrorCode;

@Getter
public enum PostCrudErrorCode implements ErrorCode {
    ;

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    PostCrudErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
        this.httpStatus = httpStatus;
        this.bizCode = bizCode;
        this.msg = msg;
    }

}
