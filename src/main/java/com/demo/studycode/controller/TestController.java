package com.demo.studycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @PostMapping(value="/test")
    public Map<Integer, String> test(@RequestBody List<String> params) {
        Map<Integer,String> data = new HashMap<>();
        data.put(1,"사과");
        data.put(2,"포도");
        data.put(3,"바나나");

        int i=4;
        for(String param:params) {
            data.put(i, param);
            i++;
        }
        return data;
    }
}

