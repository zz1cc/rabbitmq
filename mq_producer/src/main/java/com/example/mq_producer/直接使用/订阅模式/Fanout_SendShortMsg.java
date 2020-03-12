package com.example.mq_producer.直接使用.订阅模式;

import com.example.mq_producer.直接使用.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Fanout_SendShortMsg {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    private static final String QUEUE_MESSAGE = "exchange_queue_message";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.建立连接
        Connection con = ConnectionUtil.getConnection();
        //2.创建信道
        Channel channel = con.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_MESSAGE, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_MESSAGE, EXCHANGE_NAME, "");
        //队列消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("短信内容" + new String(body, "utf-8"));
            }
        };
        // 监听队列，自动返回完成
        channel.basicConsume(QUEUE_MESSAGE, true, consumer);
    }
}
