package com.demo.studycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public String home() {
        System.out.println("*** test: 홈으로");
        return "index";
    }

//    @GetMapping("check")
//    public String check() {
//        System.out.println("** 홈으로 **");
//        return "success";
//    }
}
