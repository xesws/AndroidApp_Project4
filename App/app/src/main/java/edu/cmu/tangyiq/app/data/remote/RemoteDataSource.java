package edu.cmu.tangyiq.app.data.remote;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.tangyiq.app.data.dto.ApiResponse;
import edu.cmu.tangyiq.app.data.dto.SearchResultDto;
import edu.cmu.tangyiq.app.domain.model.SearchResult;

/**
 * RemoteDataSource - Handles HTTP communication with the web service.
 * Converts DTOs to domain models.
 */
public class RemoteDataSource {
    private static final String BASE_URL = "https://symmetrical-barnacle-4vjrwjwvgj3qggv-8080.app.github.dev/";
    private final Gson gson = new Gson();

    public interface DataSourceCallback {
        void onSuccess(List<SearchResult> results);
        void onError(String message);
    }

    public void fetchResults(String query, DataSourceCallback callback) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                String encodedQuery = URLEncoder.encode(query, "UTF-8");
                URL url = new URL(BASE_URL + "search?query=" + encodedQuery);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    ApiResponse apiResponse = gson.fromJson(response.toString(), ApiResponse.class);
                    if (apiResponse.isSuccess()) {
                        List<SearchResult> results = convertToModel(apiResponse.getResults());
                        callback.onSuccess(results);
                    } else {
                        callback.onError(apiResponse.getError() != null ?
                                apiResponse.getError() : "Unknown error");
                    }
                } else {
                    callback.onError("HTTP Error: " + responseCode);
                }
            } catch (Exception e) {
                callback.onError("Network error: " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    private List<SearchResult> convertToModel(List<SearchResultDto> dtos) {
        List<SearchResult> results = new ArrayList<>();
        if (dtos != null) {
            for (SearchResultDto dto : dtos) {
                results.add(new SearchResult(
                        dto.getTitle(),
                        dto.getUrl(),
                        dto.getSnippet()
                ));
            }
        }
        return results;
    }
}
