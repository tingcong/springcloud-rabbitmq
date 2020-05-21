package com.htc.rabbit.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.htc.rabbit.producer.dlx.DeadLetterConfig.*;

/**
 * @author: hutingcong
 * @date: 2020/5/21.
 * @descr:
 */
@Configuration
public class BussinessConfig {

    public final static String BUSSINESS_EXCHANGE = "htc.bussiness.exchange";
    public final static String BUSSINESS_QUEUE = "htc.bussiness.queue";
    public final static String BUSSINESS_ROUTING_KEY = "htc.bussiness.routing.key";

    /**
     * 声明业务交换机
     * @return
     */
    @Bean(name = BUSSINESS_EXCHANGE)
    public FanoutExchange bussinessExchange() {
        return new FanoutExchange(BUSSINESS_EXCHANGE);
    }

    /**
     * 声明业务队列
     * @return
     */
    @Bean(name = BUSSINESS_QUEUE)
    public Queue queue(){
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
     * @return
     */
    @Bean
    public Binding bind(){
        return new Binding(BUSSINESS_QUEUE, Binding.DestinationType.QUEUE, BUSSINESS_EXCHANGE, BUSSINESS_ROUTING_KEY, null);
    }
}
