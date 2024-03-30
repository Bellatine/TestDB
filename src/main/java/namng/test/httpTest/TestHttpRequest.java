package namng.test.httpTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static java.net.HttpURLConnection.HTTP_OK;

public class TestHttpRequest {

    //Cách 1. Các trường field1, field2 được đóng gói trong url (urlencoded):
    public String httpGetExcample(String URL){
        try {
            //System.out.println(URL.equals("https://api.thingspeak.com/update?api_key=T7H40F0X82VGW7L5&field1=20&field2=33") ? "Yes" : "No");
            //tạo url
            URL url = new URL(URL);
            //tạo kết nối http
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //cấu hình loại request
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            String response = null ;
            if(responseCode==HTTP_OK) {
                //response trả về
                 response = parseResponseToString(connection.getInputStream());
                //System.out.println("Response "+ response);
            }else {
                return "Fail to connection "+responseCode;
                //System.out.println("Fail to connection "+responseCode);
            }
            //ngắt kết nối
            connection.disconnect();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String httpGetExcample(String URL, String jsonFileDir){
        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject params = null;
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFileDir))){
                //đọc file json, parse thành json object
                params = (JSONObject) jsonParser.parse(bufferedReader);
            }

            //tạo đường dẫn
            StringBuilder urlBuilder = new StringBuilder(URL);
            if (!params.isEmpty()) {
                Iterator<String> keys = params.keySet().iterator();
                while (keys.hasNext()) {
                    //thêm các trường thông tin vào url
                    String key = keys.next();
                    String value = params.get(key).toString();
                    urlBuilder.append("&").append(key).append("=").append(value);
                }
            }


            //tiến hành gửi request
            return httpGetExcample(urlBuilder.toString());

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Cách 2. Các trường field1, field2 được đóng gói trong body request bằng json.

    private String parseResponseToString(InputStream io){
        try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(io));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Print the response
        return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public org.json.JSONObject getDataUsingHttpGet(String URL){
        String stringJsonData = httpGetExcample(URL);
        org.json.JSONObject jsonData = new org.json.JSONObject(stringJsonData);
        return jsonData;
    }
}
