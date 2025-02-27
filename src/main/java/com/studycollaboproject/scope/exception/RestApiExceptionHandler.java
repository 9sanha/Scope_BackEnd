package com.studycollaboproject.scope.exception;

import com.studycollaboproject.scope.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {RestApiException.class})
    public ResponseEntity<Object> handleRuntimeErrorException(RestApiException ex) {
        log.info("400 Bad Request Error, [{}], message={}", MDC.get("UUID"), ex.getMessage());
        ResponseDto restApiException = new ResponseDto("400", ex.getMessage(), "");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleApiRequestErrorException(MethodArgumentNotValidException ex) {
        String msg = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        log.info("400 Bad Request Parameter Error, [{}], message={}", MDC.get("UUID"), msg);
        ResponseDto restApiException = new ResponseDto("400", msg, "");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleBadRequestErrorException(RuntimeException ex) {
        String msg = ErrorCode.INVALID_INPUT_ERROR.getMessage();
        log.info("400 Bad Request Parameter Error, [{}], message={}", MDC.get("UUID"), ex.getMessage());
        ResponseDto restApiException = new ResponseDto("400", msg, "");
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }
}
