/**
 * TavilyDemo.java
 *
 * A simple Java application that demonstrates fetching data from the Tavily Search API.
 * This program makes a search request and displays the results.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TavilyDemo {

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.tavily.com/search";

    public static void main(String[] args) {
        try {
            // Create the search query
            String searchQuery = "Carnegie Mellon University";

            System.out.println("=== Tavily API Demo ===");
            System.out.println("Searching for: " + searchQuery);
            System.out.println();

            // Make the API request
            String response = makeSearchRequest(searchQuery);

            // Parse and display the results
            parseAndDisplayResults(response);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String makeSearchRequest(String query) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Create JSON request body
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("api_key", API_KEY);
        requestBody.addProperty("query", query);
        requestBody.addProperty("search_depth", "basic");
        requestBody.addProperty("max_results", 5);

        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    private static void parseAndDisplayResults(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(jsonResponse, JsonObject.class);

        // Display the answer if available
        if (response.has("answer") && !response.get("answer").isJsonNull()) {
            System.out.println("Answer: " + response.get("answer").getAsString());
            System.out.println();
        }

        // Display search results
        if (response.has("results")) {
            JsonArray results = response.getAsJsonArray("results");
            System.out.println("Search Results:");
            System.out.println("---------------");

            for (int i = 0; i < results.size(); i++) {
                JsonObject result = results.get(i).getAsJsonObject();
                String title = result.get("title").getAsString();
                String resultUrl = result.get("url").getAsString();

                System.out.println((i + 1) + ". " + title);
                System.out.println("   URL: " + resultUrl);

                // Display snippet if available
                if (result.has("content") && !result.get("content").isJsonNull()) {
                    String content = result.get("content").getAsString();
                    // Truncate long content
                    if (content.length() > 150) {
                        content = content.substring(0, 150) + "...";
                    }
                    System.out.println("   Snippet: " + content);
                }
                System.out.println();
            }
        }

        System.out.println("=== Demo Complete ===");
    }
}
