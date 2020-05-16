package com.htc.rabbit.producer.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import java.util.HashMap;
import java.util.Map;


/**
 * @author: hutingcong
 * @date: 2020/5/16.
 * @descr: 死信队列的简单实现
 */
public class DLX {
    private final static String DLX_EXCHANGE = "exchange.dlx";
    private final static String NORMAL_EXCHANGE = "exchange.normal";

    private final static String DLX_ROUTING_KEY = "dlx.routing.key";

    private final static String NORMAL_QUEUE = "queue.normal";
    private final static String DLX_QUEUE = "queue.dlx";


    private static CachingConnectionFactory factory;

    static {
        new DLX();
    }

    public DLX() {
        factory = new CachingConnectionFactory("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
    }

    public static CachingConnectionFactory getInstance() {
        return factory;
    }


    public static void dlx() throws Exception {
        //创建一个信道,一个信道对应一个线程，
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);


        //创建业务交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, "fanout", true);

        //创建死信交换机
        channel.exchangeDeclare(DLX_EXCHANGE, "direct", true);

        Map<String, Object> args = new HashMap<>();
        //设置队列的ttl, 时间单位为毫秒
        args.put("x-message-ttl", 10000);
        //指定死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        //指定死信路由键
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        //创建一个配置了死信交换机的业务队列
        channel.queueDeclare(NORMAL_QUEUE, true, false, false, args);
        //将业务队列绑定到业务交换机，因为业务交换机类型为fanout，所以无需指定路由键
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "");

        //创建死信队列
        channel.queueDeclare(DLX_QUEUE, true, false, false, null);
        //将死信队列绑定到死信交换机并指定路由键,死信交换机类型为direct，（注意这里的路由键要和业务队列中arguments参数中配置的路由键一致，不然会导致死信交换机找不到匹配的死信队列，然后丢失消息）
        channel.queueBind(DLX_QUEUE, DLX_EXCHANGE, DLX_ROUTING_KEY);

        channel.basicPublish(NORMAL_EXCHANGE, "aaaaa", MessageProperties.PERSISTENT_TEXT_PLAIN, "this is a message about dlx !".getBytes());

        channel.close();
        connection.close();
    }

    public static void main(String[] args) {
        try {
            dlx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
