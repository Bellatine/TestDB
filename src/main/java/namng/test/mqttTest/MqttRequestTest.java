package namng.test.mqttTest;

import namng.test.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class MqttRequestTest {
    private static final Logger logger = LogManager.getLogger(MqttRequestTest.class);

    public void createConnection(){
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "ID_OF_CLIENT";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            mqttClient.setCallback(new TestMqttCallback()); // Set callback to your custom callback class

            logger.info("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            logger.info("Connected");

            String subTopic = " ";
            String pubTopic = "/ktmt/iot";
            String content = "I'm Giang Nam";
            int qos = 1;

            mqttClient.subscribe(subTopic);

            MqttMessage mqttMessage = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(qos);

            logger.info("Publishing message " + content);
            mqttClient.publish(pubTopic, mqttMessage);
            logger.info("publish success");

//            mqttClient.publish(topic,
//                    content.getBytes(),
//                    qos);

            // Wait for some time to receive messages
            Thread.sleep(5000);

            mqttClient.disconnect();
            logger.info("Disconnected");
            System.exit(0);
        } catch (Exception e) {
            logger.error(e,e);
        }
    }

}
