package com.example.exception.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import com.example.exception.dto.TestDTO;
import com.example.exception.handle_exeption.handler.AppException;
import com.example.exception.handle_exeption.handler.BadRequestException;
import com.example.exception.handle_exeption.handler.NotFoundException;
import com.example.exception.handle_exeption.http.ApiResult;
import com.example.exception.handle_exeption.http.ResponseData;
import com.example.exception.handle_exeption.message.Message;
import com.example.exception.handle_exeption.message.MessageHelper;

import javax.validation.Valid;

@RestController
public class ProductController {

    // test return result when call API
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> hello(@PathVariable("id") Long id) {

        if (id == 1) {
            throw new AppException(MessageHelper.getMessage("NotFoundException"), new Throwable());
        } else if (id == 2) {
            throw new NotFoundException(MessageHelper.getMessage("NotFoundException"), new Throwable());
        } else if (id == 3) {
            throw new BadRequestException(MessageHelper.getMessage("BadRequestException"), new Throwable());
        } else if (id == 4) {
            throw new BadRequestException(MessageHelper.getMessage(Message.Keys.I0001), new Throwable());
        }
        return ApiResult.success();
    }

    // test auto catch handle method
    @PostMapping
    public ResponseEntity<ResponseData> test(@Valid @RequestBody TestDTO testDTO) {
        ResponseData responseData = new ResponseData();
        responseData.addData("testDTO", testDTO);
        return ApiResult.success(responseData);
    }

}