package com.htc.euraka.euraka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaServer
@SpringBootApplication(exclude = RabbitAutoConfiguration.class)
public class RabbitEurakaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitEurakaApplication.class, args);
    }

}
