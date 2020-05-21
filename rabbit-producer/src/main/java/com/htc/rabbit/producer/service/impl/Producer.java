package com.htc.rabbit.producer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.htc.rabbit.constant.QueueName.BUSSINESS_QUEUE;

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

    /**
     * 业务队列
     * @param rabbitMsg
     * @return
     */
    @GetMapping("/producer")
    public String producer(String rabbitMsg) {
        log.info("生产者发送消息：{}", rabbitMsg);
        rabbitTemplate.convertAndSend(BUSSINESS_QUEUE, rabbitMsg);
        return "success";
    }
}
