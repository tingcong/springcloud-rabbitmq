package com.htc.euraka.euraka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RabbitEurakaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitEurakaApplication.class, args);
    }

}
