import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CurrencyAPI {

    public static void main(String[] args) throws IOException, JSONException {

        // use OKHttp client to create the connection and retrieve data

        OkHttpClient client = new OkHttpClient();
        String myAPIKy = "AIzaSyDPncBYn__D0MO2C9oW59CVoD0-d-eqetk";
        String mySearch = "HaNofar Street 3, Netanya";
        String myURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+mySearch+"&key="+myAPIKy;
        Request request = new Request.Builder()
                .url(myURL)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        parse2(jsonData);

    }
    public static void parse(String jsonLine) {
        JsonElement jelement = new JsonParser().parse(jsonLine);
        JsonObject jobject = jelement.getAsJsonObject();
        //result is an array and we want first result from array of result
        JsonArray jarray = jobject.getAsJsonArray("results");
        jobject = jarray.get(0).getAsJsonObject();
        jobject = jobject.getAsJsonObject("geometry");
        jobject = jobject.getAsJsonObject("location");
        String lat = jobject.get("lat").getAsString();
        String lng = jobject.get("lng").getAsString();
        System.out.println(lat);
        System.out.println(lng);


    }
    public static void parse2(String jsonLine) {
        Gson gson = new Gson();
        ResponseMap response = gson.fromJson(jsonLine, ResponseMap.class);

        System.out.println("lat is: " + response.getlat());


    }

}