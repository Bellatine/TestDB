package namng.test.mqttTest;

import namng.test.Main;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class MqttRequestTest {
    private static final Logger logger = LogManager.getLogger(MqttRequestTest.class);

    public void publishMessage(MqttClient mqttClient, String subTopic, String pubTopic, String contentDir,int qos ){
        try{
            // Đăng ký nhận tin nhắn từ subTopic
            mqttClient.subscribe(subTopic);

            // Đọc bản tin JSON từ file
            JsonObject jsonMessage = readJsonFromFile(contentDir);
            // Chuyển đổi bản tin JSON thành chuỗi và tạo bản tin MQTT
            String jsonString = jsonMessage.toString();
            MqttMessage mqttMessage = new MqttMessage(jsonString.getBytes(StandardCharsets.UTF_8));
            //Đặt lever
            mqttMessage.setQos(qos);

            logger.info("Publishing message " + jsonString);
            //Gửi tin nhắn tới pobTopic
            mqttClient.publish(pubTopic, mqttMessage);
            logger.info("publish success");

            Thread.sleep(5000);

            disconnectConnection(mqttClient);

        }catch (Exception e){
            logger.error(e,e);
        }
    }

    public MqttClient createConnection(String broker, String clientId){
        // Lưu trữ tạm thời
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            // Khởi tạo MqttClient để kết nối tới broker MQTT
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // Đặt session là "clean" (không lưu trữ lại thông tin session)
            connOpts.setCleanSession(true);

            mqttClient.setCallback(new TestMqttCallback()); // Set callback to your custom callback class

            logger.info("Connecting to broker: " + broker);
            // Kết nối tới broker
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
            // Ngắt kết nối từ broker
            mqttClient.disconnect();
            logger.info("Disconnected");
            System.exit(0);
        }catch (Exception e){
            logger.error("Error disconnect connection ", e);
        }
    }
    private static JsonObject readJsonFromFile(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            JsonParser parser = new JsonParser();
            return parser.parse(reader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
