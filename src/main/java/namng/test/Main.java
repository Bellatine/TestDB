package namng.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import namng.test.httpTest.TestHttpRequest;
import namng.test.mqttTest.MqttRequestTest;
import namng.test.rabbitMQ.ConsumerRabbitMQ;
import namng.test.rabbitMQ.PublisherRabbitMQ;
import namng.test.rabbitMQ.RabbitMQConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.JSONObject;


public class Main {
    private static String log4jConfigFileDir = "etc/log4j.xml";
    //private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) {
        loadConfig();

        Logger logger = LogManager.getLogger(Main.class);
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try (Connection connection = factory.newConnection();
//             Channel channel = connection.createChannel()) {
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            String message = "Hello, RabbitMQ!";
//            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//            System.out.println(" [x] Sent '" + message + "'");
//        }catch (Exception e){
//
//        }
        // <editor-fold desc="Test HTTP">
//        String URL = "https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5&field1=11&field2=22";
//        String URL1 = "https://api.thingspeak.com/channels/1529099/feeds.json?results=2";
//        TestHttpRequest testHttpRequest = new TestHttpRequest();
//        logger.info("Gui du lieu qua API bang url :" +testHttpRequest.httpGetExcample(URL));
//
//
//        String pathJsonFile = c
//        String URL2 = "https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5";
//        logger.info("Gui du lieu qua API bang file Json :" + testHttpRequest.httpGetExcample(URL2,pathJsonFile));
//
//
//        logger.info("Lay du lieu tu API:");
//        String key = "channel";
//        String key1 = "field1";
//        String key2 = "field2";
//        JSONObject jsonData = testHttpRequest.getDataUsingHttpGet(URL1);
//        String field1 = jsonData.getJSONObject(key).getString(key1);
//        String field2 = jsonData.getJSONObject(key).getString(key2);
//        logger.info(key1 + " :" + field1);
//        logger.info(key2 + " :" + field2);
        // </editor-fold>

        // <editor-fold desc="Test MQTT">
//        MqttRequestTest mqttRequestTest = new MqttRequestTest();
//        String broker = "tcp://broker.hivemq.com:1883";
//        String clientId = "namng";
//        String subTopic = "/test/namng1";
//        String pubTopic = "/test/namng1";
//        String contentDir = "etc/data.json";
//        int qos = 1;
//        mqttRequestTest.publishMessage(mqttRequestTest.createConnection(broker,clientId),subTopic,pubTopic,contentDir,qos);
        // </editor-fold>

        // <editor-fold desc="Test MQTT">
        try {
            String RABBITMQ_HOST = "localhost";
            int RABBITMQ_PORT = 15672;
            String RABBITMQ_USERNAME = "guest";
            String RABBITMQ_PASSWORD = "guest";
            String message = "Hello NamNG!";
            String QUEUENAME = "NamngTestRabbitMQ";
            RabbitMQConnection rabbitMQConnection = new RabbitMQConnection();
            Connection connection = rabbitMQConnection.createConnection(RABBITMQ_HOST, RABBITMQ_PORT, RABBITMQ_USERNAME, RABBITMQ_PASSWORD);
            PublisherRabbitMQ publisherRabbitMQ = new PublisherRabbitMQ();
            ConsumerRabbitMQ consumerRabbitMQ = new ConsumerRabbitMQ();
            publisherRabbitMQ.sendData(connection, message, QUEUENAME);
            consumerRabbitMQ.receiveData(connection,QUEUENAME);
        }catch (Exception e){

        }
        // </editor-fold>
    }

    private static void loadConfig(){

        Configurator.initialize(null,log4jConfigFileDir);

    }
}
