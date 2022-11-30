package com.example.exception.handle_exeption.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Getter
@Setter
public class ResponseData {

    private Boolean isSuccess;
    private ErrorMessage errorMessage;
    private HttpStatus statusCode;
    private HashMap<String, Object> data;

    public ResponseData() {
        this.data = new HashMap<>();
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

}
