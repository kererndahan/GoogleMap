import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.SQLException;

public class GMapRest {
    private Double lat;
    private Double lng;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Helper myHelper = new Helper();
    private static DBActions myDB = new DBActions();


    public static void main(String[] args) throws IOException, JSONException, SQLException, ClassNotFoundException {
        // use OKHttp client to create the connection and retrieve data
        /*OkHttpClient client = new OkHttpClient();
        String myAPIKy = "AIzaSyDPncBYn__D0MO2C9oW59CVoD0-d-eqetk";
        String DanielKey = "AIzaSyCu2vjNMuQDCQSB67-2zsJRXkvQYgFSW64";
        String mySearch = "HaNofar Street 3, Netanya";
        String myURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+mySearch+"&key="+DanielKey;
        Request request = new Request.Builder()
                .url(myURL)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        parse2(jsonData);
        parse(jsonData);*/
        //String myJson = GetJsonLine();
        //parse(myJson);
    }
    public  String GetJsonLine() throws IOException, SQLException, ClassNotFoundException {
        String myURL= myDB.getaInfo("URL");
        String mySearch= myDB.getaInfo("mySearch");
        String myAPI= myDB.getaInfo("api");

        OkHttpClient client = new OkHttpClient();
        String URL = myURL+"?query="+mySearch+"&key="+myAPI;
        Request request = new Request.Builder()
                .url(URL)
                .build();
        Response response = client.newCall(request).execute();
        String jsonline = response.body().string();
        System.out.println(jsonline);
        return jsonline;
    }
    public  void parse(String jsonLine) {
        JsonElement jelement = new JsonParser().parse(jsonLine);
        JsonObject jobject = jelement.getAsJsonObject();
        //result is an array and we want first result from array of result
        JsonArray jarray = jobject.getAsJsonArray("results");
        jobject = jarray.get(0).getAsJsonObject();
        jobject = jobject.getAsJsonObject("geometry");
        jobject = jobject.getAsJsonObject("location");
        Double lat = jobject.get("lat").getAsDouble();
        Double lng = jobject.get("lng").getAsDouble();
        System.out.println(lat);
        System.out.println(lng);
        setLat(lat);
        setLng (lng);
    }

    public static void parse2(String jsonLine) {
        Gson gson = new Gson();
        ResponseMap response = gson.fromJson(jsonLine, ResponseMap.class);
        System.out.println("lat is: " + response.getlat());
        System.out.println("lng is: " + response.getlng());
    }
    public double getLat (){ return lat;}
    public void setLat (double lat) {this.lat = lat;}
    public double getLng (){ return lng;}
    public void setLng (double lng) {this.lng = lng;}
    }
