package com.example.exception.handle_exeption.handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.exception.handle_exeption.message.Message;

import javax.print.attribute.standard.Severity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private Message msg;

    private Severity severity;

    @JsonIgnore
    private Throwable rootCause;

}
