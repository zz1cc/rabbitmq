package com.example.mq_producer.直接使用.简单模式;

import com.example.mq_producer.直接使用.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一个生产者，一个消费者
 */
public class Simple_Producer {
    private static final String QUEUE_NAME = "test_queue_simple";

    public static void main(String[] args) {
        Connection con = null;
        Channel channel = null;
        try {
            //1.建立连接
            con = ConnectionUtil.getConnection();
            //2.创建信道
            channel = con.createChannel();
            //表示队列给消费者发消息时只能取一个，消费完毕才能取下一个，也可以设多个
            //手动ack才会生效
            channel.basicQos(1);
            //3.创建队列
            /**
             * 参数说明
             * 1.队列名称
             * 2.是否持久化，如果持久化，mq重启后队列还在
             * 3.是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,
             * 如果将此参数设置true可用于临时队列的创建
             * 4.自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true
             * 就可以实现临时队列（队列不用了就自动删除）
             * 5.参数，可以设置一个队列的扩展参数，比如：可设置存活时间
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //4.消息内容
            String content = "I LOVE LMM";
            //5.向指定的队列发送消息
            /**
             * 参数说明
             * 1.交换机，不指定会使用mq的默认交换机(设置为"")
             * 2.routing key 路由key 交换机由key将消息转发到指定的队列，如果使用默认的交换机，
             * routing key设置为队列的名称
             * 3.消息属性
             * 4.消息内容
             */
            for (int i=0; i<10; i++){
                channel.basicPublish("", QUEUE_NAME, null, (content + (i+1)).getBytes());
                System.out.println(" [x] Sent '" + content + "'");
            }
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
