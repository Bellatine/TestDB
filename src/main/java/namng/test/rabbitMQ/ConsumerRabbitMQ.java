package namng.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

public class ConsumerRabbitMQ {
    private static final Logger logger = LogManager.getLogger(ConsumerRabbitMQ.class);

    public void receiveData(Connection connection, String QUEUE_NAME){
        try(Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            NamNGDeliverCallBack deliverCallBack = new NamNGDeliverCallBack();
            logger.info("Waitting for message!");
            channel.basicConsume(QUEUE_NAME,true,deliverCallBack,consumerTag -> { });
        }catch (Exception e){
            logger.error("consumer error: ", e);
        }
    }

}
