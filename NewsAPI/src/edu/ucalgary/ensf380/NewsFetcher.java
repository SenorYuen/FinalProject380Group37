package edu.ucalgary.ensf380;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.HttpURLConnection;

public class NewsFetcher {
	private List<String> headlineList;

    /**
    * This method will accept command line arguments and fetch information from the newsapi.org api
    *
    * @param city      The city for which to fetch news headlines.
    * @param startDate The start date for fetching news headlines.
    * @param endDate   The end date for fetching news headlines.
    * @param sortOrder The sorting order for news headlines.
    */
    public NewsFetcher(String city, String startDate, String endDate, String sortOrder) {
        String apiKey = "4ac0116bc1aa41a4be6548aa56b6ad7e";

        String finalDestination = "https://newsapi.org/v2/everything?q=" + city + "&from=" + startDate + "&to=" + endDate + "&sortBy=" + sortOrder + "&apiKey=" + apiKey;

        try {
            // Create a URL object from the endpoint string and connect to it
            URL url = new URL(finalDestination);
            HttpURLConnection connectionInstance = (HttpURLConnection) url.openConnection();
            connectionInstance.setRequestMethod("GET");

            // Read the response and store it in a string buffer
            String temporaryParsingLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(connectionInstance.getInputStream()));
            StringBuffer storedResponse = new StringBuffer();
            while ((temporaryParsingLine = in.readLine()) != null) {
                storedResponse.append(temporaryParsingLine);
            }
            in.close();

            // Parse and store the titles in an ArrayList
            headlineList = parseHeadlines(storedResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            headlineList = new ArrayList<>();
        }
    }
    
    /**
    * Parses news titles from a JSON response.
    * @param json The JSON response to parse.
    * @return A list of news titles.
    */
    private List<String> parseHeadlines(String json) {
        List<String> titles = new ArrayList<>();
        String pattern = "\"title\":\"(.*?)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(json);

        while (m.find()) {
            titles.add(m.group(1));
        }
        return titles;
    }

    /**
    * A getter function for the headlines
    * @return The list of all the fetched news headlines.
    */
    public List<String> getHeadlines() {
        return headlineList;
    }

    /**
    * The main method that runs the NewsApi fetching logic.
    *
    * @param args CLI arguments are of city, startDate, endDate, sortOrder.
    */
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java NewsApiClient <city> <startDate> <endDate> <sortOrder>");
            return;
        }

        String city = args[0];
        String startDate = args[1];
        String endDate = args[2];
        String sortOrder = args[3];

        
        //new apiClient(city, startDate, endDate, sortOrder);
        NewsFetcher newsApiClient = new NewsFetcher(city, startDate, endDate, sortOrder);
        // Print the news titles to the console
        for (String title : newsApiClient.getHeadlines()) {
            System.out.println(title);
        }
    }
}

