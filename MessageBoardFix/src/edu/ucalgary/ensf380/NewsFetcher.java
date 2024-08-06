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
    * @author Adam Yuen <a href="mailto:adam.yuen@ucalgary.ca">adam.yuen@ucalgary.ca</a>
    * @version 1.1
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
    * Included are some test cases that will determine if everything works.
    * @param args CLI arguments are of city, startDate, endDate, sortOrder.
    */
    public static void main(String[] args) {
        //Test cases that emulate and autmate the process of adding things in by command line. 
        String[] testArgs1 = {"Calgary", "2024-08-01", "2024-08-02", "popularity"};
        String[] testArgs2 = {"Toronto", "2024-08-01", "2024-08-02", "relevancy"};
        String[] testArgs3 = {"Vancouver", "2024-08-01", "2024-08-02", "publishedAt"};

        System.out.println("Test Case 1: Calgary News");
        NewsFetcher test1 = new NewsFetcher(testArgs1[0], testArgs1[1], testArgs1[2], testArgs1[3]);
        for (String title : test1.getHeadlines()) {
            System.out.println(title);
        }

        System.out.println("\n\nTest Case 2: Toronto News");
        NewsFetcher test2 = new NewsFetcher(testArgs2[0], testArgs2[1], testArgs2[2], testArgs2[3]);
        for (String title : test2.getHeadlines()) {
            System.out.println(title);
        }

        System.out.println("\n\nTest Case 3: Vancouver News");
        NewsFetcher test3 = new NewsFetcher(testArgs3[0], testArgs3[1], testArgs3[2], testArgs3[3]);
        for (String title : test3.getHeadlines()) {
            System.out.println(title);
        }
    }

}

