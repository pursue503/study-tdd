package study.tdd.simpleboard.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Arrays;

@Getter
public enum GeneralParameterErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, -999, "매개변수가 충분히 전달되지 못했거나 올바르지 않은 매개변수 값이 전달되었습니다.");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    GeneralParameterErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
        this.httpStatus = httpStatus;
        this.bizCode = bizCode;
        this.msg = msg;
    }

    public Integer findMatchBizCode(String failMessage) {
        return Arrays.stream(GeneralParameterErrorCode.values())
                .filter(errorCode -> (errorCode.msg).equals(failMessage))
                .map(GeneralParameterErrorCode::getBizCode)
                .findAny()
                .orElse(-999);
    }
}
