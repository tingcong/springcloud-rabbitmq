package com.htc.trace.rabbittrace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hutingcong
 * @date: 2020/5/21.
 * @descr:
 */
@RestController
public class DemoController {
    @GetMapping("/trace")
    public String trace(){
        return "success";
    }
}
