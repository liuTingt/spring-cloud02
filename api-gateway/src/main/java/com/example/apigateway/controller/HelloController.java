package com.example.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/local/hello",method = RequestMethod.GET)
    public String hello() {
        return  "Hello Word local";
    }
}
