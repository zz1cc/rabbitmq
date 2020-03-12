package com.example.mq_producer.整合spring.rabbitConfig;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 通过实现ReturnCallback接口，如果消息从交换器发送到对应队列失败时触发
 * （比如根据发送消息时指定的routingKey找不到队列时会触发）
 * 需要配置文件增加此配置； spring.rabbitmq.publisher-returns = true
 */
@Component
public class RabbitReturnCallback implements RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //bean创建完成会自动调用@PostConstruct修饰的方法
    @PostConstruct
    public void init(){
        //指定returnCallback
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("--- return ---");
        System.out.println("message：" + s);
        System.out.println("message：" + s1);
        System.out.println("message：" + s2);
        System.out.println("--- return ---");
    }
}
