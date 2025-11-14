package com.example.JasonShaw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(required = false) String name) {

        if (name == null || name.isEmpty()) {
            return "Hello!!!";
        }

        return "Hello " + name + "!!!";
    }
}

