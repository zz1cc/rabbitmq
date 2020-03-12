package com.example.mq_producer.整合spring.rabbitConfig;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * https://www.cnblogs.com/nizuimeiabc1/p/9397326.html
 * 通过实现ConfirmCallBack接口，消息发送到交换器Exchange后触发回调。
 * 需要配置文件增加此配置； spring.rabbitmq.publisher-confirms = true
 */
@Component
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //bean创建完成会自动调用@PostConstruct修饰的方法
    @PostConstruct
    public void init(){
        //指定confirmCallback
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("--- confirm ---");
        System.out.println("message：" + s);
        System.out.println("--- confirm ---");
    }
}
