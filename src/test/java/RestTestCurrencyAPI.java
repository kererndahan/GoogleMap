import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class RestTestCurrencyAPI {
    private static final String USD_ILS_CURRENCY_NAME = "USD_ILS";
    private static final String USD_ILS_CURRENCY_END_POINT = "https://free.currencyconverterapi.com/api/v6/convert?q=USD_ILS&compact=y&apiKey=ea0f2330356269b02cac";
    public static void main(String[] args) {
        WelcomeUser();
        //getCurrency();
        ShowResult();
        quit();
    }

    private static void WelcomeUser() {
        System.out.println("Wellcome to Currencly convertor!");
        System.out.println("Plese Enter an amount of shekels to convert");
    }

    private static void ShowResult() {
        DecimalFormat numberformat = new DecimalFormat(".00");
        System.out.println("Rsult is: " + String.valueOf(numberformat.format(getUSerInput())) + " Shekels");
    }

    private static double getUSerInput() {
        Scanner input = new Scanner(System.in);
        double amout = 0.0;
        try {
            amout = input.nextDouble() * getCurrency();

        } catch (Exception e) {
            System.out.println("Wrong input!");
            getUSerInput();
        }
        return amout;
    }

    private static double getCurrency() {
        double value = 0.0;
        OkHttpClient client = new OkHttpClient();
        Request requet = new Request.Builder().url(USD_ILS_CURRENCY_END_POINT).build();
        try {
            Response response = client.newCall(requet).execute();
            JSONObject mainJsonObject = new JSONObject(response.body().string()).getJSONObject(USD_ILS_CURRENCY_NAME);
            USD_ILS result = new Gson().fromJson(mainJsonObject.toString(),USD_ILS.class);
            return result.getVal();

        } catch (IOException e) {
            System.out.println("JSON parse to double failed!");
        }
        return value;
    }
    private static void quit(){
        System.out.println("Thank you for using currency convertor!");

    }
}
