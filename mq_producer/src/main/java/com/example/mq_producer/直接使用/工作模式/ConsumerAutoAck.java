package com.example.mq_producer.直接使用.工作模式;

import com.example.mq_producer.直接使用.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费消息
 * 自动ack机制，自己的消费逻辑出现异常，消息也会被消费掉,队列会清除此消息
 */
public class ConsumerAutoAck {
    private static final String QUEUE_NAME = "test_queue_work";
    public static void main(String[] args) {
        try {
            //1.建立连接
            Connection con = ConnectionUtil.getConnection();
            //2.创建信道
            Channel channel = con.createChannel();
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

            //实现消费方法
            DefaultConsumer consumer = new DefaultConsumer(channel){
                //监听方法，有消息被发送到此消费者会自动调用

                /**
                 * @param consumerTag 消费者标签，用来标识消费者的，在监听队列时设置channel.basicConsume
                 * @param envelope 信封，通过envelope
                 * @param properties 消息属性
                 * @param body 消息内容
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        // 模拟业务异常的手动ack
                        // int i = 1/0;
                        //此消费者完成消费时间慢，模拟轮询消息模式
                        Thread.sleep(1000);
                        System.out.println(new String(body, "utf-8"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            //监听队列
            /**
             * 参数明细：
             * 1、queue 队列名称
             * 2、autoAck 自动回复，当消费者接收到消息后要告诉mq消息已接收，如果将此参数设置为tru表示会自动回复mq，如果设置为false要通过编程实现回复
             * 3、callback，消费方法，当消费者接收到消息要执行的方法
             */
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
