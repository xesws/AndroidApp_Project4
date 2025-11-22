/**
 * TavilyClient - Handles communication with Tavily Search API.
 * Makes POST requests to Tavily and parses search results.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TavilyClient {
    private static final String TAVILY_API_URL = "https://api.tavily.com/search";
    private static final String API_KEY = "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";

    public static class SearchResponse {
        public List<Map<String, String>> results;
        public long latencyMs;
        public String error;
    }

    public SearchResponse search(String query) {
        SearchResponse response = new SearchResponse();
        response.results = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(TAVILY_API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("api_key", API_KEY);
            requestBody.addProperty("query", query);
            requestBody.addProperty("search_depth", "basic");
            requestBody.addProperty("max_results", 5);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                JsonObject jsonResponse = JsonParser.parseString(sb.toString()).getAsJsonObject();
                JsonArray resultsArray = jsonResponse.getAsJsonArray("results");

                if (resultsArray != null) {
                    for (int i = 0; i < resultsArray.size(); i++) {
                        JsonObject result = resultsArray.get(i).getAsJsonObject();
                        Map<String, String> resultMap = new HashMap<>();
                        resultMap.put("title", result.has("title") ? result.get("title").getAsString() : "");
                        resultMap.put("url", result.has("url") ? result.get("url").getAsString() : "");
                        resultMap.put("snippet", result.has("content") ? result.get("content").getAsString() : "");
                        response.results.add(resultMap);
                    }
                }
            } else {
                response.error = "Tavily API error: " + responseCode;
            }
        } catch (Exception e) {
            response.error = "Tavily request failed: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            response.latencyMs = System.currentTimeMillis() - startTime;
        }

        return response;
    }
}
