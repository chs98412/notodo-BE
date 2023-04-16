package com.notodo.notodo.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hello")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
