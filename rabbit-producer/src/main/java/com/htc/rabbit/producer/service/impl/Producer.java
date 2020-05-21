package com.htc.rabbit.producer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.htc.rabbit.producer.config.BussinessConfig.BUSSINESS_QUEUE;

/**
 * @author: hutingcong
 * @date: 2020/5/16.
 * @descr:
 */
@Slf4j
@RestController
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/producer")
    public String send(String rabbitMsg) {
        log.info("生产者发送消息：{}", rabbitMsg);
        rabbitTemplate.convertAndSend(BUSSINESS_QUEUE, rabbitMsg);
        return "success";
    }
}
