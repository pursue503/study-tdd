package study.tdd.simpleboard.api.post;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Getter
public class InvalidParameterException extends BizException {

    private final String fieldErrorMsg;

    public InvalidParameterException(List<FieldError> fieldErrors) {
        super(GeneralParameterErrorCode.INVALID_PARAMETER);
        this.fieldErrorMsg = fieldErrors.stream()
                                        .map(FieldError::getDefaultMessage)
                                        .collect(joining(""));
    }
}
