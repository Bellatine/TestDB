package namng.test.mqttTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TestMqttCallback implements MqttCallback {
    private static final Logger logger = LogManager.getLogger(TestMqttCallback.class);
    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("Connection lost!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        logger.info("Message received content: " + new String(mqttMessage.getPayload()));
        logger.info("Message received Qos " + mqttMessage.getQos());
        logger.info("Message received topic " + topic);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("Delivery complete! " + iMqttDeliveryToken.isComplete());
    }
}
