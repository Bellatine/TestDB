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

    public void publishMessage(MqttClient mqttClient, String subTopic, String pubTopic, String content,int qos ){
        try{

            mqttClient.subscribe(subTopic);

            MqttMessage mqttMessage = new MqttMessage(content.getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(qos);

            logger.info("Publishing message " + content);
            mqttClient.publish(pubTopic, mqttMessage);
            logger.info("publish success");

            Thread.sleep(5000);

            disconnectConnection(mqttClient);

        }catch (Exception e){
            logger.error(e,e);
        }
    }

    public MqttClient createConnection(String broker, String clientId){
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            mqttClient.setCallback(new TestMqttCallback()); // Set callback to your custom callback class

            logger.info("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            logger.info("Connected");

            return mqttClient;
        } catch (Exception e) {
            logger.error("Error create connection ", e);
            return null;
        }
    }

    public void disconnectConnection(MqttClient mqttClient){
        try{
            mqttClient.disconnect();
            logger.info("Disconnected");
            System.exit(0);
        }catch (Exception e){
            logger.error("Error disconnect connection ", e);
        }
    }

}
