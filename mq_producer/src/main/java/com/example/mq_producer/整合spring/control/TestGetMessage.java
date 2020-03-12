package com.example.mq_producer.整合spring.control;

import com.example.mq_producer.整合spring.util.MqName;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TestGetMessage implements MqName {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_MESSAGE, declare = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, ignoreDeclarationExceptions = "true", type = ExchangeTypes.DIRECT),
            key =  {ROUTING_MESSAGE}
    ))
    public void sendMessage(String msg, Channel channel, Message message) {
        try {
            Thread.sleep(10000);
            System.out.println(msg);
            //确认消息已经被消费，队列删除此消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //捕获到异常最好做处理，不要不管而导致消息永远再队列中
            //重新放入队列
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            e.printStackTrace();
        }
    }
}
