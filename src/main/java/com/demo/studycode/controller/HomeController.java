package com.demo.studycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class HomeController {
//    @GetMapping("/")
//    public String home(Model model) {
//        System.out.println("*** test: 홈으로");
//        return "home";
//    }

    @GetMapping("check")
    public String check() {
        return "Success";
    }
}
