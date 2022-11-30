package com.example.exception.handle_exeption.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErr {

    private String field;

    private String message;

    public FieldErr() {
        super();
    }

    public FieldErr(String field, String message) {
        super();
        this.field = field;
        this.message = message;
    }

}
