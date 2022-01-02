package study.tdd.simpleboard.api.common;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDTO<T> {
    private final T data;
    private final String message;
    private final HttpStatus httpStatus;

    public ResponseDTO(T data, SuccessMessage message, HttpStatus httpStatus) {
        this.data = data;
        this.message = message.getSuccessMsg();
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
