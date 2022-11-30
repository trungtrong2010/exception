package com.example.exception.handle_exeption.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private Keys id;
    private String content;

    @Override
    public String toString() {
        return content;
    }

    public enum Keys {
        I0001,
        I0002,
    }

}
