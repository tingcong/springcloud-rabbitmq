package com.htc.rabbit.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.htc.rabbit.constant.ExchangeName.DIRECT_EXCHANGE;
import static com.htc.rabbit.constant.QueueName.BUSSINESS_A_QUEUE;
import static com.htc.rabbit.constant.RoutingKey.DIRECT_ROUTING_KEY;


/**
 * @author: hutingcong
 * @date: 2020/5/16.
 * @descr:
 */
@Configuration
public class DirectConfig {


    @Bean(name = BUSSINESS_A_QUEUE)
    public Queue bussinessAQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        return new Queue(BUSSINESS_A_QUEUE, true, false, false);
    }

    /**
     * Direct交换机
     *
     * @return
     */
    @Bean(name = DIRECT_EXCHANGE)
    public DirectExchange directExchange() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    /**
     * 将队列和交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingDirect(@Qualifier(BUSSINESS_A_QUEUE) Queue queue, @Qualifier(DIRECT_EXCHANGE) DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DIRECT_ROUTING_KEY);
    }
}
