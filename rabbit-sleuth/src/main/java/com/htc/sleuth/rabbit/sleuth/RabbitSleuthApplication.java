package com.htc.sleuth.rabbit.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
@RestController
public class RabbitSleuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitSleuthApplication.class, args);
    }

    @GetMapping("/sleuth")
    public String sleuth() {
        return "success";
    }

}
