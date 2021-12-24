package study.tdd.simpleboard.exception.post;

import org.springframework.validation.Errors;
import study.tdd.simpleboard.exception.common.ErrorCode;
import study.tdd.simpleboard.exception.common.InvalidParameterException;

/**
 * 게시물과 관련된 작업 중 발생하는 예외를 처리
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
public class InvalidPostParameterException extends InvalidParameterException {
    
    public InvalidPostParameterException(Errors errors, ErrorCode errorCode) {
        super(errors, errorCode);
    }

}
