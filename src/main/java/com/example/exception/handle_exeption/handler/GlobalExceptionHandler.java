package com.example.exception.handle_exeption.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.exception.handle_exeption.http.ApiResult;
import com.example.exception.handle_exeption.http.FieldErr;
import com.example.exception.handle_exeption.http.ResponseData;
import com.example.exception.handle_exeption.message.Message;
import com.example.exception.handle_exeption.message.MessageHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyUsedException.class)
    public ResponseEntity<ResponseData> handleAlreadyUsedException(AlreadyUsedException exception) {
        return ApiResult.failed(HttpStatus.BAD_REQUEST, exception.getMsg());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseData> handleBadRequestException(BadRequestException exception) {
        return ApiResult.failed(HttpStatus.BAD_REQUEST, exception.getMsg());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseData> handleNotFoundException(NotFoundException exception) {
        return ApiResult.failed(HttpStatus.NOT_FOUND, exception.getMsg());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseData> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest httpServletRequest) {
        log.error("Method handleHttpRequestMethodNotSupportedException(): Not found URL {}", httpServletRequest.getRequestURL());
        return ApiResult.failed(HttpStatus.METHOD_NOT_ALLOWED, MessageHelper.getMessage(exception.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseData> handleBindException(BindException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldErr> fieldErrs = result
                .getFieldErrors()
                .stream()
                .map(f -> new FieldErr(f.getField(), StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()))
                .collect(Collectors.toList());
        return ApiResult.failed(HttpStatus.BAD_REQUEST, MessageHelper.getMessage("Input field is not in the correct format!"), fieldErrs);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ResponseData> handleNumberFormatException(NumberFormatException exception) {
        log.error("Method handleNumberFormatException(): {}", exception.getMessage());
        return ApiResult.failed(HttpStatus.BAD_REQUEST, MessageHelper.getMessage(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Method handleMethodArgumentNotValidException(): {}", exception.getMessage());
        return ApiResult.failed(HttpStatus.BAD_REQUEST, MessageHelper.getMessage(exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseData> handleRuntimeException(RuntimeException exception) {
        log.error("Error status INTERNAL_SERVER_ERROR: ", exception);
        return ApiResult.failed(HttpStatus.INTERNAL_SERVER_ERROR, MessageHelper.getMessage(Message.Keys.I0002));
    }

}
