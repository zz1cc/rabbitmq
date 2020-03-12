package com.example.mq_producer.直接使用.路由模式;

import com.example.mq_producer.直接使用.util.ConnectionUtil;
import com.example.mq_producer.直接使用.util.ExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Routing 路由模型
 * 发送消息到交换机并且要指定路由key ，消费者将队列绑定到交换机时需要指定路由key。
 * 生产者，向Exchange发送消息，发送消息时，会指定一个routing key。
 * Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列
 */
public class Direct_Producer {
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) {
        Connection con = null;
        Channel channel = null;
        try {
            //1.建立连接
            con = ConnectionUtil.getConnection();
            //2.创建信道
            channel = con.createChannel();
            // 明exchange，指定类型为fanout
            /**
             * Fanout：广播，将消息交给所有绑定到交换机的队列
             * Direct：定向，把消息交给符合指定routing key 的队列
             * Topic：通配符，把消息交给符合routing pattern（路由模式） 的队列
             * Header：header模式与routing不同的地方在于，header模式取消routingkey，
             * 使用header中的 key/value（键值对）匹配队列。
             * Header模式不展开了，感兴趣可以参考这篇文章
             * https://blog.csdn.net/zhu_tianwei/article/details/40923131
             */
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.DIRECT);
            //4.消息内容
            String content = "注册成功！！";
            //5.向指定的队列发送消息
            //routingkey 为第二个参数
            channel.basicPublish(EXCHANGE_NAME, "sms", null, content.getBytes());
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
