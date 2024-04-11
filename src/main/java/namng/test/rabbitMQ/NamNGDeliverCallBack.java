package namng.test.rabbitMQ;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class NamNGDeliverCallBack implements DeliverCallback {
    private static final Logger logger = LogManager.getLogger(NamNGDeliverCallBack.class);

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), "UTF-8");
        logger.info("- s0s - Receive: ," + message + "'");
    }
}
