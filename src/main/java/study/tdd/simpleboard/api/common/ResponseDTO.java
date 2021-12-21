package study.tdd.simpleboard.api.post;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ResponseDTO<T> {
    private final T data;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
