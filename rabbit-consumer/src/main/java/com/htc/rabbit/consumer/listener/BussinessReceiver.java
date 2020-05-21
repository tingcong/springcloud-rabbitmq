package com.htc.rabbit.consumer.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.htc.rabbit.constant.QueueName.BUSSINESS_QUEUE;

/**
 * @author: hutingcong
 * @date: 2020/5/22.
 * @descr: 监听业务队列
 */
@Slf4j
@RabbitListener(queues = BUSSINESS_QUEUE)
@Component
public class BussinessReceiver {
    @RabbitHandler
    public void handler(Message msg) {
        log.info("【业务队列消费者】reviecer: " + JSON.toJSONString(msg));
    }
}
