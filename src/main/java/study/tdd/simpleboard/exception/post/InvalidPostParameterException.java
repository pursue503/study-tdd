package study.tdd.simpleboard.exception.post;

import org.springframework.validation.Errors;
import study.tdd.simpleboard.exception.common.ErrorCode;
import study.tdd.simpleboard.exception.common.InvalidParameterException;

public class InvalidPostParameterException extends InvalidParameterException {
    
    public InvalidPostParameterException(Errors errors, ErrorCode errorCode) {
        super(errors, errorCode);
    }

}
