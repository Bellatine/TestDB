package namng.test;

import namng.test.httpTest.TestHttpRequest;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {
        String URL = "https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5&field1=11&field2=22";
        String URL1 = "https://api.thingspeak.com/channels/1529099/feeds.json?results=2";
        TestHttpRequest testHttpRequest = new TestHttpRequest();
        System.out.println("Gui du lieu qua API bang url :" +testHttpRequest.httpGetExcample(URL));


        String pathJsonFile = "etc/data.json";
        String URL2 = "https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5";
        System.out.println("Gui du lieu qua API bang file Json :" + testHttpRequest.httpGetExcample(URL2,pathJsonFile));


        System.out.println("Lay du lieu tu API:");
        String key = "channel";
        String key1 = "field1";
        String key2 = "field2";
        JSONObject jsonData = testHttpRequest.getDataUsingHttpGet(URL1);
        String field1 = jsonData.getJSONObject(key).getString(key1);
        String field2 = jsonData.getJSONObject(key).getString(key2);
        System.out.println(key1 + " :" + field1);
        System.out.println(key2 + " :" + field2);

    }
}
