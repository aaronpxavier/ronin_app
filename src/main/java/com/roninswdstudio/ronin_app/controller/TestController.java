package com.roninswdstudio.ronin_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


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
