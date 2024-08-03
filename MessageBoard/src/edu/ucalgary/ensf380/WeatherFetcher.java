package edu.ucalgary.ensf380;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherFetcher {

    private static final Logger log = Logger.getLogger(WeatherFetcher.class.getName());
    
    private static String condition;
    private static String temperature;
    private static String wind;
    private static String humidity;
    private static String rain;

    public String getCondition() {
        return condition;
    }
    
    public String getTemperature() {
        return temperature;
    }
    
    public String getWind() {
        return wind;
    }
    
    public String getHumidity() {
        return humidity;
    }
    
    public String getRain() {
        return rain;
    }
    

    public WeatherFetcher(String city) {
        // Store the desired city and call the fetch function.
        String targetCity = city;
        String weatherInfo = retrieveWeatherData(targetCity);

        // Make sure the fetched data actually exists - change later when putting it in the GUI
        if (weatherInfo != null) {
            log.log(Level.INFO, "Fetched weather data:\n{0}", weatherInfo);
            analyzeAndShowWeatherData(weatherInfo);
        } else {
            log.log(Level.SEVERE, "Fetch failed");
        }
    }

    public static String retrieveWeatherData(String city) {
        // Format the fetched data in the requested way
        String apiEndpoint = "https://wttr.in/" + city + "?format=%C+%t+%w+%h+%p";
        try {
            // Create the URL endpoint and send a GET request to the site.
            URL urlObj = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");

            // Determine the HTTP response code for error checking.
            int httpResponse = conn.getResponseCode();
            log.log(Level.INFO, "Response Code: {0}", httpResponse);

            // If the response is 200, it means the fetch was successful.
            if (httpResponse == HttpURLConnection.HTTP_OK) {
                // For efficiency, network latency, smoothing data flow, and allowing for error handling.
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String readLine;
                StringBuilder response = new StringBuilder();

                // Read all of the API information by lines.
                while ((readLine = br.readLine()) != null) {
                    response.append(readLine);
                }

                br.close();
                conn.disconnect();

                return response.toString();
            } else {
                log.log(Level.SEVERE, "Unexpected response code: {0}", httpResponse);
                return null;
            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error fetching weather data", ex);
            return null;
        }
    }

    public static void analyzeAndShowWeatherData(String data) {
        try {
            /*  EXPLANATION OF THE REGULAR EXPRESSION FOR PARSING THE API DATA:
             *  (\\w+): obtains one or more alphanumeric characters. Places it in a capturing group.
             *      this one will capture the weather condition in the parsed data (Sunny)
             *  (\\s+): This capture group will capture one or more whitespace characters.
             *  ([+-]?\\d+°C): this will capture an optional +- that would precede the temperature value.
             *      \\d+ will match at least one digit
             *      °C will find a physical match (we will be limited to Celsius with this one)
             *  (\\s+): another capture group
             *  (\\d+%): this will capture the percent humidity (one or more numbers, and a percent (direct match))
             *  (\\s+): another capture group
             *  (\\d+\\.\\d+mm): this will capture the precipitation value.
             *      \\d+ for one or more digits
             *      \\. will match a dot for the decimal point.
             *      mm will literally match mm for the precipitation.
             */
            String pattern = "(\\w+)\\s+([+-]?\\d+°C)\\s+(\\S+\\d+km/h)\\s+(\\d+%)\\s+(\\d+\\.\\d+mm)";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(data);

            // Separate all of the captured things into specific strings.
            if (matcher.find()) {
                condition = matcher.group(1);
                temperature = matcher.group(2);
                wind = matcher.group(3);
                humidity = matcher.group(4);
                rain = matcher.group(5);
            } else {
                throw new IllegalArgumentException("Weather data is not formatted properly.");
            }
        } catch (Exception e) {
            Logger.getLogger(WeatherFetcher.class.getName()).log(Level.SEVERE, "Failed to parse weather data.", e);
        }
    }
}
