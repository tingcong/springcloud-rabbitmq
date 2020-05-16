package com.htc.rabbit.producer.service.impl;

import com.htc.rabbit.domain.RabbitMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/send")
    public void send(String rabbitMsg) {
        log.info("生产者发送消息：{}", rabbitMsg);
        rabbitTemplate.convertAndSend(rabbitMsg);
    }
}
