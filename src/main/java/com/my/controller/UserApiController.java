package com.my.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author hutf
 * @createTime 2021年07月27日 23:20:00
 */
@RestController
@RequestMapping("/user/api")
public class UserApiController {

    @GetMapping("/ok")
    public String okMethod() {
        return "this is user/api/ok result :" + UUID.randomUUID().toString();
    }
}
