package com.example.mq_producer.直接使用.订阅模式;

import com.example.mq_producer.直接使用.util.ConnectionUtil;
import com.example.mq_producer.直接使用.util.ExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Publish/subscribe
 * 一个生产者发送的消息会被多个消费者获取。
 * 先运行生产者（此类），再运行消费者
 * 将消息交给所有绑定到交换机的队列
 */
public class Fanout_Producer {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) {
        Connection con = null;
        Channel channel = null;
        try {
            //1.建立连接
            con = ConnectionUtil.getConnection();
            //2.创建信道
            channel = con.createChannel();
            // 明exchange，指定类型为fanout
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.FANOUT);
            //4.消息内容
            String content = "注册成功！！";
            //5.向指定的队列发送消息
            channel.basicPublish(EXCHANGE_NAME, "", null, content.getBytes());
            System.out.println(" [生产者] Sent '" + content + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭信道和连接
            try {
                if(channel!=null) channel.close();
                if(con!=null) con.close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
