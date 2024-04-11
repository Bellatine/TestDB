package namng.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnection {
    private static final Logger logger = LogManager.getLogger(RabbitMQConnection.class);


    public Connection createConnection(String RABBITMQ_HOST, int RABBITMQ_PORT, String RABBITMQ_USERNAME, String RABBITMQ_PASSWORD) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RABBITMQ_HOST);
            //factory.setPort(RABBITMQ_PORT);
            //factory.setUsername(RABBITMQ_USERNAME);
            //factory.setPassword(RABBITMQ_PASSWORD);
            return factory.newConnection();
        }catch (Exception e){
            logger.error("Error create coonnection ", e);
            return null;
        }
    }
}

