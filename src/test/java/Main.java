import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        getJSONAndSerializes();
    }
    private static void getJSONAndSerializes()throws IOException, JSONException {
        //create connection and retrive data
        OkHttpClient client = new OkHttpClient();
        String myAPIKy = "AIzaSyDPncBYn__D0MO2C9oW59CVoD0-d-eqetk";
        String DanielKey = "AIzaSyCu2vjNMuQDCQSB67-2zsJRXkvQYgFSW64";
        String mySearch = "HaNofar%20Street%203,%20Netanya";
        String myURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+mySearch+"&key="+DanielKey;
        System.out.println(myURL);
        Request request = new Request.Builder().url(myURL).build();
        //get OK HTTP response
        Response response = client.newCall(request).execute();
        //convert reposnse to JSON
        JSONObject mainJsonObject = new JSONObject(response.body().string());
        //get Result Json Object
        JSONArray resultJson = mainJsonObject.getJSONArray("results");
        //get first result in the array of the results
        JSONObject mainjson = resultJson.getJSONObject(0);
        JSONObject geometryJson = mainjson.getJSONObject("geometry");
        JSONObject locationJson = geometryJson.getJSONObject("location");
        System.out.println("Latidude is " + locationJson.get("lat") + "Longitude is " + locationJson.get("lng"));
        Gson gson = new Gson();
        Place place = gson.fromJson(geometryJson.toString(),Place.class);
        System.out.println("GSON : Latidue is : " + place.getLat() + "Longitude is :" + place.getLng());
    }
}
