package study.tdd.simpleboard.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BizException extends RuntimeException {
    private final String message;
    private final Integer bizCode;
    private final HttpStatus httpStatus;

    public BizException(ErrorCode code) {
        super(code.getMsg(), new Throwable(code.getHttpStatus().getReasonPhrase()));
        this.message = code.getMsg();
        this.bizCode = code.getBizCode();
        this.httpStatus = code.getHttpStatus();
    }
}
