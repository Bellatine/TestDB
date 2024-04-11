package namng.test.mqttTest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        // Nhận tin nhắn và xử lý
        String json = new String(mqttMessage.getPayload());
        logger.info("Received message: " + json);

        // Phân tích tin nhắn JSON và in ra từng trường dữ liệu
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        for (String key : jsonObject.keySet()) {
            JsonElement value = jsonObject.get(key);
            logger.info("Field " + key + ": " + value);
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("Delivery complete! " + iMqttDeliveryToken.isComplete());
    }
}
