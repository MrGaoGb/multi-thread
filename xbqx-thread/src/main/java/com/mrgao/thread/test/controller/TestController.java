package com.mrgao.thread.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/3 22:38
 */
@RestController
public class TestController {


    @GetMapping(value = "/test/funOne", produces = MediaType.APPLICATION_JSON_VALUE)
    public String funOne() {
        return "fun one";
    }

    @GetMapping("/test/funTwo")
    public ResponseEntity funTwo() {
        ResponseEntity responseEntity = new ResponseEntity("fun two", HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
