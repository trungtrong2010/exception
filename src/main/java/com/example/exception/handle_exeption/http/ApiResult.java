package com.example.exception.handle_exeption.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.exception.handle_exeption.message.Message;
import com.example.exception.handle_exeption.message.MessageHelper;

import java.util.List;

public class ApiResult {

    public static ResponseEntity<ResponseData> success() {
        return response(HttpStatus.OK, MessageHelper.getMessage(Message.Keys.I0001), new ResponseData(), null);
    }

    public static ResponseEntity<ResponseData> success(ResponseData data) {
        return response(HttpStatus.OK, MessageHelper.getMessage(Message.Keys.I0001), data, null);
    }

    public static ResponseEntity<ResponseData> createSuccess(ResponseData data) {
        return response(HttpStatus.CREATED, MessageHelper.getMessage(Message.Keys.I0001), data, null);
    }

    public static ResponseEntity<ResponseData> failed() {
        return response(HttpStatus.BAD_REQUEST, MessageHelper.getMessage(Message.Keys.I0002), new ResponseData(), null);
    }

    public static ResponseEntity<ResponseData> failed(Message message) {
        return response(HttpStatus.BAD_REQUEST, message, new ResponseData(), null);
    }


    public static ResponseEntity<ResponseData> failed(Message message, List<FieldErr> fieldErrs) {
        return response(HttpStatus.BAD_REQUEST, message, new ResponseData(), fieldErrs);
    }

    public static ResponseEntity<ResponseData> failed(List<FieldErr> fieldErrs) {
        return response(HttpStatus.BAD_REQUEST, null, new ResponseData(), fieldErrs);
    }

    public static ResponseEntity<ResponseData> response(
            HttpStatus httpStatus,
            Message message,
            ResponseData data,
            List<FieldErr> fieldErrs
    ) {
        if (data == null) {
            data = new ResponseData();
        }
        if (httpStatus.equals(HttpStatus.OK) || httpStatus.equals(HttpStatus.CREATED)) {
            data.setStatusCode(httpStatus);
            data.setIsSuccess(true);
        } else {
            data.setIsSuccess(false);
            ErrorMessage errorMessage = new ErrorMessage();

            errorMessage.setMessage(message.getContent());

            errorMessage.setFieldErrs(fieldErrs);

            data.setStatusCode(httpStatus);
            data.setErrorMessage(errorMessage);
            data.setData(null);
        }
        return ResponseEntity.status(httpStatus).body(data);
    }

}
