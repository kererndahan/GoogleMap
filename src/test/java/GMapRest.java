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
    private String searchtxt;
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Helper myHelper = new Helper();
    private static DBActions myDB = new DBActions();



    public  String GetJsonLine() throws IOException, SQLException, ClassNotFoundException {
        String myURL= myDB.getaInfo("URL");
        String mySearch= myDB.getaInfo("mySearch");
        setSearch(mySearch);
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
    public String getSearch (){ return searchtxt;}
    public void setSearch (String search) {this.searchtxt = search;}
    }
