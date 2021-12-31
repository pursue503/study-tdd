package study.tdd.simpleboard.exception.jwt;

import org.springframework.http.HttpStatus;
import study.tdd.simpleboard.exception.common.ErrorCode;

public enum JWTErrorCode implements ErrorCode {

    JWT_ACCESS_TOKEN_TIME_EXPIRED(HttpStatus.UNAUTHORIZED, 0, "AccessToken 시간이 만료 되었습니다."),
    JWT_REFRESH_TOKEN_TIME_EXPIRED(HttpStatus.UNAUTHORIZED, 1 , "RefreshToken 시간이 만료 되었습니다."),
    JWT_VALID_NOT_SIGNATURE(HttpStatus.UNAUTHORIZED, 2, "토큰의 서명값이 올바르지 않습니다."),
    JWT_VALID_NOT_INCORRECT_CLAIM(HttpStatus.UNAUTHORIZED, 3 , "토큰의 필수 클레임값이 존재 하지 않습니다.");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    JWTErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
        this.httpStatus = httpStatus;
        this.bizCode = bizCode;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getBizCode() {
        return bizCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Integer findMatchBizCode(String failMessage) {
        return null;
    }
}
