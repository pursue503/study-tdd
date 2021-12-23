package study.tdd.simpleboard.exception.common;

import org.springframework.http.HttpStatus;

/**
 * 에러 목록을 열거형으로 가지는 클래스들을 확장하기 위한 인터페이스. <br><br>
 * findMatchBizCode는 <br>
 * 열거형 중 하나의 msg 또는 httpStatus와 일치하는 값을 찾아 bizCode를 반환하도록 구현해야 합니다. <br>
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
public interface ErrorCode {
    String getMsg();
    Integer getBizCode();
    HttpStatus getHttpStatus();
    Integer findMatchBizCode(String failMessage);
}