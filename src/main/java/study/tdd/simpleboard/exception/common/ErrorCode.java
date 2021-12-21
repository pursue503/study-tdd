package study.tdd.simpleboard.api.post;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMsg();
    Integer getBizCode();
    HttpStatus getHttpStatus();
}