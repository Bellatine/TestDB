package namng.test.mqttTest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttRequestTest {

    public void createConnection(){
        String broker = "broker.hivemq.com:1883";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");

            String topic = "topic";
            int qos = 2;

            mqttClient.subscribe(topic, qos);

            mqttClient.setCallback(new TestMqttCallback()); // Set callback to your custom callback class

            String content = "Hello, MQTT!";
//            mqttClient.publish(topic,
//                    content.getBytes(),
//                    qos);

            // Wait for some time to receive messages
            Thread.sleep(5000);

            mqttClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            me.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
