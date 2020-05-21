package com.htc.rabbit.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.htc.rabbit.constant.ExchangeName.BUSSINESS_EXCHANGE;
import static com.htc.rabbit.constant.ExchangeName.DLX_EXCHANGE;
import static com.htc.rabbit.constant.QueueName.BUSSINESS_QUEUE;
import static com.htc.rabbit.constant.RoutingKey.BUSSINESS_ROUTING_KEY;
import static com.htc.rabbit.constant.RoutingKey.DLX_ROUTING_KEY;

/**
 * @author: hutingcong
 * @date: 2020/5/21.
 * @descr:
 */
@Configuration
public class BussinessConfig {

    /**
     * 声明业务交换机
     *
     * @return
     */
    @Bean(name = BUSSINESS_EXCHANGE)
    public FanoutExchange bussinessExchange() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        return new FanoutExchange(BUSSINESS_EXCHANGE, true, false);
    }

    /**
     * 声明业务队列
     *
     * @return
     */
    @Bean(name = BUSSINESS_QUEUE)
    public Queue queue() {

        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。

        Map<String, Object> args = new HashMap<String, Object>();
        //设置队列的ttl, 时间单位为毫秒
        args.put("x-message-ttl", 10000);
        //指定死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        //指定死信路由键
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return new Queue(BUSSINESS_QUEUE, true, false, false, args);
    }

    /**
     * 业务队列绑定业务交换机
     *
     * @return
     */
    @Bean
    public Binding bind() {
        return new Binding(BUSSINESS_QUEUE, Binding.DestinationType.QUEUE, BUSSINESS_EXCHANGE, BUSSINESS_ROUTING_KEY, null);
    }
}
