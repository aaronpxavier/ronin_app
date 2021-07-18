package com.roninswdstudio.ronin_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestController {
    @GetMapping("/admin")
    public String admin () {
        return "<h1>Hello Admin</h1>";
    }

    @GetMapping("/user")
    public String user () {
        return "<h1>Hello User</h1>";
    }

    @GetMapping("/")
    public String home() {
        return "<h1>hello</h1>";
    }

}
