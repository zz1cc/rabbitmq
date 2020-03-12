package com.example.mq_producer.整合spring.util;

public interface MqName {
    //交换机名称
    public static final String EXCHANGE_NAME = "direct_exchange";

    //队列名称
    public static final String QUEUE_EMAIL = "queue_email";
    public static final String QUEUE_MESSAGE = "queue_message";

    //routingKey名称
    public static final String ROUTING_EMAIL = "routing_email";
    public static final String ROUTING_MESSAGE = "routing_message";
}
