package study.tdd.simpleboard.exception.common;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMsg();
    Integer getBizCode();
    HttpStatus getHttpStatus();
}