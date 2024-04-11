package namng.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class PublisherRabbitMQ {
    private static final Logger logger = LogManager.getLogger(PublisherRabbitMQ.class);

    public void sendData(Connection connection, String message, String QUEUE_NAME){
        try(Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            logger.info("push message complete: "+ message);
        }catch (Exception e){
            logger.error("publish error: ", e);
        }
    }

    private final static String QUEUE_NAME = "NamNG";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            NamNGDeliverCallBack deliverCallback = new NamNGDeliverCallBack();
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
    }

}
