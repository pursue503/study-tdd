package study.tdd.simpleboard.exception.common;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public abstract class InvalidParameterException extends BizException {

    private final Errors errors;

    public InvalidParameterException(Errors errors) {
        super(GeneralParameterErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }
}
