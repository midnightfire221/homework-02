
/*
Last Updated: February 11th, 2021
This is an API prototype for getting the latest currency exchange rates.
Sengthida Lorvan
 */

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.*;

public class API extends config {
    // API call for the latest rates with base being Euros.
    public static void getLatestRates () {
        String _baseUrl = "http://data.fixer.io/api/";
        String _version = "1/";
        String _callAction = "latest";
        String _urlString = _baseUrl + _callAction + "?access_key=" + config.API_KEY1;
        URL _url;
        try {
            _url = new URL(_urlString);
            HttpURLConnection connect = (HttpURLConnection) _url.openConnection();
            connect.setRequestMethod("GET");
        // Informs user if there is a connection error along with the HTTP error code.
            int _status = connect.getResponseCode();
            if (_status != 200) {
                System.out.println("Error: Could not get latest exchange rates: " + _status);
            } else {
                //Converting the input stream to text.
                BufferedReader _input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                String _inputLine;
                StringBuffer content = new StringBuffer();
                while ((_inputLine = _input.readLine()) != null) {
                    content.append(_inputLine);
                }
                // Close the connections.
                _input.close();
                connect.disconnect();
                // Printing out the JSON String.
                System.out.println("Output: " + content.toString());
                // Creating an object into a usable JSON object.
                JSONObject _obj = new JSONObject(content.toString());
                double eurToAUDRates = (double) _obj.getJSONObject("rates").opt("AUD");
                // Printing out the conversion rate between Euros and Australian Dollars.
                System.out.println("EUR to AUD: " + eurToAUDRates);
            }
        } catch (Exception _ex) {
            System.out.println("Error: " + _ex);
        }
    }

    public static void getLatestRates2 () {
        // Another API call method for getting the latest exchange rates. Currency base is US Dollars.
        try {
            HttpRequest _request = HttpRequest.newBuilder()
                    .uri(URI.create("https://fixer-fixer-currency-v1.p.rapidapi.com/latest?base=USD&symbols=GBP%2CJPY%2CEUR%2CLAK%2CCNY"))
                    .header("x-rapidapi-key", config.API_KEY2)
                    .header("x-rapidapi-host", "fixer-fixer-currency-v1.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(_request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception _exception) {
            System.out.println("Error: " + _exception);
        }
    }

    // Test of the methods.
    public static void main(String[] args) {
        API.getLatestRates();
        API.getLatestRates2();
    }
}
