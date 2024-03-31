package namng.test;

import namng.test.httpTest.TestHttpRequest;
import namng.test.mqttTest.MqttRequestTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.JSONObject;


public class Main {
    private static String log4jConfigFileDir = "etc/log4j.xml";
    public static void main(String[] args) {
        loadConfig();

        Logger logger = LogManager.getLogger(Main.class);

        String URL = "https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5&field1=11&field2=22";
        String URL1 = "https://api.thingspeak.com/channels/1529099/feeds.json?results=2";
        TestHttpRequest testHttpRequest = new TestHttpRequest();
        logger.info("Gui du lieu qua API bang url :" +testHttpRequest.httpGetExcample(URL));


        String pathJsonFile = "etc/data.json";
        String URL2 = "https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5";
        logger.info("Gui du lieu qua API bang file Json :" + testHttpRequest.httpGetExcample(URL2,pathJsonFile));


        logger.info("Lay du lieu tu API:");
        String key = "channel";
        String key1 = "field1";
        String key2 = "field2";
        JSONObject jsonData = testHttpRequest.getDataUsingHttpGet(URL1);
        String field1 = jsonData.getJSONObject(key).getString(key1);
        String field2 = jsonData.getJSONObject(key).getString(key2);
        logger.info(key1 + " :" + field1);
        logger.info(key2 + " :" + field2);

        MqttRequestTest mqttRequestTest = new MqttRequestTest();
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "namng";
        String subTopic = "/test/namng";
        String pubTopic = "/test/namng";
        String content = "I'm Giang Nam";
        int qos = 1;
        mqttRequestTest.publishMessage(mqttRequestTest.createConnection(broker,clientId),subTopic,pubTopic,content,qos);

    }

    private static void loadConfig(){

        Configurator.initialize(null,log4jConfigFileDir);

    }
}
