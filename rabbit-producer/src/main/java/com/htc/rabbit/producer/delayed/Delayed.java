package com.htc.rabbit.producer.delayed;

import com.htc.rabbit.producer.dlx.DLX;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import java.io.IOException;
import java.util.*;

/**
 * @author: hutingcong
 * @date: 2020/5/16.
 * @descr: 延时队列的简单实现
 */
public class Delayed {

    private final static String NORMAL_DIRECT_EXCHANGE = "exchange.direct.normal";
    private final static String NORMAL_TOPIC_EXCHANGE = "exchange.topic.normal";
    private final static String DLX_Direct_EXCHANGE = "exchange.direct.dlx";
    private final static String DLX_TOPIC_EXCHANGE = "exchange.topic.dlx";

    /**
     * 4个业务队列
     */
    private final static String NORMAL_QUEUE_5s = "queue.normal.5s";
    private final static String NORMAL_QUEUE_10s = "queue.normal.10s";
    private final static String NORMAL_QUEUE_30s = "queue.normal.30s";
    private final static String NORMAL_QUEUE_1min = "queue.normal.1min";

    /**
     * 4个死信队列
     */
    private final static String DLX_QUEUE_5s = "queue.dlx.5s";
    private final static String DLX_QUEUE_10s = "queue.dlx.10s";
    private final static String DLX_QUEUE_30s = "queue.dlx.30s";
    private final static String DLX_QUEUE_1min = "queue.dlx.1min";

    /**
     * 4个死信交换机
     */
    private final static String DLX_EXCHANGE_5s = "exchange.dlx.5s";
    private final static String DLX_EXCHANGE_10s = "exchange.dlx.10s";
    private final static String DLX_EXCHANGE_30s = "exchange.dlx.30s";
    private final static String DLX_EXCHANGE_1min = "exchange.dlx.1min";

    private final static String ROUTING_KEY_5s = "routing.key.5s";
    private final static String ROUTING_KEY_10s = "routing.key.10s";
    private final static String ROUTING_KEY_30s = "routing.key.30s";
    private final static String ROUTING_KEY_1min = "routing.key.1min";

    /**
     * 这里的bingdingkey就是routingkey
     */
    private final static String BINDING_KEY_5s = "routing.key.5s";
    private final static String BINDING_KEY_10s = "routing.key.10s";
    private final static String BINDING_KEY_30s = "routing.key.30s";
    private final static String BINDING_KEY_1min = "routing.key.1min";


    private static CachingConnectionFactory factory;

    static {
        new Delayed();
    }

    public Delayed() {
        factory = new CachingConnectionFactory("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
    }

    public static CachingConnectionFactory getInstance() {
        return factory;
    }


    public static void direct() throws Exception {
        //创建一个信道,一个信道对应一个线程，
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);


        //创建topic业务交换机
        channel.exchangeDeclare(NORMAL_DIRECT_EXCHANGE, "direct", true);

        //创建topic死信交换机
        channel.exchangeDeclare(DLX_Direct_EXCHANGE, "direct", true);

        Map<String, Object> args = new HashMap<>();
        //指定死信路由键 ，因为死信交换机是fanout类型的，所以可以不指定路由键


        /**分别创建四个业务队列并绑定交换机 queue.normal.5s  queue.normal.10s  queue.normal.30s  queue.normal.51min */

        // 5s
        args.put("x-dead-letter-routing-key", ROUTING_KEY_5s);
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange", DLX_Direct_EXCHANGE);
        channel.queueDeclare(NORMAL_QUEUE_5s, true, false, false, args);
        // 业务交换机绑定业务队列queue.normal.5s，注意：由于是topic类型的交换机，所以这里的路由键规则必须设计合理，不然消息无法路由进来
        channel.queueBind(NORMAL_QUEUE_5s, NORMAL_DIRECT_EXCHANGE, ROUTING_KEY_5s);

        //10s
        args.put("x-dead-letter-routing-key", ROUTING_KEY_10s);
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", DLX_Direct_EXCHANGE);
        channel.queueDeclare(NORMAL_QUEUE_10s, true, false, false, args);
        channel.queueBind(NORMAL_QUEUE_10s, NORMAL_DIRECT_EXCHANGE, ROUTING_KEY_10s);

//        //30s
//        args.put("x-dead-letter-routing-key", ROUTING_KEY_30s);
//        args.put("x-message-ttl", 30000);
//        args.put("x-dead-letter-exchange", DLX_Direct_EXCHANGE);
//        channel.queueDeclare(NORMAL_QUEUE_30s, true, false, false, args);
//        channel.queueBind(NORMAL_QUEUE_30s, NORMAL_DIRECT_EXCHANGE, ROUTING_KEY_30s);
//
//        //1min
//        args.put("x-dead-letter-routing-key", ROUTING_KEY_1min);
//        args.put("x-message-ttl", 60000);
//        args.put("x-dead-letter-exchange", DLX_Direct_EXCHANGE);
//        channel.queueDeclare(NORMAL_QUEUE_1min, true, false, false, args);
//        channel.queueBind(NORMAL_QUEUE_30s, NORMAL_DIRECT_EXCHANGE, ROUTING_KEY_1min);


        /**分别创建四个死信队列并绑定死信交换机 queue.dlx.5s  queue.dlx.10s  queue.dlx.30s  queue.dlx.51min */

        // 5s
        channel.queueDeclare(DLX_QUEUE_5s, true, false, false, null);
        // 业务交换机绑定业务队列queue.normal.5s，注意：由于是topic类型的交换机，所以这里的路由键规则必须设计合理，不然消息无法路由进来
        channel.queueBind(DLX_QUEUE_5s, DLX_Direct_EXCHANGE, ROUTING_KEY_5s);

        //10s
        channel.queueDeclare(DLX_QUEUE_10s, true, false, false, null);
        channel.queueBind(NORMAL_QUEUE_10s, DLX_Direct_EXCHANGE, ROUTING_KEY_10s);

//        //30s
//        channel.queueDeclare(DLX_QUEUE_30s, true, false, false, null);
//        channel.queueBind(NORMAL_QUEUE_30s, DLX_Direct_EXCHANGE, ROUTING_KEY_30s);
//
//        //1min
//        channel.queueDeclare(DLX_QUEUE_1min, true, false, false, null);
//        channel.queueBind(NORMAL_QUEUE_30s, DLX_Direct_EXCHANGE, ROUTING_KEY_1min);


        //分别向四个业务队列发送10条消息
        channel.basicPublish(NORMAL_DIRECT_EXCHANGE, ROUTING_KEY_5s, MessageProperties.PERSISTENT_TEXT_PLAIN, "this is a message about delayed !".getBytes());
        channel.basicPublish(NORMAL_DIRECT_EXCHANGE, ROUTING_KEY_10s, MessageProperties.PERSISTENT_TEXT_PLAIN, "this is a message about delayed !".getBytes());


        channel.close();
        connection.close();
    }

    public static void topic() throws Exception {
        Map<String, Object> args = new HashMap<>();
        //创建一个信道,一个信道对应一个线程，
        Connection connection = factory.createConnection();
        Channel channel = connection.createChannel(false);

        channel.exchangeDeclare("exchange.direct.dlx.5s", "fanout", true);
        channel.exchangeDeclare("exchange.direct.dlx.10s", "fanout", true);
        channel.exchangeDeclare(NORMAL_TOPIC_EXCHANGE, "topic", true);

        /********************************************/

        args.put("x-dead-letter-routing-key", "5s");
        args.put("x-message-ttl", 5000);
        args.put("x-dead-letter-exchange", "exchange.direct.dlx.5s");
        channel.queueDeclare(NORMAL_QUEUE_5s, true, false, false, args);
        channel.queueBind(NORMAL_QUEUE_5s, NORMAL_TOPIC_EXCHANGE, "#.5s");

        args.put("x-dead-letter-routing-key", "10s");
        args.put("x-message-ttl", 10000);
        args.put("x-dead-letter-exchange", "exchange.topic.dlx.10s");
        channel.queueDeclare(NORMAL_QUEUE_10s, true, false, false, args);
        channel.queueBind(NORMAL_QUEUE_10s, NORMAL_TOPIC_EXCHANGE, "#.10s");

        /********************************************/


        // 5s
        channel.queueDeclare(DLX_QUEUE_5s, true, false, false, null);
        // 业务交换机绑定业务队列queue.normal.5s，注意：由于是topic类型的交换机，所以这里的路由键规则必须设计合理，不然消息无法路由进来
        channel.queueBind(DLX_QUEUE_5s, "exchange.direct.dlx.5s", "5s");

        //10s
        channel.queueDeclare(DLX_QUEUE_10s, true, false, false, null);
        channel.queueBind(NORMAL_QUEUE_10s, "exchange.direct.dlx.10s", "10s");

        //构建一个路由键集合,这里注意路由键的值和声明中的路由键有何区别！！！
        Set<String> normalRoutingKey = new HashSet<String>() {
            {
                add(ROUTING_KEY_5s);
                add(ROUTING_KEY_10s);
            }
        };

        channel.basicPublish(NORMAL_TOPIC_EXCHANGE, "10.5s", MessageProperties.PERSISTENT_TEXT_PLAIN, "this is topic delayed !".getBytes());
        channel.basicPublish(NORMAL_TOPIC_EXCHANGE, "10.10s", MessageProperties.PERSISTENT_TEXT_PLAIN, "this is topic delayed !".getBytes());

        channel.close();
        connection.close();

    }


    public static void main(String[] args) {
        try {
            direct();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}