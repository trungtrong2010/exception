package com.example.exception.handle_exeption.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.exception.handle_exeption.http.ApiResult;
import com.example.exception.handle_exeption.http.FieldErr;
import com.example.exception.handle_exeption.http.ResponseData;
import com.example.exception.handle_exeption.message.Message;
import com.example.exception.handle_exeption.message.MessageHelper;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseData> handleRuntimeException(RuntimeException exception) {
        return ApiResult.response(HttpStatus.INTERNAL_SERVER_ERROR, MessageHelper.getMessage(Message.Keys.I0002), null, null);
    }

    @ResponseBody
    @ExceptionHandler(AlreadyUsedException.class)
    public ResponseEntity<ResponseData> handleAlreadyUsedException(AlreadyUsedException exception) {
        return ApiResult.response(HttpStatus.BAD_REQUEST, exception.getMsg(), null, null);
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseData> handleBadRequestException(BadRequestException exception) {
        return ApiResult.response(HttpStatus.BAD_REQUEST, exception.getMsg(), null, null);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseData> handleNotFoundException(NotFoundException exception) {
        return ApiResult.response(HttpStatus.NOT_FOUND, exception.getMsg(), null, null);
    }

    @ResponseBody
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseData> handleAppException(AppException exception) {
        return ApiResult.response(HttpStatus.NOT_FOUND, exception.getMsg(), null, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseData> handleBindException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldErr> fieldErrs = result
                .getFieldErrors()
                .stream()
                .map(f -> new FieldErr(f.getField(), StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()))
                .collect(Collectors.toList());
        return ApiResult.response(HttpStatus.BAD_REQUEST, MessageHelper.getMessage("Input field fail!"), null, fieldErrs);
    }

}
