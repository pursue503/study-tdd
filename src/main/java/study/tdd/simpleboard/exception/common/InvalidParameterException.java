package study.tdd.simpleboard.exception.common;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public abstract class InvalidParameterException extends BizException {

    private final Errors errors;
    private final ErrorCode errorCode;

    public InvalidParameterException(Errors errors, ErrorCode errorCode) {
        super(GeneralParameterErrorCode.INVALID_PARAMETER);
        this.errors = errors;
        this.errorCode = errorCode;
    }
}
