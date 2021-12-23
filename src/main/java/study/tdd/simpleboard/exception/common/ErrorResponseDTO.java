package study.tdd.simpleboard.exception.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ErrorResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("timestamp")
    private LocalDateTime now;
    private final String message;
    @JsonProperty("code")
    private final Integer errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("status")
    private HttpStatus httpStatus;
    /**
     * errors(BindingResult result)와 같은 방식을 통해 customFieldErrors에 값을 설정하면
     * errors라는 json key값이 에러처리 응답메시지에 포함되어 전달됩니다. 설정하지 않으면 errors json key값은 클라이언트에 전달되지 않습니다.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

    private ErrorResponseDTO(ErrorResponseDTO.ErrorResponseDTOBuilder builder) {
        if (builder.displayNow) this.now = LocalDateTime.now();
        this.message = builder.message;
        this.errorCode = builder.errorCode;
        this.httpStatus = builder.httpStatus;
        this.customFieldErrors = builder.customFieldErrors;
    }

    public static ErrorResponseDTO.ErrorResponseDTOBuilder builder() {
        return new ErrorResponseDTO.ErrorResponseDTOBuilder();
    }

    public static class ErrorResponseDTOBuilder {
        private boolean displayNow = true;
        private String message;
        private Integer errorCode;
        private HttpStatus httpStatus;
        private List<CustomFieldError> customFieldErrors;

        private ErrorResponseDTOBuilder() {}

        public ErrorResponseDTO.ErrorResponseDTOBuilder errorCode(Integer code) {
            this.errorCode = code;
            return this;
        }

        public ErrorResponseDTO.ErrorResponseDTOBuilder httpStatus(HttpStatus status) {
            this.httpStatus = status;
            return this;
        }

        public ErrorResponseDTO.ErrorResponseDTOBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * errors 메서드는 (@Valid 또는 @Validated)와 BindingResult 를 사용할 때만 사용해야 합니다.
         *
         * @param errors BindingResult.getFieldErrors() 메소드를 통해 전달받은 fieldErrors
         * @return 빌더 메서드 체이닝 ErrorResponseDTO
         */
        public ErrorResponseDTO.ErrorResponseDTOBuilder errors(Errors errors) {
            setCustomFieldErrors(errors.getFieldErrors());
            return this;
        }

        /**
         * BindingResult.getFieldErrors() 메소드를 통해 전달받은 fieldErrors
         */
        private void setCustomFieldErrors(List<FieldError> fieldErrors) {

            customFieldErrors = new ArrayList<>();

            for (FieldError fieldError : fieldErrors) {
                customFieldErrors.add(new CustomFieldError(
                        Objects.requireNonNull(fieldError.getCodes())[0].split("\\.")[2],
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()));
            }
        }

        /**
         * now (timestamp) 출력을 비활성화합니다.
         *
         * @return now (timestamp) false
         */
        public ErrorResponseDTO.ErrorResponseDTOBuilder offDisplayTimeStamp() {
            this.displayNow = false;
            return this;
        }

        public ErrorResponseDTO build() {
            return new ErrorResponseDTO(this);
        }
    }

    /**
     * # @Valid 또는 @Validated 에 의한 parameter 검증에 통과하지 못한 필드가 담긴 클래스.
     */
    @Getter
    @RequiredArgsConstructor
    public static class CustomFieldError {
        private final String rejectedParameter;
        private final Object rejectedValue;
        private final String reason;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}