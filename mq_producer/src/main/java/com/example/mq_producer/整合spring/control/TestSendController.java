package com.example.mq_producer.整合spring.control;

import com.example.mq_producer.整合spring.util.MqName;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMessage")
    public String sendMessage () {
        String message = "发送短信";
        rabbitTemplate.convertAndSend(MqName.EXCHANGE_NAME,
                MqName.ROUTING_MESSAGE, message);
        return message;
    }

}
