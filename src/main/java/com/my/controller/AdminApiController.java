package com.my.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author hutf
 * @createTime 2021年07月27日 23:18:00
 */
@RestController
@RequestMapping("/admin/api")
public class AdminApiController {


    @GetMapping("/ok")
    public String adminOK() {
        String result = UUID.randomUUID().toString();
        return "this is admin/api/ok result :" +  result;
    }


}
