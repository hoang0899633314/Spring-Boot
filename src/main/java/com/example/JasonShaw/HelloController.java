package com.example.JasonShaw;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/greeting") // API, endpoint
    public String hello(@RequestParam(defaultValue = "") String name,
                        @RequestParam(defaultValue = "Đà Nẵng") String address) {
        return "Hello " + name + "," + address;
    }

    @RequestMapping("/hello") // API, endpoint
    public String hello2() {
        return "Hello Jason";
    }
}
