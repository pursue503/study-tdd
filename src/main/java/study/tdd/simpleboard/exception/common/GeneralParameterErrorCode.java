package study.tdd.simpleboard.api.post;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GeneralParameterErrorCode implements ErrorCode {
    INVALID_PARAMETER( HttpStatus.BAD_REQUEST, -999,"올바르지 않은 매개변수값이 전달되었습니다.");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    GeneralParameterErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
        this.httpStatus = httpStatus;
        this.bizCode = bizCode;
        this.msg = msg;
    }

}
