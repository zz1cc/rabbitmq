package com.example.mq_producer.直接使用.通配符模式;

import com.example.mq_producer.直接使用.util.ConnectionUtil;
import com.example.mq_producer.直接使用.util.ExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Topics 通配符模式
 * 把消息交给符合routing pattern（路由模式） 的队列
 * 每个消费者监听自己的队列，并且设置带统配符的routingkey,生产者将消息发给broker，
 * 由交换机根据routingkey来转发消息到指定的队列。
 * Routingkey一般都是有一个或者多个单词组成，多个单词之间以“.”分割，例如：inform.sms
 * #：匹配一个或多个词
 * *：匹配不多不少恰好1个词
 * 举例：
 * audit.#：能够匹配audit.irs.corporate 或者 audit.irs
 * audit.*：只能匹配audit.irs
 */
public class Topics_Producer {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) {
        Connection con = null;
        Channel channel = null;
        try {
            //1.建立连接
            con = ConnectionUtil.getConnection();
            //2.创建信道
            channel = con.createChannel();
            // 明exchange，指定类型为fanout
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.TOPIC);
            //4.消息内容
            String content = "注册成功！！";
            //5.向指定的队列发送消息
            String routingKey = "";
            //可以匹配短信、邮件消费者
            //routingKey = "rabbit.email.message";
            //只能匹配短信消费者
            //routingKey = "rabbit.send.message";
            //只能匹配邮件消费者
            routingKey = "send.email.rabbit";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, content.getBytes());
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
