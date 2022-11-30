package com.example.exception.handle_exeption.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.exception.handle_exeption.message.Message;
import com.example.exception.handle_exeption.message.MessageHelper;

import java.util.List;

public class ApiResult {

    public static ResponseEntity<ResponseData> success() {
        return response(HttpStatus.OK, MessageHelper.getMessage(Message.Keys.I0001), null, null);
    }

    public static ResponseEntity<ResponseData> success(ResponseData data) {
        return response(HttpStatus.OK, MessageHelper.getMessage(Message.Keys.I0001), data, null);
    }

    public static ResponseEntity<ResponseData> createSuccess(ResponseData data) {
        return response(HttpStatus.CREATED, MessageHelper.getMessage(Message.Keys.I0001), data, null);
    }

    public static ResponseEntity<ResponseData> failed() {
        return response(HttpStatus.BAD_REQUEST, MessageHelper.getMessage(Message.Keys.I0002), null, null);
    }

    public static ResponseEntity<ResponseData> failed(Message message) {
        return response(HttpStatus.BAD_REQUEST, message, null, null);
    }

    public static ResponseEntity<ResponseData> failed(HttpStatus httpStatus, Message message) {
        return response(httpStatus, message, null, null);
    }

    public static ResponseEntity<ResponseData> failed(HttpStatus httpStatus, Message message, List<FieldErr> fieldErrs) {
        return response(httpStatus, message, null, fieldErrs);
    }

    public static ResponseEntity<ResponseData> response(HttpStatus httpStatus, Message message, ResponseData data, List<FieldErr> fieldErrs) {

        ResponseData responseData = new ResponseData();

        if (data == null) {
            responseData.setData(null);
        } else {
            responseData.setData(data.getData());
        }

        ErrorMessage errorMessage = new ErrorMessage();
        if (httpStatus.equals(HttpStatus.OK) || httpStatus.equals(HttpStatus.CREATED)) {
            responseData.setIsSuccess(true);
            errorMessage.setFieldErrs(null);

        } else {
            responseData.setIsSuccess(false);
            errorMessage.setFieldErrs(fieldErrs);
        }

        errorMessage.setText(message.getContent());
        responseData.setMessage(errorMessage);

        responseData.setHttpStatus(httpStatus);

        return ResponseEntity.status(httpStatus).body(responseData);
    }

}
