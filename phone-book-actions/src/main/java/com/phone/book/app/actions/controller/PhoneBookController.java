package com.phone.book.app.actions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneBookController {

    @GetMapping("/")
    public String index() {
        return "Test";
    }
}
