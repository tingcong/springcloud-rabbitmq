package com.htc.rabbit.producer.dlx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hutingcong
 * @date: 2020/5/21.
 * @descr: 死信队列配置
 */
@Configuration
public class DeadLetterConfig {
    public final static String DLX_EXCHANGE = "htc.dlx.exchange";
    public final static String DLX_QUEUE = "htc.dlx.queue";
    public final static String DLX_ROUTING_KEY = "htc.dlx.routing.key";

    /**
     * 声明死信交换机
     *
     * @return
     */
    @Bean(name = DLX_EXCHANGE)
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE, true, false);
    }

    /**
     * 声明死信队列
     *
     * @return
     */
    @Bean(name = DLX_QUEUE)
    public Queue dlxQueue() {
        return new Queue(DLX_QUEUE);
    }

    /**
     * 死信队列绑定死信交换机
     *
     * @return
     */
    @Bean
    public Binding dlxBind(@Qualifier(DLX_EXCHANGE) DirectExchange exchange, @Qualifier(DLX_QUEUE) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(DLX_ROUTING_KEY);
    }
}
