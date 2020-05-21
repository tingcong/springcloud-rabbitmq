package com.htc.rabbit.rabbitsleuth.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@SpringBootApplication
public class RabbitSleuthMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitSleuthMqApplication.class, args);
    }

    @GetMapping("/sleuth/mq")
    public String demo() {
        return "success";
    }

}
