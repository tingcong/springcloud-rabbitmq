package com.htc.rabbit.constant;

/**
 * @author: hutingcong
 * @date: 2020/5/16.
 * @descr:
 */
public interface RoutingKey {
    /**
     * 业务路由键
     */
    public final static String BUSSINESS_ROUTING_KEY = "htc.bussiness.routing.key";

    /**
     * 死信路由键
     */
    public final static String DLX_ROUTING_KEY = "htc.dlx.routing.key";

}
