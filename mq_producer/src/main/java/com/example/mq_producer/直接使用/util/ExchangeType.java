package com.example.mq_producer.直接使用.util;

/**
 * 交换机类型
 * Fanout：广播，将消息交给所有绑定到交换机的队列
 * Direct：定向，把消息交给符合指定routing key 的队列
 * Topic：通配符，把消息交给符合routing pattern（路由模式） 的队列
 * Header：header模式与routing不同的地方在于，header模式取消routingkey，
 * 使用header中的 key/value（键值对）匹配队列。
 * Header模式不展开了，感兴趣可以参考这篇文章
 * https://blog.csdn.net/zhu_tianwei/article/details/40923131
 */
public class ExchangeType {

    public static final String FANOUT = "fanout";
    public static final String DIRECT = "direct";
    public static final String TOPIC = "topic";
    public static final String HEADER = "header";

}
