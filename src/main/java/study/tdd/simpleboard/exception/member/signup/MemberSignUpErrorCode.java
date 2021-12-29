package study.tdd.simpleboard.exception.member.signup;

import org.springframework.http.HttpStatus;
import study.tdd.simpleboard.exception.common.ErrorCode;

public enum MemberSignUpErrorCode implements ErrorCode {
    SIGN_UP_PARAM_NULL_OR_EMPTY(HttpStatus.BAD_REQUEST, 0, "필수 입력 항목을 입력해주세요."),
    SIGN_UP_NICKNAME_NOT_VALID(HttpStatus.BAD_REQUEST, -1, "닉네임 조건이 맞지 않습니다, (최소 3자 이상, 알파벳 대소문자 1개 포함, 숫자 0~9 1개 포함)"),
    SIGN_UP_PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, -2, "비밀번호 조건이 맞지 않습니다. (최소 8자 이상, 대문자 1개 포함, 특수문자 1개 포함, 숫자 1개 포함)"),
    SIGN_UP_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, 0 , "중복된 닉네임 입니다.");

    private final HttpStatus httpStatus;
    private final Integer bizCode;
    private final String msg;

    MemberSignUpErrorCode(HttpStatus httpStatus, Integer bizCode, String msg) {
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
