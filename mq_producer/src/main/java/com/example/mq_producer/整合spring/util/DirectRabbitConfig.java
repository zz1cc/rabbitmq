package com.example.mq_producer.整合spring.util;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig implements MqName{

    @Bean(EXCHANGE_NAME)
    public Exchange exchange () {
        // durable(true) 持久化交换机，mq重启也还在
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明队列
     *   new Queue(QUEUE_EMAIL,true,false,false)
     *   durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
     *   auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     *   exclusive  表示该消息队列是否只在当前connection生效,默认是false
     * @return
     */
    @Bean(QUEUE_EMAIL)
    public Queue emailQueue () {
        return new Queue(QUEUE_EMAIL);
    }
    @Bean(QUEUE_MESSAGE)
    public Queue messageQueue () {
        return new Queue(QUEUE_MESSAGE);
    }

    /**
     * 队列绑定交换机，指定routingkey
     */
    public Binding bindEmail (@Qualifier(QUEUE_EMAIL) Queue queue,
                              @Qualifier(EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_EMAIL).noargs();
    }
    public Binding bindMessage (@Qualifier(QUEUE_MESSAGE) Queue queue,
                              @Qualifier(EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_MESSAGE).noargs();
    }

}