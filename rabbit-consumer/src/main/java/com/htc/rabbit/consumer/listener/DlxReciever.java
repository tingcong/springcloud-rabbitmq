package com.htc.rabbit.consumer.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.htc.rabbit.constant.QueueName.DLX_QUEUE;

/**
 * @author: hutingcong
 * @date: 2020/5/22.
 * @descr:
 */
@Slf4j
@Component
@RabbitListener(queues = DLX_QUEUE)
public class DlxReciever {

    @RabbitHandler
    public void handler(Message msg) {
        log.info("【死信队列消费者】reciever:" + JSON.toJSONString(msg));
    }
}
